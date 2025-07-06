package dev.donutquine.renderer.impl.swf.objects;

import dev.donutquine.editor.renderer.Stage;
import dev.donutquine.math.Rect;
import dev.donutquine.swf.ColorTransform;
import dev.donutquine.swf.Matrix2x3;
import dev.donutquine.swf.textfields.TextFieldOriginal;
import dev.donutquine.utilities.RenderConfig;

public class TextField extends DisplayObject {
    private boolean isInteractive;
    private float cursorBlinkTime;
    private Rect bounds;

    public static DisplayObject createTextField(TextFieldOriginal original) {
        TextField textField = new TextField();
        textField.id = original.getId();
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
        return false;
    }

    public void setId(int id) {
        this.id = id;
    }
}
