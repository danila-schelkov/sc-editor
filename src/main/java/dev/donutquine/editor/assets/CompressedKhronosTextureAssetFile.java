package dev.donutquine.editor.assets;

import java.nio.file.Path;

public class CompressedKhronosTextureAssetFile extends AssetFile<byte[]> {
    private final Path path;

    CompressedKhronosTextureAssetFile(byte[] asset, Path path) {
        super(asset);

        this.path = path;
    }

    @Override
    public String getName() {
        return this.path.getFileName().toString();
    }

    @Override
    public void close() {}
}
