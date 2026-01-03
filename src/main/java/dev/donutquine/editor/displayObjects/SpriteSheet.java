package dev.donutquine.editor.displayObjects;

import dev.donutquine.editor.renderer.DrawApi;
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
        renderConfigBits |= this.getRenderConfigBits();

        Stage stage = this.getStage();
        stage.addTriangles(2, INDICES);

        DrawApi drawApi = stage.getDrawApi();
        drawApi.drawTexture(this.texture, this.bounds);

        if (this.shouldDisplayPolygons) {
            for (ShapeDrawBitmapCommand command : this.drawBitmapCommands) {
                ShapeDrawBitmapCommandRenderer.renderUV(command, stage, UV_COLOR_TRANSFORM, renderConfigBits);
            }
        }

        return true;
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
