package dev.donutquine.editor.renderer;

import dev.donutquine.editor.renderer.texture.RenderableTexture;
import dev.donutquine.math.ReadonlyRect;
import dev.donutquine.math.Rect;
import dev.donutquine.renderer.impl.swf.objects.DisplayObject;
import dev.donutquine.renderer.impl.swf.objects.StageSprite;

public interface Stage {
    void reset();

    void render(float deltaTime);

    boolean startShape(ReadonlyRect rect, RenderableTexture texture, int renderConfigBits);

    void addTriangles(int count, int[] indices);

    void addVertex(float x, float y, float u, float v, float redMul, float greenMul, float blueMul, float alpha, float redAdd, float greenAdd, float blueAdd);

    void setStencilRenderingState(RenderStencilState state);

    RenderableTexture getTextureByIndex(int textureIndex);

    DrawApi getDrawApi();

    StageSprite getStageSprite();

    /// MUST be callable without any stage (rendering) context
    Rect getDisplayObjectBounds(DisplayObject displayObject);

    float getPixelSize();
}
