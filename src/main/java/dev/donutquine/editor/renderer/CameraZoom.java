package dev.donutquine.editor.renderer;

import dev.donutquine.math.MathHelper;

public class CameraZoom {
    public static final int DEFAULT_SCALE_STEP = 39;
    public static final float DEFAULT_POINT_SIZE = 1.0f;
    public static final int MAX_POINT_SIZE = 10;

    private int scaleStep;
    private float pointSize;

    public static int estimateCurrentScaleStep(float pointSize) {
        int step = 0;

        int estimatedStep = step;
        float minDelta = Float.MAX_VALUE;

        float scale;
        do {
            scale = getScaleByStep(step++);

            float delta = Math.abs(pointSize - scale / 100f);
            if (delta < minDelta) {
                estimatedStep = step;
                minDelta = delta;
            }
        } while (scale <= MAX_POINT_SIZE * 100);

        return estimatedStep;
    }

    private static float getScaleByStep(int step) {
        float scale = 2.5f;
        for (int index = 0; index < step; index++) {
            scale += MathHelper.round(scale, 1) / 10f;
        }
        return scale;
    }

    public void reset() {
        this.scaleStep = DEFAULT_SCALE_STEP;
        this.pointSize = DEFAULT_POINT_SIZE;
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
    }

    public float getScaleFactor() {
        return DEFAULT_POINT_SIZE / this.pointSize;
    }

    public void zoomTo(int step) {
        float scale = getScaleByStep(step);

        if (scale > 1000f) {
            setPointSize(MAX_POINT_SIZE);
            scaleStep = Math.min(step, estimateCurrentScaleStep(MAX_POINT_SIZE));
            return;
        }

        scaleStep = step;
        setPointSize(scale / 100f);
    }

    public void zoomIn(int step) {
        this.zoomTo(this.scaleStep + step);
    }

    public void zoomOut(int step) {
        this.zoomTo(this.scaleStep - step);
    }
}
