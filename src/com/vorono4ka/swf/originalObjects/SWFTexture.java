package com.vorono4ka.swf.originalObjects;

import com.jogamp.opengl.GL3;
import com.vorono4ka.streams.ByteStream;
import com.vorono4ka.swf.GLImage;
import com.vorono4ka.swf.SupercellSWF;
import com.vorono4ka.swf.constants.Tag;
import team.nulls.ntengine.assets.KhronosTexture;
import team.nulls.ntengine.assets.KhronosTextureDataLoader;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public class SWFTexture extends GLImage implements Savable {
    public static final int TILE_SIZE = 32;

    private Buffer pixels;

    private int index;

    private Tag tag;

    public SWFTexture() {
        this.index = -1;
    }

    public SWFTexture(Tag tag) {
        this.tag = tag;
        this.index = -1;
    }

    public void load(SupercellSWF swf, Tag tag, boolean hasTexture) {
        this.tag = tag;

        int khronosTextureLength = 0;
        if (tag == Tag.KHRONOS_TEXTURE) {
            khronosTextureLength = swf.readInt();
            assert khronosTextureLength > 0;
        }

        int type = swf.readUnsignedChar();
        this.width = swf.readShort();
        this.height = swf.readShort();

        int pixelFormat = GL3.GL_RGBA;
        int pixelType = GL3.GL_UNSIGNED_BYTE;
        int pixelBytes = 4;

        switch (type) {
            case 2, 8 -> {
                pixelType = GL3.GL_UNSIGNED_SHORT_4_4_4_4;
                pixelBytes = 2;
            }
            case 3 -> {
                pixelType = GL3.GL_UNSIGNED_SHORT_5_5_5_1;
                pixelBytes = 2;
            }
            case 4 -> {
                pixelType = GL3.GL_UNSIGNED_SHORT_5_6_5;
                pixelFormat = GL3.GL_RGB;
                pixelBytes = 2;
            }
            case 6 -> {
                pixelFormat = GL3.GL_LUMINANCE_ALPHA;
                pixelBytes = 2;
            }
            case 10 -> {
                pixelFormat = GL3.GL_LUMINANCE;
                pixelBytes = 1;
            }
        }

        this.pixelFormat = pixelFormat;

        if (!hasTexture) return;

        int finalPixelFormat = pixelFormat;
        int finalPixelType = pixelType;
        int textureFilter = switch (tag) {
            case TEXTURE, TEXTURE_4, TEXTURE_5, TEXTURE_6 -> 1;
            case TEXTURE_2, TEXTURE_3, TEXTURE_7 -> 2;
            case TEXTURE_8, KHRONOS_TEXTURE -> 0;
            default -> throw new IllegalStateException("Unsupported texture tag: " + tag);
        };

        KhronosTexture ktx = null;
        if (tag == Tag.KHRONOS_TEXTURE) {
            byte[] bytes = swf.readByteArray(khronosTextureLength);
            ktx = KhronosTextureDataLoader.decodeKtx(ByteBuffer.wrap(bytes));
        } else {
            this.loadTexture(swf, this.width, this.height, 0, pixelBytes, pixelType, tag == Tag.TEXTURE_5 || tag == Tag.TEXTURE_6 || tag == Tag.TEXTURE_7);
        }

        GLImage.createWithFormat(this, ktx, false, textureFilter, this.width, this.height, this.pixels, finalPixelFormat, finalPixelType);
    }

    @Override
    public void save(ByteStream stream) {
        stream.writeUnsignedChar(0);  // TODO: calculate type
        stream.writeShort(this.width);
        stream.writeShort(this.height);
    }

    private void loadTexture(SupercellSWF swf, int width, int height, int mipmapLevel, int pixelBytes, int pixelType, boolean separatedByTiles) {
        switch (pixelBytes) {
            case 1 -> this.loadTextureAsChar(swf, width, height, mipmapLevel, pixelType, separatedByTiles);
            case 2 -> this.loadTextureAsShort(swf, width, height, mipmapLevel, pixelType, separatedByTiles);
            case 4 -> this.loadTextureAsInt(swf, width, height, mipmapLevel, pixelType, separatedByTiles);
        }
    }

    private void loadTextureAsChar(SupercellSWF swf, int width, int height, int mipmapLevel, int pixelType, boolean separatedByTiles) {
        if (separatedByTiles) {
            int xChunksCount = width / TILE_SIZE;
            int yChunksCount = height / TILE_SIZE;

            ByteBuffer pixels = ByteBuffer.allocate(width * height);

            for (int tileY = 0; tileY < yChunksCount + 1; tileY++) {
                for (int tileX = 0; tileX < xChunksCount + 1; tileX++) {
                    int tileWidth = Math.min(width - (tileX * TILE_SIZE), TILE_SIZE);
                    int tileHeight = Math.min(height - (tileY * TILE_SIZE), TILE_SIZE);

                    byte[] tilePixels = readTileAsChar(swf, tileWidth, tileHeight);

                    for (int y = 0; y < tileHeight; y++) {
                        int pixelY = (tileY * TILE_SIZE) + y;

                        for (int x = 0; x < tileWidth; x++) {
                            int pixelX = (tileX * TILE_SIZE) + x;

                            pixels.put(pixelY * width + pixelX, tilePixels[y * tileWidth + x]);
                        }
                    }
                }
            }

            this.pixels = pixels;
        } else {
            this.pixels = ByteBuffer.wrap(swf.readByteArray(width * height));
        }
    }

    private void loadTextureAsShort(SupercellSWF swf, int width, int height, int mipmapLevel, int pixelType, boolean separatedByTiles) {
        if (separatedByTiles) {
            int xChunksCount = width / TILE_SIZE;
            int yChunksCount = height / TILE_SIZE;

            ShortBuffer pixels = ShortBuffer.allocate(width * height);

            for (int tileY = 0; tileY < yChunksCount + 1; tileY++) {
                for (int tileX = 0; tileX < xChunksCount + 1; tileX++) {
                    int tileWidth = Math.min(width - (tileX * TILE_SIZE), TILE_SIZE);
                    int tileHeight = Math.min(height - (tileY * TILE_SIZE), TILE_SIZE);

                    short[] tilePixels = readTileAsShort(swf, tileWidth, tileHeight);

                    for (int y = 0; y < tileHeight; y++) {
                        int pixelY = (tileY * TILE_SIZE) + y;

                        for (int x = 0; x < tileWidth; x++) {
                            int pixelX = (tileX * TILE_SIZE) + x;

                            pixels.put(pixelY * width + pixelX, tilePixels[y * tileWidth + x]);
                        }
                    }
                }
            }

            this.pixels = pixels;
        } else {
            this.pixels = ShortBuffer.wrap(swf.readShortArray(width * height));
        }
    }

    private void loadTextureAsInt(SupercellSWF swf, int width, int height, int mipmapLevel, int pixelType, boolean separatedByTiles) {
        if (separatedByTiles) {
            int xChunksCount = width / TILE_SIZE;
            int yChunksCount = height / TILE_SIZE;

            IntBuffer pixels = IntBuffer.allocate(width * height);

            for (int tileY = 0; tileY < yChunksCount + 1; tileY++) {
                for (int tileX = 0; tileX < xChunksCount + 1; tileX++) {
                    int tileWidth = Math.min(width - (tileX * TILE_SIZE), TILE_SIZE);
                    int tileHeight = Math.min(height - (tileY * TILE_SIZE), TILE_SIZE);

                    int[] tilePixels = readTileAsInt(swf, tileWidth, tileHeight);

                    for (int y = 0; y < tileHeight; y++) {
                        int pixelY = (tileY * TILE_SIZE) + y;

                        for (int x = 0; x < tileWidth; x++) {
                            int pixelX = (tileX * TILE_SIZE) + x;

                            pixels.put(pixelY * width + pixelX, tilePixels[y * tileWidth + x]);
                        }
                    }
                }
            }

            this.pixels = pixels;
        } else {
            this.pixels = IntBuffer.wrap(swf.readIntArray(width * height));
        }
    }

    private byte[] readTileAsChar(SupercellSWF swf, int width, int height) {
        return swf.readByteArray(width * height);
    }

    private short[] readTileAsShort(SupercellSWF swf, int width, int height) {
        return swf.readShortArray(width * height);
    }

    private int[] readTileAsInt(SupercellSWF swf, int width, int height) {
        return swf.readIntArray(width * height);
    }

    public int getIndex() {
        return this.index;
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

    @Override
    public String toString() {
        return "width=" + this.width + ", height=" + this.height;
    }
}
