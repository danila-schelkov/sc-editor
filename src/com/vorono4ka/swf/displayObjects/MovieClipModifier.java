package com.vorono4ka.swf.displayObjects;

import com.vorono4ka.editor.renderer.Stage;
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
        Stage stage = this.getStage();

        int stencilRenderingState;
        switch (this.tag) {
            case MODIFIER_STATE_2 -> stencilRenderingState = 2;
            case MODIFIER_STATE_3 -> stencilRenderingState = 3;
            case MODIFIER_STATE_4 -> stencilRenderingState = 4;
            default -> {
                return false;
            }
        }

        stage.setStencilRenderingState(stencilRenderingState);

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

    public void setId(int id) {
        this.id = id;
    }
}
