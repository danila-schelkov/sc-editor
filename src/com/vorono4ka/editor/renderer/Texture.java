package com.vorono4ka.editor.renderer;

import com.jogamp.opengl.GL3;

import java.nio.IntBuffer;

public class Texture {
    private final GL3 gl;
    private final int id;

    private int width, height;

    public Texture(GL3 gl, int width, int height) {
        this.gl = gl;
        this.width = width;
        this.height = height;

        IntBuffer ids = IntBuffer.allocate(1);
        gl.glGenTextures(1, ids);

        this.id = ids.get(0);

        this.bind();
        this.gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MAG_FILTER, GL3.GL_LINEAR);
        this.gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MIN_FILTER, GL3.GL_LINEAR);
        this.unbind();
    }

    public void bind() {
        gl.glBindTexture(GL3.GL_TEXTURE_2D, this.id);
    }

    public void unbind() {
        gl.glBindTexture(GL3.GL_TEXTURE_2D, 0);
    }


    public int getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    // Ensure using in the render thread
    public IntBuffer getPixels() {
        IntBuffer pixels = IntBuffer.allocate(width * height * 4);
        this.gl.glReadPixels(0, 0, width, height, GL3.GL_RGBA, GL3.GL_UNSIGNED_BYTE, pixels);
        return pixels;
    }

    public void delete() {
        this.gl.glDeleteTextures(1, new int[] {this.id}, 0);
    }
}
