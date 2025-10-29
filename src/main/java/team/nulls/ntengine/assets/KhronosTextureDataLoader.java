package team.nulls.ntengine.assets;

import dev.donutquine.utilities.BufferUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Loads <a href="https://www.khronos.org/ktx/">ktx</a> file from buffer to {@link KhronosTexture} object.
 *
 @author <a href="https://github.com/daniillnull">daniillnull</a>
 */
public class KhronosTextureDataLoader {
    private static final byte[] HEADER = new byte[]{(byte) 0xAB, 0x4B, 0x54, 0x58, 0x20, 0x31, 0x31, (byte) 0xBB, 0x0D, 0x0A, 0x1A, 0x0A};
    private static final Logger LOGGER = Logger.getLogger(KhronosTextureDataLoader.class.getName());

    public static KhronosTexture decodeStream(InputStream is) throws IOException {
        ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
        byte[] tmp = new byte[1024];
        int count;
        while ((count = is.read(tmp)) > 0) {
            outputBuffer.write(tmp, 0, count);
        }
        return decodeKtx(BufferUtils.wrapDirect(outputBuffer.toByteArray()));
    }

    public static KhronosTexture decodeKtx(ByteBuffer buffer) {
        byte[] header = new byte[12];
        buffer.get(header);
        if (!Arrays.equals(header, HEADER)) {
            throw new RuntimeException("invalid KTX header");
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
            throw new RuntimeException("pixelDepth != 0");
        }
        if (buffer.getInt() != 0) {
            throw new RuntimeException("numberOfArrayElements != 0");
        }
        if (buffer.getInt() != 1) {
            throw new RuntimeException("numberOfFaces != 1");
        }
        int mipmapLevels = buffer.getInt();
        int dictSize = buffer.getInt();
        LOGGER.info("Dict: " + decodeDict(buffer, dictSize));
        byte[][] levels = new byte[mipmapLevels][];
        for (int i = 0; i < mipmapLevels; i++) {
            int dataChunkSize = addPadding4(buffer.getInt());
            byte[] dataChunk = new byte[dataChunkSize];
            buffer.get(dataChunk);
            levels[i] = dataChunk;
        }

        return new KhronosTexture(glType, glTypeSize, glFormat, glInternalFormat, glBaseInternalFormat, width, height, levels);
    }

    private static Map<String, String> decodeDict(ByteBuffer buffer, int size) {
        Map<String, String> dict = new HashMap<>();
        int dictEndPos = buffer.position() + size;
        while (buffer.position() < dictEndPos) {
            int pairSize = addPadding4(buffer.getInt());
            byte[] pair = new byte[pairSize];
            buffer.get(pair);
            int keyEndPos = -1, valueEndPos = pairSize;
            for (int i = 0; i < pairSize; i++) {
                if (pair[i] == '\0') { // Ah shit NULL-terminated strings here...
                    if (keyEndPos == -1) {
                        keyEndPos = i;
                    } else {
                        valueEndPos = i;
                        break;
                    }
                }
            }
            String key = new String(pair, 0, keyEndPos, StandardCharsets.UTF_8);
            String value = new String(pair, keyEndPos + 1, valueEndPos - keyEndPos - 1, StandardCharsets.UTF_8);
            dict.put(key, value);
        }
        return dict;
    }

    private static int addPadding4(int offset) {
        return offset + (3 - ((offset + 3) % 4));
    }
}
