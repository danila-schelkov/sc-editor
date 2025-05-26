package com.vorono4ka.editor.renderer.gl.texture;

import com.vorono4ka.editor.renderer.gl.GLConstants;
import com.vorono4ka.editor.renderer.gl.GLRendererContext;
import com.vorono4ka.editor.renderer.texture.RenderableTexture;
import com.vorono4ka.utilities.BufferUtils;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class GLTexture implements RenderableTexture {
    private final GLRendererContext gl;
    private final int width, height;

    private int id;

    private int internalFormat, format;
    private int pixelType;

    public GLTexture(GLRendererContext gl, int width, int height) {
        this.gl = gl;
        this.width = width;
        this.height = height;
    }

    public void bindContext() {
        this.id = gl.glGenTexture();

        this.bind();

        this.setFilters(GLConstants.GL_LINEAR, GLConstants.GL_LINEAR);
        this.setWrap(GLConstants.GL_REPEAT);

        this.unbind();
    }

    /**
     * Initializes 2d texture in OpenGL.
     *
     * @param level          mip map level
     * @param internalFormat e.g. GL_RGBA
     * @param format         e.g. GL_RGBA
     * @param pixelType      e.g. GL_UNSIGNED_BYTE
     * @param pixels         buffer containing texture pixels
     * @return 0 if succeeded, otherwise gl error code
     * @see GLTexture#init(int, int, int, int, int, int, Buffer)
     */
    public int init(int level, int internalFormat, int format, int pixelType, Buffer pixels) {
        return init(level, width >> level, height >> level, internalFormat, format, pixelType, pixels);
    }

    /**
     * Initializes 2d texture in OpenGL with specified width and height.
     *
     * @param level          mip map level
     * @param width          texture level width
     * @param height         texture level height
     * @param internalFormat e.g. GL_RGBA
     * @param format         e.g. GL_RGBA
     * @param pixelType      e.g. GL_UNSIGNED_BYTE
     * @param pixels         buffer containing texture pixels
     * @return 0 if succeeded, otherwise gl error code
     * @see GLTexture#init(int, int, int, int, Buffer)
     */
    public int init(int level, int width, int height, int internalFormat, int format, int pixelType, Buffer pixels) {
        this.internalFormat = internalFormat;
        this.format = format;
        this.pixelType = pixelType;

        gl.glTexImage2D(GLConstants.GL_TEXTURE_2D, level, internalFormat, width, height, 0, format, pixelType, pixels);
        return gl.glGetError();
    }

    /**
     * Initializes compressed 2d texture in OpenGL.
     *
     * @param level          mip map level
     * @param internalFormat e.g. GL_RGBA
     * @param format         e.g. GL_RGBA
     * @param data           buffer containing compressed texture data
     * @return 0 if succeeded, otherwise gl error code
     * @see GLTexture#initCompressed(int, int, int, int, int, ByteBuffer)
     */
    public int initCompressed(int level, int internalFormat, int format, ByteBuffer data) {
        return initCompressed(level, width >> level, height >> level, internalFormat, format, data);
    }

    /**
     * Initializes compressed 2d texture in OpenGL with specified width and height.
     *
     * @param level          mip map level
     * @param width          texture level width
     * @param height         texture level height
     * @param internalFormat e.g. GL_RGBA
     * @param format         e.g. GL_RGBA
     * @param data           buffer containing compressed texture data
     * @return 0 if succeeded, otherwise gl error code
     * @see GLTexture#initCompressed(int, int, int, ByteBuffer)
     */
    public int initCompressed(int level, int width, int height, int internalFormat, int format, ByteBuffer data) {
        this.internalFormat = format;
        this.format = format;

        gl.glCompressedTexImage2D(GLConstants.GL_TEXTURE_2D, level, internalFormat, width, height, 0, data.remaining(), data);
        return gl.glGetError();
    }

    /**
     * Updates 2d texture sub image in OpenGL.
     *
     * @param level     mip map level
     * @param format    e.g. GL_RGBA
     * @param pixelType e.g. GL_UNSIGNED_BYTE
     * @param pixels    buffer containing texture pixels
     * @return 0 if succeeded, otherwise gl error code
     */
    public int update(int level, int xOffset, int yOffset, int width, int height, int format, int pixelType, Buffer pixels) {
        this.internalFormat = format;
        this.format = format;
        this.pixelType = pixelType;

        gl.glTexSubImage2D(GLConstants.GL_TEXTURE_2D, level, xOffset, yOffset, width, height, format, pixelType, pixels);
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
            case GLConstants.GL_RGBA -> 4;
            case GLConstants.GL_RGB -> 3;
            case GLConstants.GL_LUMINANCE_ALPHA, GLConstants.GL_RG -> 2;
            case GLConstants.GL_LUMINANCE, GLConstants.GL_RED -> 1;
            default ->
                throw new IllegalArgumentException("Unsupported pixel format for pixel storage, pixel format: " + format);
        };
    }

    public int getPixelBytes() {
        return switch (pixelType) {
            case GLConstants.GL_UNSIGNED_BYTE -> getChannelCount();
            case GLConstants.GL_UNSIGNED_SHORT, GLConstants.GL_UNSIGNED_SHORT_4_4_4_4,
                 GLConstants.GL_UNSIGNED_SHORT_5_5_5_1,
                 GLConstants.GL_UNSIGNED_SHORT_5_6_5 -> 2;
            case GLConstants.GL_UNSIGNED_INT, GLConstants.GL_UNSIGNED_INT_24_8 -> 4;
            default ->
                throw new IllegalArgumentException("Unsupported pixel type: " + pixelType);
        };
    }

    public int getAlignment() {
        int rowSize = getPixelBytes() * width;
        if ((rowSize & 7) == 0) return 8;
        if ((rowSize & 3) == 0) return 4;
        if ((rowSize & 1) == 0) return 2;

        return 1;
    }

    public void bind() {
        // Fix of https://stackoverflow.com/a/70339902/14915825
        gl.glActiveTexture(GLConstants.GL_TEXTURE0);
        gl.glBindTexture(GLConstants.GL_TEXTURE_2D, this.id);
    }

    public void unbind() {
        gl.glBindTexture(GLConstants.GL_TEXTURE_2D, 0);
    }

    public void delete() {
        gl.glDeleteTexture(this.id);
    }

    public void generateMipMap() {
        gl.glGenerateMipmap(GLConstants.GL_TEXTURE_2D);
    }

    /// Ensure using in the render thread
    public IntBuffer getPixels(int level) {
        IntBuffer pixels = BufferUtils.allocateDirectInt(width * height * getChannelCount());
        gl.glGetTexImage(GLConstants.GL_TEXTURE_2D, level, this.internalFormat, this.pixelType, pixels);
        return pixels;
    }

    /// Ensure using in the render thread after binding texture
    public IntBuffer getCompressedData(int level) {
        IntBuffer isCompressed = IntBuffer.allocate(1);
        gl.glGetTexLevelParameteriv(GLConstants.GL_TEXTURE_2D, level, GLConstants.GL_TEXTURE_COMPRESSED, isCompressed);

        if (isCompressed.get() == GLConstants.GL_FALSE) return null;

        IntBuffer compressedImageSize = IntBuffer.allocate(1);
        gl.glGetTexLevelParameteriv(GLConstants.GL_TEXTURE_2D, level, GLConstants.GL_TEXTURE_COMPRESSED_IMAGE_SIZE, compressedImageSize);

        IntBuffer pixels = BufferUtils.allocateDirect(compressedImageSize.get()).asIntBuffer();
        gl.glGetCompressedTexImage(GLConstants.GL_TEXTURE_2D, level, pixels);
        return pixels;
    }

    public void setWrap(int wrap) {
        setWrapS(wrap);
        setWrapT(wrap);
    }

    public void setWrapS(int wrap) {
        gl.glTexParameteri(GLConstants.GL_TEXTURE_2D, GLConstants.GL_TEXTURE_WRAP_S, wrap);
    }

    public void setWrapT(int wrap) {
        gl.glTexParameteri(GLConstants.GL_TEXTURE_2D, GLConstants.GL_TEXTURE_WRAP_T, wrap);
    }

    public void setFilters(int minFilter, int magFilter) {
        setMagFilter(magFilter);
        setMinFilter(minFilter);
    }

    public void setMinFilter(int minFilter) {
        gl.glTexParameteri(GLConstants.GL_TEXTURE_2D, GLConstants.GL_TEXTURE_MIN_FILTER, minFilter);
    }

    public void setMagFilter(int magFilter) {
        gl.glTexParameteri(GLConstants.GL_TEXTURE_2D, GLConstants.GL_TEXTURE_MAG_FILTER, magFilter);
    }

    public void setParameter(int type, IntBuffer value) {
        gl.glTexParameteriv(GLConstants.GL_TEXTURE_2D, type, value);
    }

    // TODO: remove and replace in ktx loader with KTX format parsing
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
