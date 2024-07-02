package com.vorono4ka.editor.renderer;

import com.jogamp.opengl.GL3;

import java.util.Arrays;

public class Batch {
    public static final int SIZE = 512;

    private final Attribute[] attributes;
    private final Texture texture;
    private final Shader shader;
    private final int stencilRenderingState;
    private final int vertexSize, vertexSizeInBytes;

    private float[] vertices;
    private int[] indices;

    /**
     * Represents max triangle count.
     */
    private int capacity;

    private int renderConfigBits;
    private int triangleCount;
    private int vertexIndex;
    private int pointCount;

    private VAO vao;
    private VBO vbo;
    private EBO ebo;

    public Batch(Shader shader, Texture texture, int stencilRenderingState) {
        this.shader = shader;
        this.texture = texture;
        this.stencilRenderingState = stencilRenderingState;

        this.capacity = SIZE;
        this.attributes = shader.getAttributes();
        this.vertexSize = calculateVertexSize(this.attributes);
        this.vertexSizeInBytes = calculateVertexSizeInBytes(this.attributes);

        this.vertices = new float[this.capacity * vertexSize * 3];
        this.indices = new int[this.capacity * 3];
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

    public void init(GL3 gl) {
        this.vao = new VAO(gl);
        this.vao.bind();

        this.vbo = new VBO(gl, this.vertices, GL3.GL_DYNAMIC_DRAW);
        this.ebo = new EBO(gl, this.indices, GL3.GL_DYNAMIC_DRAW);
        this.ebo.bind();

        linkAttributes();
        this.vao.bind();

        this.vao.unbind();
        this.ebo.unbind();
    }

    public void render(GL3 gl) {
        if (this.texture != null) {
            this.texture.bind();
        }

        this.vbo.subData(0, this.vertices);
        this.ebo.subData(0, this.indices);

        this.vao.bind();
        gl.glDrawElements(GL3.GL_TRIANGLES, this.triangleCount * 3, GL3.GL_UNSIGNED_INT, 0);
        this.vao.unbind();
    }

    public void reset() {
        Arrays.fill(this.vertices, 0);
        Arrays.fill(this.indices, 0);

        this.triangleCount = 0;
        this.pointCount = 0;
        this.vertexIndex = 0;
    }

    public void delete() {
        this.vao.delete();
        this.vbo.delete();
        this.ebo.delete();
    }

    public boolean startShape(int renderConfigBits) {
        this.renderConfigBits = renderConfigBits;

        return true;
    }

    public void addVertex(float x, float y, float u, float v) {
        addVertex(new Float[]{x, y, u, v});
    }

    public void addVertex(float x, float y, float u, float v, float redMul, float greenMul, float blueMul, float alpha, float redAdd, float greenAdd, float blueAdd) {
        addVertex(new Float[]{x, y, u, v, redMul, greenMul, blueMul, alpha, redAdd, greenAdd, blueAdd});
    }

    public void addVertex(Float... parameters) {
        if (parameters.length != vertexSize) {
            throw new IllegalStateException("Parameter count doesn't match to vertex attributes");
        }

        for (int i = 0; i < parameters.length; i++) {
            this.vertices[this.vertexIndex * vertexSize + i] = parameters[i];
        }

        this.vertexIndex++;
    }

    public void addTriangles(int count, int[] triangles) {
        if (!this.hasSpaceFor(count)) {
            this.capacity += SIZE;

            float[] vertices = this.vertices;
            this.vertices = new float[this.capacity * vertexSize * 3];
            System.arraycopy(vertices, 0, this.vertices, 0, vertices.length);

            int[] indices = this.indices;
            this.indices = new int[this.capacity * 3];
            System.arraycopy(indices, 0, this.indices, 0, indices.length);

            this.delete();
            this.init(Stage.getInstance().getGl());
        }

        for (int i = 0; i < triangles.length; i++) {
            this.indices[this.triangleCount * 3 + i] = triangles[i] + this.pointCount;
        }

        this.triangleCount += count;
        this.pointCount += count + 2;
    }

    public boolean hasSpaceFor(int trianglesCount) {
        return this.triangleCount + trianglesCount <= this.capacity;
    }

    public boolean hasSame(Shader shader, Texture texture) {
        return this.shader == shader && this.texture == texture;
    }

    public boolean hasSame(Shader shader, Texture texture, int stencilRenderingState) {
        return this.shader == shader && this.texture == texture && this.stencilRenderingState == stencilRenderingState;
    }

    @Override
    public String toString() {
        return "Batch {" +
            "textureId=" + (this.texture != null ? this.texture.getId() : -1) +
            ", stencilRenderingState=" + this.stencilRenderingState +
            "}";
    }

    public int getStencilRenderingState() {
        return stencilRenderingState;
    }

    private void linkAttributes() {
        int offset = 0;
        for (Attribute attribute : attributes) {
            this.vao.linkAttrib(this.vbo, attribute.layout(), attribute.size(), attribute.glType(), vertexSizeInBytes, offset);
            offset += attribute.sizeInBytes();
        }
    }
}
