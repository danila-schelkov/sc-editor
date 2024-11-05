package com.vorono4ka.swf.displayobjects;

import com.vorono4ka.editor.renderer.Stage;
import com.vorono4ka.swf.ColorTransform;
import com.vorono4ka.swf.Matrix2x3;
import com.vorono4ka.swf.Tag;
import com.vorono4ka.swf.movieclips.MovieClipModifierOriginal;

public class MovieClipModifier extends DisplayObject {
    private final Tag tag;

    public MovieClipModifier(Tag tag) {
        this.tag = tag;
    }

    public static DisplayObject createModifier(MovieClipModifierOriginal original) {
        MovieClipModifier modifier = new MovieClipModifier(original.getTag());
        modifier.id = original.getId();
        return modifier;
    }

    @Override
    public boolean render(Matrix2x3 matrix, ColorTransform colorTransform, int a4, float deltaTime) {
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
}
