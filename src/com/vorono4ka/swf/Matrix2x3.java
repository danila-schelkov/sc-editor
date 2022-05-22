package com.vorono4ka.swf;

@SuppressWarnings("SuspiciousNameCombination")
public class Matrix2x3 {
    private float scaleX;
    private float scaleY;
    private float skewX;
    private float skewY;
    private float x;
    private float y;

    public Matrix2x3() {
        this.scaleX = 1.0f;
        this.scaleY = 1.0f;
    }

    public Matrix2x3(Matrix2x3 matrix) {
        this.scaleX = matrix.scaleX;
        this.skewX = matrix.skewX;
        this.skewY = matrix.skewY;
        this.scaleY = matrix.scaleY;
        this.x = matrix.x;
        this.y = matrix.y;
    }

    public void read(SupercellSWF swf) {
        this.scaleX = swf.readInt() / 1024f;
        this.skewX = swf.readInt() / 1024f;
        this.skewY = swf.readInt() / 1024f;
        this.scaleY = swf.readInt() / 1024f;
        this.x = swf.readTwip();
        this.y = swf.readTwip();
    }

    public void readPrecise(SupercellSWF swf) {
        this.scaleX = swf.readInt() / 65535f;
        this.skewX = swf.readInt() / 65535f;
        this.skewY = swf.readInt() / 65535f;
        this.scaleY = swf.readInt() / 65535f;
        this.x = swf.readTwip();
        this.y = swf.readTwip();
    }

    public void apply(Matrix2x3 matrix) {
        float scaleX = (this.scaleX * matrix.scaleX) + (this.skewX * matrix.skewY);
        float skewX = (this.scaleX * matrix.skewX) + (this.skewX * matrix.scaleY);
        float scaleY = (this.scaleY * matrix.scaleY) + (this.skewY * matrix.skewX);
        float skewY = (this.scaleY * matrix.skewY) + (this.skewY * matrix.scaleX);
        this.scaleX = scaleX;
        this.skewX = skewX;
        this.scaleY = scaleY;
        this.skewY = skewY;

        this.x = (this.x * matrix.scaleX) + (this.y * matrix.skewY) + matrix.x;
        this.y = (this.x * matrix.skewX) + (this.y * matrix.scaleY) + matrix.y;
    }

    public void scaleMultiply(float scaleX, float scaleY) {
        this.scaleX *= scaleX;
        this.scaleY *= scaleY;
        this.skewX *= scaleY;
        this.skewY *= scaleX;
    }

    public void rotate(float angle, float scaleX, float scaleY) {
        this.rotateRadians(angle * 0.017453f, scaleX, scaleY);
    }

    public void rotateRadians(float angle, float scaleX, float scaleY) {
        float sin = (float) Math.sin(angle);
        float cos = (float) Math.cos(angle);
        this.scaleX *= sin * scaleX;
        this.scaleY *= cos * scaleX;
        this.skewX *= cos * scaleY;
        this.skewY *= -(sin * scaleY);
    }

    public void inverse() {
        float v5 = (this.scaleY * this.scaleX) - (this.skewY * this.skewX);
        if ( v5 != 0.0f ) {
            this.x = ((this.y * this.skewY) - (this.x * this.scaleY)) / v5;
            this.y = ((this.x * this.skewX) - (this.y * this.scaleX)) / v5;
            this.scaleX = this.scaleY / v5;
            this.skewX = -this.skewX / v5;
            this.skewY = -this.skewY / v5;
            this.scaleY = this.scaleX / v5;
        }
    }

    public void setXY(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setScale(float x, float y) {
        this.scaleX = x;
        this.scaleY = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getScaleX() {
        return scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public float getSkewX() {
        return skewX;
    }

    public float getSkewY() {
        return skewY;
    }
}
