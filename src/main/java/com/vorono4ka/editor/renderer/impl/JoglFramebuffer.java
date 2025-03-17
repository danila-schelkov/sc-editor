package com.vorono4ka.editor.renderer.impl;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL3;
import com.vorono4ka.editor.renderer.Framebuffer;
import com.vorono4ka.editor.renderer.Renderbuffer;
import com.vorono4ka.editor.renderer.impl.texture.JoglTexture;
import com.vorono4ka.editor.renderer.texture.RenderableTexture;
import com.vorono4ka.utilities.BufferUtils;
import com.vorono4ka.utilities.ImageUtils;

import java.nio.IntBuffer;

public class JoglFramebuffer extends Framebuffer {
    protected final RenderableTexture texture, stencilTexture;
    protected final Renderbuffer renderbuffer;

    private final GL3 gl;
    private final int id;

    private int previousFramebuffer;

    public JoglFramebuffer(GL3 gl, int width, int height) {
        super(width, height);

        this.gl = gl;

        this.id = generateId(gl);

        this.bind();

        JoglTexture texture = new JoglTexture(this.gl, this.width, this.height);
        texture.bind();
        texture.init(0, GL3.GL_RGBA, GL3.GL_RGBA, GL3.GL_UNSIGNED_BYTE, null);
        this.attachTexture(texture, GL3.GL_COLOR_ATTACHMENT0);

        this.texture = texture;

        JoglTexture stencilTexture = new JoglTexture(this.gl, this.width, this.height);
        stencilTexture.bind();
        stencilTexture.init(0, GL3.GL_DEPTH24_STENCIL8, GL3.GL_DEPTH_STENCIL, GL3.GL_UNSIGNED_INT_24_8, null);
//        stencilTexture.setFilters(GL3.GL_NEAREST, GL3.GL_NEAREST);
        this.attachTexture(stencilTexture, GL3.GL_DEPTH_STENCIL_ATTACHMENT);

        this.stencilTexture = stencilTexture;

        // Note: it broke the framebuffer rendering, so I've commented it out
        // this.renderbuffer = new JoglRenderbuffer(this.gl, this.width, this.height);
        // this.attachRenderbuffer(renderbuffer, GL3.GL_STENTIC_ATTACHMENT);
        this.renderbuffer = null;

        assert this.gl.glCheckFramebufferStatus(GL3.GL_FRAMEBUFFER) == GL3.GL_FRAMEBUFFER_COMPLETE : "Framebuffer is not complete";

        this.unbind();
    }

    private static int generateId(GL3 gl) {
        IntBuffer ids = IntBuffer.allocate(1);
        gl.glGenFramebuffers(1, ids);
        return ids.get(0);
    }

    @Override
    protected void attachRenderbuffer(Renderbuffer renderbuffer, @SuppressWarnings("SameParameterValue") int attachmentType) {
        this.gl.glFramebufferRenderbuffer(GL3.GL_FRAMEBUFFER, attachmentType, GL.GL_RENDERBUFFER, renderbuffer.getId());
    }

    @Override
    protected void attachTexture(RenderableTexture texture, int attachmentType) {
        this.gl.glFramebufferTexture2D(GL3.GL_FRAMEBUFFER, attachmentType, GL3.GL_TEXTURE_2D, texture.getId(), 0);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void bind() {
        this.previousFramebuffer = this.gl.getBoundFramebuffer(GL3.GL_FRAMEBUFFER);
        this.gl.glBindFramebuffer(GL3.GL_FRAMEBUFFER, this.id);
    }

    @Override
    public void unbind() {
        this.gl.glBindFramebuffer(GL3.GL_FRAMEBUFFER, this.previousFramebuffer);
        this.previousFramebuffer = -1;
    }

    @Override
    public void delete() {
        this.gl.glDeleteFramebuffers(1, new int[]{this.id}, 0);
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
