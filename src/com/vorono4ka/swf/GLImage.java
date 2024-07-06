package com.vorono4ka.swf;

import com.jogamp.opengl.GL3;
import com.vorono4ka.editor.renderer.Stage;
import com.vorono4ka.editor.renderer.Texture;
import com.vorono4ka.utilities.BufferUtils;
import team.nulls.ntengine.assets.KhronosTexture;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class GLImage {
    protected Texture texture;
    protected int width;
    protected int height;
    protected int pixelFormat;

    private static void loadImage(Texture texture, Buffer pixels, int pixelFormat, int pixelType) {
        int error = texture.init(0, pixelFormat, pixelFormat, pixelType, pixels);
        if (error == GL3.GL_INVALID_ENUM && (pixelFormat == GL3.GL_LUMINANCE_ALPHA || pixelFormat == GL3.GL_LUMINANCE)) {
            IntBuffer swizzleMask = null;
            int format = -1;

            switch (pixelFormat) {
                case GL3.GL_LUMINANCE_ALPHA -> {
                    swizzleMask = BufferUtils.wrapDirect(GL3.GL_RED, GL3.GL_RED, GL3.GL_RED, GL3.GL_GREEN);
                    format = GL3.GL_RG;
                }
                case GL3.GL_LUMINANCE -> {
                    swizzleMask = BufferUtils.wrapDirect(GL3.GL_RED, GL3.GL_RED, GL3.GL_RED, 1);
                    format = GL3.GL_RED;
                }
                default -> {
                    assert false : "GL_INVALID_ENUM";
                }
            }

            error = texture.init(0, GL3.GL_RGBA, format, pixelType, pixels);
            if (error == GL3.GL_NO_ERROR) {
                texture.setParameter(GL3.GL_TEXTURE_SWIZZLE_RGBA, swizzleMask);
            }
        }
    }

    private static void loadKtx(Texture texture, KhronosTexture ktx) {
        if (ktx.glFormat() != 0) {
            for (int level = 0; level < ktx.levels().length; level++) {
                texture.init(level, ktx.glInternalFormat(), ktx.glFormat(), GL3.GL_UNSIGNED_INT, ktx.levels()[level]);
            }
        } else {
            for (int level = 0; level < ktx.levels().length; level++) {
                ByteBuffer data = ktx.levels()[level];
                texture.initCompressed(level, ktx.glInternalFormat(), ktx.glBaseInternalFormat(), ktx.width(), ktx.height(), data);
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getPixelFormat() {
        return this.pixelFormat;
    }

    public void setPixelFormat(int pixelFormat) {
        this.pixelFormat = pixelFormat;
    }

    public Texture getTexture() {
        return texture;
    }

    public int getTextureId() {
        return texture.getId();
    }

    public void createWithFormat(KhronosTexture ktx, boolean clampToEdge, int filter, int width, int height, Buffer pixels, int pixelFormat, int pixelType) {
        Stage stage = Stage.getInstance();
        GL3 gl = stage.getGl();

        this.width = width;
        this.height = height;
        this.setPixelFormat(pixelFormat);

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

        stage.doInRenderThread(() -> {
            if (this.texture != null) {
                this.texture.delete();
            }

            texture = new Texture(gl, width, height);
            texture.bind();

            texture.setWrap(clampToEdge ? GL3.GL_CLAMP_TO_EDGE : GL3.GL_REPEAT);
            texture.setFilters(minFilter, magFilter);

            if (ktx != null) {
                loadKtx(texture, ktx);
            } else {
                loadImage(texture, pixels, pixelFormat, pixelType);
            }

            texture.generateMipMap();

            int channelCount = texture.getChannelCount();
            gl.glPixelStorei(GL3.GL_UNPACK_ALIGNMENT, channelCount);

            texture.unbind();
        });
    }
}
