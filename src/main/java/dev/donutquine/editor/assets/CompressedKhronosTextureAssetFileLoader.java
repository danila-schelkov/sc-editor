package dev.donutquine.editor.assets;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import dev.donutquine.editor.assets.exceptions.AssetLoadingException;
import dev.donutquine.ktx.KhronosTexture;
import dev.donutquine.swf.exceptions.TextureFileNotFound;
import dev.donutquine.swf.file.compression.Zstandard;

public class CompressedKhronosTextureAssetFileLoader implements AssetFileLoader<KhronosTexture> {
    private final Path path;

    public CompressedKhronosTextureAssetFileLoader(Path path) {
        this.path = path;
    }

    @Override
    public AssetFile<KhronosTexture> load() throws AssetLoadingException {
        return new KhronosTextureAssetFile(loadInternal(this.path), this.path);
    }

    public static KhronosTexture loadInternal(Path path) throws AssetLoadingException {
        byte[] compressedData;
        try (FileInputStream fis = new FileInputStream(path.toFile())) {
            compressedData = fis.readAllBytes();
        } catch (FileNotFoundException e) {
            throw new AssetLoadingException(new TextureFileNotFound(path.toString()));
        } catch (IOException e) {
            throw new AssetLoadingException(e);
        }

        byte[] decompressed = Zstandard.decompress(compressedData, 0);
        return KhronosTextureAssetFileLoader.loadInternal(decompressed);
    }
}
