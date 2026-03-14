package dev.donutquine.editor.assets;

import java.nio.file.Path;

import dev.donutquine.editor.assets.exceptions.AssetLoadingException;

public interface AssetFileLoaderFactory {
    AssetFileLoader<?> create(Path path) throws AssetLoadingException;
}
