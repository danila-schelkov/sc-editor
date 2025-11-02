package dev.donutquine.editor.renderer.gl;

import dev.donutquine.editor.renderer.gl.buffers.BufferObject;

public class Vao implements BufferObject {
    private final GLContext gl;
    private final int id;

    public Vao(GLContext gl) {
        this.gl = gl;

        this.id = gl.glGenVertexArray();
    }

    @Override
    public void bind() {
        gl.glBindVertexArray(this.id);
    }

    @Override
    public void unbind() {
        gl.glBindVertexArray(0);
    }

    @Override
    public void delete() {
        gl.glDeleteVertexArray(this.id);
    }
}
