package dev.donutquine.editor.assets;

import dev.donutquine.editor.assets.exceptions.AssetLoadingException;

public interface AssetFileLoader<T> {
    AssetFile<T> load() throws AssetLoadingException;
}
