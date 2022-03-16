package com.vorono4ka.compression;

import com.vorono4ka.compression.exceptions.UnknownFileMagicException;
import com.vorono4ka.compression.exceptions.UnknownFileVersionException;
import io.airlift.compress.zstd.ZstdDecompressor;
import org.sevenzip.compression.LZMA.Decoder;

import java.io.*;

public class Decompressor {
    public static final ZstdDecompressor ZSTD_DECOMPRESSOR;

    static {
        ZSTD_DECOMPRESSOR = new ZstdDecompressor();
    }

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

                byte[] decoderProperties = new byte[5];
                stream.read(decoderProperties);
                decoder.setDecoderProperties(decoderProperties);

                int outSize = stream.readInt();

                ByteArrayOutputStream outputArray = new ByteArrayOutputStream();
                BufferedOutputStream outputStream = new BufferedOutputStream(outputArray);

                decoder.code(stream, outputStream, outSize);
                decompressed = outputArray.toByteArray();
            }
            case 2, 3 -> {
                int decompressedSize = (int) ZstdDecompressor.getDecompressedSize(compressedData, 0, compressedData.length);

                decompressed = new byte[decompressedSize];

                ZSTD_DECOMPRESSOR.decompress(
                    compressedData,
                    0,
                    compressedData.length,
                    decompressed,
                    0,
                    decompressed.length
                );
            }
            default -> throw new UnknownFileVersionException("Unknown file version: " + version);
        }

        return decompressed;
    }
}
