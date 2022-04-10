package com.vorono4ka.swf.displayObjects.original;

import com.jogamp.opengl.GL3;
import com.vorono4ka.editor.renderer.Stage;
import com.vorono4ka.swf.GLImage;
import com.vorono4ka.swf.SupercellSWF;
import com.vorono4ka.swf.constants.Tag;

public class SWFTexture {
    private static final int i = 0;

    private int width;
    private int height;
    private int textureId;

    public void load(SupercellSWF swf, Tag tag, boolean hasTexture) {
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

        if (!hasTexture) return;

        this.loadTexture(swf, this.width, this.height, 0, pixelBytes, pixelType, tag.ordinal() - 27 < 3);

        swf.skip(this.width * this.height * pixelBytes);

        int finalPixelFormat = pixelFormat;
        int finalPixelType = pixelType;
        Stage.INSTANCE.doInRenderThread(() -> GLImage.createWithFormat(this, true, 1, this.width, this.height, finalPixelFormat, finalPixelType));

//        GL3 gl = Main.editor.getRenderer().getGl();
//        int mipmapLevel = 1;
//        gl.glTexImage2D(
//            GL3.GL_TEXTURE_2D,
//            mipmapLevel,
//            pixelFormat,
//            this.width >> mipmapLevel,
//            this.height >> mipmapLevel,
//            0, // border
//            pixelFormat,
//            pixelType,
//            imageBuffer
//        );
    }

    private void loadTexture(SupercellSWF swf, int width, int height, int mipmapLevel, int pixelBytes, int pixelType, boolean separatedByTiles) {
        switch (pixelBytes) {
            case 1 -> this.loadTextureAsChar(swf, width, height, mipmapLevel, pixelType, separatedByTiles);
            case 2 -> this.loadTextureAsShort(swf, width, height, mipmapLevel, pixelType, separatedByTiles);
            case 4 -> this.loadTextureAsInt(swf, width, height, mipmapLevel, pixelType, separatedByTiles);
        }
    }

    private void loadTextureAsChar(SupercellSWF swf, int width, int height, int mipmapLevel, int pixelType, boolean separatedByTiles) {

    }

    private void loadTextureAsShort(SupercellSWF swf, int width, int height, int mipmapLevel, int pixelType, boolean separatedByTiles) {

    }

    private void loadTextureAsInt(SupercellSWF swf, int width, int height, int mipmapLevel, int pixelType, boolean separatedByTiles) {

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getTextureId() {
        return this.textureId;
    }

    public void setTextureId(int textureId) {
        this.textureId = textureId;
    }
}
