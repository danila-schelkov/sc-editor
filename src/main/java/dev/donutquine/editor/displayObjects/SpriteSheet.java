package dev.donutquine.editor.displayObjects;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import dev.donutquine.editor.renderer.DrawApi;
import dev.donutquine.editor.renderer.Stage;
import dev.donutquine.editor.renderer.texture.RenderableTexture;
import dev.donutquine.math.Rect;
import dev.donutquine.renderer.impl.swf.objects.DisplayObject;
import dev.donutquine.swf.ColorTransform;
import dev.donutquine.swf.Matrix2x3;
import dev.donutquine.swf.shapes.ShapeDrawBitmapCommand;

public class SpriteSheet extends DisplayObject {
    private static final int[] INDICES = {0, 1, 2, 0, 2, 3};

    private final int index;
    private final RenderableTexture texture;
    private final Rect bounds;
    private final @NotNull List<ShapeDrawBitmapCommand> drawBitmapCommands;

    public SpriteSheet(int index, RenderableTexture texture, @NotNull List<ShapeDrawBitmapCommand> drawBitmapCommands) {
        this.index = index;
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

        return true;
    }

    @Override
    public boolean collisionRender(Matrix2x3 matrix) {
        return false;
    }

    public int getIndex() {
        return this.index;
    }

    public int getWidth() {
        return texture.getWidth();
    }

    public int getHeight() {
        return texture.getHeight();
    }

    public RenderableTexture getTexture() {
        return texture;
    }

    public List<ShapeDrawBitmapCommand> getDrawBitmapCommands() {
        return drawBitmapCommands;
    }
}
