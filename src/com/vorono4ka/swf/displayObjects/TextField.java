package com.vorono4ka.swf.displayObjects;

import com.vorono4ka.editor.renderer.Stage;
import com.vorono4ka.math.Rect;
import com.vorono4ka.swf.ColorTransform;
import com.vorono4ka.swf.Matrix2x3;

public class TextField extends DisplayObject {

    private boolean isInteractive;
    private float cursorBlinkTime;
    private Rect bounds;

    @Override
    public boolean render(Matrix2x3 matrix, ColorTransform colorTransform, int a4, float deltaTime) {
        Matrix2x3 matrixApplied = new Matrix2x3(this.getMatrix());
        matrixApplied.multiply(matrix);

        ColorTransform colorTransformApplied = new ColorTransform(this.getColorTransform());
        colorTransformApplied.multiply(colorTransform);

        int redMultiplier = colorTransformApplied.getRedMultiplier();
        int greenMultiplier = colorTransformApplied.getGreenMultiplier();
        int blueMultiplier = colorTransformApplied.getBlueMultiplier();
        int alpha = colorTransformApplied.getAlpha();

        int redAddition = colorTransformApplied.getRedAddition();
        int greenAddition = colorTransformApplied.getGreenAddition();
        int blueAddition = colorTransformApplied.getBlueAddition();

        int v45 = a4;
        if (redMultiplier + greenMultiplier + blueMultiplier + alpha != 1020)
            v45 = a4 | 1;
        if (redAddition + greenAddition + blueAddition > 0)
            v45 = a4 | 3;

        this.cursorBlinkTime = (this.cursorBlinkTime + deltaTime) % 1.0f;
        return this.shapeRender(this.getStage(), matrixApplied, colorTransformApplied, this.getRenderConfigBits() | v45, this.bounds == null);
    }

    @Override
    public boolean collisionRender(Matrix2x3 matrix) {
        if (!this.isInteractive) return false;

        Matrix2x3 matrixApplied = new Matrix2x3(this.getMatrix());
        matrixApplied.multiply(matrix);

        return this.shapeRender(this.getStage(), matrixApplied, this.getColorTransform(), 0, false);
    }

    private boolean shapeRender(Stage stage, Matrix2x3 matrix, ColorTransform colorTransform, int renderConfigBits, boolean noBounds) {
        return false;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean isTextField() {
        return true;
    }
}
