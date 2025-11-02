package dev.donutquine.editor.renderer;

import dev.donutquine.editor.renderer.gl.GLConstants;
import dev.donutquine.editor.renderer.shader.Attribute;
import dev.donutquine.editor.renderer.shader.Shader;
import dev.donutquine.editor.renderer.texture.RenderableTexture;
import dev.donutquine.math.Rect;
import dev.donutquine.resources.AssetManager;

public class BasicDrawApi implements DrawApi {
    private static final int[] RECT_INDICES = {0, 1, 2, 0, 2, 3};
    
    private final Renderer renderer;
    private final Shader textureShader;

    public BasicDrawApi(Renderer renderer, AssetManager assetManager) {
        this.renderer = renderer;

        this.textureShader = assetManager.getShader(
            "texture.vertex.glsl",
            "texture.fragment.glsl",
            new Attribute(0, 2, Float.BYTES, GLConstants.GL_FLOAT),
            new Attribute(1, 2, Float.BYTES, GLConstants.GL_FLOAT)
        );
    }

    @Override
    public void drawTexture(RenderableTexture texture, Rect rect) {
        if (this.renderer.startShape(textureShader, rect, texture, 0, null)) {
            this.renderer.addTriangles(2, RECT_INDICES);

            this.renderer.addVertex(rect.getLeft(), rect.getTop(), 0, 0);
            this.renderer.addVertex(rect.getLeft(), rect.getBottom(), 0, 1);
            this.renderer.addVertex(rect.getRight(), rect.getBottom(), 1, 1);
            this.renderer.addVertex(rect.getRight(), rect.getTop(), 1, 0);
        }
    }
}
