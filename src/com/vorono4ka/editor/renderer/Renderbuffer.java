package com.vorono4ka.editor.renderer;

import com.jogamp.opengl.GL3;

public class Renderbuffer {
    private final GL3 gl;
    private final int id;
    private final int width, height;

    public Renderbuffer(GL3 gl, int width, int height) {
        this.gl = gl;
        this.width = width;
        this.height = height;

        int[] RBOs = new int[1];
        this.gl.glGenRenderbuffers(1, RBOs, 0);
        this.id = RBOs[0];

        this.bind();
        this.gl.glRenderbufferStorage(GL3.GL_FRAMEBUFFER, GL3.GL_DEPTH_COMPONENT32, this.width, this.height);
        this.unbind();
    }

    public void bind() {
        this.gl.glBindRenderbuffer(GL3.GL_RENDERBUFFER, this.id);
    }

    public void unbind() {
        this.gl.glBindRenderbuffer(GL3.GL_RENDERBUFFER, 0);
    }

    public void delete() {
        this.gl.glDeleteRenderbuffers(1, new int[] {this.id}, 0);
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
