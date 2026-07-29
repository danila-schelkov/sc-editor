package dev.donutquine.editor.assets;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.util.Arrays;
import dev.donutquine.editor.assets.exceptions.AssetLoadingException;
import dev.donutquine.ktx.KhronosTexture;
import dev.donutquine.ktx.KhronosTextureDataLoader;
import dev.donutquine.ktx.KhronosTexture1DataLoader;
import dev.donutquine.ktx.KhronosTexture2DataLoader;
import dev.donutquine.ktx.KhronosTextureLoadingException;
import dev.donutquine.swf.exceptions.TextureFileNotFound;
import dev.donutquine.utilities.BufferUtils;

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
        ByteBuffer buffer = BufferUtils.wrapDirect(ktxData);

        KhronosTextureDataLoader loader = null;
        byte[] header = new byte[12];
        buffer.get(header);
        if (Arrays.equals(header, KhronosTexture1DataLoader.HEADER)) {
            loader = KhronosTexture1DataLoader.INSTANCE;
        } else if (Arrays.equals(header, KhronosTexture2DataLoader.HEADER)) {
            loader = KhronosTexture2DataLoader.INSTANCE;
        } else {
            throw new AssetLoadingException("Unknown khronos texture header: " + Arrays.toString(header));
        }

        try {
            buffer.position(0);
            return loader.decodeKtx(buffer);
        } catch (KhronosTextureLoadingException e) {
            throw new AssetLoadingException(e);
        }
    }
}
