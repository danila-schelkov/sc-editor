package com.vorono4ka.swf.originalObjects;

import com.vorono4ka.flatloader.SerializeType;
import com.vorono4ka.flatloader.annotations.FlatType;
import com.vorono4ka.flatloader.annotations.VTableClass;
import com.vorono4ka.flatloader.annotations.VTableField;
import com.vorono4ka.streams.ByteStream;
import com.vorono4ka.swf.TextureInfo;
import com.vorono4ka.swf.constants.Tag;
import com.vorono4ka.swf.exceptions.LoadingFaultException;
import com.vorono4ka.swf.exceptions.TextureFileNotFound;
import com.vorono4ka.utilities.ArrayUtils;
import com.vorono4ka.utilities.BufferUtils;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

@VTableClass
public class SWFTexture implements Savable {
    public static final int TILE_SIZE = 32;

    private transient Tag tag;

    @VTableField(0)
    private int flags;
    @VTableField(1)
    private byte type;
    @VTableField(2)
    @FlatType(value = SerializeType.INT16, isUnsigned = true)
    private int width;
    @VTableField(3)
    @FlatType(value = SerializeType.INT16, isUnsigned = true)
    private int height;
    @VTableField(4)
    private Byte[] ktxData;
    @VTableField(5)
    private int textureFilenameReferenceId;
    private transient String textureFilename;

    private transient Buffer pixels;

    private transient int index = -1;
    private transient TextureInfo textureInfo;

    private static boolean hasInterlacing(Tag tag) {
        return tag == Tag.TEXTURE_5 || tag == Tag.TEXTURE_6 || tag == Tag.TEXTURE_7;
    }

    public void load(ByteStream stream, Tag tag, boolean hasTexture) throws LoadingFaultException, TextureFileNotFound {
        this.tag = tag;

        int khronosTextureLength = 0;
        if (tag == Tag.KHRONOS_TEXTURE) {
            khronosTextureLength = stream.readInt();
            assert khronosTextureLength > 0;
        }

        if (tag == Tag.COMPRESSED_KHRONOS_TEXTURE) {
            textureFilename = stream.readAscii();
            if (textureFilename == null) {
                throw new LoadingFaultException("Compressed texture filename cannot be null.");
            }
        } else {
            textureFilename = null;
        }

        type = (byte) stream.readUnsignedChar();
        width = stream.readShort();
        height = stream.readShort();

        if (!hasTexture) return;

        textureInfo = TextureInfo.getTextureInfoByType(type);

        if (Objects.requireNonNull(tag) == Tag.KHRONOS_TEXTURE) {
            ktxData = ArrayUtils.toObject(stream.readByteArray(khronosTextureLength));
        } else {
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

    public byte[] getKtxData() {
        return ktxData != null ? ArrayUtils.toPrimitive(ktxData) : null;
    }

    public String getTextureFilename() {
        return textureFilename;
    }

    public Buffer getPixels() {
        return pixels;
    }

    public void resolve() {
        tag = Tag.KHRONOS_TEXTURE;
        textureInfo = TextureInfo.getTextureInfoByType(type);
    }

    public void resolveStrings(List<String> strings) {
        textureFilename = strings.get(textureFilenameReferenceId);
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
