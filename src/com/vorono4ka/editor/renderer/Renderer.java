package com.vorono4ka.editor.renderer;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.util.PMVMatrix;
import com.vorono4ka.editor.Main;

public class Renderer {
    public static final float unitsWide = 2;

    private Shader shader;
    private VAO vao;
    private VBO vbo;
    private EBO ebo;

    private GL3 gl;
    private boolean initialized;

    private float scale;

    private int pmvMatrixUniform;
    private int scaleUniform;

    public Renderer() {
        this.scale = 1.0f;
    }

    public void initialize(GL3 gl) {
        this.shader = new Shader(gl, "vertex.glsl", "fragment.glsl");

        this.scaleUniform = this.shader.getUniformLocation("scale");
        this.pmvMatrixUniform = this.shader.getUniformLocation("pmv");

        this.vao = new VAO(gl);
        this.vao.bind();

        float[] vertices = {
            -0.5f, -0.5f, 0.0f,
            -0.5f, 0.5f, 0.0f,
            0.5f, 0.5f, 0.0f,
            0.5f, -0.5f, 0.0f
        };

        int[] indices = {
            0, 1, 2,
            0, 2, 3
        };
        this.vbo = new VBO(gl, vertices);
        this.ebo = new EBO(gl, indices);
        this.ebo.bind();
        this.vao.linkAttrib(this.vbo, 0, 3, GL3.GL_FLOAT, 3 * Float.BYTES, 0);
        this.vao.unbind();
        this.ebo.unbind();

        this.gl = gl;
        this.initialized = true;
    }

    public void render(GL3 gl) {
        if (!this.initialized) return;

        gl.glClearColor(.5f, .5f, .5f, 1);
        gl.glClear(GL3.GL_COLOR_BUFFER_BIT);
        this.shader.use();

        gl.glUniform1f(scaleUniform, this.scale);

        this.vao.bind();
        gl.glDrawElements(GL3.GL_LINE_LOOP, this.ebo.getIndicesCount(), GL3.GL_UNSIGNED_INT, 0);
        this.vao.unbind();
    }

    public void setPMVMatrix(PMVMatrix matrix) {
        this.shader.use();
        this.gl.glUniformMatrix4fv(pmvMatrixUniform, 1, false, matrix.glGetMatrixf());
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public int getWidth() {
        return Main.editor.getWindow().getCanvas().getWidth();
    }

    public int getHeight() {
        return Main.editor.getWindow().getCanvas().getHeight();
    }

    public float getAspectRatio() {
        return getWidth() / (float) getHeight();
    }

    public GL3 getGl() {
        return this.gl;
    }
}
