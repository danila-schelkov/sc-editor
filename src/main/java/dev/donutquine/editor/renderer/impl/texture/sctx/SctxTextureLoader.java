package dev.donutquine.editor.renderer.impl.texture.sctx;

import dev.donutquine.editor.renderer.gl.texture.GLTexture;
import com.vorono4ka.sctx.SctxTexture;

public interface SctxTextureLoader {
    boolean isAvailable();

    void load(GLTexture texture, SctxTexture sctxTexture) throws Exception;
}
