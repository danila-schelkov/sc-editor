package com.vorono4ka.editor.renderer;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.util.PMVMatrix;
import com.vorono4ka.editor.Main;
import com.vorono4ka.swf.ColorTransform;
import com.vorono4ka.swf.Matrix2x3;
import com.vorono4ka.swf.displayObjects.DisplayObject;

public class Renderer {
    public static final float unitsWide = 2;

    private final PMVMatrix matrix;
    private float scale;

    private Shader shader;
    private int pmvMatrixUniform;
    private int scaleUniform;

    private VAO vao;
    private VBO vbo;
    private EBO ebo;

    private GL3 gl;
    private boolean initialized;

    public Renderer() {
        this.matrix = new PMVMatrix();
        this.scale = 1.0f;
    }

    public void initialize(GL3 gl) {
        this.shader = new Shader(gl, "vertex.glsl", "fragment.glsl");

        this.scaleUniform = this.shader.getUniformLocation("scale");
        this.pmvMatrixUniform = this.shader.getUniformLocation("pmv");

        this.vao = new VAO(gl);
        this.vao.bind();

        float[] vertices = {
            -0.5f, -0.5f,
            -0.5f, 0.5f,
            0.5f, 0.5f,
            0.5f, -0.5f
        };

        int[] indices = {
            0, 1, 2,
            0, 2, 3
        };
        this.vbo = new VBO(gl, vertices);
        this.ebo = new EBO(gl, indices);
        this.ebo.bind();
        this.vao.linkAttrib(this.vbo, 0, 2, GL3.GL_FLOAT, 2 * Float.BYTES, 0);
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

        gl.glUniform1f(this.scaleUniform, this.scale);

        this.vao.bind();
        gl.glDrawElements(GL3.GL_TRIANGLES, this.ebo.getIndicesCount(), GL3.GL_UNSIGNED_INT, 0);
        this.vao.unbind();

        DisplayObject selectedObject = Main.editor.getSelectedObject();
        if (selectedObject == null) return;

        Matrix2x3 matrix = new Matrix2x3();
//        matrix.setScale(this.scale, this.scale);
        selectedObject.render(matrix, new ColorTransform(), 0, 0);
//        gl.glDrawArrays(GL3.GL_POINTS, 0, 4);
    }

    public PMVMatrix getPMVMatrix() {
        return matrix;
    }

    public void setPMVMatrix(PMVMatrix matrix) {
        this.shader.use();
        this.gl.glUniformMatrix4fv(this.pmvMatrixUniform, 1, false, matrix.glGetMatrixf());
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
