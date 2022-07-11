package com.vorono4ka.swf.displayObjects;

import com.vorono4ka.swf.*;
import com.vorono4ka.swf.constants.MovieClipState;

public class MovieClip extends Sprite {
    private String exportName;
    private float frameTime;
    private int fps;
    private float msPerFrame;
    private DisplayObject[] timelineChildren;
    private String[] timelineChildrenNames;
    private MovieClipFrame[] frames;
    private ScMatrixBank matrixBank;
    private int currentFrame;
    private int loopFrame;

    public MovieClip() {
        this.currentFrame = -1;
        this.loopFrame = -1;
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
                        framesSkipped = this.frames.length - 1;
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

            this.setFrame(nextFrame % this.frames.length);
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

    public void setFrame(int index) {
        if (this.loopFrame == index) {
            this.setState(MovieClipState.STOPPED);
        }

        if (this.currentFrame == index) return;
        this.currentFrame = index;

        MovieClipFrame frame = this.frames[index];
        int childIndex = 0;

        for (MovieClipFrameElement element : frame.getElements()) {
            DisplayObject child = this.timelineChildren[element.getChildIndex()];
            if (child == null) continue;

            int matrixIndex = element.getMatrixIndex();
            if (matrixIndex != 0xFFFF) {
                Matrix2x3 matrix = this.matrixBank.getMatrix(matrixIndex);
                child.setMatrix(new Matrix2x3(matrix));
            } else {
                child.setMatrix(new Matrix2x3());
            }

            int colorTransformIndex = element.getColorTransformIndex();
            if (colorTransformIndex != 0xFFFF) {
                ColorTransform colorTransform = this.matrixBank.getColorTransforms(colorTransformIndex);
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

    private void interpolateFrames() {
        System.out.println("MovieClip.interpolateFrames");
        System.out.println("Isn't implemented yet");
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
        int frameIndex = passedFrames % this.frames.length;
        this.gotoAndPlayFrameIndex(frameIndex, -1);

        for (DisplayObject child : this.timelineChildren) {
            if (child.isMovieClip()) {
                ((MovieClip) child).gotoAbsoluteTimeRecursive(time);
            }
        }
    }

    public void gotoAndStop(String frameLabel) {
        int frameIndex = -1;

        if (frameLabel != null) {
            for (int i = 0; i < this.frames.length; i++) {
                if (this.frames[i].getLabel().equals(frameLabel)) {
                    frameIndex = i;
                    break;
                }
            }
        }

        this.gotoAndStopFrameIndex(frameIndex);
    }

    public void gotoAndPlay(String frameLabel, String loopFrameLabel) {
        int frameIndex = -1;
        int loopFrameIndex = -1;

        if (frameLabel != null) {
            for (int i = 0; i < this.frames.length; i++) {
                if (this.frames[i].getLabel().equals(frameLabel)) {
                    frameIndex = i;
                    break;
                }
            }
        }

        if (loopFrameLabel != null) {
            for (int i = 0; i < this.frames.length; i++) {
                if (this.frames[i].getLabel().equals(loopFrameLabel)) {
                    loopFrameIndex = i;
                    break;
                }
            }
        }

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
        if (frame >= 0 && this.frames.length > frame) {
            this.setFrame(frame);
        }

        if (frame == loopFrame) {
            this.setState(MovieClipState.STOPPED);
            return;
        }

        this.setState(state);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setLoopFrame(int frame) {
        this.loopFrame = frame;
    }


    public String getExportName() {
        return exportName;
    }

    public void setExportName(String exportName) {
        this.exportName = exportName;
    }

    public int getFps() {
        return fps;
    }

    public void setFPS(int fps) {
        this.fps = fps;
        this.msPerFrame = 1f / fps;
    }

    public float getMSPerFrame() {
        return msPerFrame;
    }

    public float getDuration() {
        return this.msPerFrame * this.frames.length;
    }

    public DisplayObject[] getTimelineChildren() {
        return timelineChildren;
    }

    public void setTimelineChildren(DisplayObject[] timelineChildren) {
        this.timelineChildren = timelineChildren;
    }

    public String[] getTimelineChildrenNames() {
        return timelineChildrenNames;
    }

    public void setTimelineChildrenNames(String[] timelineChildrenNames) {
        this.timelineChildrenNames = timelineChildrenNames;
    }

    public MovieClipFrame[] getFrames() {
        return frames;
    }

    public String getFrameLabel(int index) {
        return frames[index].getLabel();
    }

    public void setFrames(MovieClipFrame[] frames) {
        this.frames = frames;
    }

    public void setMatrixBank(ScMatrixBank matrixBank) {
        this.matrixBank = matrixBank;
    }

    @Override
    public boolean isMovieClip() {
        return true;
    }
}
