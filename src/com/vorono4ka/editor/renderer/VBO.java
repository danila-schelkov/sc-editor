package com.vorono4ka.editor.renderer;

import com.jogamp.opengl.GL3;

import java.nio.FloatBuffer;

public class VBO {
    private final GL3 gl;
    private final int id;

    public VBO(GL3 gl, float[] vertices, int usage) {
        this.gl = gl;

        int[] VBOs = new int[1];
        this.gl.glGenBuffers(1, VBOs, 0);
        this.id = VBOs[0];

        this.bind();
        this.gl.glBufferData(GL3.GL_ARRAY_BUFFER, (long) vertices.length * Float.BYTES, FloatBuffer.wrap(vertices), usage);
        this.unbind();
    }

    public void subData(int offset, float[] vertices) {
        this.bind();
        this.gl.glBufferSubData(GL3.GL_ARRAY_BUFFER, offset, (long) vertices.length * Float.BYTES, FloatBuffer.wrap(vertices));
        this.unbind();
    }

    public void bind() {
        this.gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, this.id);
    }

    public void unbind() {
        this.gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, 0);
    }

    public void delete() {
        this.gl.glDeleteBuffers(1, new int[] {this.id}, 0);
    }
}
