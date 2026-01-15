package dev.donutquine.editor.renderer.gl;

import dev.donutquine.editor.renderer.Renderbuffer;

public class GLRenderbuffer extends Renderbuffer {
    private final GLContext gl;
    private final int id;

    public GLRenderbuffer(GLContext gl, int width, int height) {
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
