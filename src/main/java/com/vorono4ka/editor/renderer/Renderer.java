package com.vorono4ka.editor.renderer;

import com.vorono4ka.math.ReadonlyRect;

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
