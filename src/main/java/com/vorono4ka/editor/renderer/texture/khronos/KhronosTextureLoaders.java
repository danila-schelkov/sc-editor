package com.vorono4ka.editor.renderer.texture.khronos;

import java.util.ArrayList;
import java.util.List;

public final class KhronosTextureLoaders {
    private static final List<KhronosTextureLoader> loaders = new ArrayList<>();

    static {
        registerLoader(new KhronosToolTextureLoader());
    }

    public static void registerLoader(KhronosTextureLoader loader) {
        loaders.add(0, loader);
    }

    public static void unregisterLoader(KhronosTextureLoader loader) {
        loaders.remove(loader);
    }

    public static KhronosTextureLoader getLoader() {
        for (KhronosTextureLoader loader : loaders) {
            if (loader.isAvailable()) {
                return loader;
            }
        }

        return null;
    }
}
