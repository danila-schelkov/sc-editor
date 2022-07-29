package com.vorono4ka.swf.originalObjects;

import com.jogamp.opengl.GL3;
import com.vorono4ka.streams.ByteStream;
import com.vorono4ka.swf.GLImage;
import com.vorono4ka.swf.SupercellSWF;
import com.vorono4ka.swf.constants.Tag;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public class SWFTexture implements SavableObject {
    public static final int TILE_SIZE = 32;

    private final GLImage image;

    private int width;
    private int height;
    private int pixelFormat;

    private Buffer pixels;

    private int index;

    private Tag tag;

    public SWFTexture() {
        this.image = new GLImage();
        this.index = -1;
    }

    public SWFTexture(Tag tag) {
        this.tag = tag;
        this.image = new GLImage();
        this.index = -1;
    }

    public void load(SupercellSWF swf, Tag tag, boolean hasTexture) {
        this.tag = tag;

        int pixelFormat = GL3.GL_RGBA;
        int pixelType = GL3.GL_UNSIGNED_BYTE;
        int pixelBytes = 4;

        int type = swf.readUnsignedChar();
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

        this.width = swf.readShort();
        this.height = swf.readShort();
        this.pixelFormat = pixelFormat;

        if (!hasTexture) return;

        int finalPixelFormat = pixelFormat;
        int finalPixelType = pixelType;
        GLImage.createWithFormat(this, false, 0, this.width, this.height, null, finalPixelFormat, finalPixelType);

        this.loadTexture(swf, this.width, this.height, 0, pixelBytes, pixelType, tag == Tag.TEXTURE_5 || tag == Tag.TEXTURE_6 || tag == Tag.TEXTURE_7);
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

        GLImage.updateSubImage(this, this.pixels, 0, 0, width, height, pixelType, mipmapLevel);
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

        GLImage.updateSubImage(this, this.pixels, 0, 0, width, height, pixelType, mipmapLevel);
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

        GLImage.updateSubImage(this, this.pixels, 0, 0, width, height, pixelType, mipmapLevel);
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

    public GLImage getImage() {
        return image;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getPixelFormat() {
        return this.pixelFormat;
    }

    public void setPixelFormat(int pixelFormat) {
        this.pixelFormat = pixelFormat;
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
}
