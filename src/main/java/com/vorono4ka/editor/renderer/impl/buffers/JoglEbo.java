package com.vorono4ka.editor.renderer.impl.buffers;

import com.jogamp.opengl.GL3;

import java.nio.IntBuffer;

/// Element Buffer Object
public class JoglEbo implements IntBufferObject {
    private final GL3 gl;
    private final int id;

    public JoglEbo(GL3 gl, int capacity, int usage) {
        this.gl = gl;

        int[] EBOs = new int[1];
        this.gl.glGenBuffers(1, EBOs, 0);
        this.id = EBOs[0];

        init(capacity, usage);
    }

    @Override
    public void subData(int offset, IntBuffer buffer) {
        this.bind();
        this.gl.glBufferSubData(GL3.GL_ELEMENT_ARRAY_BUFFER, offset, (long) buffer.capacity() * Integer.BYTES, buffer);
        this.unbind();
    }

    @Override
    public void bind() {
        this.gl.glBindBuffer(GL3.GL_ELEMENT_ARRAY_BUFFER, this.id);
    }

    @Override
    public void unbind() {
        this.gl.glBindBuffer(GL3.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    @Override
    public void delete() {
        this.gl.glDeleteBuffers(1, new int[] {this.id}, 0);
    }

    private void init(int capacity, int usage) {
        this.bind();
        this.gl.glBufferData(GL3.GL_ELEMENT_ARRAY_BUFFER, (long) capacity * Integer.BYTES, null, usage);
        this.unbind();
    }
}
