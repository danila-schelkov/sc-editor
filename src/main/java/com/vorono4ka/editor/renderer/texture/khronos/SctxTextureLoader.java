package com.vorono4ka.editor.renderer.texture.khronos;

import com.vorono4ka.editor.renderer.texture.Texture;
import com.vorono4ka.sctx.SctxTexture;

public interface SctxTextureLoader {
    boolean isAvailable();

    void load(Texture texture, SctxTexture sctxTexture) throws Exception;
}
