package com.vorono4ka.editor.renderer;

import com.jogamp.opengl.GL3;
import com.vorono4ka.editor.renderer.texture.Texture;
import com.vorono4ka.utilities.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Batch {
    public static final int SIZE = 512;

    private final Attribute[] attributes;
    private final Texture texture;
    private final Shader shader;
    private final int stencilRenderingState;
    private final int vertexSize, vertexSizeInBytes;

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

    public void init(GL3 gl) {
        this.vao = new VAO(gl);
        this.vao.bind();

        this.vbo = new VBO(gl, this.vertices.capacity(), GL3.GL_DYNAMIC_DRAW);
        this.ebo = new EBO(gl, this.indices.capacity(), GL3.GL_DYNAMIC_DRAW);
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
        this.vertices.clear();
        this.indices.clear();

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
            this.init(Stage.getInstance().getGl());
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
