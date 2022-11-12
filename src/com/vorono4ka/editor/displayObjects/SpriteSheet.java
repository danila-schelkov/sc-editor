package com.vorono4ka.editor.displayObjects;

import com.vorono4ka.editor.Main;
import com.vorono4ka.editor.renderer.Stage;
import com.vorono4ka.math.Rect;
import com.vorono4ka.swf.ColorTransform;
import com.vorono4ka.swf.Matrix2x3;
import com.vorono4ka.swf.displayObjects.DisplayObject;
import com.vorono4ka.swf.displayObjects.ShapeDrawBitmapCommand;
import com.vorono4ka.swf.originalObjects.SWFTexture;

import java.util.List;

public class SpriteSheet extends DisplayObject {
    private static final int[] INDICES = {0, 1, 2, 0, 2, 3};

    private final SWFTexture texture;
    private final float[][] vertices;
    private final Rect bounds;
    private List<ShapeDrawBitmapCommand> drawBitmapCommands;

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
    public boolean render(Matrix2x3 matrix, ColorTransform colorTransform, int a4, float deltaTime) {
        Stage stage = this.getStage();
        if (stage.startShape(this.bounds, this.texture, 0)) {
            stage.addTriangles(2, INDICES);

            for (float[] vertex : this.vertices) {
                stage.addVertex(vertex[0], vertex[1], vertex[2], vertex[3], 1, 1, 1, 0, 0, 0, 1);
            }

            if (Main.editor.shouldDisplayPolygons()) {
                for (ShapeDrawBitmapCommand drawBitmapCommand : this.drawBitmapCommands) {
                    drawBitmapCommand.renderUV(stage, 0);
                }
            }

            return true;
        }

        return false;
    }

    @Override
    public boolean collisionRender(Matrix2x3 matrix) {
        return false;
    }

    public void setBitmaps(List<ShapeDrawBitmapCommand> commands) {
        this.drawBitmapCommands = commands;
    }
}
