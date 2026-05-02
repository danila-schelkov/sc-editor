package dev.donutquine.editor.assets.events;

import dev.donutquine.editor.assets.AssetFile;

public record AssetFileOpenedEvent(AssetFile<?> file, int fileIndex) {
}
