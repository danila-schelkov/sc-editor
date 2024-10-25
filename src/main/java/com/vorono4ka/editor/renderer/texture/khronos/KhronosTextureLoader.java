package com.vorono4ka.editor.renderer.texture.khronos;

import com.vorono4ka.editor.renderer.texture.Texture;

import java.nio.ByteBuffer;

public interface KhronosTextureLoader {
    boolean isAvailable();

    void load(Texture texture, ByteBuffer khronosTextureFileData) throws Exception;
}
