package com.vorono4ka.math;

public class Rect {
    private float left;
    private float top;
    private float right;
    private float bottom;

    public Rect() {}

    public Rect(float left, float top, float right, float bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public void movePosition(float x, float y) {
        this.left += x;
        this.right += x;
        this.top += y;
        this.bottom += y;
    }

    public void addPoint(float x, float y) {
        if (this.left > x)
            this.left = x;
        else if (this.right < x)
            this.right = x;

        if (this.top > y)
            this.top = y;
        else if (this.bottom < y)
            this.bottom = y;
    }

    public boolean containsPoint(float x, float y) {
        return this.left <= x && this.right >= x && this.top <= y && this.bottom <= y;
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
