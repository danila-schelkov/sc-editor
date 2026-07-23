package dev.donutquine.editor.gui.layout.windows;

import dev.donutquine.editor.assets.AssetFile;
import dev.donutquine.editor.assets.SupercellSWFAssetFile;
import dev.donutquine.editor.assets.TextureAssetFile;
import dev.donutquine.editor.gui.layout.LayoutController;
import dev.donutquine.editor.gui.layout.SupercellSWFLayoutController;
import dev.donutquine.editor.gui.layout.TextureAssetLayoutController;

public class LayoutControllerFactory {
    public static LayoutController<?> createLayoutForFile(EditorWindow window, AssetFile<?> file) {
        if (file == null) return null;
        if (file instanceof SupercellSWFAssetFile swfAssetFile) {
            return new SupercellSWFLayoutController(window, swfAssetFile);
        }
        if (file instanceof TextureAssetFile<?> textureAssetFile) {
            return new TextureAssetLayoutController(window, textureAssetFile);
        }

        throw new IllegalStateException("Unknown file type: " + file);
    }
}
