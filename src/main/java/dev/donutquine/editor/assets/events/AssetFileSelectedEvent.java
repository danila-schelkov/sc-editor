package dev.donutquine.editor.assets.events;

import dev.donutquine.editor.assets.AssetFile;

public record AssetFileSelectedEvent(AssetFile<?> file) {
}
