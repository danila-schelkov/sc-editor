package com.vorono4ka.editor.renderer;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL3;
import com.vorono4ka.editor.renderer.texture.Texture;
import com.vorono4ka.utilities.BufferUtils;
import com.vorono4ka.utilities.ImageUtils;

import java.nio.IntBuffer;

public class Framebuffer {
    private final GL3 gl;
    private final int id;
    private final int width, height;
    private final Texture texture, stencilTexture;
    private final Renderbuffer renderbuffer;

    private int previousFramebuffer;

    public Framebuffer(GL3 gl, int width, int height) {
        this.gl = gl;
        this.width = width;
        this.height = height;

        this.id = generateId(gl);

        this.bind();
        this.texture = new Texture(this.gl, this.width, this.height);
        this.texture.bind();
        this.texture.init(0, GL3.GL_RGBA, GL3.GL_RGBA, GL3.GL_UNSIGNED_BYTE, null);
        this.attachTexture(texture, GL3.GL_COLOR_ATTACHMENT0);

        this.stencilTexture = new Texture(this.gl, this.width, this.height);
        this.stencilTexture.bind();
        this.stencilTexture.init(0, GL3.GL_DEPTH24_STENCIL8, GL3.GL_DEPTH_STENCIL, GL3.GL_UNSIGNED_INT_24_8, null);
        this.attachTexture(stencilTexture, GL3.GL_DEPTH_STENCIL_ATTACHMENT);

        // Note: it brokes the framebuffer rendering, so I've commented it out
        // this.renderbuffer = new Renderbuffer(this.gl, this.width, this.height);
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

    private void attachRenderbuffer(Renderbuffer renderbuffer, @SuppressWarnings("SameParameterValue") int attachmentType) {
        this.gl.glFramebufferRenderbuffer(GL3.GL_FRAMEBUFFER, attachmentType, GL.GL_RENDERBUFFER, renderbuffer.getId());
    }

    private void attachTexture(Texture texture, @SuppressWarnings("SameParameterValue") int attachmentType) {
        this.gl.glFramebufferTexture2D(GL3.GL_FRAMEBUFFER, attachmentType, GL3.GL_TEXTURE_2D, texture.getId(), 0);
    }

    public void bind() {
        this.previousFramebuffer = this.gl.getBoundFramebuffer(GL3.GL_FRAMEBUFFER);
        this.gl.glBindFramebuffer(GL3.GL_FRAMEBUFFER, this.id);
    }

    public void unbind() {
        this.gl.glBindFramebuffer(GL3.GL_FRAMEBUFFER, this.previousFramebuffer);
        this.previousFramebuffer = -1;
    }

    public void delete() {
        this.gl.glDeleteFramebuffers(1, new int[]{this.id}, 0);
        if (this.renderbuffer != null) this.renderbuffer.delete();
        this.texture.delete();
        this.stencilTexture.delete();
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

    /**
     * Returns pixel array of the framebuffer from the graphics memory.
     * @param flipY whether to flip pixels along the Y axis or not
     * @return integer array of pixels
     */
    public int[] getPixelArray(boolean flipY) {
        texture.bind();
        IntBuffer pixels = texture.getPixels(0);
        texture.unbind();

        int[] pixelArray = BufferUtils.toArray(pixels);

        if (flipY) {
            ImageUtils.flipY(width, height, pixelArray);
        }

        return pixelArray;
    }
}
