package com.vorono4ka.swf.displayObjects;

import com.vorono4ka.editor.Main;
import com.vorono4ka.editor.renderer.Renderer;
import com.vorono4ka.swf.ColorTransform;
import com.vorono4ka.swf.Matrix2x3;

public class Shape extends DisplayObject {
    protected ShapeDrawBitmapCommand[] commands;

    @Override
    public void render(Matrix2x3 matrix, ColorTransform colorTransform, int a4, float a5) {
        Matrix2x3 matrixApplied = new Matrix2x3(this.getMatrix());
        matrixApplied.apply(matrix);

        ColorTransform colorTransformApplied = new ColorTransform(this.getColorTransform());
        colorTransformApplied.apply(colorTransform);

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

        int a3 = this.getRenderConfigBits() | v35;

        Renderer renderer = Main.editor.getRenderer();
        for (ShapeDrawBitmapCommand command : this.commands) {
            command.render(renderer, matrixApplied, colorTransformApplied, a3);
        }
    }

    @Override
    public void collisionRender(Matrix2x3 matrix, ColorTransform colorTransform) {

    }

    public ShapeDrawBitmapCommand[] getCommands() {
        return commands;
    }

    public void setCommands(ShapeDrawBitmapCommand[] commands) {
        this.commands = commands;
    }

    @Override
    public boolean isShape() {
        return true;
    }
}
