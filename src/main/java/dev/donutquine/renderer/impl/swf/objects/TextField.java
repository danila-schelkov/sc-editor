package dev.donutquine.renderer.impl.swf.objects;

import dev.donutquine.editor.renderer.DrawApi;
import dev.donutquine.editor.renderer.Stage;
import dev.donutquine.math.Rect;
import dev.donutquine.swf.ColorTransform;
import dev.donutquine.swf.Matrix2x3;
import dev.donutquine.swf.textfields.TextFieldOriginal;
import dev.donutquine.utilities.RenderConfig;

import java.awt.*;

public class TextField extends DisplayObject {
    private boolean isInteractive;
    private float cursorBlinkTime;
    private Rect bounds;

    public static DisplayObject createTextField(TextFieldOriginal original) {
        TextField textField = new TextField();
        textField.id = original.getId();
        textField.bounds = original.getBounds();
        return textField;
    }

    @Override
    public boolean render(Matrix2x3 matrix, ColorTransform colorTransform, int renderConfigBits, float deltaTime) {
        Matrix2x3 matrixApplied = new Matrix2x3(this.getMatrix());
        matrixApplied.multiply(matrix);

        ColorTransform colorTransformApplied = new ColorTransform(this.getColorTransform());
        colorTransformApplied.multiply(colorTransform);

        int v45 = RenderConfig.getUnknownRenderModification(colorTransformApplied) | renderConfigBits;

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

    @Override
    public boolean isTextField() {
        return true;
    }

    private boolean shapeRender(Stage stage, Matrix2x3 matrix, ColorTransform colorTransform, int renderConfigBits, boolean noBounds) {
        Rect transformedBounds = new Rect(
            matrix.applyX(bounds.getLeft(), bounds.getTop()),
            matrix.applyY(bounds.getLeft(), bounds.getTop()),
            matrix.applyX(bounds.getRight(), bounds.getBottom()),
            matrix.applyY(bounds.getRight(), bounds.getBottom())
        );

        if (stage.startShape(transformedBounds, null, renderConfigBits)) {
            DrawApi drawApi = stage.getDrawApi();
            Color color = new Color(colorTransform.getRedMultiplier(), colorTransform.getGreenMultiplier(), colorTransform.getBlueMultiplier(), colorTransform.getAlpha() / 2);
            drawApi.drawRectangleLines(transformedBounds, color, 1);
            return true;
        }

        return false;
    }

    public void setId(int id) {
        this.id = id;
    }
}
