package com.vorono4ka.swf.displayObjects;

import com.vorono4ka.swf.ColorTransform;
import com.vorono4ka.swf.Matrix2x3;

public abstract class DisplayObject {
    protected int id;

    private ColorTransform colorTransform;
    private Matrix2x3 matrix;
    private int renderConfigBits;
    private boolean isVisible;

    public DisplayObject() {
        this.id = -1;

        this.colorTransform = new ColorTransform();
        this.matrix = new Matrix2x3();
        this.renderConfigBits = 0;
        this.isVisible = true;
    }

    public abstract void render(Matrix2x3 matrix, ColorTransform colorTransform, int a4, float a5);

    public abstract void collisionRender(Matrix2x3 matrix, ColorTransform colorTransform);


    public int getId() {
        return this.id;
    }


    public void setVisibleRecursive(boolean visible) {
        this.isVisible = visible;
    }

    public void setInteractiveRecursive(boolean interactive) { }

    public int getRenderConfigBits() {
        return renderConfigBits;
    }

    public void setBlendMode(int blendMode) {
        this.renderConfigBits = this.renderConfigBits & 0xFFFFFC7F | (((blendMode >> 7) & 7) << 7);
    }

    public void setGrayOut(boolean grayOut) {
        if (grayOut) this.renderConfigBits |= 4;
        else this.renderConfigBits &= 0xFFFFFFFB;
    }

    public Matrix2x3 getMatrix() {
        return matrix;
    }

    public void setMatrix(Matrix2x3 matrix) {
        this.matrix = matrix;
    }

    public ColorTransform getColorTransform() {
        return colorTransform;
    }

    public void setColorTransform(ColorTransform colorTransform) {
        this.colorTransform = colorTransform;
    }

    public void setPixelSnappedXY(float x, float y) {
        this.matrix.setXY((float) Math.floor(x), (float) Math.floor(y));
    }

    public void setXY(float x, float y) {
        this.matrix.setXY(x, y);
    }

    public float getX() {
        return this.matrix.getX();
    }

    public void setX(float x) {
        this.matrix.setX(x);
    }

    public float getY() {
        return this.matrix.getY();
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
