package com.vorono4ka.swf.displayObjects;

import com.vorono4ka.editor.renderer.Stage;
import com.vorono4ka.swf.ColorTransform;
import com.vorono4ka.swf.Matrix2x3;

public class Shape extends DisplayObject {
    protected ShapeDrawBitmapCommand[] commands;

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

        Stage stage = this.getStage();

        boolean result = false;
        for (ShapeDrawBitmapCommand command : this.commands) {
            result |= command.render(stage, matrixApplied, colorTransformApplied, renderConfigBits);
        }

        return result;
    }

    @Override
    public boolean collisionRender(Matrix2x3 matrix) {
        Matrix2x3 matrixApplied = new Matrix2x3(this.getMatrix());
        matrixApplied.multiply(matrix);

        // TODO: accurateCollisionRender

        Stage stage = this.getStage();

        boolean result = false;

        for (ShapeDrawBitmapCommand command : this.commands) {
            result |= command.collisionRender(stage, matrixApplied, this.getColorTransform());
        }

        return result;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCommands(ShapeDrawBitmapCommand[] commands) {
        this.commands = commands;
    }

    @Override
    public boolean isShape() {
        return true;
    }
}
