package com.vorono4ka.editor.renderer.impl.texture.sctx;

import com.vorono4ka.editor.renderer.texture.GLTexture;
import com.vorono4ka.sctx.SctxTexture;

public interface SctxTextureLoader {
    boolean isAvailable();

    void load(GLTexture texture, SctxTexture sctxTexture) throws Exception;
}
