package com.vorono4ka.editor.renderer.gl;

import com.vorono4ka.editor.renderer.Framebuffer;
import com.vorono4ka.editor.renderer.Renderbuffer;
import com.vorono4ka.editor.renderer.gl.texture.GLTexture;
import com.vorono4ka.editor.renderer.texture.RenderableTexture;

public class GLFramebuffer extends Framebuffer {
    protected final RenderableTexture texture, stencilTexture;
    protected final Renderbuffer renderbuffer;

    private final GLRendererContext gl;
    private final int id;

    private int previousFramebuffer;

    public GLFramebuffer(GLRendererContext gl, int width, int height) {
        super(width, height);

        this.gl = gl;

        this.id = gl.glGenFramebuffer();

        this.bind();

        GLTexture texture = new GLTexture(gl, this.width, this.height);
        texture.bindContext();
        texture.bind();
        texture.init(0, GLConstants.GL_RGBA, GLConstants.GL_RGBA, GLConstants.GL_UNSIGNED_BYTE, null);
        this.attachTexture(texture, GLConstants.GL_COLOR_ATTACHMENT0);

        this.texture = texture;

        GLTexture stencilTexture = new GLTexture(gl, this.width, this.height);
        stencilTexture.bindContext();
        stencilTexture.bind();
        stencilTexture.init(0, GLConstants.GL_DEPTH24_STENCIL8, GLConstants.GL_DEPTH_STENCIL, GLConstants.GL_UNSIGNED_INT_24_8, null);
//        stencilTexture.setFilters(GLConstants.GL_NEAREST, GLConstants.GL_NEAREST);
        this.attachTexture(stencilTexture, GLConstants.GL_DEPTH_STENCIL_ATTACHMENT);

        this.stencilTexture = stencilTexture;

        // Note: it broke the framebuffer rendering, so I've commented it out
        // this.renderbuffer = new JoglRenderbuffer(this.gl, this.width, this.height);
        // this.attachRenderbuffer(renderbuffer, GLConstants.GL_STENTIC_ATTACHMENT);
        this.renderbuffer = null;

        assert this.gl.glCheckFramebufferStatus(GLConstants.GL_FRAMEBUFFER) == GLConstants.GL_FRAMEBUFFER_COMPLETE : "Framebuffer is not complete";

        this.unbind();
    }

    @Override
    protected void attachRenderbuffer(Renderbuffer renderbuffer, @SuppressWarnings("SameParameterValue") int attachmentType) {
        this.gl.glFramebufferRenderbuffer(GLConstants.GL_FRAMEBUFFER, attachmentType, GLConstants.GL_RENDERBUFFER, renderbuffer.getId());
    }

    @Override
    protected void attachTexture(RenderableTexture texture, int attachmentType) {
        this.gl.glFramebufferTexture2D(GLConstants.GL_FRAMEBUFFER, attachmentType, GLConstants.GL_TEXTURE_2D, texture.getId(), 0);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void bind() {
        this.previousFramebuffer = this.gl.getBoundFramebuffer(GLConstants.GL_FRAMEBUFFER);
        this.gl.glBindFramebuffer(GLConstants.GL_FRAMEBUFFER, this.id);
    }

    @Override
    public void unbind() {
        this.gl.glBindFramebuffer(GLConstants.GL_FRAMEBUFFER, this.previousFramebuffer);
        this.previousFramebuffer = -1;
    }

    @Override
    public void delete() {
        this.gl.glDeleteFramebuffer(this.id);
        if (this.renderbuffer != null) this.renderbuffer.delete();
        this.texture.delete();
        this.stencilTexture.delete();
    }

    @Override
    public RenderableTexture getTexture() {
        return texture;
    }

    @Override
    public Renderbuffer getRenderbuffer() {
        return renderbuffer;
    }
}
