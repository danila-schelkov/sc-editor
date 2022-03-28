package com.vorono4ka.swf.displayObjects;

import com.vorono4ka.swf.ColorTransform;
import com.vorono4ka.swf.Matrix2x3;
import com.vorono4ka.swf.MovieClipFrame;
import com.vorono4ka.swf.ScMatrixBank;

public class MovieClip extends Sprite {
    private String exportName;
    private float frameTime;
    private float msPerFrame;
    private DisplayObject[] timelineChildren;
    private String[] timelineChildrenNames;
    private MovieClipFrame[] frames;
    private ScMatrixBank matrixBank;
    private int currentFrame;
    private int loopFrame;

    public MovieClip() {
        this.currentFrame = -1;
    }

    @Override
    public void render(Matrix2x3 matrix, ColorTransform colorTransform, int a3, float a4) {

    }

    @Override
    public void collisionRender(Matrix2x3 matrix, ColorTransform colorTransform, int a3, float a4) {

    }

    public void setFrame(int index) {
        if (this.loopFrame == index && this.state != 2) {
            this.frameTime = 0.0f;
            this.state = 2;
        }
    }

    public void setExportName(String exportName) {
        this.exportName = exportName;
    }

    public void setMsPerFrame(float msPerFrame) {
        this.msPerFrame = msPerFrame;
    }

    public void setTimelineChildren(DisplayObject[] timelineChildren) {
        this.timelineChildren = timelineChildren;
    }

    public void setTimelineChildrenNames(String[] timelineChildrenNames) {
        this.timelineChildrenNames = timelineChildrenNames;
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
