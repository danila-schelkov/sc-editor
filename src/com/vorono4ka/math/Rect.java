package com.vorono4ka.math;

public class Rect {
    private float left;
    private float top;
    private float right;
    private float bottom;

    public Rect() {}

    public Rect(Rect rect) {
        this.left = rect.left;
        this.top = rect.top;
        this.right = rect.right;
        this.bottom = rect.bottom;
    }

    public Rect(float width, float height) {
        this.right = width;
        this.bottom = height;
    }

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

    public float getMidX() {
        return this.left + this.getWidth() / 2f;
    }

    public float getMidY() {
        return this.top + this.getHeight() / 2f;
    }

    // getMinX
    public float getLeft() {
        return this.left;
    }

    // getMinY
    public float getTop() {
        return this.top;
    }

    // getMaxX
    public float getRight() {
        return this.right;
    }

    // getMaxY
    public float getBottom() {
        return this.bottom;
    }

    public boolean overlaps(Rect other) {
        return this.left < other.right &&
                this.top < other.bottom &&
                this.right > other.left &&
                this.bottom > other.top;
    }

    public void scale(float scaleFactor) {
        this.left *= scaleFactor;
        this.right *= scaleFactor;
        this.top *= scaleFactor;
        this.bottom *= scaleFactor;
    }

    public void copyFrom(Rect rect) {
        this.left = rect.left;
        this.top = rect.top;
        this.right = rect.right;
        this.bottom = rect.bottom;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Rect)) return false;

        Rect rect = ((Rect) obj);
        return this.left == rect.left && this.top == rect.top && this.right == rect.right && this.bottom == rect.bottom;
    }
}
