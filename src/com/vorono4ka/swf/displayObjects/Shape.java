package com.vorono4ka.swf.displayObjects;

import com.vorono4ka.swf.ColorTransform;
import com.vorono4ka.swf.Matrix2x3;

public class Shape extends Sprite {
    @Override
    public void render(Matrix2x3 matrix, ColorTransform colorTransform, int a3, float a4) {

    }

    @Override
    public void collisionRender(Matrix2x3 matrix, ColorTransform colorTransform, int a3, float a4) {

    }

    @Override
    public boolean isShape() {
        return true;
    }
}
