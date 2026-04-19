package dev.donutquine.editor.assets;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import dev.donutquine.editor.assets.exceptions.AssetLoadingException;
import dev.donutquine.swf.exceptions.TextureFileNotFound;
import dev.donutquine.utilities.BufferUtils;
import team.nulls.ntengine.assets.KhronosTexture;
import team.nulls.ntengine.assets.KhronosTextureDataLoader;
import team.nulls.ntengine.assets.KhronosTextureLoadingException;

public class KhronosTextureAssetFileLoader implements AssetFileLoader<KhronosTexture> {
    private final Path path;

    public KhronosTextureAssetFileLoader(Path path) {
        this.path = path;
    }

    @Override
    public AssetFile<KhronosTexture> load() throws AssetLoadingException {
        return new KhronosTextureAssetFile(loadInternal(this.path), this.path);
    }

    public static KhronosTexture loadInternal(Path path) throws AssetLoadingException {
        byte[] ktxData;
        try (FileInputStream fis = new FileInputStream(path.toFile())) {
            ktxData = fis.readAllBytes();
        } catch (FileNotFoundException e) {
            throw new AssetLoadingException(new TextureFileNotFound(path.toString()));
        } catch (IOException e) {
            throw new AssetLoadingException(e);
        }

        return loadInternal(ktxData);
    }

    public static KhronosTexture loadInternal(byte[] ktxData) throws AssetLoadingException {
        try {
            return KhronosTextureDataLoader.decodeKtx(BufferUtils.wrapDirect(ktxData));
        } catch (KhronosTextureLoadingException e) {
            throw new AssetLoadingException(e);
        }
    }
}
