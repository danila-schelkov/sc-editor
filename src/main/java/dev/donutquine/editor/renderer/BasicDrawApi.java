package dev.donutquine.editor.renderer;

import dev.donutquine.editor.renderer.gl.GLConstants;
import dev.donutquine.editor.renderer.shader.Attribute;
import dev.donutquine.editor.renderer.shader.Shader;
import dev.donutquine.editor.renderer.texture.RenderableTexture;
import dev.donutquine.math.Rect;
import dev.donutquine.resources.AssetManager;

import java.awt.*;
import java.nio.FloatBuffer;

public class BasicDrawApi implements DrawApi {
    private static final int[] RECT_INDICES = {0, 1, 2, 0, 2, 3};
    
    private final Renderer renderer;
    private final Shader textureShader;
    private final Shader colorShader;

    public BasicDrawApi(Renderer renderer, AssetManager assetManager) {
        this.renderer = renderer;

        this.textureShader = assetManager.getShader(
            "texture.vertex.glsl",
            "texture.fragment.glsl",
            new Attribute(0, 2, Float.BYTES, GLConstants.GL_FLOAT),
            new Attribute(1, 2, Float.BYTES, GLConstants.GL_FLOAT)
        );

        this.colorShader = assetManager.getShader(
            "color.vertex.glsl",
            "color.fragment.glsl",
            new Attribute(0, 2, Float.BYTES, GLConstants.GL_FLOAT),
            new Attribute(1, 4, Float.BYTES, GLConstants.GL_FLOAT)
        );
    }

    @Override
    public void drawTexture(RenderableTexture texture, Rect rect) {
        if (this.renderer.startShape(textureShader, rect, texture, 0, null)) {
            this.renderer.addTriangles(2, RECT_INDICES);

            this.renderer.addVertex(rect.getLeft(), rect.getTop(), 0, 1);
            this.renderer.addVertex(rect.getLeft(), rect.getBottom(), 0, 0);
            this.renderer.addVertex(rect.getRight(), rect.getBottom(), 1, 0);
            this.renderer.addVertex(rect.getRight(), rect.getTop(), 1, 1);
        }
    }

    @Override
    public void drawRectangle(Rect rect, Color color) {
        if (this.renderer.startShape(colorShader, rect, null, 0, null)) {
            this.renderer.addTriangles(2, RECT_INDICES);

            float[] rgba = new float[4];
            rgba[3] = 1;
            color.getColorComponents(rgba);

            this.renderer.addVertex(rect.getLeft(), rect.getTop(), rgba[0], rgba[1], rgba[2], rgba[3]);
            this.renderer.addVertex(rect.getLeft(), rect.getBottom(), rgba[0], rgba[1], rgba[2], rgba[3]);
            this.renderer.addVertex(rect.getRight(), rect.getBottom(), rgba[0], rgba[1], rgba[2], rgba[3]);
            this.renderer.addVertex(rect.getRight(), rect.getTop(), rgba[0], rgba[1], rgba[2], rgba[3]);
        }
    }

    public void setPMVMatrix(FloatBuffer matrixBuffer) {
        this.colorShader.bind();
        this.colorShader.setUniformMatrix4f("pmv", matrixBuffer);
        // this.colorShader.bind();
        this.textureShader.bind();
        this.textureShader.setUniformMatrix4f("pmv", matrixBuffer);
        this.textureShader.unbind();
    }
}
