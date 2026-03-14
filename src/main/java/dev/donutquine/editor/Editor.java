package dev.donutquine.editor;

import dev.donutquine.editor.assets.AssetFileManager;
import dev.donutquine.editor.assets.CompressedKhronosTextureAssetFileLoader;
import dev.donutquine.editor.assets.ExtensionBasedAssetFileLoaderFactory;
import dev.donutquine.editor.assets.SupercellSWFAssetFileLoader;
import dev.donutquine.editor.assets.SupercellTextureAssetFileLoader;
import dev.donutquine.editor.settings.EditorSettings;

public class Editor {
    public static final String REPO_URL = "https://github.com/danila-schelkov/sc-editor";

    private static final ExtensionBasedAssetFileLoaderFactory assetFileLoaderFactory = new ExtensionBasedAssetFileLoaderFactory();

    static {
        assetFileLoaderFactory.register("sc", SupercellSWFAssetFileLoader::new);
        assetFileLoaderFactory.register("sc1", SupercellSWFAssetFileLoader::new);
        assetFileLoaderFactory.register("sc2", SupercellSWFAssetFileLoader::new);
        assetFileLoaderFactory.register("sctx", SupercellTextureAssetFileLoader::new);
        assetFileLoaderFactory.register("zktx", CompressedKhronosTextureAssetFileLoader::new);
    }

    private final AssetFileManager assetFileManager = new AssetFileManager(assetFileLoaderFactory);

    private final EditorSettings settings;

    public Editor(EditorSettings settings) {
        this.settings = settings;
    }

    public AssetFileManager getAssetFileManager() {
        return this.assetFileManager;
    }

    public EditorSettings getSettings() {
        return settings;
    }
}
