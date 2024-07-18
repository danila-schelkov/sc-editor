package com.vorono4ka.editor.renderer.texture.khronos;

import com.jogamp.opengl.GL3;
import com.vorono4ka.editor.renderer.texture.Texture;
import team.nulls.ntengine.assets.KhronosTexture;
import team.nulls.ntengine.assets.KhronosTextureDataLoader;

import java.nio.ByteBuffer;

public class ExtensionKhronosTextureLoader implements KhronosTextureLoader {
    private final GL3 gl;

    public ExtensionKhronosTextureLoader(GL3 gl) {
        this.gl = gl;
    }

    @Override
    public boolean isAvailable() {
        return gl.isExtensionAvailable("GL_KHR_texture_compression_astc_ldr");
    }

    @Override
    public void load(Texture texture, ByteBuffer khronosTextureFileData) {
        KhronosTexture ktx = KhronosTextureDataLoader.decodeKtx(khronosTextureFileData);

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
}
