package dev.donutquine.editor.renderer.impl.texture.khronos;

import dev.donutquine.editor.renderer.gl.texture.GLTexture;
import dev.donutquine.ktx.KhronosTexture;

public interface KhronosTextureLoader {
    boolean isAvailable();

    void load(GLTexture texture, KhronosTexture ktx) throws Exception;
}
