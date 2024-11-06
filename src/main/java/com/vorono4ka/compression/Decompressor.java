package com.vorono4ka.compression;

import com.github.luben.zstd.Zstd;
import com.vorono4ka.compression.exceptions.UnknownFileMagicException;
import com.vorono4ka.compression.exceptions.UnknownFileVersionException;
import com.vorono4ka.utilities.ArrayUtils;
import org.sevenzip.compression.LZMA.Decoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;

public class Decompressor {
    private static final int SC_MAGIC = 0x5343;
    private static final byte[] START_SECTION_BYTES = {'S', 'T', 'A', 'R', 'T'};

    public static byte[] decompress(DataInputStream stream, byte[] compressedData, int version) throws UnknownFileMagicException, UnknownFileVersionException, IOException {
        switch (version) {
            case 1, 2, 3 -> {
                int hashLength = stream.readInt();
                stream.skip(hashLength);
                int startSectionIndex = ArrayUtils.indexOf(compressedData, START_SECTION_BYTES);
                if (startSectionIndex != -1) {
                    compressedData = Arrays.copyOf(compressedData, startSectionIndex);
                }
            }
            case 0x05000000 -> {
                int metadataRootTableOffset = stream.readInt();
                int littleEndian = swapEndian32(metadataRootTableOffset);
                stream.skip(littleEndian);
            }
        }

        byte[] decompressed;

        switch (version) {
            case 1 -> decompressed = decompressLzma(stream);
            case 2, 3, 0x05000000 ->
                decompressed = decompressZstd(compressedData, compressedData.length - stream.available());
            default ->
                throw new UnknownFileVersionException("Unknown file version: " + version);
        }

        return decompressed;
    }

    public static int parseVersion(DataInputStream stream) throws IOException {
        int version = stream.readInt();
        if (version == 4) {
            version = stream.readInt();
        }
        return version;
    }

    public static void checkMagic(DataInputStream stream) throws IOException, UnknownFileMagicException {
        int magic = stream.readShort();
        if (magic != SC_MAGIC) {
            throw new UnknownFileMagicException("Unknown file magic: " + magic);
        }
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

    public static DataInputStream createDataInputStreamFromBytes(byte[] compressedData) {
        return new DataInputStream(new ByteArrayInputStream(compressedData));
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

    private static int swapEndian32(int metadataRootTableOffset) {
        return (metadataRootTableOffset >> 24) & 0xFF | (((metadataRootTableOffset >> 16) & 0xFF) << 8) | (((metadataRootTableOffset >> 8) & 0xFF) << 16) | ((metadataRootTableOffset & 0xFF) << 24);
    }
}
