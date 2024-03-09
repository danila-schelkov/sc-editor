package com.vorono4ka.compression;

import com.github.luben.zstd.Zstd;
import com.vorono4ka.compression.exceptions.UnknownFileMagicException;
import com.vorono4ka.compression.exceptions.UnknownFileVersionException;
import org.sevenzip.compression.LZMA.Encoder;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Compressor {
    private static final byte[] LZMA_PROPERTIES = new byte[] {
        0x5d, 0x00, 0x00, 0x04, 0x00
    };

    private static final MessageDigest MD5;

    static {
        try {
            MD5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] compress(byte[] data, int compressionVersion) throws IOException, UnknownFileVersionException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(byteArrayOutputStream);

        dos.writeShort(0x5343);  // MAGIC

        dos.writeInt(compressionVersion);
        if (compressionVersion == 4) {
            compressionVersion = 1;
            dos.writeInt(compressionVersion);
        }

        byte[] hash = MD5.digest(data);

        dos.writeInt(hash.length);
        dos.write(hash);

        switch (compressionVersion) {
            case 1 -> {
                Encoder encoder = new Encoder();

                dos.write(LZMA_PROPERTIES);
                for (int i = 0; i < 4; i++) {
                    dos.writeByte((data.length >> (8 * i)) & 0xFF);
                }

                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
                ByteArrayOutputStream outputArray = new ByteArrayOutputStream();

                encoder.code(byteArrayInputStream, outputArray, null);

                dos.write(outputArray.toByteArray());
            }
            case 2, 3 -> {  // TODO: fix
                byte[] compressed = new byte[(int) Zstd.compressBound(data.length)];

                int compressedLength = (int) Zstd.compress(
                    data,
                    compressed,
                    Zstd.defaultCompressionLevel()
                );

                dos.write(compressed, 0, compressedLength);
            }
            default -> throw new UnknownFileVersionException("Unknown file version: " + compressionVersion);
        }

        return byteArrayOutputStream.toByteArray();
    }

    public static void main(String[] args) throws UnknownFileVersionException, IOException {
        byte[] compressed = compress(new byte[]{'t', 'e', 's', 't'}, 1);

        File file = new File("D:\\Projects\\GitHub\\sc-compression\\examples\\compressor\\out\\test.sc");
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(compressed);
        }

        try {
            byte[] decompress = Decompressor.decompress(compressed);
            System.out.println(Arrays.toString(decompress));
        } catch (UnknownFileMagicException e) {
            throw new RuntimeException(e);
        }
    }
}
