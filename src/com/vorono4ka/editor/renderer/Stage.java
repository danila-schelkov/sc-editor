package com.vorono4ka.editor.renderer;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.util.PMVMatrix;
import com.vorono4ka.editor.Main;
import com.vorono4ka.swf.ColorTransform;
import com.vorono4ka.swf.Matrix2x3;
import com.vorono4ka.swf.displayObjects.DisplayObject;

public class Stage {
    public static Stage INSTANCE;

    private float scale;

    private Shader shader;

    private GL3 gl;
    private boolean initialized;

    private int pointsCount;
    private int vertexIndex;
    private float[] vertices;
    private int[] indices;

    public Stage() {
        this.scale = 1.0f;
        this.pointsCount = 0;
    }

    public static void createInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Stage();
        }
    }

    public void init(GL3 gl, int x, int y, int width, int height) {
        Shader shader = new Shader(gl, "vertex.glsl", "fragment.glsl");

        gl.glViewport(x, y, width, height);

        PMVMatrix matrix = new PMVMatrix();
        matrix.glLoadIdentity();
        matrix.glOrthof(
            // left
            -(width / 2f),
            // right
            width / 2f,
            // bottom
            height / 2f,
            // top
            -(height / 2f),
            // near
            -1,
            // far
            1
        );

        shader.bind();
        gl.glUniformMatrix4fv(shader.getUniformLocation("pmv"), 1, false, matrix.glGetMatrixf());
        shader.unbind();

        this.vertices = new float[0];
        this.indices = new int[0];

        this.gl = gl;
        this.shader = shader;
        this.initialized = true;
    }

    public void render() {
        if (!this.initialized) return;

        this.gl.glClearColor(.5f, .5f, .5f, 1);
        this.gl.glClear(GL3.GL_COLOR_BUFFER_BIT);

        DisplayObject selectedObject = Main.editor.getSelectedObject();
        if (selectedObject == null) return;

        Matrix2x3 matrix = new Matrix2x3();
        matrix.setScale(1 / this.scale, 1 / this.scale);

        this.pointsCount = 0;
        this.vertexIndex = 0;
        this.vertices = new float[0];
        this.indices = new int[0];

        selectedObject.render(matrix, new ColorTransform(), 0, 0);

        this.shader.bind();

        VAO vao = new VAO(this.gl);
        vao.bind();

        VBO vbo = new VBO(this.gl, this.vertices);
        EBO ebo = new EBO(this.gl, this.indices);
        ebo.bind();
        vao.linkAttrib(vbo, 0, 2, GL3.GL_FLOAT, 4 * Float.BYTES, 0);
        vao.linkAttrib(vbo, 1, 2, GL3.GL_FLOAT, 4 * Float.BYTES, 2 * Float.BYTES);
        vao.unbind();
        ebo.unbind();

        vao.bind();
        this.gl.glDrawElements(GL3.GL_TRIANGLES, ebo.getIndicesCount(), GL.GL_UNSIGNED_INT, 0);
        vao.unbind();

        this.shader.unbind();

        vao.delete();
        vbo.delete();
        ebo.delete();
    }

    public void unbindRender() {
        if (!this.initialized) return;

        this.initialized = false;
        this.gl.glDisableVertexAttribArray(0);
        this.gl.glDisableVertexAttribArray(1);
        this.gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, 0);
        this.gl.glBindBuffer(GL3.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public void addTriangles(int count, int[] indices) {
        int pointsCount = this.pointsCount;
        this.pointsCount += count + 2;

        float[] newVertices = new float[this.pointsCount * 4];
        System.arraycopy(this.vertices, 0, newVertices, 0, this.vertices.length);
        this.vertices = newVertices;

        int[] newIndices = new int[this.indices.length + indices.length];
        System.arraycopy(this.indices, 0, newIndices, 0, this.indices.length);
        for (int i = 0; i < indices.length; i++) {
            newIndices[this.indices.length + i] = indices[i] + pointsCount;
        }
        this.indices = newIndices;
    }

    public void addVertex(float x, float y, float u, float v) {
        this.vertices[this.vertexIndex * 4] = x;
        this.vertices[this.vertexIndex * 4 + 1] = y;
        this.vertices[this.vertexIndex * 4 + 2] = u;
        this.vertices[this.vertexIndex * 4 + 3] = v;

        this.vertexIndex++;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public GL3 getGl() {
        return this.gl;
    }
}
