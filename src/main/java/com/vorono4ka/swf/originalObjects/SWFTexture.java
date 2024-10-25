package com.vorono4ka.swf.originalObjects;

import com.vorono4ka.compression.Decompressor;
import com.vorono4ka.streams.ByteStream;
import com.vorono4ka.swf.TextureInfo;
import com.vorono4ka.swf.constants.Tag;
import com.vorono4ka.swf.exceptions.LoadingFaultException;
import com.vorono4ka.swf.exceptions.TextureFileNotFound;
import com.vorono4ka.utilities.ArrayUtils;
import com.vorono4ka.utilities.BufferUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.nio.file.Path;
import java.util.function.BiConsumer;

public class SWFTexture implements Savable {
    public static final int TILE_SIZE = 32;

    private Tag tag;

    private int type;
    private int width, height;
    private ByteBuffer ktxData;
    private Buffer pixels;

    private int index = -1;
    private TextureInfo textureInfo;

    public SWFTexture() {
    }

    private static byte[] getTextureFileBytes(Path directory, String compressedTextureFilename) throws TextureFileNotFound {
        Path compressedTextureFilepath = directory.resolve(compressedTextureFilename);
        File file = new File(compressedTextureFilepath.toUri());

        byte[] compressedData;
        try (FileInputStream fis = new FileInputStream(file)) {
            compressedData = fis.readAllBytes();
        } catch (FileNotFoundException e) {
            throw new TextureFileNotFound(compressedTextureFilepath.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return compressedData;
    }

    private static boolean hasInterlacing(Tag tag) {
        return tag == Tag.TEXTURE_5 || tag == Tag.TEXTURE_6 || tag == Tag.TEXTURE_7;
    }

    public void load(ByteStream stream, Tag tag, boolean hasTexture, Path directory) throws LoadingFaultException, TextureFileNotFound {
        this.tag = tag;

        int khronosTextureLength = 0;
        if (tag == Tag.KHRONOS_TEXTURE) {
            khronosTextureLength = stream.readInt();
            assert khronosTextureLength > 0;
        }

        String compressedTextureFilename = null;
        if (tag == Tag.COMPRESSED_KHRONOS_TEXTURE) {
            compressedTextureFilename = stream.readAscii();
            if (compressedTextureFilename == null) {
                throw new LoadingFaultException("Compressed texture filename cannot be null.");
            }
        }

        type = stream.readUnsignedChar();
        width = stream.readShort();
        height = stream.readShort();

        if (!hasTexture) return;

        textureInfo = TextureInfo.getTextureInfoByType(type);

        switch (tag) {
            case KHRONOS_TEXTURE -> {
                byte[] bytes = stream.readByteArray(khronosTextureLength);
                ktxData = BufferUtils.wrapDirect(bytes);
            }
            case COMPRESSED_KHRONOS_TEXTURE -> {
                byte[] compressedData = getTextureFileBytes(directory, compressedTextureFilename);
                byte[] decompressed = Decompressor.decompressZstd(compressedData, 0);
                ktxData = BufferUtils.wrapDirect(decompressed);
            }
            default ->
                pixels = loadTexture(stream, width, height, textureInfo.pixelBytes(), hasInterlacing(tag));
        }
    }

    @Override
    public void save(ByteStream stream) {
        stream.writeUnsignedChar(type);  // TODO: calculate type
        stream.writeShort(width);
        stream.writeShort(height);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public int getType() {
        return type;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public TextureInfo getTextureInfo() {
        return textureInfo;
    }

    public ByteBuffer getKtxData() {
        return ktxData;
    }

    public Buffer getPixels() {
        return pixels;
    }

    private Buffer loadTexture(ByteStream stream, int width, int height, int pixelBytes, boolean hasInterlacing) {
        return switch (pixelBytes) {
            case 1 -> loadTextureAsChar(stream, width, height, hasInterlacing);
            case 2 -> loadTextureAsShort(stream, width, height, hasInterlacing);
            case 4 -> loadTextureAsInt(stream, width, height, hasInterlacing);
            default -> throw new IllegalStateException("Unexpected value: " + pixelBytes);
        };
    }

    private ByteBuffer loadTextureAsChar(ByteStream stream, int width, int height, boolean separatedByTiles) {
        if (separatedByTiles) {
            ByteBuffer pixels = BufferUtils.allocateDirect(width * height);

            loadInterlacedTexture(stream, width, height, this::readTileAsChar, pixels::put);

            return pixels;
        } else {
            return BufferUtils.wrapDirect(stream.readByteArray(width * height));
        }
    }

    private ShortBuffer loadTextureAsShort(ByteStream stream, int width, int height, boolean separatedByTiles) {
        if (separatedByTiles) {
            ShortBuffer pixels = BufferUtils.allocateDirect(width * height * Short.BYTES).asShortBuffer();

            loadInterlacedTexture(stream, width, height, this::readTileAsShort, pixels::put);

            return pixels;
        } else {
            return BufferUtils.wrapDirect(stream.readShortArray(width * height));
        }
    }

    private IntBuffer loadTextureAsInt(ByteStream stream, int width, int height, boolean separatedByTiles) {
        if (separatedByTiles) {
            IntBuffer pixels = BufferUtils.allocateDirect(width * height * Integer.BYTES).asIntBuffer();

            loadInterlacedTexture(stream, width, height, this::readTileAsInt, pixels::put);

            return pixels;
        } else {
            return BufferUtils.wrapDirect(stream.readIntArray(width * height));
        }
    }

    private Byte[] readTileAsChar(ByteStream stream, int width, int height) {
        return ArrayUtils.toObject(stream.readByteArray(width * height));
    }

    private Short[] readTileAsShort(ByteStream stream, int width, int height) {
        return ArrayUtils.toObject(stream.readShortArray(width * height));
    }

    private Integer[] readTileAsInt(ByteStream stream, int width, int height) {
        return ArrayUtils.toObject(stream.readIntArray(width * height));
    }

    private <T> void loadInterlacedTexture(ByteStream stream, int width, int height, TileReader<T> tileReader, BiConsumer<Integer, T> pixelConsumer) {
        int xTileCount = width / TILE_SIZE;
        int yTileCount = height / TILE_SIZE;

        for (int tileY = 0; tileY < yTileCount + 1; tileY++) {
            for (int tileX = 0; tileX < xTileCount + 1; tileX++) {
                int tileWidth = Math.min(width - (tileX * TILE_SIZE), TILE_SIZE);
                int tileHeight = Math.min(height - (tileY * TILE_SIZE), TILE_SIZE);

                T[] tilePixels = tileReader.readTile(stream, tileWidth, tileHeight);

                for (int y = 0; y < tileHeight; y++) {
                    int pixelY = (tileY * TILE_SIZE) + y;

                    for (int x = 0; x < tileWidth; x++) {
                        int pixelX = (tileX * TILE_SIZE) + x;

                        int index = pixelY * width + pixelX;
                        int tilePixelIndex = y * tileWidth + x;
                        T tilePixel = tilePixels[tilePixelIndex];
                        pixelConsumer.accept(index, tilePixel);
                    }
                }
            }
        }
    }

    @FunctionalInterface
    private interface TileReader<T> {
        T[] readTile(ByteStream stream, int tileWidth, int tileHeight);
    }
}
