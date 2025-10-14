package dev.donutquine.editor.renderer.impl.texture;

import com.vorono4ka.sctx.MipMapData;
import com.vorono4ka.sctx.PixelType;
import com.vorono4ka.sctx.SctxTexture;
import dev.donutquine.editor.layout.dialogs.ExceptionDialog;
import dev.donutquine.editor.renderer.gl.GLConstants;
import dev.donutquine.editor.renderer.gl.texture.GLTexture;
import dev.donutquine.editor.renderer.impl.EditorStage;
import dev.donutquine.editor.renderer.impl.texture.khronos.KhronosTextureDataSaver;
import dev.donutquine.editor.renderer.impl.texture.khronos.KhronosTextureLoader;
import dev.donutquine.editor.renderer.impl.texture.sctx.SctxPixelType;
import dev.donutquine.utilities.BufferUtils;
import team.nulls.ntengine.assets.KhronosTexture;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.List;

public final class GLImage {
    public static KhronosTextureLoader khronosTextureLoader;

    private GLImage() {
    }

    public static void loadImage(GLTexture texture, Buffer pixels, int pixelFormat, int pixelType) {
        int error = texture.init(0, pixelFormat, pixelFormat, pixelType, pixels);
        if (error == GLConstants.GL_INVALID_ENUM && (pixelFormat == GLConstants.GL_LUMINANCE_ALPHA || pixelFormat == GLConstants.GL_LUMINANCE)) {
            IntBuffer swizzleMask = null;
            int format = -1;

            switch (pixelFormat) {
                case GLConstants.GL_LUMINANCE_ALPHA -> {
                    swizzleMask = BufferUtils.wrapDirect(GLConstants.GL_RED, GLConstants.GL_RED, GLConstants.GL_RED, GLConstants.GL_GREEN);
                    format = GLConstants.GL_RG;
                }
                case GLConstants.GL_LUMINANCE -> {
                    swizzleMask = BufferUtils.wrapDirect(GLConstants.GL_RED, GLConstants.GL_RED, GLConstants.GL_RED, 1);
                    format = GLConstants.GL_RED;
                }
                default -> {
                    assert false : "GL_INVALID_ENUM";
                }
            }

            error = texture.init(0, GLConstants.GL_RGBA, format, pixelType, pixels);
            if (error == GLConstants.GL_NO_ERROR) {
                texture.setParameter(GLConstants.GL_TEXTURE_SWIZZLE_RGBA, swizzleMask);
            }
        }
    }

    public static GLTexture createWithFormat(int width, int height, boolean clampToEdge, ImageFilter filter, int pixelFormat, int pixelType, Buffer pixels, SctxTexture sctxTexture, byte[] khronosTextureFileData) {
        EditorStage stage = EditorStage.getInstance();

        GLTexture texture = new GLTexture(stage.getGlContext(), width, height);
        texture.setPixelInfo(pixelFormat, pixelType);

        stage.doInRenderThread(() -> {
            texture.bindContext();
            texture.bind();

            texture.setWrap(clampToEdge ? GLConstants.GL_CLAMP_TO_EDGE : GLConstants.GL_REPEAT);
            texture.setFilters(filter.getMinFilter(), filter.getMagFilter());

            stage.getGlContext().glPixelStorei(GLConstants.GL_UNPACK_ALIGNMENT, texture.getAlignment());

            if (khronosTextureFileData != null) {
                loadKhronosTexture(texture, BufferUtils.wrapDirect(khronosTextureFileData));
            } else if (sctxTexture != null) {
                loadTexture(texture, sctxTexture);
            } else {
                loadImage(texture, pixels, pixelFormat, pixelType);
                texture.generateMipMap();
            }

            texture.unbind();
        });

        return texture;
    }

    public static GLTexture updateSubImage(GLTexture texture, boolean clampToEdge, ImageFilter filter, int width, int height, int pixelFormat, int pixelType, Buffer pixels) {
        EditorStage stage = EditorStage.getInstance();

        stage.doInRenderThread(() -> {
            texture.bind();

            texture.setWrap(clampToEdge ? GLConstants.GL_CLAMP_TO_EDGE : GLConstants.GL_REPEAT);
            texture.setFilters(filter.getMinFilter(), filter.getMagFilter());

            stage.getGlContext().glPixelStorei(GLConstants.GL_UNPACK_ALIGNMENT, texture.getAlignment());
            texture.update(0, 0, 0, width, height, pixelFormat, pixelType, pixels);

            texture.unbind();
        });

        return texture;
    }

    private static void loadKhronosTexture(GLTexture texture, ByteBuffer khronosTextureFileData) {
        if (khronosTextureLoader != null) {
            try {
                khronosTextureLoader.load(texture, khronosTextureFileData);
            } catch (Throwable e) {
                ExceptionDialog.showExceptionDialog(Thread.currentThread(), e);
                throw new RuntimeException(e);
            }

            return;
        }

        throw new RuntimeException("Khronos textures aren't supported on your device. Install ktx2ktx2 and ktx from https://github.com/KhronosGroup/KTX-Software and restart the program.");
    }

    private static void loadTexture(GLTexture texture, SctxTexture sctxTexture) {
        PixelType pixelType = sctxTexture.getPixelType();

        int glFormat = SctxPixelType.getFormat(pixelType);
        int glInternalFormat = SctxPixelType.getInternalFormat(pixelType);
        int glPixelType = SctxPixelType.getPixelType(pixelType);

        List<MipMapData> mipMaps = sctxTexture.getMipMaps();
        byte[][] levels = new byte[mipMaps.size()][];

        for (int level = 0; level < mipMaps.size(); level++) {
            MipMapData mipMap = mipMaps.get(level);
            assert mipMap.width() == (sctxTexture.getWidth() >> level);
            assert mipMap.height() == (sctxTexture.getHeight() >> level);
            levels[level] = mipMap.data();
        }

        KhronosTexture ktx = new KhronosTexture(
            pixelType == PixelType.UNCOMPRESSED ? glPixelType : 0,
            // For texture data which does not depend on platform endianness, including compressed texture data, glTypeSize must equal 1.
            // https://registry.khronos.org/KTX/specs/1.0/ktxspec.v1.html
            1,
            pixelType == PixelType.UNCOMPRESSED ? glFormat : 0,
            glInternalFormat,
            glFormat,
            sctxTexture.getWidth(),
            sctxTexture.getHeight(),
            levels
        );

        ByteBuffer khronosTextureFileData = KhronosTextureDataSaver.encodeKtx(ktx);

        loadKhronosTexture(texture, khronosTextureFileData);
    }
}
