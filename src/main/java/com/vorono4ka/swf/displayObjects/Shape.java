package com.vorono4ka.swf.displayObjects;

import com.vorono4ka.editor.renderer.Stage;
import com.vorono4ka.swf.ColorTransform;
import com.vorono4ka.swf.Matrix2x3;
import com.vorono4ka.swf.originalObjects.ShapeDrawBitmapCommand;
import com.vorono4ka.swf.originalObjects.ShapeOriginal;
import com.vorono4ka.utilities.RenderConfig;

import java.util.List;

public class Shape extends DisplayObject {
    protected List<ShapeDrawBitmapCommand> commands;

    public static Shape createShape(ShapeOriginal original) {
        Shape shape = new Shape();

        shape.id = original.getId();
        shape.commands = List.of(original.getCommands());

        return shape;
    }

    @Override
    public boolean render(Matrix2x3 matrix, ColorTransform colorTransform, int a4, float deltaTime) {
        Matrix2x3 matrixApplied = new Matrix2x3(this.getMatrix());
        matrixApplied.multiply(matrix);

        ColorTransform colorTransformApplied = new ColorTransform(this.getColorTransform());
        colorTransformApplied.multiply(colorTransform);

        int v35 = RenderConfig.getUnknownRenderModification(colorTransformApplied, a4);

        int renderConfigBits = this.getRenderConfigBits() | v35;

        Stage stage = this.getStage();

        boolean result = false;
        for (ShapeDrawBitmapCommand command : this.commands) {
            result |= ShapeDrawBitmapCommandRenderer.render(command, stage, matrixApplied, colorTransformApplied, renderConfigBits);
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
            result |= ShapeDrawBitmapCommandRenderer.collisionRender(command, stage, matrixApplied, this.getColorTransform());
        }

        return result;
    }

    @Override
    public boolean isShape() {
        return true;
    }

    public int getCommandCount() {
        return this.commands.size();
    }

    public ShapeDrawBitmapCommand getCommand(int index) {
        if (index >= 0 && index < this.commands.size()) {
            return this.commands.get(index);
        }

        return null;
    }
}
