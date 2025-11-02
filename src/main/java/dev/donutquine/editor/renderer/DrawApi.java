package dev.donutquine.editor.renderer;

import dev.donutquine.editor.renderer.texture.RenderableTexture;
import dev.donutquine.math.Rect;

import java.awt.*;

// Note: actually I don't like separation of DrawApi and Renderer, but then I have to rename Renderer to something
public interface DrawApi {
    // Note: ReadonlyRect will be definitely a better choice
    void drawTexture(RenderableTexture texture, Rect rect);

    void drawRectangle(Rect rect, Color color);
}
