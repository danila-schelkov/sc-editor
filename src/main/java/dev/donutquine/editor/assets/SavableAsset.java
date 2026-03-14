package dev.donutquine.editor.assets;

import java.nio.file.Path;

public interface SavableAsset {
    void save(Path path);
}
