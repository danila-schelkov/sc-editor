package com.vorono4ka.editor.renderer.displayObjects;

import com.vorono4ka.editor.renderer.Stage;
import com.vorono4ka.math.Rect;
import com.vorono4ka.swf.ColorTransform;
import com.vorono4ka.swf.Matrix2x3;
import com.vorono4ka.swf.displayObjects.DisplayObject;
import com.vorono4ka.swf.displayObjects.original.SWFTexture;

public class SpriteSheet extends DisplayObject {
    private static final int[] INDICES = {0, 1, 2, 0, 2, 3};

    private final SWFTexture texture;
    private final float[][] vertices;
    private final Rect bounds;

    public SpriteSheet(SWFTexture texture) {
        this.texture = texture;

        float halfWidth = texture.getWidth() / 2f;
        float halfHeight = texture.getHeight() / 2f;

        this.vertices = new float[][] {
            {-halfWidth, -halfHeight, 0, 0},
            {-halfWidth, halfHeight, 0, 1},
            {halfWidth, halfHeight, 1, 1},
            {halfWidth, -halfHeight, 1, 0}
        };
        this.bounds = new Rect(-halfWidth, -halfHeight, halfWidth, halfHeight);
    }

    @Override
    public void render(Matrix2x3 matrix, ColorTransform colorTransform, int a4, float deltaTime) {
        Stage stage = Stage.getInstance();
        if (stage.startShape(this.bounds, this.texture.getImage(), 0)) {
            stage.addTriangles(2, INDICES);

            for (float[] vertex : this.vertices) {
                stage.addVertex(vertex[0], vertex[1], vertex[2], vertex[3], 1, 1, 1, 0, 0, 0, 1);
            }
        }
    }

    @Override
    public void collisionRender(Matrix2x3 matrix, ColorTransform colorTransform) {

    }
}
