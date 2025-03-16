package com.vorono4ka.editor.renderer.texture;

import com.jogamp.opengl.GL3;
import com.vorono4ka.editor.renderer.Stage;
import com.vorono4ka.editor.renderer.texture.khronos.KhronosTextureLoader;
import com.vorono4ka.editor.renderer.texture.khronos.SctxTextureLoader;
import com.vorono4ka.sctx.SctxTexture;
import com.vorono4ka.utilities.BufferUtils;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class GLImage {
    public static KhronosTextureLoader khronosTextureLoader;
    public static SctxTextureLoader sctxTextureLoader;

    protected Texture texture;
    protected int width;
    protected int height;
    protected int pixelFormat;

    public static void loadImage(Texture texture, Buffer pixels, int pixelFormat, int pixelType) {
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

    private static void loadKhronosTexture(Texture texture, ByteBuffer khronosTextureFileData) {
        if (khronosTextureLoader != null) {
            try {
                khronosTextureLoader.load(texture, khronosTextureFileData);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            return;
        }

        throw new RuntimeException("Khronos textures aren't supported on your device. Install ktx2ktx2 and ktx from https://github.com/KhronosGroup/KTX-Software and restart the program.");
    }

    private static void loadTexture(Texture texture, SctxTexture sctxTexture) {
        if (sctxTextureLoader != null && sctxTextureLoader.isAvailable()) {
            try {
                sctxTextureLoader.load(texture, sctxTexture);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            return;
        }

        throw new RuntimeException("ASTC textures aren't supported on your device.");
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

    public Texture getTexture() {
        return texture;
    }

    public int getTextureId() {
        return texture.getId();
    }

    public void createWithFormat(byte[] khronosTextureFileData, SctxTexture sctxTexture, boolean clampToEdge, int filter, int width, int height, Buffer pixels, int pixelFormat, int pixelType) {
        this.width = width;
        this.height = height;
        this.pixelFormat = pixelFormat;

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

        Stage stage = Stage.getInstance();
        stage.doInRenderThread(() -> {
            if (this.texture != null) {
                this.texture.delete();
            }

            GL3 gl = stage.getGl();

            texture = new Texture(gl, width, height);
            texture.bind();

            texture.setPixelInfo(pixelFormat, pixelType);

            texture.setWrap(clampToEdge ? GL3.GL_CLAMP_TO_EDGE : GL3.GL_REPEAT);
            texture.setFilters(minFilter, magFilter);

            if (khronosTextureFileData != null) {
                loadKhronosTexture(texture, BufferUtils.wrapDirect(khronosTextureFileData));
            } else if (sctxTexture != null) {
                loadTexture(texture, sctxTexture);
            } else {
                loadImage(texture, pixels, pixelFormat, pixelType);
                texture.generateMipMap();
            }

            int channelCount = texture.getChannelCount();
            gl.glPixelStorei(GL3.GL_UNPACK_ALIGNMENT, channelCount);

            texture.unbind();
        });
    }
}
