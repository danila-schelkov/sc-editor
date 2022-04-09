package com.vorono4ka.editor.renderer;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.util.PMVMatrix;
import com.vorono4ka.editor.Main;
import com.vorono4ka.math.Rect;
import com.vorono4ka.swf.ColorTransform;
import com.vorono4ka.swf.Matrix2x3;
import com.vorono4ka.swf.displayObjects.DisplayObject;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Stage {
    public static Stage INSTANCE;

    private final ConcurrentLinkedQueue<Runnable> tasks;

    private boolean initialized;
    private Shader shader;
    private GL3 gl;

    private Rect viewport;
    private int scaleStep;
    private float scale;
    private float offsetX;
    private float offsetY;

    private int pointsCount;
    private int vertexIndex;
    private float[] vertices;
    private int[] indices;

    public Stage() {
        this.tasks = new ConcurrentLinkedQueue<>();

        this.scaleStep = 39;
        this.scale = 1.0f;
    }

    public static void createInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Stage();
        }
    }

    public void init(GL3 gl, int x, int y, int width, int height) {
        this.shader = new Shader(gl, "vertex.glsl", "fragment.glsl");
        this.gl = gl;

        this.viewport = new Rect(-(width / 2f), -(height / 2f), width / 2f, height / 2f);

        gl.glViewport(x, y, width, height);

        this.updatePMVMatrix();

        gl.glActiveTexture(GL3.GL_TEXTURE0);

        this.vertices = new float[0];
        this.indices = new int[0];

        this.initialized = true;
    }

    public void render() {
        if (!this.initialized) return;

        Iterator<Runnable> iterator = this.tasks.iterator();
        while (iterator.hasNext()) {
            Runnable task = iterator.next();
            task.run();

            iterator.remove();
        }

        this.gl.glClearColor(.5f, .5f, .5f, 1);
        this.gl.glClear(GL3.GL_COLOR_BUFFER_BIT);

        DisplayObject selectedObject = Main.editor.getSelectedObject();
        if (selectedObject == null) return;

        this.pointsCount = 0;
        this.vertexIndex = 0;
        this.vertices = new float[0];
        this.indices = new int[0];

        selectedObject.render(new Matrix2x3(), new ColorTransform(), 0, 1 / 60f);

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

    public void updatePMVMatrix() {
        PMVMatrix matrix = new PMVMatrix();
        matrix.glLoadIdentity();
        matrix.glOrthof(
            // left
            (this.viewport.getMinX() / this.scale + this.offsetX),
            // right
            (this.viewport.getMaxX() / this.scale + this.offsetX),
            // bottom
            (this.viewport.getMaxY() / this.scale + this.offsetY),
            // top
            (this.viewport.getMinY() / this.scale + this.offsetY),
            // near
            -1,
            // far
            1
        );

        this.shader.bind();
        this.gl.glUniformMatrix4fv(this.shader.getUniformLocation("pmv"), 1, false, matrix.glGetMatrixf());
        this.shader.unbind();
    }

    public void doInRenderThread(Runnable task) {
        this.tasks.add(task);
    }

    public int getScaleStep() {
        return scaleStep;
    }

    public void setScaleStep(int scaleStep) {
        this.scaleStep = scaleStep;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void addOffset(float x, float y) {
        this.offsetX += x;
        this.offsetY += y;
    }

    public void setOffset(float x, float y) {
        this.offsetX = x;
        this.offsetY = y;
    }

    public GL3 getGl() {
        return this.gl;
    }
}
