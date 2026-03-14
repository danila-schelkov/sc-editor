package dev.donutquine.editor.layout;

import dev.donutquine.editor.assets.AssetFile;
import dev.donutquine.editor.assets.TextureAsset;

public interface TextureLayoutController<T extends AssetFile<?> & TextureAsset> extends LayoutController<T> {
    void openSpriteSheet(int textureIndex);
}
