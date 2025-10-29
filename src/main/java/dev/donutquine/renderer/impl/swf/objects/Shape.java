package dev.donutquine.renderer.impl.swf.objects;

import dev.donutquine.editor.renderer.Stage;
import dev.donutquine.swf.ColorTransform;
import dev.donutquine.swf.Matrix2x3;
import dev.donutquine.swf.shapes.ShapeDrawBitmapCommand;
import dev.donutquine.swf.shapes.ShapeOriginal;
import dev.donutquine.utilities.RenderConfig;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Shape extends DisplayObject {
    protected List<ShapeDrawBitmapCommand> commands;

    private final Set<Integer> disabledCommands = new HashSet<>();

    public static Shape createShape(ShapeOriginal original) {
        Shape shape = new Shape();

        shape.id = original.getId();
        shape.commands = original.getCommands();

        return shape;
    }

    @Override
    public boolean render(Matrix2x3 matrix, ColorTransform colorTransform, int a4, float deltaTime) {
        Matrix2x3 matrixApplied = new Matrix2x3(this.getMatrix());
        matrixApplied.multiply(matrix);

        ColorTransform colorTransformApplied = new ColorTransform(this.getColorTransform());
        colorTransformApplied.multiply(colorTransform);

        int v35 = RenderConfig.getUnknownRenderModification(colorTransformApplied) | a4;

        int renderConfigBits = this.getRenderConfigBits() | v35;

        Stage stage = this.getStage();

        boolean result = false;
        List<ShapeDrawBitmapCommand> shapeDrawBitmapCommands = this.commands;
        for (int i = 0; i < shapeDrawBitmapCommands.size(); i++) {
            ShapeDrawBitmapCommand command = shapeDrawBitmapCommands.get(i);

            if (disabledCommands.contains(i)) {
                continue;
            }

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

        List<ShapeDrawBitmapCommand> shapeDrawBitmapCommands = this.commands;
        for (int i = 0; i < shapeDrawBitmapCommands.size(); i++) {
            ShapeDrawBitmapCommand command = shapeDrawBitmapCommands.get(i);

            if (disabledCommands.contains(i)) {
                continue;
            }

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

    public void setCommandVisibility(int commandIndex, boolean isVisible) {
        if (isVisible) {
            disabledCommands.remove(commandIndex);
        } else {
            disabledCommands.add(commandIndex);
        }
    }

    public boolean isCommandVisible(int commandIndex) {
        return !disabledCommands.contains(commandIndex);
    }
}
