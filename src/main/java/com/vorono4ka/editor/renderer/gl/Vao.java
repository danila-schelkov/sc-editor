package com.vorono4ka.editor.renderer.gl;

import com.vorono4ka.editor.renderer.gl.buffers.BufferObject;

public class Vao implements BufferObject {
    private final GLRendererContext gl;
    private final int id;

    public Vao(GLRendererContext gl) {
        this.gl = gl;

        this.id = gl.glGenVertexArray();
    }

    public void linkAttrib(BufferObject vbo, int layout, int size, int type, int stride, int offset) {
        vbo.bind();
        gl.glEnableVertexAttribArray(layout);
        gl.glVertexAttribPointer(layout, size, type, false, stride, offset);
        vbo.unbind();
    }

    public void bind() {
        gl.glBindVertexArray(this.id);
    }

    public void unbind() {
        gl.glBindVertexArray(0);
    }

    public void delete() {
        gl.glDeleteVertexArray(this.id);
    }
}
