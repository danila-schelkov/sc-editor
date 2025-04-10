package com.vorono4ka.editor.renderer.impl.texture.khronos;

import com.vorono4ka.editor.renderer.gl.texture.GLTexture;

import java.nio.ByteBuffer;

public interface KhronosTextureLoader {
    boolean isAvailable();

    void load(GLTexture texture, ByteBuffer khronosTextureFileData) throws Exception;
}
