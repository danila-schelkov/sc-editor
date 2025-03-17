package com.vorono4ka.editor.renderer.impl.texture.khronos;

import com.jogamp.opengl.GL3;
import com.vorono4ka.editor.renderer.texture.GLTexture;
import com.vorono4ka.sctx.MipMapData;
import com.vorono4ka.sctx.PixelType;
import com.vorono4ka.sctx.SctxTexture;
import com.vorono4ka.utilities.BufferUtils;

import java.util.List;

public class ExtensionSctxTextureLoader implements SctxTextureLoader {
    private final GL3 gl;

    public ExtensionSctxTextureLoader(GL3 gl) {
        this.gl = gl;
    }

    @Override
    public boolean isAvailable() {
        return gl.isExtensionAvailable("GL_KHR_texture_compression_astc_ldr");
    }

    @Override
    public void load(GLTexture texture, SctxTexture sctxTexture) {
        PixelType pixelType = sctxTexture.getPixelType();

        if (pixelType == PixelType.UNCOMPRESSED) {
            List<MipMapData> mipMaps = sctxTexture.getMipMaps();
            for (int level = 0; level < mipMaps.size(); level++) {
                MipMapData mipMap = mipMaps.get(level);
                texture.init(level, GL3.GL_RGBA, GL3.GL_RGBA, GL3.GL_UNSIGNED_INT, BufferUtils.wrapDirect(mipMap.data()));
            }
        } else {
            int format = getFormat(pixelType);
            int internalFormat = getInternalFormat(pixelType);

            List<MipMapData> mipMaps = sctxTexture.getMipMaps();
            for (int level = 0; level < mipMaps.size(); level++) {
                MipMapData mipMap = mipMaps.get(level);
                texture.initCompressed(level, internalFormat, format, BufferUtils.wrapDirect(mipMap.data()));
            }
        }
    }

    private static int getFormat(PixelType pixelType) {
        return switch (pixelType) {
            case NONE, UNCOMPRESSED, ASTC_SRGBA8_4x4, ASTC_SRGBA8_5x4, ASTC_SRGBA8_5x5,
                 ASTC_SRGBA8_6x5, ASTC_SRGBA8_6x6, ASTC_SRGBA8_8x5, ASTC_SRGBA8_8x6,
                 ASTC_SRGBA8_8x8, ASTC_SRGBA8_10x5, ASTC_SRGBA8_10x6, ASTC_SRGBA8_10x8,
                 ASTC_SRGBA8_10x10, ASTC_SRGBA8_12x10, ASTC_SRGBA8_12x12,
                 ASTC_RGBA8_4x4, ASTC_RGBA8_5x4, ASTC_RGBA8_5x5, ASTC_RGBA8_6x5,
                 ASTC_RGBA8_6x6, ASTC_RGBA8_8x5, ASTC_RGBA8_8x6, ASTC_RGBA8_8x8,
                 ASTC_RGBA8_10x5, ASTC_RGBA8_10x6, ASTC_RGBA8_10x8, ASTC_RGBA8_10x10,
                 ASTC_RGBA8_12x10, ASTC_RGBA8_12x12, ETC2_EAC_RGBA8, ETC2_EAC_SRGBA8 ->
                GL3.GL_RGBA;
            case ETC2_RGB8, ETC2_SRGB8, ETC2_RGB8_PUNCHTHROUGH_ALPHA1,
                 ETC2_SRGB8_PUNCHTHROUGH_ALPHA1, ETC1_RGB8 -> GL3.GL_RGB;
            case EAC_R11, EAC_SIGNED_R11, ETC1_LUMINANCE -> GL3.GL_LUMINANCE;
            case EAC_RG11, EAC_SIGNED_RG11, ETC1_LUMINANCE_ALPHA ->
                GL3.GL_LUMINANCE_ALPHA;
        };
    }

