package dev.donutquine.ktx;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Map;

/**
 * Loads <a href="https://registry.khronos.org/KTX/specs/1.0/ktxspec.v1.html">ktx</a> file from buffer to {@link KhronosTexture} object.
 */
public class KhronosTexture1DataLoader extends KhronosTextureDataLoader {
    public static final KhronosTexture1DataLoader INSTANCE = new KhronosTexture1DataLoader();

    public static final byte[] HEADER = new byte[]{(byte) 0xAB, 0x4B, 0x54, 0x58, 0x20, 0x31, 0x31, (byte) 0xBB, 0x0D, 0x0A, 0x1A, 0x0A};

    KhronosTexture1DataLoader() {}

    @Override
    public KhronosTexture decodeKtx(ByteBuffer buffer) throws KhronosTextureLoadingException {
        byte[] header = new byte[12];
        buffer.get(header);
        if (!Arrays.equals(header, HEADER)) {
            throw new KhronosTextureLoadingException("invalid KTX header");
        }
        if (buffer.getInt() == 0x01020304) {
            buffer.order(ByteOrder.LITTLE_ENDIAN);
        }

        int glType = buffer.getInt();
        int glTypeSize = buffer.getInt();
        int glFormat = buffer.getInt();
        int glInternalFormat = buffer.getInt();
        int glBaseInternalFormat = buffer.getInt();
        int width = buffer.getInt();
        int height = buffer.getInt();
        if (buffer.getInt() != 0) {
            throw new KhronosTextureLoadingException("pixelDepth != 0");
        }
        if (buffer.getInt() != 0) {
            throw new KhronosTextureLoadingException("numberOfArrayElements != 0");  // layerCount
        }
        if (buffer.getInt() != 1) {
            throw new KhronosTextureLoadingException("numberOfFaces != 1");  // faceCount
        }
        int levelCount = buffer.getInt();  // mipmapLevels
        int dictSize = buffer.getInt();
        Map<String, String> dictionary = decodeDict(buffer, dictSize);
        LOGGER.debug("Dict: " + dictionary);
        byte[][] levels = new byte[levelCount][];
        for (int i = 0; i < levelCount; i++) {
            int dataChunkSize = addPadding(buffer.getInt(), 4);
            byte[] dataChunk = new byte[dataChunkSize];
            buffer.get(dataChunk);
            levels[i] = dataChunk;
        }

        return new KhronosTexture(glType, glTypeSize, glFormat, glInternalFormat, glBaseInternalFormat, width, height, levels);
    }
}
