package dev.donutquine.renderer.impl.swf.objects;

import dev.donutquine.editor.assets.TextureAsset;
import dev.donutquine.editor.renderer.Stage;
import dev.donutquine.math.ReadonlyRect;
import dev.donutquine.math.Rect;
import dev.donutquine.swf.ColorTransform;
import dev.donutquine.swf.Matrix2x3;
import dev.donutquine.swf.shapes.ShapeDrawBitmapCommand;
import dev.donutquine.swf.shapes.ShapeOriginal;
import dev.donutquine.utilities.RenderConfig;

public class Shape9Slice extends Shape {
    private final Rect scalingGrid;

    public Shape9Slice(ReadonlyRect scalingGrid) {
        this.scalingGrid = new Rect(scalingGrid);
    }

    public static Shape9Slice createShape(ShapeOriginal original, TextureAsset textureAsset, ReadonlyRect scalingGrid) {
        Shape9Slice shape9Slice = new Shape9Slice(scalingGrid);

        shape9Slice.id = original.getId();
        shape9Slice.commands = original.getCommands();
        shape9Slice.textureAsset = textureAsset;

        return shape9Slice;
    }

    @Override
    public boolean render(Matrix2x3 matrix, ColorTransform colorTransform, int a4, float deltaTime) {
        Matrix2x3 frameMatrix = calculateFrameMatrix(matrix);
        ColorTransform frameColorTransform = calculateFrameColorTransform(colorTransform);

        int renderConfigBits = this.getRenderConfigBits() | RenderConfig.getShader(frameColorTransform) | a4;

        // Calculating bounds
        Rect bounds = new Rect(100000, 100000, -100000, -100000);
        for (ShapeDrawBitmapCommand command : this.commands) {
            for (int i = 0; i < command.getVertexCount(); i++) {
                bounds.addPoint(command.getX(i), command.getY(i));
            }
        }

        Rect movedGrid = new Rect(this.scalingGrid);
        // Why not matrix applied? Maybe here is a bug?
        movedGrid.movePosition(-this.getMatrix().getX(), -this.getMatrix().getY());

        float widthSheared = this.scalingGrid.getWidth() * frameMatrix.getB();
        float widthScaled = this.scalingGrid.getWidth() * frameMatrix.getA();
        float widthDistance = widthSheared * widthSheared + widthScaled * widthScaled;

        float heightSheared = this.scalingGrid.getHeight() * frameMatrix.getC();
        float heightScaled = this.scalingGrid.getHeight() * frameMatrix.getD();
        float heightDistance = heightSheared * heightSheared + heightScaled * heightScaled;

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
            result |= ShapeDrawBitmapCommandRenderer.render9Slice(command, this.textureAsset, stage, frameMatrix, frameColorTransform, renderConfigBits, movedGrid, bounds, scaledWidth, scaledHeight);
        }

        return result;
    }

    public Rect getScalingGrid() {
        return scalingGrid;
    }
}
