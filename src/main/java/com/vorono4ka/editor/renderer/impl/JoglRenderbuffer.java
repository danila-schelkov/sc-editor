package com.vorono4ka.editor.renderer.impl;

import com.jogamp.opengl.GL3;
import com.vorono4ka.editor.renderer.Renderbuffer;

import java.nio.IntBuffer;

public class JoglRenderbuffer extends Renderbuffer {
    private final GL3 gl;
    private final int id;

    public JoglRenderbuffer(GL3 gl, int width, int height) {
        super(width, height);

        this.gl = gl;

        this.id = generateId(gl);

        this.bind();
        this.gl.glRenderbufferStorage(GL3.GL_FRAMEBUFFER, GL3.GL_DEPTH_COMPONENT32, this.width, this.height);
        this.unbind();
    }

    private static int generateId(GL3 gl) {
        IntBuffer ids = IntBuffer.allocate(1);
        gl.glGenRenderbuffers(1, ids);
        return ids.get(0);
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void bind() {
        this.gl.glBindRenderbuffer(GL3.GL_RENDERBUFFER, this.id);
    }

    @Override
    public void unbind() {
        this.gl.glBindRenderbuffer(GL3.GL_RENDERBUFFER, 0);
    }

    @Override
    public void delete() {
        this.gl.glDeleteRenderbuffers(1, new int[]{this.id}, 0);
    }
}
