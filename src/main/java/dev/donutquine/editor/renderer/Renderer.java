package dev.donutquine.editor.renderer;

import dev.donutquine.math.ReadonlyRect;

// Note: more like GPU context, not actually rendering anything, but rather changing states
public interface Renderer {
    void printInfo();

    void setViewport(int x, int y, int width, int height);

    ReadonlyRect getViewport();

    void setRenderStencilState(RenderStencilState state);

    boolean bindBlendMode(BlendMode blendMode);

    void clear(int flags);

    void clearColor(float r, float g, float b, float a);

    void clearStencil();
}
