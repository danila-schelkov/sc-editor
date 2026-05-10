package dev.donutquine.editor.assets;

public abstract class TextureAssetFile<T> extends AssetFile<T> implements TextureAsset {
    public TextureAssetFile(T asset) {
        super(asset);
    }
}
