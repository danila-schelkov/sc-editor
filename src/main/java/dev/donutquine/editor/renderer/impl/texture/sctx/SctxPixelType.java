package dev.donutquine.editor.renderer.impl.texture.sctx;

import dev.donutquine.editor.renderer.gl.GLConstants;
import dev.donutquine.sctx.PixelType;

public final class SctxPixelType {
    private SctxPixelType() {
    }

    public static int getFormat(PixelType pixelType) {
        return switch (pixelType) {
            case NONE, UNCOMPRESSED, ASTC_SRGBA8_4x4, ASTC_SRGBA8_5x4, ASTC_SRGBA8_5x5,
                 ASTC_SRGBA8_6x5, ASTC_SRGBA8_6x6, ASTC_SRGBA8_8x5, ASTC_SRGBA8_8x6,
                 ASTC_SRGBA8_8x8, ASTC_SRGBA8_10x5, ASTC_SRGBA8_10x6, ASTC_SRGBA8_10x8,
                 ASTC_SRGBA8_10x10, ASTC_SRGBA8_12x10, ASTC_SRGBA8_12x12,
                 ASTC_RGBA8_4x4, ASTC_RGBA8_5x4, ASTC_RGBA8_5x5, ASTC_RGBA8_6x5,
                 ASTC_RGBA8_6x6, ASTC_RGBA8_8x5, ASTC_RGBA8_8x6, ASTC_RGBA8_8x8,
                 ASTC_RGBA8_10x5, ASTC_RGBA8_10x6, ASTC_RGBA8_10x8, ASTC_RGBA8_10x10,
                 ASTC_RGBA8_12x10, ASTC_RGBA8_12x12, ETC2_EAC_RGBA8, ETC2_EAC_SRGBA8 ->
                GLConstants.GL_RGBA;
            case ETC2_RGB8, ETC2_SRGB8, ETC2_RGB8_PUNCHTHROUGH_ALPHA1,
                 ETC2_SRGB8_PUNCHTHROUGH_ALPHA1, ETC1_RGB8 -> GLConstants.GL_RGB;
            case EAC_R11, EAC_SIGNED_R11, ETC1_LUMINANCE -> GLConstants.GL_LUMINANCE;
            case EAC_RG11, EAC_SIGNED_RG11, ETC1_LUMINANCE_ALPHA ->
                GLConstants.GL_LUMINANCE_ALPHA;
        };
    }

