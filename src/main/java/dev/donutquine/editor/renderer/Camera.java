package dev.donutquine.editor.renderer;

import dev.donutquine.math.ReadonlyRect;
import dev.donutquine.math.Rect;

public class Camera {
    private final CameraZoom zoom = new CameraZoom();
    private Rect viewport, clipArea;
    private float offsetX, offsetY;

    public Camera() {
        this.reset();
    }

    public void reset() {
        this.zoom.reset();
        this.offsetX = 0;
        this.offsetY = 0;
    }

    public void init(Rect rect) {
        init(rect.getLeft(), rect.getTop(), rect.getRight(), rect.getBottom());
    }

    public void init(int width, int height) {
        init(-(width / 2f), -(height / 2f), width / 2f, height / 2f);
    }

    public void init(float left, float top, float right, float bottom) {
        this.viewport = new Rect(left, top, right, bottom);
        this.clipArea = new Rect(this.viewport);
    }

    public Rect updateClipArea() {
        this.clipArea.copyFrom(this.viewport);
        this.clipArea.scale(this.zoom.getScaleFactor());
        this.clipArea.movePosition(this.offsetX, this.offsetY);

        return this.clipArea;
    }

    public void addOffset(float x, float y) {
        this.offsetX += x;
        this.offsetY += y;
    }

    public float getOffsetX() {
        return offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public ReadonlyRect getViewport() {
        return viewport;
    }

    public ReadonlyRect getClipArea() {
        return clipArea;
    }

    public CameraZoom getZoom() {
        return zoom;
    }

    public float getWorldX(int screenX) {
        // TODO: figure out what's wrong with macos rendering viewport width
        float viewportX = screenX - this.viewport.getWidth() / 2f;
        viewportX /= zoom.getPointSize();

        return viewportX + offsetX;
    }

    public float getWorldY(int screenY) {
        // TODO: figure out what's wrong with macos rendering viewport height
        float viewportY = screenY - this.viewport.getHeight() / 2f;
        viewportY /= zoom.getPointSize();

        return viewportY + offsetY;
    }

    public void zoomToFit(Rect bounds) {
        reset();

        float pointSize = Math.min(viewport.getWidth() / bounds.getWidth(), viewport.getHeight() / bounds.getHeight());

        zoom.setScaleStep(CameraZoom.estimateCurrentScaleStep(pointSize));
        zoom.setPointSize(pointSize);

        float offsetX = bounds.getMidX() - viewport.getMidX();
        float offsetY = bounds.getMidY() - viewport.getMidY();

        addOffset(offsetX, offsetY);
    }

    public void moveToFit(Rect bounds) {
        reset();

        float offsetX = bounds.getMidX() - viewport.getMidX();
        float offsetY = bounds.getMidY() - viewport.getMidY();

        addOffset(offsetX, offsetY);
    }
}
