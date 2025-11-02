package dev.donutquine.editor.renderer.impl.texture.khronos;

import dev.donutquine.editor.renderer.gl.GLContext;
import dev.donutquine.editor.renderer.gl.texture.GLTexture;
import dev.donutquine.utilities.BufferUtils;
import team.nulls.ntengine.assets.KhronosTexture;
import team.nulls.ntengine.assets.KhronosTextureDataLoader;

import java.nio.ByteBuffer;

public class ExtensionKhronosTextureLoader implements KhronosTextureLoader {
    private final GLContext gl;

    public ExtensionKhronosTextureLoader(GLContext gl) {
        this.gl = gl;
    }

    @Override
    public boolean isAvailable() {
        return gl.isExtensionAvailable("GL_KHR_texture_compression_astc_ldr");
    }

    @Override
    public void load(GLTexture texture, ByteBuffer khronosTextureFileData) {
        KhronosTexture ktx = KhronosTextureDataLoader.decodeKtx(khronosTextureFileData);

        for (int level = 0; level < ktx.levels().length; level++) {
            byte[] data = ktx.levels()[level];

            ByteBuffer dataBuffer = BufferUtils.allocateDirect(data.length);
            dataBuffer.put(data);
            dataBuffer.position(0);

            if (ktx.glFormat() != 0) {
                texture.init(level, ktx.width() >> level, ktx.height() >> level, ktx.glInternalFormat(), ktx.glFormat(), ktx.glType(), dataBuffer);
            } else {
                texture.initCompressed(level, ktx.width() >> level, ktx.height() >> level, ktx.glInternalFormat(), ktx.glBaseInternalFormat(), dataBuffer);
            }
        }
    }
}
