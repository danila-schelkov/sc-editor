package dev.donutquine.editor.renderer.gl.buffers;

import dev.donutquine.editor.renderer.gl.GLConstants;
import dev.donutquine.editor.renderer.gl.GLContext;

import java.nio.IntBuffer;

/// Element Buffer Object
public class Ebo implements IntBufferObject {
    private final GLContext gl;
    private final int id;

    public Ebo(GLContext gl, int capacity, int usage) {
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
