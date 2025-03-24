package com.vorono4ka.renderer.impl.swf.objects;

import com.vorono4ka.swf.*;
import com.vorono4ka.swf.exceptions.UnableToFindObjectException;
import com.vorono4ka.swf.movieclips.*;

import java.util.List;

public class MovieClip extends Sprite {
    private String exportName;
    private float frameTime;
    private int fps;
    private float msPerFrame;
    private DisplayObject[] timelineChildren;
    private String[] timelineChildrenNames;
    private List<MovieClipFrame> frames;
    private ScMatrixBank matrixBank;
    private int currentFrame;
    private int loopFrame;

    public MovieClip() {
        this.currentFrame = -1;
        this.loopFrame = -1;
    }

    public static MovieClip createMovieClip(MovieClipOriginal original, SupercellSWF swf) throws UnableToFindObjectException {
        original.createTimelineChildren(swf);

        MovieClip movieClip = new MovieClip();
        movieClip.id = original.getId();
        movieClip.matrixBank = swf.getMatrixBank(original.getMatrixBankIndex());

        List<MovieClipChild> clipChildren = original.getChildren();
        DisplayObjectOriginal[] timelineChildrenOriginal = original.getTimelineChildren();
        DisplayObject[] timelineChildren = new DisplayObject[clipChildren.size()];
        for (int i = 0; i < timelineChildren.length; i++) {
            DisplayObjectOriginal child = timelineChildrenOriginal[i];
            DisplayObject displayObject = DisplayObjectFactory.createFromOriginal(child, swf, original.getScalingGrid());
            displayObject.setVisibleRecursive((clipChildren.get(i).blend() & 64) == 0);
            displayObject.setInteractiveRecursive(true);

            timelineChildren[i] = displayObject;
        }

        movieClip.timelineChildren = timelineChildren;
        movieClip.timelineChildrenNames = clipChildren.stream().map(MovieClipChild::name).toArray(String[]::new);
        movieClip.frames = original.getFrames();
        movieClip.setFps(original.getFps());
        movieClip.exportName = original.getExportName();
        movieClip.setFrame(0);

        return movieClip;
    }

    @Override
    public boolean render(Matrix2x3 matrix, ColorTransform colorTransform, int a4, float deltaTime) {
        if (deltaTime <= 0.0f) {
            return super.render(matrix, colorTransform, a4, deltaTime);
        }

        if (this.frameTime >= this.msPerFrame) {
            int framesPassed = (int) (this.frameTime / this.msPerFrame);
            this.frameTime -= (this.msPerFrame * framesPassed);

            int nextFrame;
            if (this.state == MovieClipState.PLAYING_ANY_DIRECTION) {
                if (this.frameSkippingType != 0) {
                    if (this.loopFrame >= this.currentFrame) {
                        nextFrame = this.currentFrame + framesPassed;
                        if (nextFrame > this.loopFrame) {
                            nextFrame = this.loopFrame;
                        }
                    } else {
                        nextFrame = this.currentFrame - framesPassed;
                        if (nextFrame < this.loopFrame) {
                            nextFrame = this.loopFrame;
                        }
                    }
                } else {
                    int framesSkipped = 1;
                    if (this.loopFrame < this.currentFrame)
                        framesSkipped = this.frames.size() - 1;
                    nextFrame = this.currentFrame + framesSkipped;
                }
            } else {
                if (this.frameSkippingType != 0) {
                    nextFrame = this.currentFrame + framesPassed;
                    if (this.currentFrame < this.loopFrame && nextFrame > this.loopFrame) {
                        nextFrame = this.loopFrame;
                    }
                } else {
                    nextFrame = this.currentFrame + 1;
                }
            }

            this.setFrame(nextFrame % this.frames.size());
        }

        if (this.frameSkippingType == 2) {
            this.interpolateFrames();
        }

        if (this.state == MovieClipState.PLAYING || this.state == MovieClipState.PLAYING_ANY_DIRECTION) {
            this.frameTime += deltaTime;
        }

        return super.render(matrix, colorTransform, a4, deltaTime);
    }

    @Override
    public boolean collisionRender(Matrix2x3 matrix) {
        return false;
    }

    @Override
    public boolean isMovieClip() {
        return true;
    }

    public void setFrame(int index) {
        if (this.loopFrame == index) {
            this.setState(MovieClipState.STOPPED);
        }

        if (this.currentFrame == index) return;
        this.currentFrame = index;

        MovieClipFrame frame = this.frames.get(index);
        int childIndex = 0;

        for (MovieClipFrameElement element : frame.getElements()) {
            DisplayObject child = this.timelineChildren[element.childIndex()];
            if (child == null) continue;

            int matrixIndex = element.matrixIndex();
            if (matrixIndex != 0xFFFF) {
                Matrix2x3 matrix = this.matrixBank.getMatrix(matrixIndex);
                child.setMatrix(new Matrix2x3(matrix));
            } else {
                child.setMatrix(new Matrix2x3());
            }

            int colorTransformIndex = element.colorTransformIndex();
            if (colorTransformIndex != 0xFFFF) {
                ColorTransform colorTransform = this.matrixBank.getColorTransform(colorTransformIndex);
                child.setColorTransform(new ColorTransform(colorTransform));
            } else {
                child.setColorTransform(new ColorTransform());
            }

            this.addChildAt(child, childIndex++);
        }

        int childrenCount = this.getChildrenCount();
        while (childrenCount > childIndex) {
            this.removeChildAt(--childrenCount);
        }
    }

