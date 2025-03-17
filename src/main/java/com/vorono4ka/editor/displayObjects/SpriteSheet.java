package com.vorono4ka.editor.displayObjects;

import com.vorono4ka.editor.Main;
import com.vorono4ka.editor.renderer.Renderer;
import com.vorono4ka.math.Rect;
import com.vorono4ka.swf.ColorTransform;
import com.vorono4ka.editor.renderer.impl.texture.GLImage;
import com.vorono4ka.swf.Matrix2x3;
import com.vorono4ka.renderer.impl.swf.objects.DisplayObject;
import com.vorono4ka.renderer.impl.swf.objects.ShapeDrawBitmapCommandRenderer;
import com.vorono4ka.swf.shapes.ShapeDrawBitmapCommand;

import java.util.List;

public class SpriteSheet extends DisplayObject {
    private static final int[] INDICES = {0, 1, 2, 0, 2, 3};

    private final GLImage image;
    private final Rect bounds;
    private final List<ShapeDrawBitmapCommand> drawBitmapCommands;

    public SpriteSheet(GLImage image, List<ShapeDrawBitmapCommand> drawBitmapCommands) {
        this.image = image;
        this.drawBitmapCommands = drawBitmapCommands;

        float halfWidth = this.image.getWidth() / 2f;
        float halfHeight = this.image.getHeight() / 2f;

        this.bounds = new Rect(-halfWidth, -halfHeight, halfWidth, halfHeight);
    }

    @Override
    public boolean render(Matrix2x3 matrix, ColorTransform colorTransform, int a4, float deltaTime) {
        Renderer renderer = this.getRenderer();
        if (renderer.startShape(this.bounds, this.image.getTexture(), 0)) {
            renderer.addTriangles(2, INDICES);

            renderer.addVertex(this.bounds.getLeft(), this.bounds.getTop(), 0, 0, 1, 1, 1, 1, 0, 0, 0);
            renderer.addVertex(this.bounds.getLeft(), this.bounds.getBottom(), 0, 1, 1, 1, 1, 1, 0, 0, 0);
            renderer.addVertex(this.bounds.getRight(), this.bounds.getBottom(), 1, 1, 1, 1, 1, 1, 0, 0, 0);
            renderer.addVertex(this.bounds.getRight(), this.bounds.getTop(), 1, 0, 1, 1, 1, 1, 0, 0, 0);

            if (Main.editor.shouldDisplayPolygons()) {
                for (ShapeDrawBitmapCommand command : this.drawBitmapCommands) {
                    ShapeDrawBitmapCommandRenderer.renderUV(command, renderer, 0);
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

    public int getWidth() {
        return image.getWidth();
    }

    public int getHeight() {
        return image.getHeight();
    }
}