    /// This must be sized in KTX, so in OpenGL too I guess.
    public static int getInternalFormat(PixelType pixelType) {
        return switch (pixelType) {
            case UNCOMPRESSED -> GLConstants.GL_RGBA8;
            case ASTC_SRGBA8_4x4 -> GLConstants.GL_COMPRESSED_SRGB8_ALPHA8_ASTC_4x4;
            case ASTC_SRGBA8_5x4 -> GLConstants.GL_COMPRESSED_SRGB8_ALPHA8_ASTC_5x4;
            case ASTC_SRGBA8_5x5 -> GLConstants.GL_COMPRESSED_SRGB8_ALPHA8_ASTC_5x5;
            case ASTC_SRGBA8_6x5 -> GLConstants.GL_COMPRESSED_SRGB8_ALPHA8_ASTC_6x5;
            case ASTC_SRGBA8_6x6 -> GLConstants.GL_COMPRESSED_SRGB8_ALPHA8_ASTC_6x6;
            case ASTC_SRGBA8_8x5 -> GLConstants.GL_COMPRESSED_SRGB8_ALPHA8_ASTC_8x5;
            case ASTC_SRGBA8_8x6 -> GLConstants.GL_COMPRESSED_SRGB8_ALPHA8_ASTC_8x6;
            case ASTC_SRGBA8_8x8 -> GLConstants.GL_COMPRESSED_SRGB8_ALPHA8_ASTC_8x8;
            case ASTC_SRGBA8_10x5 -> GLConstants.GL_COMPRESSED_SRGB8_ALPHA8_ASTC_10x5;
            case ASTC_SRGBA8_10x6 -> GLConstants.GL_COMPRESSED_SRGB8_ALPHA8_ASTC_10x6;
            case ASTC_SRGBA8_10x8 -> GLConstants.GL_COMPRESSED_SRGB8_ALPHA8_ASTC_10x8;
            case ASTC_SRGBA8_10x10 -> GLConstants.GL_COMPRESSED_SRGB8_ALPHA8_ASTC_10x10;
            case ASTC_SRGBA8_12x10 -> GLConstants.GL_COMPRESSED_SRGB8_ALPHA8_ASTC_12x10;
            case ASTC_SRGBA8_12x12 -> GLConstants.GL_COMPRESSED_SRGB8_ALPHA8_ASTC_12x12;
            case ASTC_RGBA8_4x4 -> GLConstants.GL_COMPRESSED_RGBA_ASTC_4x4;
            case ASTC_RGBA8_5x4 -> GLConstants.GL_COMPRESSED_RGBA_ASTC_5x4;
            case ASTC_RGBA8_5x5 -> GLConstants.GL_COMPRESSED_RGBA_ASTC_5x5;
            case ASTC_RGBA8_6x5 -> GLConstants.GL_COMPRESSED_RGBA_ASTC_6x5;
            case ASTC_RGBA8_6x6 -> GLConstants.GL_COMPRESSED_RGBA_ASTC_6x6;
            case ASTC_RGBA8_8x5 -> GLConstants.GL_COMPRESSED_RGBA_ASTC_8x5;
            case ASTC_RGBA8_8x6 -> GLConstants.GL_COMPRESSED_RGBA_ASTC_8x6;
            case ASTC_RGBA8_8x8 -> GLConstants.GL_COMPRESSED_RGBA_ASTC_8x8;
            case ASTC_RGBA8_10x5 -> GLConstants.GL_COMPRESSED_RGBA_ASTC_10x5;
            case ASTC_RGBA8_10x6 -> GLConstants.GL_COMPRESSED_RGBA_ASTC_10x6;
            case ASTC_RGBA8_10x8 -> GLConstants.GL_COMPRESSED_RGBA_ASTC_10x8;
            case ASTC_RGBA8_10x10 -> GLConstants.GL_COMPRESSED_RGBA_ASTC_10x10;
            case ASTC_RGBA8_12x10 -> GLConstants.GL_COMPRESSED_RGBA_ASTC_12x10;
            case ASTC_RGBA8_12x12 -> GLConstants.GL_COMPRESSED_RGBA_ASTC_12x12;
            case ETC2_EAC_RGBA8 -> GLConstants.GL_COMPRESSED_RGBA8_ETC2_EAC;
            case ETC2_EAC_SRGBA8 -> GLConstants.GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC;
            case ETC2_RGB8 -> GLConstants.GL_COMPRESSED_RGB8_ETC2;
            case ETC2_SRGB8 -> GLConstants.GL_COMPRESSED_SRGB8_ETC2;
            case ETC2_RGB8_PUNCHTHROUGH_ALPHA1 ->
                GLConstants.GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2;
            case ETC2_SRGB8_PUNCHTHROUGH_ALPHA1 ->
                GLConstants.GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2;
            case EAC_R11 -> GLConstants.GL_COMPRESSED_R11_EAC;
            case EAC_SIGNED_R11 -> GLConstants.GL_COMPRESSED_SIGNED_R11_EAC;
            case EAC_RG11 -> GLConstants.GL_COMPRESSED_RG11_EAC;
            case EAC_SIGNED_RG11 -> GLConstants.GL_COMPRESSED_SIGNED_RG11_EAC;
            case NONE, ETC1_RGB8, ETC1_LUMINANCE, ETC1_LUMINANCE_ALPHA ->
                throw new IllegalStateException("Unexpected value: " + pixelType);
        };
    }

    public static int getPixelType(PixelType pixelType) {
        return switch (pixelType) {
            case UNCOMPRESSED, ASTC_SRGBA8_4x4, ASTC_SRGBA8_5x4, ASTC_SRGBA8_5x5,
                 ASTC_SRGBA8_6x5, ASTC_SRGBA8_6x6, ASTC_SRGBA8_8x5, ASTC_SRGBA8_8x6,
                 ASTC_SRGBA8_8x8, ASTC_SRGBA8_10x5, ASTC_SRGBA8_10x6, ASTC_SRGBA8_10x8,
                 ASTC_SRGBA8_10x10, ASTC_SRGBA8_12x10, ASTC_SRGBA8_12x12,
                 ASTC_RGBA8_4x4, ASTC_RGBA8_5x4, ASTC_RGBA8_5x5, ASTC_RGBA8_6x5,
                 ASTC_RGBA8_6x6, ASTC_RGBA8_8x5, ASTC_RGBA8_8x6, ASTC_RGBA8_8x8,
                 ASTC_RGBA8_10x5, ASTC_RGBA8_10x6, ASTC_RGBA8_10x8, ASTC_RGBA8_10x10,
                 ASTC_RGBA8_12x10, ASTC_RGBA8_12x12, ETC2_EAC_RGBA8, ETC2_EAC_SRGBA8,
                 ETC2_RGB8, ETC2_SRGB8, ETC2_RGB8_PUNCHTHROUGH_ALPHA1,
                 ETC2_SRGB8_PUNCHTHROUGH_ALPHA1, ETC1_RGB8, EAC_R11, EAC_SIGNED_R11,
                 ETC1_LUMINANCE, EAC_RG11, EAC_SIGNED_RG11, ETC1_LUMINANCE_ALPHA ->
                GLConstants.GL_UNSIGNED_BYTE;
            case NONE ->
                throw new IllegalStateException("Unexpected value: " + pixelType);
        };
    }
}
