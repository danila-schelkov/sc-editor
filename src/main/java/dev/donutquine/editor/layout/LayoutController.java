package dev.donutquine.editor.layout;

import dev.donutquine.editor.assets.AssetFile;

public interface LayoutController<T extends AssetFile<?>> {
    T getAssetFile();

    void start();

    void finish();
}
