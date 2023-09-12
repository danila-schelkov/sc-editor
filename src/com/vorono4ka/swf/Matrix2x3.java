package com.vorono4ka.swf;

import com.vorono4ka.streams.ByteStream;
import com.vorono4ka.swf.constants.Tag;
import com.vorono4ka.swf.originalObjects.Savable;

@SuppressWarnings("SuspiciousNameCombination")
public class Matrix2x3 implements Savable {
    public static final float PRECISE_MULTIPLIER = 65535f;
    public static final float DEFAULT_MULTIPLIER = 1024f;

    private float scaleX;
    private float scaleY;
    private float shearX;
    private float shearY;
    private float x;
    private float y;

    public Matrix2x3() {
        this.scaleX = 1.0f;
        this.scaleY = 1.0f;
    }

    public Matrix2x3(Matrix2x3 matrix) {
        this.scaleX = matrix.scaleX;
        this.shearX = matrix.shearX;
        this.shearY = matrix.shearY;
        this.scaleY = matrix.scaleY;
        this.x = matrix.x;
        this.y = matrix.y;
    }

    public void load(SupercellSWF swf, boolean isPrecise) {
        float divider = isPrecise ? PRECISE_MULTIPLIER : DEFAULT_MULTIPLIER;

        this.scaleX = swf.readInt() / divider;
        this.shearX = swf.readInt() / divider;
        this.shearY = swf.readInt() / divider;
        this.scaleY = swf.readInt() / divider;
        this.x = swf.readTwip();
        this.y = swf.readTwip();
    }

    @Override
    public void save(ByteStream stream) {
        float multiplier = isPrecise() ? PRECISE_MULTIPLIER : DEFAULT_MULTIPLIER;

        stream.writeInt((int) (this.scaleX * multiplier));
        stream.writeInt((int) (this.shearX * multiplier));
        stream.writeInt((int) (this.shearY * multiplier));
        stream.writeInt((int) (this.scaleY * multiplier));
        stream.writeTwip(this.x);
        stream.writeTwip(this.y);
    }

    @Override
    public Tag getTag() {
        return isPrecise() ? Tag.MATRIX_PRECISE : Tag.MATRIX;
    }

    private boolean isPrecise() {
        return (this.scaleX != 0 && Math.abs(this.scaleX) < 0.0009765f) ||
                (this.scaleY != 0 && Math.abs(this.scaleY) < 0.0009765f) ||
                (this.shearX != 0 && Math.abs(this.shearX) < 0.0009765f) ||
                (this.shearY != 0 && Math.abs(this.shearY) < 0.0009765f);
    }

    public void multiply(Matrix2x3 matrix) {
        float scaleX = (this.scaleX * matrix.scaleX) + (this.shearX * matrix.shearY);
        float shearX = (this.scaleX * matrix.shearX) + (this.shearX * matrix.scaleY);
        float scaleY = (this.scaleY * matrix.scaleY) + (this.shearY * matrix.shearX);
        float shearY = (this.scaleY * matrix.shearY) + (this.shearY * matrix.scaleX);
        float x = matrix.applyX(this.x, this.y);
        float y = matrix.applyY(this.x, this.y);

        this.scaleX = scaleX;
        this.shearX = shearX;
        this.scaleY = scaleY;
        this.shearY = shearY;
        this.x = x;
        this.y = y;
    }

    public float applyX(float x, float y) {
        return x * this.scaleX + y * this.shearY + this.x;
    }

    public float applyY(float x, float y) {
        return y * this.scaleY + x * this.shearX + this.y;
    }

    public void scaleMultiply(float scaleX, float scaleY) {
        this.scaleX *= scaleX;
        this.scaleY *= scaleY;
        this.shearX *= scaleY;
        this.shearY *= scaleX;
    }

    public void rotate(float angle, float scaleX, float scaleY) {
        this.rotateRadians(angle * 0.017453f, scaleX, scaleY);
    }

    public void rotateRadians(float angle, float scaleX, float scaleY) {
        float sin = (float) Math.sin(angle);
        float cos = (float) Math.cos(angle);
        this.scaleX *= sin * scaleX;
        this.scaleY *= cos * scaleX;
        this.shearX *= cos * scaleY;
        this.shearY *= -(sin * scaleY);
    }

    public void inverse() {
        float determinant = (this.scaleY * this.scaleX) - (this.shearY * this.shearX);
        if (determinant != 0.0f) {
            float y = this.y;
            float x = this.x;
            float shearX = this.shearX;
            float shearY = this.shearY;
            float scaleX = this.scaleX;
            float scaleY = this.scaleY;

            this.x = ((y * shearY) - (x * scaleY)) / determinant;
            this.y = ((x * shearX) - (y * scaleX)) / determinant;
            this.scaleX = scaleY / determinant;
            this.shearX = -shearX / determinant;
            this.shearY = -shearY / determinant;
            this.scaleY = scaleX / determinant;
        }
    }

    public float getScaleX() {
        return scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public float getShearX() {
        return shearX;
    }

    public float getShearY() {
        return shearY;
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
        scaleMultiply(1f / this.scaleX, 1f / this.scaleY);  // scale to identical
        scaleMultiply(x, y);
    }
}
