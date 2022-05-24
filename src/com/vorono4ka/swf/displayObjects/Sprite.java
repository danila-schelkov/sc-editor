package com.vorono4ka.swf.displayObjects;

import com.vorono4ka.swf.ColorTransform;
import com.vorono4ka.swf.Matrix2x3;

import java.util.ArrayList;
import java.util.List;

public abstract class Sprite extends DisplayObject {
    protected List<DisplayObject> children;
    protected int frameSkippingType;
    protected int state;
    protected boolean isInteractive;

    public Sprite() {
        this.children = new ArrayList<>();
        this.isInteractive = false;
    }

    @Override
    public void render(Matrix2x3 matrix, ColorTransform colorTransform, int a4, float deltaTime) {
        Matrix2x3 matrixApplied = new Matrix2x3(this.getMatrix());
        matrixApplied.apply(matrix);

        ColorTransform colorTransformApplied = new ColorTransform(this.getColorTransform());
        colorTransformApplied.apply(colorTransform);

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

        int spriteRenderConfigBits = (this.getRenderConfigBits() & 0x3FF) | v45;
        for (DisplayObject displayObject : this.children) {
            displayObject.render(matrixApplied, colorTransformApplied, spriteRenderConfigBits, deltaTime);
        }
    }

    public void addChildAt(DisplayObject displayObject, int index) {
        this.children.add(index, displayObject);
    }

    public void removeChildAt(int index) {
        this.children.remove(index);
    }

    public int getChildrenCount() {
        return this.children.size();
    }
}
