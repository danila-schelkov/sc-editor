package dev.donutquine.editor.renderer.texture;

import java.nio.IntBuffer;

public interface RenderableTexture extends Texture {
    void bind();

    void unbind();

    void delete();

    void generateMipMap();

    IntBuffer getPixels(int level);
}
