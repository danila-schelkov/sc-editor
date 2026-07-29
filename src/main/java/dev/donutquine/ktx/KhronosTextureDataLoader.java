package dev.donutquine.ktx;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dev.donutquine.utilities.BufferUtils;

public abstract class KhronosTextureDataLoader {
    protected static final Logger LOGGER = LoggerFactory.getLogger(KhronosTextureDataLoader.class);

    public KhronosTexture decodeStream(InputStream is) throws IOException, KhronosTextureLoadingException {
        ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
        byte[] tmp = new byte[1024];
        int count;
        while ((count = is.read(tmp)) > 0) {
            outputBuffer.write(tmp, 0, count);
        }
        return decodeKtx(BufferUtils.wrapDirect(outputBuffer.toByteArray()));
    }

    public abstract KhronosTexture decodeKtx(ByteBuffer buffer) throws KhronosTextureLoadingException;

    protected static Map<String, String> decodeDict(ByteBuffer buffer, int size) {
        Map<String, String> dict = new HashMap<>();
        int dictEndPos = buffer.position() + size;
        while (buffer.position() < dictEndPos) {
            int pairSize = addPadding(buffer.getInt(), 4);
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

    protected static int addPadding(int length, int padding) {
        return length + (padding - 1 - ((length + padding - 1) % padding));
    }
}
