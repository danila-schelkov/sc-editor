package dev.donutquine.editor.renderer;

import dev.donutquine.editor.renderer.shader.Shader;
import dev.donutquine.editor.renderer.texture.RenderableTexture;
import dev.donutquine.math.ReadonlyRect;
import dev.donutquine.math.Rect;

// TODO: maybe should be an abstract class
public interface Stage {
    void render(float deltaTime);

    boolean startShape(Rect rect, RenderableTexture texture, int renderConfigBits);

    boolean startShape(Shader shader, Rect rect, RenderableTexture texture, int renderConfigBits, ReadonlyRect clipArea);

    void addTriangles(int count, int[] indices);

    void addVertex(float... vertexData);

    void addVertex(float x, float y, float u, float v, float redMul, float greenMul, float blueMul, float alpha, float redAdd, float greenAdd, float blueAdd);

    void setStencilRenderingState(RenderStencilState stencilRenderingState);

    RenderableTexture getTextureByIndex(int textureIndex);
}
