package com.vorono4ka.editor.renderer;

import com.jogamp.opengl.GL3;

import java.nio.IntBuffer;

public class EBO {
    private final GL3 gl;
    private final int id;

    public EBO(GL3 gl, int capacity, int usage) {
        this.gl = gl;

        int[] EBOs = new int[1];
        this.gl.glGenBuffers(1, EBOs, 0);
        this.id = EBOs[0];

        init(capacity, usage);
    }

    // Don't forget to prepare the buffer for reading by calling buffer.clear()
    public void subData(int offset, IntBuffer buffer) {
        this.bind();
        this.gl.glBufferSubData(GL3.GL_ELEMENT_ARRAY_BUFFER, offset, (long) buffer.capacity() * Integer.BYTES, buffer);
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

    private void init(int capacity, int usage) {
        this.bind();
        this.gl.glBufferData(GL3.GL_ELEMENT_ARRAY_BUFFER, (long) capacity * Integer.BYTES, null, usage);
        this.unbind();
    }
}
