package dev.donutquine.editor.displayObjects;

import dev.donutquine.editor.renderer.Stage;
import dev.donutquine.editor.renderer.texture.RenderableTexture;
import dev.donutquine.math.Rect;
import dev.donutquine.renderer.impl.swf.objects.DisplayObject;
import dev.donutquine.renderer.impl.swf.objects.ShapeDrawBitmapCommandRenderer;
import dev.donutquine.swf.ColorTransform;
import dev.donutquine.swf.Matrix2x3;
import dev.donutquine.swf.shapes.ShapeDrawBitmapCommand;

import java.util.List;

public class SpriteSheet extends DisplayObject {
    private static final int[] INDICES = {0, 1, 2, 0, 2, 3};
    private static final ColorTransform UV_COLOR_TRANSFORM = new ColorTransform((byte) 255, (byte) 0, (byte) 0, (byte) 128, (byte) 255, (byte) 0, (byte) 0);

    private final RenderableTexture texture;
    private final Rect bounds;
    private final List<ShapeDrawBitmapCommand> drawBitmapCommands;

    private boolean shouldDisplayPolygons;

    public SpriteSheet(RenderableTexture texture, List<ShapeDrawBitmapCommand> drawBitmapCommands) {
        this.texture = texture;
        this.drawBitmapCommands = drawBitmapCommands;

        float halfWidth = this.texture.getWidth() / 2f;
        float halfHeight = this.texture.getHeight() / 2f;

        this.bounds = new Rect(-halfWidth, -halfHeight, halfWidth, halfHeight);
    }

    @Override
    public boolean render(Matrix2x3 matrix, ColorTransform colorTransform, int renderConfigBits, float deltaTime) {
        Stage renderer = this.getStage();
        if (renderer.startShape(this.bounds, this.texture, 0)) {
            renderer.addTriangles(2, INDICES);

            renderer.addVertex(this.bounds.getLeft(), this.bounds.getTop(), 0, 0, 1, 1, 1, 1, 0, 0, 0);
            renderer.addVertex(this.bounds.getLeft(), this.bounds.getBottom(), 0, 1, 1, 1, 1, 1, 0, 0, 0);
            renderer.addVertex(this.bounds.getRight(), this.bounds.getBottom(), 1, 1, 1, 1, 1, 1, 0, 0, 0);
            renderer.addVertex(this.bounds.getRight(), this.bounds.getTop(), 1, 0, 1, 1, 1, 1, 0, 0, 0);

            if (this.shouldDisplayPolygons) {
                for (ShapeDrawBitmapCommand command : this.drawBitmapCommands) {
                    ShapeDrawBitmapCommandRenderer.renderUV(command, renderer, UV_COLOR_TRANSFORM, 0);
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
        return texture.getWidth();
    }

    public int getHeight() {
        return texture.getHeight();
    }

    public void setShouldDisplayPolygons(boolean shouldDisplayPolygons) {
        this.shouldDisplayPolygons = shouldDisplayPolygons;
    }
}
