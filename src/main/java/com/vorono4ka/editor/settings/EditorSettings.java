package com.vorono4ka.editor.settings;

// TODO: serialize and save somewhere in program directory
public class EditorSettings {
    private boolean shouldDisplayPolygons;
    private float pixelSize = 1.0f;

    public boolean isShouldDisplayPolygons() {
        return shouldDisplayPolygons;
    }

    public void setShouldDisplayPolygons(boolean shouldDisplayPolygons) {
        this.shouldDisplayPolygons = shouldDisplayPolygons;
    }

    public float getPixelSize() {
        return pixelSize;
    }

    public void setPixelSize(float pixelSize) {
        if (pixelSize < 0) throw new IllegalArgumentException("pixelSize must be >= 0.0f");
        this.pixelSize = pixelSize;
    }
}
