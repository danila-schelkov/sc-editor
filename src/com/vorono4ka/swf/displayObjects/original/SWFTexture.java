package com.vorono4ka.swf.displayObjects.original;

import com.jogamp.opengl.GL3;
import com.vorono4ka.swf.GLImage;
import com.vorono4ka.swf.SupercellSWF;

public class SWFTexture {
    private static final int i = 0;

    private int width;
    private int height;
    private int textureId;

    public void load(SupercellSWF swf, int tag, boolean hasTexture) {
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

        swf.skip(this.width * this.height * pixelBytes);

        GLImage.createWithFormat(this, true, 1);

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
