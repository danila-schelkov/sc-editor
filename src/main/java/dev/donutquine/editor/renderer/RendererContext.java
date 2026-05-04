package dev.donutquine.editor.renderer;

import dev.donutquine.math.ReadonlyRect;

public interface RendererContext {
    void printInfo();

    void setViewport(int x, int y, int width, int height);

    ReadonlyRect getViewport();

    void setRenderStencilState(RenderStencilState state, int ref, int mask, int previousOrRenderDepthStencilMask, int currentNestedStencilRefMask);

    boolean bindBlendMode(BlendMode blendMode);

    void clear(boolean color, boolean depth, boolean stencil);
    
    void clearColor(int argb);

    void clearColor(float r, float g, float b, float a);

    void clearStencil();
}
