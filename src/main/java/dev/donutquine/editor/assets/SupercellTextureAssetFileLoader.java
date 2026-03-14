package dev.donutquine.editor.assets;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

import dev.donutquine.editor.assets.exceptions.AssetLoadingException;
import dev.donutquine.sctx.FlatSctxTextureLoader;
import dev.donutquine.sctx.SctxTexture;

public class SupercellTextureAssetFileLoader implements AssetFileLoader<SctxTexture> {
    private final Path path;

    public SupercellTextureAssetFileLoader(Path path) {
        this.path = path;
    }

    @Override
    public AssetFile<SctxTexture> load() throws AssetLoadingException {
        return new SupercellTextureAssetFile(loadInternal(this.path), this.path);
    }

    public static SctxTexture loadInternal(Path path) throws AssetLoadingException {
        try (FileInputStream inputStream = new FileInputStream(path.toFile())) {
            return FlatSctxTextureLoader.getInstance().load(inputStream);
        } catch (IOException e) {
            throw new AssetLoadingException(e);
        }
    }
}
