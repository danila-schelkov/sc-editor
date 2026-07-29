package dev.donutquine.editor.renderer.impl.texture.khronos;

import dev.donutquine.ktx.KhronosTexture;
import dev.donutquine.ktx.KhronosTexture1DataLoader;
import dev.donutquine.streams.ByteStream;
import dev.donutquine.utilities.BufferUtils;

import java.nio.ByteBuffer;

/**
 * Loads <a href="https://www.khronos.org/ktx/">ktx</a> file from {@link KhronosTexture} object to {@link ByteBuffer} object.
 */
public class KhronosTextureDataSaver {
    private static final byte[] HEADER = KhronosTexture1DataLoader.HEADER;

    public static ByteBuffer encodeKtx(KhronosTexture ktx) {
        ByteStream stream = new ByteStream();
        stream.write(HEADER);
        stream.writeInt(0x04030201);  // endianness

        stream.writeInt(ktx.glType());
        stream.writeInt(ktx.glTypeSize());
        stream.writeInt(ktx.glFormat());
        stream.writeInt(ktx.glInternalFormat());
        stream.writeInt(ktx.glBaseInternalFormat());
        stream.writeInt(ktx.width());
        stream.writeInt(ktx.height());
        stream.writeInt(0);  // pixelDepth
        stream.writeInt(0);  // numberOfArrayElements
        stream.writeInt(1);  // numberOfFaces

        stream.writeInt(ktx.levels().length);
        stream.writeInt(0);  // dict stuff

        for (byte[] level : ktx.levels()) {
            stream.writeInt(level.length);
            stream.write(level);
            stream.write(new byte[getPadding(level.length, 4)]);
        }

        return BufferUtils.wrapDirect(stream.getData());
    }

    private static int getPadding(int offset, int padding) {
        return padding - 1 - ((offset + padding - 1) % padding);
    }
}
