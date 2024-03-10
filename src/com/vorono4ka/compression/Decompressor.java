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
    public static final int SC_MAGIC = 0x5343;

    public static byte[] decompress(byte[] compressedData) throws UnknownFileMagicException, UnknownFileVersionException, IOException {
        DataInputStream stream = createDataInputStreamFromBytes(compressedData);

        int MAGIC = stream.readShort();
        if (MAGIC != SC_MAGIC) {
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
            case 1 -> decompressed = decompressLzma(stream);
            case 2, 3 ->
                decompressed = decompressZstd(compressedData, compressedData.length - stream.available());
            default ->
                throw new UnknownFileVersionException("Unknown file version: " + version);
        }

        return decompressed;
    }

    public static byte[] decompressZstd(byte[] compressedData, int offset) {
        int decompressedSize = (int) Zstd.getFrameContentSize(compressedData, offset, compressedData.length - offset);

        byte[] zstdContent;
        if (offset > 0) {
            zstdContent = new byte[compressedData.length - offset];
            System.arraycopy(compressedData, offset, zstdContent, 0, zstdContent.length);
        } else {
            zstdContent = compressedData;
        }

        return Zstd.decompress(
            zstdContent,
            decompressedSize
        );
    }

    private static byte[] decompressLzma(DataInputStream stream) throws IOException {
        Decoder decoder = new Decoder();

        byte[] decoderProperties = stream.readNBytes(5);
        decoder.setDecoderProperties(decoderProperties);

        int outSize = 0;
        for (int i = 0; i < 4; i++) {
            outSize |= (stream.read() & 0xFF) << (i * 8);
        }

        ByteArrayOutputStream outputArray = new ByteArrayOutputStream();
        decoder.code(stream, outputArray, outSize);

        return outputArray.toByteArray();
    }

    private static DataInputStream createDataInputStreamFromBytes(byte[] compressedData) {
        return new DataInputStream(new ByteArrayInputStream(compressedData));
    }
}
