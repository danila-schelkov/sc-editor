package com.vorono4ka.editor.renderer.gl.buffers;

import com.vorono4ka.editor.renderer.gl.GLConstants;
import com.vorono4ka.editor.renderer.gl.GLRendererContext;

import java.nio.IntBuffer;

/// Element Buffer Object
public class Ebo implements IntBufferObject {
    private final GLRendererContext gl;
    private final int id;

    public Ebo(GLRendererContext gl, int capacity, int usage) {
        this.gl = gl;

        this.id = gl.glGenBuffer();

        init(capacity, usage);
    }

    @Override
    public void subData(int offset, IntBuffer buffer) {
        this.bind();
        this.gl.glBufferSubData(GLConstants.GL_ELEMENT_ARRAY_BUFFER, offset, (long) buffer.capacity() * Integer.BYTES, buffer);
        this.unbind();
    }

    @Override
    public void bind() {
        this.gl.glBindBuffer(GLConstants.GL_ELEMENT_ARRAY_BUFFER, this.id);
    }

    @Override
    public void unbind() {
        this.gl.glBindBuffer(GLConstants.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    @Override
    public void delete() {
        this.gl.glDeleteBuffer(this.id);
    }

    private void init(int capacity, int usage) {
        this.bind();
        this.gl.glBufferData(GLConstants.GL_ELEMENT_ARRAY_BUFFER, (long) capacity * Integer.BYTES, null, usage);
        this.unbind();
    }
}
