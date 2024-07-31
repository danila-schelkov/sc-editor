package com.vorono4ka.compression;

import com.github.luben.zstd.Zstd;
import com.vorono4ka.compression.exceptions.UnknownFileVersionException;
import org.sevenzip.compression.LZMA.Encoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Compressor {
    private static final byte[] LZMA_PROPERTIES = new byte[]{
        0x5d, 0x00, 0x00, 0x04, 0x00
    };

    private static final MessageDigest MD5;
    private static final int SC_MAGIC = 0x5343;

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

        dos.writeShort(SC_MAGIC);

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
                dos.write(LZMA_PROPERTIES);
                for (int i = 0; i < 4; i++) {
                    dos.writeByte((data.length >> (8 * i)) & 0xFF);
                }

                dos.write(compressLzma(data));
            }
            case 2, 3 -> dos.write(Zstd.compress(data));
            default ->
                throw new UnknownFileVersionException("Unknown file version: " + compressionVersion);
        }

        return byteArrayOutputStream.toByteArray();
    }

    private static byte[] compressLzma(byte[] data) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        ByteArrayOutputStream outputArray = new ByteArrayOutputStream();

        Encoder encoder = new Encoder();
        encoder.code(byteArrayInputStream, outputArray, null);

        return outputArray.toByteArray();
    }
}
