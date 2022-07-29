package com.vorono4ka.swf;

import com.vorono4ka.streams.ByteStream;
import com.vorono4ka.swf.constants.Tag;
import com.vorono4ka.swf.originalObjects.SavableObject;

@SuppressWarnings("SuspiciousNameCombination")
public class Matrix2x3 implements SavableObject {
    public static final float PRECISE_MULTIPLIER = 65535f;
    public static final float DEFAULT_MULTIPLIER = 1024f;

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

    public void load(SupercellSWF swf, boolean isPrecise) {
        float divider = isPrecise ? PRECISE_MULTIPLIER : DEFAULT_MULTIPLIER;

        this.scaleX = swf.readInt() / divider;
        this.skewX = swf.readInt() / divider;
        this.skewY = swf.readInt() / divider;
        this.scaleY = swf.readInt() / divider;
        this.x = swf.readTwip();
        this.y = swf.readTwip();
    }

    @Override
    public void save(ByteStream stream) {
        float multiplier = isPrecise() ? PRECISE_MULTIPLIER : DEFAULT_MULTIPLIER;

        stream.writeInt((int) (this.scaleX * multiplier));
        stream.writeInt((int) (this.skewX * multiplier));
        stream.writeInt((int) (this.skewY * multiplier));
        stream.writeInt((int) (this.scaleY * multiplier));
        stream.writeTwip(this.x);
        stream.writeTwip(this.y);
    }

    @Override
    public Tag getTag() {
        return isPrecise() ? Tag.MATRIX_PRECISE : Tag.MATRIX;
    }

    private boolean isPrecise() {
        return this.scaleX < 0.0009765f || this.scaleY < 0.0009765f || this.skewX < 0.0009765f || this.skewY < 0.0009765f;
    }

    public void multiply(Matrix2x3 matrix) {
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

    public float applyX(float x, float y) {
        return x * this.scaleX + y * this.skewY + this.x;
    }

    public float applyY(float x, float y) {
        return y * this.scaleY + x * this.skewX + this.y;
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

    public void setXY(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setScale(float x, float y) {
        this.scaleX = x;
        this.scaleY = y;
    }
}
