package dev.donutquine.editor.renderer;

import dev.donutquine.editor.renderer.shader.Shader;
import dev.donutquine.editor.renderer.texture.RenderableTexture;
import dev.donutquine.math.ReadonlyRect;

public interface Renderer {
    void reset();

    void beginRendering();

    void endRendering();

    /// Returns whether to render the given object or not
    boolean startShape(Shader shader, ReadonlyRect rect, RenderableTexture texture, int renderConfigBits, ReadonlyRect clipArea);

    void addTriangles(int count, int[] indices);

    void addVertex(float... vertexData);

    void setStencilRenderingState(Shader shader, RenderStencilState stencilRenderingState);

    void flush();
}
