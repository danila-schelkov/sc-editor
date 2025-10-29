package dev.donutquine.editor.renderer;

import dev.donutquine.editor.renderer.shader.Attribute;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public abstract class VertexBuffer {
    protected final Attribute[] attributes;
    protected final int vertexSizeInBytes;

    protected final DrawMode drawMode;

    public VertexBuffer(Attribute... attributes) {
        this(DrawMode.TRIANGLES, attributes);
    }

    public VertexBuffer(DrawMode drawMode, Attribute... attributes) {
        this.drawMode = drawMode;
        this.attributes = attributes;

        this.vertexSizeInBytes = calculateVertexSizeInBytes(attributes);
    }

    public abstract void init(int vertexCapacity, int indexCapacity);

    public abstract void render(int offset, int count);

    public abstract void prepareRender();

    public abstract void renderPart(int offset, int count);

    public abstract void finishRender();

    public abstract void updateVertices(int offset, FloatBuffer vertices);

    public abstract void updateIndices(int offset, IntBuffer indices);

    public abstract void delete();

    private static int calculateVertexSizeInBytes(Attribute... attributes) {
        int vertexSize = 0;
        for (Attribute attribute : attributes) {
            vertexSize += attribute.sizeInBytes();
        }
        return vertexSize;
    }
}
