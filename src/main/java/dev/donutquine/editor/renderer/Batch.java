package dev.donutquine.editor.renderer;

import dev.donutquine.editor.renderer.shader.Attribute;
import dev.donutquine.editor.renderer.shader.Shader;
import dev.donutquine.editor.renderer.texture.RenderableTexture;
import dev.donutquine.utilities.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Batch {
    protected static final int SIZE = 512;

    private final VertexBufferProducer vertexBufferProducer;
    private final Attribute[] attributes;
    private final RenderableTexture texture;
    private final Shader shader;
    private final RenderStencilState stencilRenderingState;
    private final int vertexSize;

    private VertexBuffer vertexBuffer;
    private FloatBuffer vertices;
    private IntBuffer indices;

    /**
     * Represents max triangle count.
     */
    private int capacity;

    private int renderConfigBits;
    private int triangleCount;
    private int vertexIndex;
    private int pointCount;

    private boolean isDirty;

    public Batch(Shader shader, RenderableTexture texture, RenderStencilState stencilRenderingState, VertexBufferProducer vertexBufferProducer) {
        this.shader = shader;
        this.texture = texture;
        this.stencilRenderingState = stencilRenderingState;
        this.vertexBufferProducer = vertexBufferProducer;

        this.capacity = SIZE;
        this.attributes = shader.getAttributes();
        this.vertexSize = calculateVertexSize(this.attributes);

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

    public void init() {
        this.vertexBuffer = this.vertexBufferProducer.createVertexBuffer(attributes);
        this.vertexBuffer.init(this.vertices.capacity(), this.indices.capacity());
    }

    public void delete() {
        if (this.vertexBuffer != null) {
            this.vertexBuffer.delete();
        }
    }

    public void render() {
        if (this.texture != null) {
            this.texture.bind();
        }

        if (this.isDirty) {
            this.vertexBuffer.updateVertices(0, this.vertices);
            this.vertexBuffer.updateIndices(0, this.indices);
            this.isDirty = false;
        }

        this.vertexBuffer.render(0, this.triangleCount * 3);
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
        this.isDirty = true;
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
        this.isDirty = true;
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

    @FunctionalInterface
    public interface VertexBufferProducer {
        VertexBuffer createVertexBuffer(Attribute... attributes);
    }
}