    private static int getInternalFormat(PixelType pixelType) {
        return switch (pixelType) {
            case UNCOMPRESSED -> GL3.GL_RGBA;
            case ASTC_SRGBA8_4x4 -> GL3.GL_COMPRESSED_SRGB8_ALPHA8_ASTC_4x4;
            case ASTC_SRGBA8_5x4 -> GL3.GL_COMPRESSED_SRGB8_ALPHA8_ASTC_5x4;
            case ASTC_SRGBA8_5x5 -> GL3.GL_COMPRESSED_SRGB8_ALPHA8_ASTC_5x5;
            case ASTC_SRGBA8_6x5 -> GL3.GL_COMPRESSED_SRGB8_ALPHA8_ASTC_6x5;
            case ASTC_SRGBA8_6x6 -> GL3.GL_COMPRESSED_SRGB8_ALPHA8_ASTC_6x6;
            case ASTC_SRGBA8_8x5 -> GL3.GL_COMPRESSED_SRGB8_ALPHA8_ASTC_8x5;
            case ASTC_SRGBA8_8x6 -> GL3.GL_COMPRESSED_SRGB8_ALPHA8_ASTC_8x6;
            case ASTC_SRGBA8_8x8 -> GL3.GL_COMPRESSED_SRGB8_ALPHA8_ASTC_8x8;
            case ASTC_SRGBA8_10x5 -> GL3.GL_COMPRESSED_SRGB8_ALPHA8_ASTC_10x5;
            case ASTC_SRGBA8_10x6 -> GL3.GL_COMPRESSED_SRGB8_ALPHA8_ASTC_10x6;
            case ASTC_SRGBA8_10x8 -> GL3.GL_COMPRESSED_SRGB8_ALPHA8_ASTC_10x8;
            case ASTC_SRGBA8_10x10 -> GL3.GL_COMPRESSED_SRGB8_ALPHA8_ASTC_10x10;
            case ASTC_SRGBA8_12x10 -> GL3.GL_COMPRESSED_SRGB8_ALPHA8_ASTC_12x10;
            case ASTC_SRGBA8_12x12 -> GL3.GL_COMPRESSED_SRGB8_ALPHA8_ASTC_12x12;
            case ASTC_RGBA8_4x4 -> GL3.GL_COMPRESSED_RGBA_ASTC_4x4;
            case ASTC_RGBA8_5x4 -> GL3.GL_COMPRESSED_RGBA_ASTC_5x4;
            case ASTC_RGBA8_5x5 -> GL3.GL_COMPRESSED_RGBA_ASTC_5x5;
            case ASTC_RGBA8_6x5 -> GL3.GL_COMPRESSED_RGBA_ASTC_6x5;
            case ASTC_RGBA8_6x6 -> GL3.GL_COMPRESSED_RGBA_ASTC_6x6;
            case ASTC_RGBA8_8x5 -> GL3.GL_COMPRESSED_RGBA_ASTC_8x5;
            case ASTC_RGBA8_8x6 -> GL3.GL_COMPRESSED_RGBA_ASTC_8x6;
            case ASTC_RGBA8_8x8 -> GL3.GL_COMPRESSED_RGBA_ASTC_8x8;
            case ASTC_RGBA8_10x5 -> GL3.GL_COMPRESSED_RGBA_ASTC_10x5;
            case ASTC_RGBA8_10x6 -> GL3.GL_COMPRESSED_RGBA_ASTC_10x6;
            case ASTC_RGBA8_10x8 -> GL3.GL_COMPRESSED_RGBA_ASTC_10x8;
            case ASTC_RGBA8_10x10 -> GL3.GL_COMPRESSED_RGBA_ASTC_10x10;
            case ASTC_RGBA8_12x10 -> GL3.GL_COMPRESSED_RGBA_ASTC_12x10;
            case ASTC_RGBA8_12x12 -> GL3.GL_COMPRESSED_RGBA_ASTC_12x12;
            case ETC2_EAC_RGBA8 -> GL3.GL_COMPRESSED_RGBA8_ETC2_EAC;
            case ETC2_EAC_SRGBA8 -> GL3.GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC;
            case ETC2_RGB8 -> GL3.GL_COMPRESSED_RGB8_ETC2;
            case ETC2_SRGB8 -> GL3.GL_COMPRESSED_SRGB8_ETC2;
            case ETC2_RGB8_PUNCHTHROUGH_ALPHA1 -> GL3.GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2;
            case ETC2_SRGB8_PUNCHTHROUGH_ALPHA1 -> GL3.GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2;
            case EAC_R11 -> GL3.GL_COMPRESSED_R11_EAC;
            case EAC_SIGNED_R11 -> GL3.GL_COMPRESSED_SIGNED_R11_EAC;
            case EAC_RG11 -> GL3.GL_COMPRESSED_RG11_EAC;
            case EAC_SIGNED_RG11 -> GL3.GL_COMPRESSED_SIGNED_RG11_EAC;
            case NONE, ETC1_RGB8, ETC1_LUMINANCE, ETC1_LUMINANCE_ALPHA ->
                throw new IllegalStateException("Unexpected value: " + pixelType);
        };
    }
}
