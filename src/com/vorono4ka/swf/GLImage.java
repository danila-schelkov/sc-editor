package com.vorono4ka.swf;

import com.jogamp.opengl.GL3;
import com.vorono4ka.editor.renderer.Stage;
import team.nulls.ntengine.assets.KhronosTexture;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class GLImage {
    private int textureId;

    protected int width;
    protected int height;
    protected int pixelFormat;

    public void bind() {
        GL3 gl = Stage.getInstance().getGl();
        gl.glBindTexture(GL3.GL_TEXTURE_2D, this.textureId);
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

    public int getPixelFormat() {
        return this.pixelFormat;
    }

    public void setPixelFormat(int pixelFormat) {
        this.pixelFormat = pixelFormat;
    }

    public int getTextureId() {
        return textureId;
    }

    public void setTextureId(int textureId) {
        this.textureId = textureId;
    }

    public void createWithFormat(KhronosTexture ktx, boolean clampToEdge, int filter, int width, int height, Buffer pixels, int pixelFormat, int pixelType) {
        Stage stage = Stage.getInstance();
        GL3 gl = stage.getGl();

        this.setWidth(width);
        this.setHeight(height);
        this.setPixelFormat(pixelFormat);

        stage.doInRenderThread(() -> {
            if (this.getTextureId() != 0) {
                gl.glDeleteTextures(1, new int[] {this.getTextureId()}, 0);
            }

            int id = genTexture();

            this.setTextureId(id);

            int magFilter;
            int minFilter;
            switch (filter) {
//                case 1 -> {  // TODO: find out what's wrong with linear and why sprites become transparent using this filter
//                    magFilter = GL3.GL_LINEAR;
//                    minFilter = GL3.GL_LINEAR;
//                }
                case 2 -> {
                    magFilter = GL3.GL_LINEAR;
                    minFilter = GL3.GL_LINEAR_MIPMAP_NEAREST;
                }
                case 3 -> {
                    magFilter = GL3.GL_LINEAR;
                    minFilter = GL3.GL_LINEAR_MIPMAP_LINEAR;
                }
                default -> {
                    magFilter = GL3.GL_NEAREST;
                    minFilter = GL3.GL_NEAREST;
                }
            }

            int wrap = clampToEdge ? GL3.GL_CLAMP_TO_EDGE : GL3.GL_REPEAT;

            gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_WRAP_S, wrap);
            gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_WRAP_T, wrap);
            gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MAG_FILTER, magFilter);
            gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MIN_FILTER, minFilter);

            int channelCount = getChannelCount(pixelFormat);
            gl.glPixelStorei(GL3.GL_UNPACK_ALIGNMENT, channelCount);

            if (ktx != null) {
                loadKtx(gl, ktx);
            } else {
                loadImage(gl, this, width, height, pixels, pixelFormat, pixelType);
            }

            gl.glGenerateMipmap(GL3.GL_TEXTURE_2D);

            gl.glBindTexture(GL3.GL_TEXTURE_2D, 0);
        });
    }

    private static int getChannelCount(int pixelFormat) {
        return switch (pixelFormat) {
            case GL3.GL_RGBA -> 4;
            case GL3.GL_RGB -> 3;
            case GL3.GL_LUMINANCE_ALPHA, GL3.GL_RG -> 2;
            case GL3.GL_LUMINANCE, GL3.GL_RED -> 1;
            default ->
                throw new IllegalArgumentException("Unsupported pixel format for pixel storage, pixel format: " + pixelFormat);
        };
    }

    private static void loadImage(GL3 gl, GLImage image, int width, int height, Buffer pixels, int pixelFormat, int pixelType) {
        gl.glTexImage2D(GL3.GL_TEXTURE_2D, 0, pixelFormat, width, height, 0, pixelFormat, pixelType, pixels);
        if (gl.glGetError() == GL3.GL_INVALID_ENUM && (pixelFormat == GL3.GL_LUMINANCE_ALPHA || pixelFormat == GL3.GL_LUMINANCE)) {
            IntBuffer swizzleMask = null;
            int format = -1;

            switch (pixelFormat) {
                case GL3.GL_LUMINANCE_ALPHA -> {
                    swizzleMask = IntBuffer.wrap(new int[] {GL3.GL_RED, GL3.GL_RED, GL3.GL_RED, GL3.GL_GREEN});
                    format = GL3.GL_RG;
                }
                case GL3.GL_LUMINANCE -> {
                    swizzleMask = IntBuffer.wrap(new int[] {GL3.GL_RED, GL3.GL_RED, GL3.GL_RED, 1});
                    format = GL3.GL_RED;
                }
                default -> {
                    assert false : "GL_INVALID_ENUM";
                }
            }

            gl.glTexImage2D(GL3.GL_TEXTURE_2D, 0, format, width, height, 0, format, pixelType, pixels);
            if (gl.glGetError() == GL3.GL_NO_ERROR) {
                gl.glTexParameteriv(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_SWIZZLE_RGBA, swizzleMask);
                image.setPixelFormat(format);
            }
        }
    }

    private static void loadKtx(GL3 gl, KhronosTexture ktx) {
        if (ktx.glFormat != 0) {
            for (int level = 0; level < ktx.data.length; level++) {
                gl.glTexImage2D(GL3.GL_TEXTURE_2D, level, ktx.glInternalFormat, ktx.pixelWidth, ktx.pixelHeight, 0, ktx.glFormat, GL3.GL_UNSIGNED_INT, ktx.data[level]);
            }
        } else {
            for (int level = 0; level < ktx.data.length; level++) {
                ByteBuffer data = ktx.data[level];
                gl.glCompressedTexImage2D(GL3.GL_TEXTURE_2D, level, ktx.glInternalFormat, ktx.pixelWidth, ktx.pixelHeight, 0, data.remaining(), data);
            }
        }
    }

    private static int genTexture() {
        GL3 gl = Stage.getInstance().getGl();

        IntBuffer ids = IntBuffer.allocate(1);
        gl.glGenTextures(1, ids);
        gl.glBindTexture(GL3.GL_TEXTURE_2D, ids.get(0));
        return ids.get(0);
    }
}
