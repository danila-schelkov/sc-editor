package dev.donutquine.editor.renderer;

import dev.donutquine.editor.renderer.texture.RenderableTexture;
import dev.donutquine.editor.renderer.texture.Texture;
import dev.donutquine.math.ReadonlyRect;
import dev.donutquine.math.Rect;

// Note: actually I don't like separation of DrawApi and Renderer, but then I have to rename Renderer to something
public interface DrawApi {
    // Note: ReadonlyRect will be definitely a better choice
    void drawTexture(RenderableTexture texture, Rect rect);
}
