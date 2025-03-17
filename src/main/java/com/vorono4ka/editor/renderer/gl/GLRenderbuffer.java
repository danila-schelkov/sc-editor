package com.vorono4ka.editor.renderer.gl;

import com.vorono4ka.editor.renderer.Renderbuffer;

public class GLRenderbuffer extends Renderbuffer {
    private final GLRendererContext gl;
    private final int id;

    public GLRenderbuffer(GLRendererContext gl, int width, int height) {
        super(width, height);

        this.gl = gl;

        this.id = gl.glGenRenderbuffer();

        this.bind();
        this.gl.glRenderbufferStorage(GLConstants.GL_FRAMEBUFFER, GLConstants.GL_DEPTH_COMPONENT32, this.width, this.height);
        this.unbind();
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void bind() {
        this.gl.glBindRenderbuffer(GLConstants.GL_RENDERBUFFER, this.id);
    }

    @Override
    public void unbind() {
        this.gl.glBindRenderbuffer(GLConstants.GL_RENDERBUFFER, 0);
    }

    @Override
    public void delete() {
        this.gl.glDeleteRenderbuffer(this.id);
    }
}
