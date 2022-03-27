package com.vorono4ka.editor.renderer;

import com.jogamp.opengl.GL3;

import java.nio.IntBuffer;

public class EBO {
    private final GL3 gl;

    private final int id;
    private final int[] indices;

    public EBO(GL3 gl, int[] indices) {
        this.gl = gl;
        this.indices = indices;

        int[] EBOs = new int[1];
        this.gl.glGenBuffers(1, EBOs, 0);
        this.id = EBOs[0];

        this.bind();
        this.gl.glBufferData(GL3.GL_ELEMENT_ARRAY_BUFFER, (long) indices.length * Integer.BYTES, IntBuffer.wrap(indices), GL3.GL_STATIC_DRAW);
        this.unbind();
    }

    public void bind() {
        this.gl.glBindBuffer(GL3.GL_ELEMENT_ARRAY_BUFFER, this.id);
    }

    public void unbind() {
        this.gl.glBindBuffer(GL3.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public void delete() {
        this.gl.glDeleteBuffers(1, new int[] {this.id}, 0);
    }

    public int getIndicesCount() {
        return this.indices.length;
    }
}
