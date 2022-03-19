package com.vorono4ka.swf.displayObjects.original;

import com.jogamp.opengl.GL2;
import com.vorono4ka.swf.SupercellSWF;

public class SWFTexture {
    private int width;
    private int height;

    public void load(SupercellSWF swf, int tag, boolean isTexture) {
        int pixelFormat = GL2.GL_RGBA;
        int pixelType = GL2.GL_UNSIGNED_BYTE;
        int pixelBytes = 4;

        int type = swf.readUnsignedChar();
        switch (type) {
            case 2, 8 -> {
                pixelType = GL2.GL_UNSIGNED_SHORT_4_4_4_4;
                pixelBytes = 2;
            }
            case 3 -> {
                pixelType = GL2.GL_UNSIGNED_SHORT_5_5_5_1;
                pixelBytes = 2;
            }
            case 4 -> {
                pixelType = GL2.GL_UNSIGNED_SHORT_5_6_5;
                pixelFormat = GL2.GL_RGB;
                pixelBytes = 2;
            }
            case 6 -> {
                pixelFormat = GL2.GL_LUMINANCE_ALPHA;
                pixelBytes = 2;
            }
            case 10 -> {
                pixelFormat = GL2.GL_LUMINANCE;
                pixelBytes = 1;
            }
        }

        this.width = swf.readShort();
        this.height = swf.readShort();

        if (isTexture) {
            swf.skip(this.width * this.height * pixelBytes);
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
