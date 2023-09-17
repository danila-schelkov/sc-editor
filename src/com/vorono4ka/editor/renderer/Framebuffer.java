package com.vorono4ka.editor.renderer;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL3;

public class Framebuffer {
    private final GL3 gl;
    private final int id;
    private final int width, height;
    private final Texture texture;
    private final Renderbuffer renderbuffer;

    public Framebuffer(GL3 gl, int width, int height) {
        this.gl = gl;
        this.width = width;
        this.height = height;
        this.texture = new Texture(this.gl, this.width, this.height);
        this.renderbuffer = new Renderbuffer(this.gl, this.width, this.height);

        int[] FBOs = new int[1];
        this.gl.glGenFramebuffers(1, FBOs, 0);
        this.id = FBOs[0];

        this.bind();
        this.gl.glFramebufferTexture2D(GL3.GL_FRAMEBUFFER, GL3.GL_COLOR_ATTACHMENT0, GL3.GL_TEXTURE_2D, this.texture.getId(), 0);

        this.gl.glFramebufferRenderbuffer(GL3.GL_FRAMEBUFFER, GL3.GL_DEPTH_ATTACHMENT, GL.GL_RENDERBUFFER, renderbuffer.getId());

        assert this.gl.glCheckFramebufferStatus(GL3.GL_FRAMEBUFFER) == GL3.GL_FRAMEBUFFER_COMPLETE : "Framebuffer is not complete";

        this.unbind();
    }

    public void bind() {
        this.gl.glBindFramebuffer(GL3.GL_FRAMEBUFFER, this.id);
    }

    public void unbind() {
        this.gl.glBindFramebuffer(GL3.GL_FRAMEBUFFER, 0);
    }

    public void delete() {
        this.gl.glDeleteFramebuffers(1, new int[] {this.id}, 0);
        this.renderbuffer.delete();
        this.texture.delete();
    }

    public int getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Texture getTexture() {
        return texture;
    }

    public Renderbuffer getRenderbuffer() {
        return renderbuffer;
    }
}
