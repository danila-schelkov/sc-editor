package com.vorono4ka.editor.renderer;

import com.vorono4ka.editor.renderer.shader.Shader;
import com.vorono4ka.editor.renderer.texture.RenderableTexture;
import com.vorono4ka.math.ReadonlyRect;
import com.vorono4ka.math.Rect;

public interface Renderer {
    boolean startShape(Rect rect, RenderableTexture texture, int renderConfigBits);

    boolean startShape(Shader shader, Rect rect, RenderableTexture texture, int renderConfigBits, ReadonlyRect clipArea);

    void addTriangles(int count, int[] indices);

    void addVertex(float... vertexData);

    void addVertex(float x, float y, float u, float v, float redMul, float greenMul, float blueMul, float alpha, float redAdd, float greenAdd, float blueAdd);

    void setStencilRenderingState(int stencilRenderingState);

    RenderableTexture getTextureByIndex(int textureIndex);

    RenderableTexture getGradientTexture();
}
