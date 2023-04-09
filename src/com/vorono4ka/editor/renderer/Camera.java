package com.vorono4ka.editor.renderer;

import com.vorono4ka.math.Rect;

public class Camera {
    public static final int DEFAULT_SCALE_STEP = 39;
    public static final float DEFAULT_POINT_SIZE = 1.0f;
    private Rect viewport, clipArea;
    private int scaleStep;
    private float pointSize;
    private float offsetX, offsetY;

    public Camera() {
        this.reset();
    }

    public void reset() {
        this.offsetX = 0;
        this.offsetY = 0;
        this.scaleStep = DEFAULT_SCALE_STEP;
        this.pointSize = DEFAULT_POINT_SIZE;
    }

    public void init(int width, int height) {
        this.viewport = new Rect(-(width / 2f), -(height / 2f), width / 2f, height / 2f);
        this.clipArea = new Rect(this.viewport);
    }

    public Rect updateClipArea() {
        this.clipArea.copyFrom(this.viewport);
        this.clipArea.scale(DEFAULT_POINT_SIZE / this.pointSize);
        this.clipArea.movePosition(this.offsetX, this.offsetY);

        return this.clipArea;
    }

    public int getScaleStep() {
        return scaleStep;
    }

    public void setScaleStep(int scaleStep) {
        this.scaleStep = scaleStep;
    }

    public float getPointSize() {
        return pointSize;
    }

    public void setPointSize(float pointSize) {
        this.pointSize = pointSize;
        if (pointSize == 0) {
            this.viewport = null;
        }
    }

    public void addOffset(float x, float y) {
        this.offsetX += x;
        this.offsetY += y;
    }

    public Rect getClipArea() {
        return clipArea;
    }

    public float getWorldX(int screenX) {
        float viewportX = screenX - this.viewport.getWidth() / 2f;
        viewportX /= this.pointSize;

        return viewportX;
    }

    public float getWorldY(int screenY) {
        float viewportY = screenY - this.viewport.getHeight() / 2f;
        viewportY /= this.pointSize;

        return viewportY;
    }
}
