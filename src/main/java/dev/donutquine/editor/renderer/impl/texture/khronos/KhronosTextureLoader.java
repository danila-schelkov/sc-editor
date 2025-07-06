package dev.donutquine.editor.renderer.impl.texture.khronos;

import dev.donutquine.editor.renderer.gl.texture.GLTexture;

import java.nio.ByteBuffer;

public interface KhronosTextureLoader {
    boolean isAvailable();

    void load(GLTexture texture, ByteBuffer khronosTextureFileData) throws Exception;
}
