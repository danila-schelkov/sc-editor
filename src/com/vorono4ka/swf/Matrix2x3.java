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
        if ( v5 != 0.0 )
        {
            this.x = ((this.y * this.skewY) - (this.x * this.scaleY)) / v5;
            this.y = ((this.x * this.skewX) - (this.y * this.scaleX)) / v5;
            this.scaleX = this.scaleY / v5;
            this.skewX = -this.skewX / v5;
            this.skewY = -this.skewY / v5;
            this.scaleY = this.scaleX / v5;
        }
    }
}
