package com.vorono4ka.swf.displayObjects;

import com.vorono4ka.editor.renderer.Stage;
import com.vorono4ka.math.Rect;
import com.vorono4ka.swf.ColorTransform;
import com.vorono4ka.swf.Matrix2x3;

public class Shape9Slice extends Shape {
    private final Rect scalingGrid;

    public Shape9Slice(Rect scalingGrid) {
        this.scalingGrid = scalingGrid;
    }

    @Override
    public boolean render(Matrix2x3 matrix, ColorTransform colorTransform, int a4, float deltaTime) {
        Matrix2x3 matrixApplied = new Matrix2x3(this.getMatrix());
        matrixApplied.multiply(matrix);

        ColorTransform colorTransformApplied = new ColorTransform(this.getColorTransform());
        colorTransformApplied.multiply(colorTransform);

        float redMultiplier = colorTransformApplied.getRedMultiplier();
        float greenMultiplier = colorTransformApplied.getGreenMultiplier();
        float blueMultiplier = colorTransformApplied.getBlueMultiplier();
        float alpha = colorTransformApplied.getAlpha();

        float redAddition = colorTransformApplied.getRedAddition();
        float greenAddition = colorTransformApplied.getGreenAddition();
        float blueAddition = colorTransformApplied.getBlueAddition();

        int v35 = a4;
        if (redMultiplier + greenMultiplier + blueMultiplier + alpha != 1020f)
            v35 = a4 | 1;
        if (redAddition + greenAddition + blueAddition > 0)
            v35 = a4 | 3;

        int renderConfigBits = this.getRenderConfigBits() | v35;

        // Calculating bounds
        Rect bounds = new Rect(100000, 100000, -100000, -100000);
        for (ShapeDrawBitmapCommand command : this.commands) {
            for (int i = 0; i < command.getVertexCount(); i++) {
                bounds.addPoint(command.getX(i), command.getY(i));
            }
        }

        Rect movedGrid = new Rect(this.scalingGrid);
        // COERCE_FLOAT(LODWORD(this->shape.displayObject.matrix.x) ^ 0x80000000), COERCE_FLOAT(LODWORD(this->shape.displayObject.matrix.y) ^ 0x80000000) What does that mean?
        movedGrid.movePosition(-this.getMatrix().getX(), -this.getMatrix().getY());

        float widthSkewed = this.scalingGrid.getWidth() * matrixApplied.getSkewX();
        float widthScaled = this.scalingGrid.getWidth() * matrixApplied.getScaleX();
        float widthDistance = widthSkewed * widthSkewed + widthScaled * widthScaled;

        float heightSkewed = this.scalingGrid.getHeight() * matrixApplied.getSkewY();
        float heightScaled = this.scalingGrid.getHeight() * matrixApplied.getScaleY();
        float heightDistance = heightSkewed * heightSkewed + heightScaled * heightScaled;

        float scaledWidth = 1.0f;
        if (widthDistance != 0) {
            scaledWidth = (float) (this.scalingGrid.getWidth() / Math.sqrt(widthDistance));
        }
        float scaledHeight = 1.0f;
        if (heightDistance != 0) {
            scaledHeight = (float) (this.scalingGrid.getHeight() / Math.sqrt(heightDistance));
        }

        boolean result = false;

        Stage stage = this.getStage();
        for (ShapeDrawBitmapCommand command : this.commands) {
            result |= command.render9Slice(stage, matrixApplied, colorTransformApplied, renderConfigBits, movedGrid, bounds, scaledWidth, scaledHeight);
        }

        return result;
    }

    @Override
    public boolean isShape() {
        return true;
    }
}
