package com.vorono4ka.editor.renderer.displayObjects;

import com.vorono4ka.editor.renderer.Stage;
import com.vorono4ka.swf.ColorTransform;
import com.vorono4ka.swf.Matrix2x3;
import com.vorono4ka.swf.displayObjects.DisplayObject;
import com.vorono4ka.swf.displayObjects.original.SWFTexture;

public class SpriteSheet extends DisplayObject {
    private static final int[] INDICES = {0, 1, 2, 0, 2, 3};

    private final SWFTexture texture;
    private final float[][] vertices;

    public SpriteSheet(SWFTexture texture) {
        this.texture = texture;
        this.vertices = new float[][] {
            {texture.getWidth() / -2f, texture.getHeight() / -2f, 0, 0},
            {texture.getWidth() / -2f, texture.getHeight() / 2f, 0, 1},
            {texture.getWidth() / 2f, texture.getHeight() / 2f, 1, 1},
            {texture.getWidth() / 2f, texture.getHeight() / -2f, 1, 0}
        };
    }

    @Override
    public void render(Matrix2x3 matrix, ColorTransform colorTransform, int a4, float deltaTime) {
        Stage stage = Stage.getInstance();
        if (stage.startShape(0, 0, 0, 0, this.texture.getImage(), 0)) {
            stage.addTriangles(2, INDICES);

            for (float[] vertex : this.vertices) {
                stage.addVertex(vertex[0], vertex[1], vertex[2], vertex[3]);
            }
        }
    }

    @Override
    public void collisionRender(Matrix2x3 matrix, ColorTransform colorTransform) {

    }
}
