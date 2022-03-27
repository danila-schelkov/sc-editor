package com.vorono4ka.swf.displayObjects;

import com.vorono4ka.swf.ColorTransform;
import com.vorono4ka.swf.Matrix2x3;

public abstract class DisplayObject {
    private boolean isVisible;
    private ColorTransform colorTransform;
    private Matrix2x3 matrix;
    private int renderConfigBits;

    public abstract void render(Matrix2x3 matrix, ColorTransform colorTransform, int a3, float a4);

    public abstract void collisionRender(Matrix2x3 matrix, ColorTransform colorTransform, int a3, float a4);

    public void setVisibleRecursive(boolean visible) {
        this.isVisible = visible;
    }

    public void setInteractiveRecursive(boolean interactive) { }

    public void setBlendMode(int blendMode) {
        this.renderConfigBits = this.renderConfigBits & 0xFFFFFC7F | (((blendMode >> 7) & 7) << 7);
    }

    public void setGrayOut(boolean grayOut) {
        if (grayOut) this.renderConfigBits |= 4;
        else this.renderConfigBits &= 0xFFFFFFFB;
    }

    public void setPixelSnappedXY(float x, float y) {
        this.matrix.setXY((float) Math.floor(x), (float) Math.floor(y));
    }

    public void setXY(float x, float y) {
        this.matrix.setXY(x, y);
    }

    public void setX(float x) {
        this.matrix.setX(x);
    }

    public void setY(float y) {
        this.matrix.setY(y);
    }

    public void setAlpha(float alpha) {
        this.colorTransform.setAlpha(alpha);
    }

    public boolean isShape() {
        return false;
    }

    public boolean isMovieClip() {
        return false;
    }

    public boolean isMovieClipModifier() {
        return false;
    }

    public boolean isTextField() {
        return false;
    }
}