    public void setState(MovieClipState state) {
        if (this.state == state) return;

        this.state = state;
        this.frameTime = 0;
    }

    public void reset() {
        this.loopFrame = -1;
        this.setFrame(0);
        this.setState(MovieClipState.PLAYING);

        for (DisplayObject child : this.children) {
            if (child.isMovieClip()) {
                ((MovieClip) child).reset();
            }
        }
    }

    public void resetTimelinePositionRecursive() {
        this.loopFrame = -1;
        this.setFrame(0);
        this.setState(MovieClipState.PLAYING);

        for (DisplayObject child : this.timelineChildren) {
            if (child.isMovieClip()) {
                ((MovieClip) child).resetTimelinePositionRecursive();
            }
        }
    }

    public void gotoAbsoluteTimeRecursive(float time) {  // time in milliseconds
        int passedFrames = (int) (time / this.msPerFrame);
        int frameIndex = passedFrames % this.frames.size();
        this.gotoAndPlayFrameIndex(frameIndex, -1);

        for (DisplayObject child : this.timelineChildren) {
            if (child.isMovieClip()) {
                ((MovieClip) child).gotoAbsoluteTimeRecursive(time);
            }
        }
    }

    public void gotoAndStop(String frameLabel) {
        int frameIndex = getFrameIndex(frameLabel);

        this.gotoAndStopFrameIndex(frameIndex);
    }

    public void gotoAndPlay(String frameLabel) {
        this.gotoAndPlay(frameLabel, null);
    }

    public void gotoAndPlay(String frameLabel, String loopFrameLabel) {
        int frameIndex = getFrameIndex(frameLabel);
        int loopFrameIndex = getFrameIndex(loopFrameLabel);

        this.gotoAndPlayFrameIndex(frameIndex, loopFrameIndex);
    }

    public void gotoAndStopFrameIndex(int frame) {
        this.gotoAndPlayFrameIndex(frame, -1, MovieClipState.STOPPED);
    }

    public void gotoAndPlayFrameIndex(int frame, int loopFrame) {
        this.gotoAndPlayFrameIndex(frame, loopFrame, MovieClipState.PLAYING);
    }

    public void gotoAndPlayToFrameIndexAnyDirection(int frame, int loopFrame) {
        this.gotoAndPlayFrameIndex(frame, loopFrame, MovieClipState.PLAYING_ANY_DIRECTION);
    }

    public void playToFrameIndexAnyDirection(int loopFrame) {
        this.gotoAndPlayFrameIndex(this.currentFrame, loopFrame, MovieClipState.PLAYING_ANY_DIRECTION);
    }

    public void gotoAndPlayFrameIndex(int frame, int loopFrame, MovieClipState state) {
        this.loopFrame = loopFrame;
        if (frame >= 0 && this.frames.size() > frame) {
            this.setFrame(frame);
        }

        if (frame == loopFrame) {
            this.setState(MovieClipState.STOPPED);
            return;
        }

        this.setState(state);
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public int getLoopFrame() {
        return loopFrame;
    }

    public String getExportName() {
        return exportName;
    }

    public int getFps() {
        return fps;
    }

    public void setFps(int fps) {
        this.fps = fps;
        this.msPerFrame = 1f / fps;
    }

    public float getMsPerFrame() {
        return msPerFrame;
    }

    public float getDuration() {
        return this.msPerFrame * this.frames.size();
    }

    public DisplayObject[] getTimelineChildren() {
        return timelineChildren;
    }

    public String[] getTimelineChildrenNames() {
        return timelineChildrenNames;
    }

    public List<MovieClipFrame> getFrames() {
        return frames;
    }

    public String getFrameLabel(int index) {
        return frames.get(index).getLabel();
    }

    private int getFrameIndex(String frameLabel) {
        if (frameLabel != null) {
            for (int i = 0; i < this.frames.size(); i++) {
                String label = this.frames.get(i).getLabel();
                if (label != null && label.equals(frameLabel)) {
                    return i;
                }
            }
        }

        return -1;
    }

    private void interpolateFrames() {
        throw new RuntimeException("Not implemented yet");
    }

    public int getFrameCountRecursive() {
        int frameCount = this.frames.size();

        for (DisplayObject timelineChild : children) {
            if (timelineChild.isVisible() && timelineChild.isMovieClip()) {
                int childFrameCount = ((MovieClip) timelineChild).getFrameCountRecursive();
                if (childFrameCount > frameCount) {
                    frameCount = childFrameCount;
                }
            }
        }

        return frameCount;
    }

    public int getFrameCount() {
        return this.frames.size();
    }
}
