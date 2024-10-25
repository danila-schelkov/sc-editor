package com.vorono4ka.editor.renderer.texture;

import com.jogamp.opengl.GL3;
import com.vorono4ka.utilities.BufferUtils;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class Texture {
    private final GL3 gl;
    private final int id;
    private final int width, height;

    private int internalFormat;
    private int format;
    private int pixelType;

    public Texture(GL3 gl, int width, int height) {
        this.gl = gl;
        this.width = width;
        this.height = height;

        this.id = generateId(gl);

        this.bind();

        this.setFilters(GL3.GL_LINEAR, GL3.GL_LINEAR);
        this.setWrap(GL3.GL_REPEAT);

        this.unbind();
    }

    private static int generateId(GL3 gl) {
        IntBuffer ids = IntBuffer.allocate(1);
        gl.glGenTextures(1, ids);
        return ids.get(0);
    }

    /**
     * Initializes 2d texture in OpenGL
     *
     * @param level mip map level
     * @param internalFormat e.g. GL_RGBA
     * @param format e.g. GL_RGBA
     * @param pixelType e.g. GL_UNSIGNED_BYTE
     * @param pixels buffer containing texture pixels
     * @return 0 if succeeded, otherwise gl error code
     */
    public int init(int level, int internalFormat, int format, int pixelType, Buffer pixels) {
        this.internalFormat = internalFormat;
        this.format = format;
        this.pixelType = pixelType;

        gl.glTexImage2D(GL3.GL_TEXTURE_2D, level, internalFormat, width, height, 0, format, pixelType, pixels);
        return gl.glGetError();
    }

    public void initCompressed(int level, int internalFormat, int format, int width, int height, ByteBuffer data) {
        this.internalFormat = format;
        this.format = format;

        gl.glCompressedTexImage2D(GL3.GL_TEXTURE_2D, level, internalFormat, width, height, 0, data.remaining(), data);
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

    public int getHeight() {
        return height;
    }

    public int getInternalFormat() {
        return internalFormat;
    }

    public int getFormat() {
        return format;
    }

    public int getPixelType() {
        return pixelType;
    }

    // Ensure using in the render thread
    public IntBuffer getPixels(int level) {
        IntBuffer pixels = BufferUtils.allocateDirect(width * height * Integer.BYTES * getChannelCount()).asIntBuffer();
        this.gl.glGetTexImage(GL3.GL_TEXTURE_2D, level, this.internalFormat, this.pixelType, pixels);
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

    public int getChannelCount() {
        return switch (format) {
            case GL3.GL_RGBA -> 4;
            case GL3.GL_RGB -> 3;
            case GL3.GL_LUMINANCE_ALPHA, GL3.GL_RG -> 2;
            case GL3.GL_LUMINANCE, GL3.GL_RED -> 1;
            default -> throw new IllegalArgumentException("Unsupported pixel format for pixel storage, pixel format: " + format);
        };
    }

    public void setParameter(int type, IntBuffer value) {
        gl.glTexParameteriv(GL3.GL_TEXTURE_2D, type, value);
    }

    public void setPixelInfo(int format, int pixelType) {
        this.internalFormat = format;
        this.format = format;
        this.pixelType = pixelType;
    }
}
