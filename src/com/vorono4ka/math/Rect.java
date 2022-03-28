package com.vorono4ka.math;

public class Rect {
    private final float left;
    private final float top;
    private final float right;
    private final float bottom;

    public Rect(float left, float top, float right, float bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public float getWidth() {
        return this.right - this.left;
    }

    public float getHeight() {
        return this.bottom - this.top;
    }

    private float getMidX() {
        return this.left + this.getWidth() / 2f;
    }

    private float getMidY() {
        return this.top + this.getHeight() / 2f;
    }

    public float getMinX() {
        return this.left;
    }

    public float getMinY() {
        return this.top;
    }

    public float getMaxX() {
        return this.right;
    }

    public float getMaxY() {
        return this.bottom;
    }
}
