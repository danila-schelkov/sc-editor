package com.vorono4ka.editor.renderer;

import com.jogamp.opengl.GL3;

public class VAO {
    private final GL3 gl;
    private final int id;

    public VAO(GL3 gl) {
        this.gl = gl;

        int[] VAOs = new int[1];
        this.gl.glGenVertexArrays(1, VAOs, 0);
        this.id = VAOs[0];
    }

    public void linkAttrib(VBO vbo, int layout, int size, int type, int stride, int offset) {
        vbo.bind();
        this.gl.glEnableVertexAttribArray(layout);
        this.gl.glVertexAttribPointer(layout, size, type, false, stride, offset);
        vbo.unbind();
    }

    public void bind() {
        this.gl.glBindVertexArray(this.id);
    }

    public void unbind() {
        this.gl.glBindVertexArray(0);
    }

    public void delete() {
        this.gl.glDeleteVertexArrays(1, new int[] {this.id}, 0);
    }
}
