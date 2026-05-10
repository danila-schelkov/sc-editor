package dev.donutquine.editor;

import dev.donutquine.editor.assets.AssetFileManager;
import dev.donutquine.editor.assets.CompressedKhronosTextureAssetFileLoader;
import dev.donutquine.editor.assets.ExtensionBasedAssetFileLoaderFactory;
import dev.donutquine.editor.assets.KhronosTextureAssetFileLoader;
import dev.donutquine.editor.assets.SupercellSWFAssetFileLoader;
import dev.donutquine.editor.assets.SupercellTextureAssetFileLoader;
import dev.donutquine.editor.settings.EditorPreferences;

public class Editor {
    public static final String REPO_URL = "https://github.com/danila-schelkov/sc-editor";

    private static final ExtensionBasedAssetFileLoaderFactory assetFileLoaderFactory = new ExtensionBasedAssetFileLoaderFactory();

    static {
        assetFileLoaderFactory.register(SupercellSWFAssetFileLoader::new, "sc", "sc1", "sc2");
        assetFileLoaderFactory.register("sctx", SupercellTextureAssetFileLoader::new);
        assetFileLoaderFactory.register(KhronosTextureAssetFileLoader::new, "ktx", "ktx1");
        assetFileLoaderFactory.register("zktx", CompressedKhronosTextureAssetFileLoader::new);
    }

    private final AssetFileManager assetFileManager = new AssetFileManager(assetFileLoaderFactory);

    private final EditorPreferences preferences;

    public Editor(EditorPreferences preferences) {
        this.preferences = preferences;
    }

    public AssetFileManager getAssetFileManager() {
        return this.assetFileManager;
    }

    public EditorPreferences getPreferences() {
        return preferences;
    }
}
