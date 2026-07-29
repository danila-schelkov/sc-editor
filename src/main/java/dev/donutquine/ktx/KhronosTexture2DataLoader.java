package dev.donutquine.ktx;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Map;
import dev.donutquine.editor.renderer.vk.VkFormat;

/**
 * Loads <a href="https://github.khronos.org/KTX-Specification/ktxspec.v2.html">ktx2</a> file from buffer to {@link KhronosTexture} object.
 */
public class KhronosTexture2DataLoader extends KhronosTextureDataLoader {
    public static final KhronosTexture2DataLoader INSTANCE = new KhronosTexture2DataLoader();

    public static final byte[] HEADER = new byte[]{(byte) 0xAB, 0x4B, 0x54, 0x58, 0x20, 0x32, 0x30, (byte) 0xBB, 0x0D, 0x0A, 0x1A, 0x0A};

    KhronosTexture2DataLoader() {}

    @Override
    public KhronosTexture decodeKtx(ByteBuffer buffer) throws KhronosTextureLoadingException {
        byte[] header = new byte[12];
        buffer.get(header);
        if (!Arrays.equals(header, HEADER)) {
            throw new KhronosTextureLoadingException("invalid KTX header");
        }

        buffer.order(ByteOrder.LITTLE_ENDIAN);

        int vkFormat = buffer.getInt();
        VkFormat format = VkFormat.get(vkFormat);
        int typeSize = buffer.getInt();
        assert vkFormat != 0 || typeSize == 1 : "When format is VK_FORMAT_UNDEFINED, typeSize must equal 1";

        // https://github.khronos.org/KTX-Specification/ktxspec.v2.html#dimensions
        int pixelWidth = buffer.getInt();
        assert pixelWidth > 0 : "pixelWidth must not be 0.";
        int pixelHeight = buffer.getInt();
        int pixelDepth = buffer.getInt();
        if (pixelDepth != 0) {
            throw new KhronosTextureLoadingException("pixelDepth != 0");
        }

        // https://github.khronos.org/KTX-Specification/ktxspec.v2.html#_layercount
        int layerCount = buffer.getInt();
        if (layerCount != 0) {
            throw new KhronosTextureLoadingException("array texture is not supported");
        }

        // https://github.khronos.org/KTX-Specification/ktxspec.v2.html#_facecount
        int faceCount = buffer.getInt();
        if (faceCount != 1) {
            throw new KhronosTextureLoadingException("cubemaps are not supported");
        }
        assert faceCount != 6 || (pixelHeight == pixelWidth && pixelDepth == 0) : "If faceCount is equal to 6, pixelHeight must be equal to pixelWidth, and pixelDepth must be 0.";

        int levelCount = buffer.getInt();
        // levelCount = 0 is allowed, except for block-compressed formats

        // https://github.khronos.org/KTX-Specification/ktxspec.v2.html#_supercompressionscheme
        int supercompressionScheme = buffer.getInt(); // None (0), BasisLZ (1), Zstandard (2), ZLIB (3)
        assert supercompressionScheme == 0 : "Supercompression is not supported";

        // Index
        int dfdByteOffset = buffer.getInt();
        int dfdByteLength = buffer.getInt();
        int kvdByteOffset = buffer.getInt();
        int kvdByteLength = buffer.getInt();
        // https://github.khronos.org/KTX-Specification/ktxspec.v2.html#_kvdbyteoffset
        assert kvdByteLength != 0 || kvdByteOffset == 0 : "The value must be 0 when kvdByteLength = 0.";
        long sgdByteOffset = buffer.getLong();
        long sgdByteLength = buffer.getLong();
        // https://github.khronos.org/KTX-Specification/ktxspec.v2.html#_sgdbyteoffset
        assert sgdByteLength != 0 || sgdByteOffset == 0 : "The value must be 0 when sgdByteLength = 0.";

        levelCount = Math.max(1, levelCount);
        
        LevelIndex[] levelIndices = new LevelIndex[levelCount];
        for (int i = 0; i < levelCount; i++) {
            long byteOffset = buffer.getLong();
            long byteLength = buffer.getLong();
            long uncompressedByteLength = buffer.getLong();
            assert supercompressionScheme != 0 || uncompressedByteLength == byteLength : "When supercompressionScheme == 0, levels[p].byteLength must have the same value as this.";
            assert supercompressionScheme != 1 || uncompressedByteLength == 0 : "When supercompressionScheme == 1, BasisLZ, the value must be 0.";
            // https://github.khronos.org/KTX-Specification/ktxspec.v2.html#_levelsp_uncompressedbytelength
            assert uncompressedByteLength % (faceCount * levelCount) == 0;

            levelIndices[i] = new LevelIndex(byteOffset, byteLength, uncompressedByteLength);
        }

        // Data Format Descriptor
        assert buffer.position() == dfdByteOffset;
        int dfdTotalSize = buffer.getInt();
        // https://github.khronos.org/KTX-Specification/ktxspec.v2.html#_dfdtotalsize
        assert dfdByteLength == dfdTotalSize : "dfdByteLength must equal dfdTotalSize.";
        assert dfdTotalSize == kvdByteOffset - dfdByteOffset;

        int endPosition = dfdByteOffset + dfdTotalSize;
        // while (buffer.position() < endPosition) {
        //     // TODO: read dfDescriptorBlock (https://registry.khronos.org/DataFormat/specs/1.4/dataformat.1.4.html chapters 2-11)
        // }
        //
        buffer.position(endPosition);
        assert buffer.position() == dfdByteOffset + dfdTotalSize;

        // Key/Value Data
        assert buffer.position() == kvdByteOffset;
        Map<String, String> dictionary = decodeDict(buffer, kvdByteLength);
        assert buffer.position() == kvdByteOffset + kvdByteLength;
        LOGGER.debug("Dict: " + dictionary);

        // Supercompression Global Data
        if (sgdByteLength > 0) {
            buffer.position(addPadding(buffer.position(), 8));
            assert buffer.position() == sgdByteOffset;
            // WARN: cast to int from long
            byte[] supercompressionGlobalData = new byte[(int) sgdByteLength];
            buffer.get(supercompressionGlobalData);
            assert buffer.position() == sgdByteOffset + sgdByteLength;
        }

        // > This is only required when supercompressionScheme == 0.
        // > https://github.khronos.org/KTX-Specification/ktxspec.v2.html#_mippadding
        int mipPadding = supercompressionScheme == 0 ? lcm(format.texelBlockSize, 4) : 1;

        // Mip Level Array
        byte[][] levels = new byte[levelCount][];
        for (int i = 0; i < levelCount; i++) {
            LevelIndex levelIndex = levelIndices[i];

            buffer.position(addPadding(buffer.position(), mipPadding));
            assert buffer.position() == levelIndex.byteOffset();
            // WARN: cast to int from long
            byte[] dataChunk = new byte[(int) levelIndex.byteLength()];
            buffer.get(dataChunk);
            assert buffer.position() == levelIndex.byteOffset() + levelIndex.byteLength();
            levels[levelCount - i - 1] = dataChunk;
        }

        return new KhronosTexture(format.glType, typeSize, format.glFormat, format.glInternalFormat, format.glInternalFormat, pixelWidth, pixelHeight, levels);
    }

    private record LevelIndex(long byteOffset, long byteLength, long uncompressedByteLength) {}

    private static int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    private static int lcm(int a, int b) {
        if (a == 0 || b == 0) return 0;
        return (Math.abs(a) / gcd(a, b)) * Math.abs(b);
    }
}
