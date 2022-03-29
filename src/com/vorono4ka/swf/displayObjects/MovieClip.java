package com.vorono4ka.swf.displayObjects;

import com.vorono4ka.swf.*;

public class MovieClip extends Sprite {
    private String exportName;
    private float frameTime;
    private float msPerFrame;
    private DisplayObject[] timelineChildren;
    private String[] timelineChildrenNames;
    private MovieClipFrame[] frames;
    private ScMatrixBank matrixBank;
    private final int currentFrame;
    private int loopFrame;

    public MovieClip() {
        this.currentFrame = -1;
    }

    @Override
    public void render(Matrix2x3 matrix, ColorTransform colorTransform, int a4, float a5) {
        if (a5 <= 0.0) {
            super.render(matrix, colorTransform, a4, a5);
            return;
        }

        if (this.frameTime >= this.msPerFrame) {
            int framesPassed = (int) (this.frameTime / this.msPerFrame);
            this.frameTime -= (this.msPerFrame * framesPassed);

            int nextFrame;
            if ( this.state == 1 ) {
                if ( this.frameSkippingType != 0 ) {
                    if ( this.loopFrame >= this.currentFrame ) {
                        nextFrame = this.currentFrame + framesPassed;
                        if ( nextFrame > this.loopFrame ) {
                            nextFrame = this.loopFrame;
                        }
                    } else {
                        nextFrame = this.currentFrame - framesPassed;
                        if ( nextFrame < this.loopFrame ) {
                            nextFrame = this.loopFrame;
                        }
                    }
                } else {
                    int framesSkipped = 1;
                    if ( this.loopFrame < this.currentFrame )
                        framesSkipped = this.frames.length - 1;
                    nextFrame = this.currentFrame + framesSkipped;
                }
            } else {
                if ( this.frameSkippingType != 0 ) {
                    nextFrame = this.currentFrame + framesPassed;
                    if ( this.currentFrame < this.loopFrame && nextFrame > this.loopFrame ) {
                        nextFrame = this.loopFrame;
                    }
                } else {
                    nextFrame = this.currentFrame + 1;
                }
            }

            this.setFrame(nextFrame % this.frames.length);
        }

        if ( this.frameSkippingType == 2 ) {
            this.interpolateFrames();
        }

        if ( this.state <= 1 ) {
            this.frameTime += a5;
        }

        super.render(matrix, colorTransform, a4, a5);
    }

    @Override
    public void collisionRender(Matrix2x3 matrix, ColorTransform colorTransform) {

    }

    public void setFrame(int index) {
        if (this.loopFrame == index && this.state != 2) {
            this.frameTime = 0.0f;
            this.state = 2;
        }

        if (this.currentFrame == index) return;

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

        while (this.getChildrenCount() > childIndex) {
            this.removeChildAt(++childIndex);
        }
    }

    private void interpolateFrames() {
        System.out.println("MovieClip.interpolateFrames");
        System.out.println("Isn't implemented yet");
    }

    public String getExportName() {
        return exportName;
    }

    public void setExportName(String exportName) {
        this.exportName = exportName;
    }

    public void setMsPerFrame(float msPerFrame) {
        this.msPerFrame = msPerFrame;
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
