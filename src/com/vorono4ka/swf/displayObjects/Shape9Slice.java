package com.vorono4ka.swf.displayObjects;

import com.vorono4ka.math.Rect;
import com.vorono4ka.swf.ColorTransform;
import com.vorono4ka.swf.Matrix2x3;

public class Shape9Slice extends Shape {
    private final Rect scalingGrid;

    public Shape9Slice(Rect scalingGrid) {
        this.scalingGrid = scalingGrid;
    }

    @Override
    public void render(Matrix2x3 matrix, ColorTransform colorTransform, int a4, float deltaTime) {

    }

    @Override
    public void collisionRender(Matrix2x3 matrix, ColorTransform colorTransform) {

    }

    @Override
    public boolean isShape() {
        return true;
    }
}
