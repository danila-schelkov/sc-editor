package com.vorono4ka.swf.displayObjects;

import com.vorono4ka.swf.ColorTransform;
import com.vorono4ka.swf.Matrix2x3;
import com.vorono4ka.swf.constants.Tag;

public class MovieClipModifier extends DisplayObject {
    private final Tag tag;

    public MovieClipModifier(Tag tag) {
        this.tag = tag;
    }

    @Override
    public boolean render(Matrix2x3 matrix, ColorTransform colorTransform, int a4, float deltaTime) {  // TODO
        return false;
    }

    @Override
    public boolean collisionRender(Matrix2x3 matrix) {
        return false;
    }

    @Override
    public boolean isMovieClipModifier() {
        return true;
    }
}
