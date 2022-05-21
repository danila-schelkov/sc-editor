package com.vorono4ka.editor.renderer;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL3;
import com.vorono4ka.swf.GLImage;

import java.util.Arrays;

public class Batch {
    private static final int POSITION_SIZE = 2;
    private static final int TEX_COORDS_SIZE = 2;
    private static final int VERTEX_SIZE = POSITION_SIZE + TEX_COORDS_SIZE;

    private static final int POSITION_SIZE_BYTES = POSITION_SIZE * Float.BYTES;
    private static final int TEX_COORDS_SIZE_BYTES = TEX_COORDS_SIZE * Float.BYTES;
    private static final int VERTEX_SIZE_BYTES = POSITION_SIZE_BYTES + TEX_COORDS_SIZE_BYTES;
    public static final int SIZE = 512;

    private float[] vertices;
    private int[] indices;
    private int maxSize;  // triangles count

    private GLImage image;
    private int trianglesCount;
    private int pointsCount;
    private int vertexIndex;
    private int renderConfigBits;
    private VAO vao;
    private VBO vbo;
    private EBO ebo;

    public Batch(GLImage image) {
        this.image = image;

        this.maxSize = SIZE;

        this.vertices = new float[this.maxSize * VERTEX_SIZE * 3];
        this.indices = new int[this.maxSize * 3];
    }

    public void init(GL3 gl) {
        this.vao = new VAO(gl);
        this.vao.bind();

        this.vbo = new VBO(gl, this.vertices, GL3.GL_DYNAMIC_DRAW);
        this.ebo = new EBO(gl, this.indices, GL3.GL_DYNAMIC_DRAW);
        this.ebo.bind();
        this.vao.linkAttrib(this.vbo, 0, POSITION_SIZE, GL3.GL_FLOAT, VERTEX_SIZE_BYTES, 0);
        this.vao.linkAttrib(this.vbo, 1, TEX_COORDS_SIZE, GL3.GL_FLOAT, VERTEX_SIZE_BYTES, POSITION_SIZE_BYTES);
        this.vao.unbind();
        this.ebo.unbind();
    }

    public void render(GL3 gl) {
        if (this.image != null) {
            this.image.bind();
        }

        this.vbo.subData(0, this.vertices);
        this.ebo.subData(0, this.indices);

        this.vao.bind();
        gl.glDrawElements(GL3.GL_TRIANGLES, this.indices.length, GL.GL_UNSIGNED_INT, 0);
        this.vao.unbind();
    }

    public void reset() {
        Arrays.fill(this.vertices, 0);
        Arrays.fill(this.indices, 0);

        this.trianglesCount = 0;
        this.pointsCount = 0;
        this.vertexIndex = 0;
    }

    public void delete() {
        this.vao.delete();
        this.vbo.delete();
        this.ebo.delete();
    }

    public boolean startShape(float left, float top, float right, float bottom, GLImage image, int renderConfigBits) {
        if (image == null) return false;

        if (this.image == null) {
            this.image = image;
            this.renderConfigBits = renderConfigBits;
        }

        return true;
    }

    public void addTriangles(int count, int[] triangles) {
        if (!this.hasSpaceFor(count)) {
            this.maxSize += SIZE;

            float[] vertices = this.vertices;
            this.vertices = new float[this.maxSize * VERTEX_SIZE * 3];
            System.arraycopy(vertices, 0, this.vertices, 0, vertices.length);

            int[] indices = this.indices;
            this.indices = new int[this.maxSize * 3];
            System.arraycopy(indices, 0, this.indices, 0, indices.length);

            this.delete();
            this.init(Stage.getInstance().getGl());
        }

        for (int i = 0; i < triangles.length; i++) {
            this.indices[this.trianglesCount * 3 + i] = triangles[i] + this.pointsCount;
        }

        this.trianglesCount += count;
        this.pointsCount += count + 2;
    }

    public void addVertex(float x, float y, float u, float v) {
        this.vertices[this.vertexIndex * VERTEX_SIZE] = x;
        this.vertices[this.vertexIndex * VERTEX_SIZE + 1] = y;
        this.vertices[this.vertexIndex * VERTEX_SIZE + 2] = u;
        this.vertices[this.vertexIndex * VERTEX_SIZE + 3] = v;

        this.vertexIndex++;
    }

    public boolean hasSpaceFor(int trianglesCount) {
        return this.trianglesCount + trianglesCount <= this.maxSize;
    }

    public GLImage getImage() {
        return image;
    }
}
