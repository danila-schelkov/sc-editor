package dev.donutquine.editor.layout.windows;

import dev.donutquine.editor.assets.AssetFile;
import dev.donutquine.editor.assets.SupercellSWFAssetFile;
import dev.donutquine.editor.assets.SupercellTextureAssetFile;
import dev.donutquine.editor.layout.LayoutController;
import dev.donutquine.editor.layout.SupercellSWFLayoutController;
import dev.donutquine.editor.layout.SupercellTextureLayoutController;

public class LayoutControllerFactory {
    public static LayoutController<?> createLayoutForFile(EditorWindow window, AssetFile<?> file) {
        if (file == null) return null;
        if (file instanceof SupercellSWFAssetFile swfAssetFile) {
            return new SupercellSWFLayoutController(window, swfAssetFile);
        }
        if (file instanceof SupercellTextureAssetFile sctxAssetFile) {
            return new SupercellTextureLayoutController(window, sctxAssetFile);
        }

        throw new IllegalStateException("Unknown file type: " + file);
    }
}

