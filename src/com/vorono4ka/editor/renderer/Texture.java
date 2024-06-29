package com.vorono4ka.editor.renderer;

import com.jogamp.opengl.GL3;

import java.nio.IntBuffer;

public class Texture {
    private final GL3 gl;
    private final int id;

    private int width, height;

    public Texture(GL3 gl, int width, int height, int format, int pixelFormat, int pixelType) {
        this.gl = gl;
        this.width = width;
        this.height = height;

        this.id = generateId(gl);

        this.bind();

        this.setFilters(GL3.GL_LINEAR, GL3.GL_LINEAR);

        this.setWrap(GL3.GL_REPEAT);

        this.gl.glTexImage2D(GL3.GL_TEXTURE_2D, 0, format, width, height, 0, pixelFormat, pixelType, null);
        this.gl.glGenerateMipmap(GL3.GL_TEXTURE_2D);

        this.unbind();
    }

    private static int generateId(GL3 gl) {
        IntBuffer ids = IntBuffer.allocate(1);
        gl.glGenTextures(1, ids);
        return ids.get(0);
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
        this.gl.glDeleteTextures(1, new int[]{this.id}, 0);
    }

    public void generateMipMap() {
        this.gl.glGenerateMipmap(GL3.GL_TEXTURE_2D);
    }

    public void setWrap(int wrap) {
        setWrapS(wrap);
        setWrapT(wrap);
    }

    public void setWrapS(int wrap) {
        gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_WRAP_S, wrap);
    }

    public void setWrapT(int wrap) {
        gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_WRAP_T, wrap);
    }

    public void setFilters(int minFilter, int magFilter) {
        setMagFilter(magFilter);
        setMinFilter(minFilter);
    }

    public void setMinFilter(int minFilter) {
        gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MIN_FILTER, minFilter);
    }

    public void setMagFilter(int magFilter) {
        gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MAG_FILTER, magFilter);
    }

    @Override
    public String toString() {
        return "Texture{" +
            "id=" + id +
            ", width=" + width +
            ", height=" + height +
            '}';
    }
}
