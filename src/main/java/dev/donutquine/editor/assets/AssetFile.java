package dev.donutquine.editor.assets;

import java.io.Closeable;

public abstract class AssetFile<T> implements Closeable {
    public final T asset;

    public AssetFile(T asset) {
        this.asset = asset;
    }

    public abstract String getName();

    @Override
    public abstract void close();
}
