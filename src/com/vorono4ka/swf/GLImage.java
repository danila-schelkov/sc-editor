package com.vorono4ka.swf;

import com.jogamp.opengl.GL3;
import com.vorono4ka.editor.renderer.Stage;
import com.vorono4ka.swf.displayObjects.original.SWFTexture;

import java.nio.Buffer;
import java.nio.IntBuffer;

public class GLImage {
    private int textureId;

    public void bind() {
        GL3 gl = Stage.getInstance().getGl();
        gl.glBindTexture(GL3.GL_TEXTURE_2D, this.textureId);
    }

    public int getTextureId() {
        return textureId;
    }

    public void setTextureId(int textureId) {
        this.textureId = textureId;
    }

    public static void createWithFormat(SWFTexture swfTexture, boolean clampToEdge, int filter, int width, int height, int pixelFormat, int pixelType) {
        GL3 gl = Stage.getInstance().getGl();

        if (swfTexture.getImage().getTextureId() != 0) {
            gl.glDeleteTextures(1, new int[] {swfTexture.getImage().getTextureId()}, 0);
        }

        IntBuffer ids = IntBuffer.allocate(1);
        gl.glGenTextures(1, ids);

        swfTexture.getImage().setTextureId(ids.get(0));
        gl.glBindTexture(GL3.GL_TEXTURE_2D, ids.get(0));

        int magFilter;
        int minFilter;
        switch (filter) {
            case 1 -> {
                magFilter = GL3.GL_LINEAR;
                minFilter = GL3.GL_LINEAR;
            }
            case 2 -> {
                magFilter = GL3.GL_LINEAR;
                minFilter = GL3.GL_LINEAR_MIPMAP_NEAREST;
            }
            case 3 -> {
                magFilter = GL3.GL_LINEAR;
                minFilter = GL3.GL_LINEAR_MIPMAP_LINEAR;
            }
            default -> {
                magFilter = GL3.GL_NEAREST;
                minFilter = GL3.GL_NEAREST;
            }
        }

        int wrap = clampToEdge ? GL3.GL_CLAMP_TO_EDGE : GL3.GL_REPEAT;

        gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_WRAP_S, wrap);
        gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_WRAP_T, wrap);
        gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MAG_FILTER, magFilter);
        gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MIN_FILTER, minFilter);
//        gl.glPixelStorei(GL3.GL_UNPACK_ALIGNMENT, 4);

        gl.glTexImage2D(GL3.GL_TEXTURE_2D, 0, pixelFormat, width, height, 0, pixelFormat, pixelType, null);
        gl.glGenerateMipmap(GL3.GL_TEXTURE_2D);

        gl.glBindTexture(GL3.GL_TEXTURE_2D, 0);
    }

    public static void updateSubImage(SWFTexture swfTexture, Buffer pixels, int xOffset, int yOffset, int width, int height, int pixelType, int mipmapLevel) {
        GL3 gl = Stage.getInstance().getGl();

        gl.glBindTexture(GL3.GL_TEXTURE_2D, swfTexture.getImage().getTextureId());
        gl.glTexSubImage2D(GL3.GL_TEXTURE_2D, mipmapLevel, xOffset, yOffset, width, height, swfTexture.getPixelFormat(), pixelType, pixels);
        gl.glBindTexture(GL3.GL_TEXTURE_2D, 0);
    }
}
