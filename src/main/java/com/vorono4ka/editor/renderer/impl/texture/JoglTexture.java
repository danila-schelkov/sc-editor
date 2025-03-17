package com.vorono4ka.editor.renderer.impl.texture;

import com.jogamp.opengl.GL3;
import com.vorono4ka.editor.renderer.texture.GLTexture;
import com.vorono4ka.utilities.BufferUtils;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class JoglTexture implements GLTexture {
    private final GL3 gl;
    private final int id;
    private final int width, height;

    private int internalFormat, format;
    private int pixelType;

    public JoglTexture(GL3 gl, int width, int height) {
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

    public int init(int level, int internalFormat, int format, int pixelType, Buffer pixels) {
        this.internalFormat = internalFormat;
        this.format = format;
        this.pixelType = pixelType;

        gl.glTexImage2D(GL3.GL_TEXTURE_2D, level, internalFormat, width, height, 0, format, pixelType, pixels);
        return gl.glGetError();
    }

    public int initCompressed(int level, int internalFormat, int format, ByteBuffer data) {
        this.internalFormat = format;
        this.format = format;

        gl.glCompressedTexImage2D(GL3.GL_TEXTURE_2D, level, internalFormat, width, height, 0, data.remaining(), data);
        return gl.glGetError();
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

    public int getChannelCount() {
        return switch (format) {
            case GL3.GL_RGBA -> 4;
            case GL3.GL_RGB -> 3;
            case GL3.GL_LUMINANCE_ALPHA, GL3.GL_RG -> 2;
            case GL3.GL_LUMINANCE, GL3.GL_RED -> 1;
            default ->
                throw new IllegalArgumentException("Unsupported pixel format for pixel storage, pixel format: " + format);
        };
    }

    public void bind() {
        gl.glBindTexture(GL3.GL_TEXTURE_2D, this.id);
    }

    public void unbind() {
        gl.glBindTexture(GL3.GL_TEXTURE_2D, 0);
    }

    public void delete() {
        this.gl.glDeleteTextures(1, new int[]{this.id}, 0);
    }

    public void generateMipMap() {
        this.gl.glGenerateMipmap(GL3.GL_TEXTURE_2D);
    }

    /// Ensure using in the render thread
    public IntBuffer getPixels(int level) {
        IntBuffer pixels = BufferUtils.allocateDirect(width * height * Integer.BYTES * getChannelCount()).asIntBuffer();
        this.gl.glGetTexImage(GL3.GL_TEXTURE_2D, level, this.internalFormat, this.pixelType, pixels);
        return pixels;
    }

    /// Ensure using in the render thread after binding texture
    public IntBuffer getCompressedData(int level) {
        IntBuffer isCompressed = IntBuffer.allocate(1);
        gl.glGetTexLevelParameteriv(GL3.GL_TEXTURE_2D, level, GL3.GL_TEXTURE_COMPRESSED, isCompressed);

        if (isCompressed.get() == GL3.GL_FALSE) return null;

        IntBuffer compressedImageSize = IntBuffer.allocate(1);
        gl.glGetTexLevelParameteriv(GL3.GL_TEXTURE_2D, level, GL3.GL_TEXTURE_COMPRESSED_IMAGE_SIZE, compressedImageSize);

        IntBuffer pixels = BufferUtils.allocateDirect(compressedImageSize.get()).asIntBuffer();
        this.gl.glGetCompressedTexImage(GL3.GL_TEXTURE_2D, level, pixels);
        return pixels;
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

    public void setParameter(int type, IntBuffer value) {
        gl.glTexParameteriv(GL3.GL_TEXTURE_2D, type, value);
    }

    public void setPixelInfo(int format, int pixelType) {
        this.internalFormat = format;
        this.format = format;
        this.pixelType = pixelType;
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

    @Override
    public String toString() {
        return "Texture{" + "id=" + id + ", width=" + width + ", height=" + height + '}';
    }
}
