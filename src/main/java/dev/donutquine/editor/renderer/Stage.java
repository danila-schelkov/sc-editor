package dev.donutquine.editor.renderer;

import dev.donutquine.editor.renderer.texture.RenderableTexture;
import dev.donutquine.math.Rect;

public interface Stage {
    void reset();

    void render(float deltaTime);

    boolean startShape(Rect rect, RenderableTexture texture, int renderConfigBits);

    void addTriangles(int count, int[] indices);

    void addVertex(float x, float y, float u, float v, float redMul, float greenMul, float blueMul, float alpha, float redAdd, float greenAdd, float blueAdd);

    void setStencilRenderingState(RenderStencilState state);

    RenderableTexture getTextureByIndex(int textureIndex);

    DrawApi getDrawApi();
}
