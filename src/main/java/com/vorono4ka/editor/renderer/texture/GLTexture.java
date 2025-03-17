package com.vorono4ka.editor.renderer.texture;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public interface GLTexture extends RenderableTexture {
    IntBuffer getCompressedData(int level);

    /**
     * Initializes 2d texture in OpenGL
     *
     * @param level          mip map level
     * @param internalFormat e.g. GL_RGBA
     * @param format         e.g. GL_RGBA
     * @param pixelType      e.g. GL_UNSIGNED_BYTE
     * @param pixels         buffer containing texture pixels
     * @return 0 if succeeded, otherwise gl error code
     */
    int init(int level, int internalFormat, int format, int pixelType, Buffer pixels);

    /**
     * Initializes compressed 2d texture in OpenGL
     *
     * @param level          mip map level
     * @param internalFormat e.g. GL_RGBA
     * @param format         e.g. GL_RGBA
     * @param data           buffer containing compressed texture data
     * @return 0 if succeeded, otherwise gl error code
     */
    int initCompressed(int level, int internalFormat, int format, ByteBuffer data);

    void setWrap(int wrap);

    void setWrapS(int wrap);

    void setWrapT(int wrap);

    void setFilters(int minFilter, int magFilter);

    void setMinFilter(int minFilter);

    void setMagFilter(int magFilter);

    void setParameter(int type, IntBuffer value);

    void setPixelInfo(int format, int pixelType);

    int getInternalFormat();

    int getFormat();

    int getPixelType();
}
