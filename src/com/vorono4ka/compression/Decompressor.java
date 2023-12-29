package com.vorono4ka.compression;

import com.github.luben.zstd.Zstd;
import com.vorono4ka.compression.exceptions.UnknownFileMagicException;
import com.vorono4ka.compression.exceptions.UnknownFileVersionException;
import org.sevenzip.compression.LZMA.Decoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class Decompressor {
    public static byte[] decompress(byte[] compressedData) throws UnknownFileMagicException, UnknownFileVersionException, IOException {
        DataInputStream stream = new DataInputStream(new ByteArrayInputStream(compressedData));

        int MAGIC = stream.readShort();
        if (MAGIC != 0x5343) {
            throw new UnknownFileMagicException("Unknown file magic: " + MAGIC);
        }

        int version = stream.readInt();
        if (version == 4) {
            version = stream.readInt();
        }

        int hashLength = stream.readInt();
        stream.skip(hashLength);

        byte[] decompressed;

        switch (version) {
            case 1 -> {
                Decoder decoder = new Decoder();

                byte[] decoderProperties = stream.readNBytes(5);
                decoder.setDecoderProperties(decoderProperties);

                int outSize = 0;
                for (int i = 0; i < 4; i++) {
                    outSize |= (stream.read() & 0xFF) << (i * 8);
                }

                ByteArrayOutputStream outputArray = new ByteArrayOutputStream();

                decoder.code(stream, outputArray, outSize);
                decompressed = outputArray.toByteArray();
            }
            case 2, 3 -> {
                int offset = compressedData.length - stream.available();
                int decompressedSize = (int) Zstd.getFrameContentSize(compressedData, offset, compressedData.length - offset);

                byte[] zstdContent = new byte[compressedData.length - offset];
                System.arraycopy(compressedData, offset, zstdContent, 0, zstdContent.length);

                decompressed = Zstd.decompress(
                    zstdContent,
                    decompressedSize
                );
            }
            default -> throw new UnknownFileVersionException("Unknown file version: " + version);
        }

        return decompressed;
    }
}
