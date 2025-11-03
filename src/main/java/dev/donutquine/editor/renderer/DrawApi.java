package dev.donutquine.editor.renderer;

import dev.donutquine.editor.renderer.texture.RenderableTexture;
import dev.donutquine.math.ReadonlyRect;

import java.awt.*;

// Note: actually I don't like separation of DrawApi and Renderer, but then I have to rename Renderer to something
public interface DrawApi {
    // Note: ReadonlyRect will be definitely a better choice
    void drawTexture(RenderableTexture texture, ReadonlyRect rect);

    void drawTextureFlipped(RenderableTexture texture, ReadonlyRect rect);

    void drawRectangle(ReadonlyRect rect, Color color);

    void drawRectangleLines(ReadonlyRect rect, Color color, int thickness);

    void drawLine(float x1, float y1, float x2, float y2, float thickness, Color color);
}
