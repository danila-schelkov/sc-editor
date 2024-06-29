package com.vorono4ka.editor.renderer;

import com.jogamp.opengl.GL3;

import java.nio.IntBuffer;

public class Renderbuffer {
    private final GL3 gl;
    private final int id;
    private final int width, height;

    public Renderbuffer(GL3 gl, int width, int height) {
        this.gl = gl;
        this.width = width;
        this.height = height;

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

    public void bind() {
        this.gl.glBindRenderbuffer(GL3.GL_RENDERBUFFER, this.id);
    }

    public void unbind() {
        this.gl.glBindRenderbuffer(GL3.GL_RENDERBUFFER, 0);
    }

    public void delete() {
        this.gl.glDeleteRenderbuffers(1, new int[]{this.id}, 0);
    }

    public int getId() {
        return this.id;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
