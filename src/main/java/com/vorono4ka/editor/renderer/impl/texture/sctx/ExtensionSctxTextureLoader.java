package com.vorono4ka.editor.renderer.impl.texture.sctx;

import com.vorono4ka.editor.renderer.gl.GLConstants;
import com.vorono4ka.editor.renderer.gl.GLRendererContext;
import com.vorono4ka.editor.renderer.gl.texture.GLTexture;
import com.vorono4ka.sctx.MipMapData;
import com.vorono4ka.sctx.PixelType;
import com.vorono4ka.sctx.SctxTexture;
import com.vorono4ka.utilities.BufferUtils;

import java.util.List;

public class ExtensionSctxTextureLoader implements SctxTextureLoader {
    private final GLRendererContext gl;

    public ExtensionSctxTextureLoader(GLRendererContext gl) {
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
                texture.init(level, GLConstants.GL_RGBA, GLConstants.GL_RGBA, GLConstants.GL_UNSIGNED_BYTE, BufferUtils.wrapDirect(mipMap.data()));
            }
        } else {
            int format = SctxPixelType.getFormat(pixelType);
            int internalFormat = SctxPixelType.getInternalFormat(pixelType);

            List<MipMapData> mipMaps = sctxTexture.getMipMaps();
            for (int level = 0; level < mipMaps.size(); level++) {
                MipMapData mipMap = mipMaps.get(level);
                texture.initCompressed(level, mipMap.width(), mipMap.height(), internalFormat, format, BufferUtils.wrapDirect(mipMap.data()));
            }
        }
    }
}
