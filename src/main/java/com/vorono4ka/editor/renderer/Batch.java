package com.vorono4ka.editor.renderer;

import com.vorono4ka.editor.renderer.shader.Attribute;
import com.vorono4ka.editor.renderer.shader.Shader;
import com.vorono4ka.editor.renderer.texture.RenderableTexture;
import com.vorono4ka.utilities.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public abstract class Batch {
    protected static final int SIZE = 512;

    protected final Attribute[] attributes;
    protected final RenderableTexture texture;
    protected final Shader shader;
    protected final RenderStencilState stencilRenderingState;
    protected final int vertexSize, vertexSizeInBytes;

    protected FloatBuffer vertices;
    protected IntBuffer indices;

    /**
     * Represents max triangle count.
     */
    protected int capacity;

    protected int renderConfigBits;
    protected int triangleCount;
    protected int vertexIndex;
    protected int pointCount;

    public Batch(Shader shader, RenderableTexture texture, RenderStencilState stencilRenderingState) {
        this.shader = shader;
        this.texture = texture;
        this.stencilRenderingState = stencilRenderingState;

        this.capacity = SIZE;
        this.attributes = shader.getAttributes();
        this.vertexSize = calculateVertexSize(this.attributes);
        this.vertexSizeInBytes = calculateVertexSizeInBytes(this.attributes);

        this.vertices = BufferUtils.allocateDirectFloat(this.capacity * vertexSize * 3);
        this.indices = BufferUtils.allocateDirectInt(this.capacity * 3);
    }

    private static int calculateVertexSize(Attribute... attributes) {
        int vertexSize = 0;
        for (Attribute attribute : attributes) {
            vertexSize += attribute.size();
        }
        return vertexSize;
    }

    private static int calculateVertexSizeInBytes(Attribute... attributes) {
        int vertexSize = 0;
        for (Attribute attribute : attributes) {
            vertexSize += attribute.sizeInBytes();
        }
        return vertexSize;
    }

    public abstract void init();

    public abstract void delete();

    protected abstract void renderInternal();

    public void render() {
        if (this.texture != null) {
            this.texture.bind();
        }

        renderInternal();
    }

    public void reset() {
        this.vertices.clear();
        this.indices.clear();

        this.triangleCount = 0;
        this.pointCount = 0;
        this.vertexIndex = 0;
    }

    public boolean startShape(int renderConfigBits) {
        this.renderConfigBits = renderConfigBits;

        return true;
    }

    public void addVertex(float x, float y, float u, float v) {
        addVertex(new float[]{x, y, u, v});
    }

    public void addVertex(float x, float y, float u, float v, float redMul, float greenMul, float blueMul, float alpha, float redAdd, float greenAdd, float blueAdd) {
        addVertex(new float[]{x, y, u, v, redMul, greenMul, blueMul, alpha, redAdd, greenAdd, blueAdd});
    }

    public void addVertex(float... parameters) {
        if (parameters.length != vertexSize) {
            throw new IllegalStateException("Parameter count doesn't match to vertex attributes");
        }

        for (int i = 0; i < parameters.length; i++) {
            this.vertices.put(this.vertexIndex * vertexSize + i, parameters[i]);
        }

        this.vertexIndex++;
    }

    public void addTriangles(int count, int[] triangles) {
        if (!this.hasSpaceFor(count)) {
            this.capacity += SIZE;

            FloatBuffer vertices = this.vertices;
            this.vertices = BufferUtils.allocateDirectFloat(this.capacity * vertexSize * 3);
            this.vertices.put(vertices);

            this.vertices.clear();

            IntBuffer indices = this.indices;
            this.indices = BufferUtils.allocateDirectInt(this.capacity * 3);
            this.indices.put(indices);

            this.indices.clear();

            this.delete();
            this.init();
        }

        for (int i = 0; i < triangles.length; i++) {
            this.indices.put(this.triangleCount * 3 + i, triangles[i] + this.pointCount);
        }

        this.triangleCount += count;
        this.pointCount += count + 2;
    }

    public boolean hasSpaceFor(int triangleCount) {
        return this.triangleCount + triangleCount <= this.capacity;
    }

    public boolean hasSame(Shader shader, RenderableTexture texture) {
        return this.shader == shader && this.texture == texture;
    }

    public boolean hasSame(Shader shader, RenderableTexture texture, RenderStencilState stencilRenderingState) {
        return this.shader == shader && this.texture == texture && this.stencilRenderingState == stencilRenderingState;
    }

    public RenderStencilState getStencilRenderingState() {
        return stencilRenderingState;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
            "textureId=" + (this.texture != null ? this.texture.getId() : -1) +
            ", stencilRenderingState=" + this.stencilRenderingState +
            "}";
    }
}
