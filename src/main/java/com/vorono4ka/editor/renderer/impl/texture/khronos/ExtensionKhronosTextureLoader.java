package com.vorono4ka.editor.renderer.impl.texture.khronos;

import com.vorono4ka.editor.renderer.gl.GLConstants;
import com.vorono4ka.editor.renderer.gl.GLRendererContext;
import com.vorono4ka.editor.renderer.texture.GLTexture;
import team.nulls.ntengine.assets.KhronosTexture;
import team.nulls.ntengine.assets.KhronosTextureDataLoader;

import java.nio.ByteBuffer;

public class ExtensionKhronosTextureLoader implements KhronosTextureLoader {
    private final GLRendererContext gl;

    public ExtensionKhronosTextureLoader(GLRendererContext gl) {
        this.gl = gl;
    }

    @Override
    public boolean isAvailable() {
        return gl.isExtensionAvailable("GL_KHR_texture_compression_astc_ldr");
    }

    @Override
    public void load(GLTexture texture, ByteBuffer khronosTextureFileData) {
        KhronosTexture ktx = KhronosTextureDataLoader.decodeKtx(khronosTextureFileData);

        if (ktx.glFormat() != 0) {
            for (int level = 0; level < ktx.levels().length; level++) {
                texture.init(level, ktx.width() >> level, ktx.height() >> level, ktx.glInternalFormat(), ktx.glFormat(), GLConstants.GL_UNSIGNED_INT, ktx.levels()[level]);
            }
        } else {
            for (int level = 0; level < ktx.levels().length; level++) {
                ByteBuffer data = ktx.levels()[level];
                texture.initCompressed(level, ktx.width() >> level, ktx.height() >> level, ktx.glInternalFormat(), ktx.glBaseInternalFormat(), data);
            }
        }
    }
}
