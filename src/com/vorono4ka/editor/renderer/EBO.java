package com.vorono4ka.editor.renderer;

import com.jogamp.opengl.GL3;

import java.nio.IntBuffer;

public class EBO {
    private final GL3 gl;
    private final int id;

    public EBO(GL3 gl, int[] indices, int usage) {
        this.gl = gl;

        int[] EBOs = new int[1];
        this.gl.glGenBuffers(1, EBOs, 0);
        this.id = EBOs[0];

        this.bind();
        this.gl.glBufferData(GL3.GL_ELEMENT_ARRAY_BUFFER, (long) indices.length * Integer.BYTES, IntBuffer.wrap(indices), usage);
        this.unbind();
    }

    public void subData(int offset, int[] indices) {
        this.bind();
        this.gl.glBufferSubData(GL3.GL_ELEMENT_ARRAY_BUFFER, offset, (long) indices.length * Integer.BYTES, IntBuffer.wrap(indices));
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
}
