package com.vorono4ka.swf;

import com.jogamp.opengl.GL3;
import com.vorono4ka.editor.renderer.Stage;
import com.vorono4ka.swf.displayObjects.original.SWFTexture;

public class GLImage {
    public static void createWithFormat(SWFTexture swfTexture, boolean clampToEdge, int filter, int width, int height, int pixelFormat, int pixelType) {
        GL3 gl = Stage.INSTANCE.getGl();

        int textureId = swfTexture.getTextureId();
        if (textureId != 0) {
            gl.glDeleteTextures(1, new int[] {textureId}, 0);
            swfTexture.setTextureId(0);
        }

        int[] ids = new int[1];
        gl.glGenTextures(1, ids, 0);
        textureId = ids[0];
        swfTexture.setTextureId(textureId);

        gl.glBindTexture(GL3.GL_TEXTURE_2D, textureId);

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
        gl.glPixelStorei(GL3.GL_UNPACK_ALIGNMENT, 4);

        gl.glBindTexture(GL3.GL_TEXTURE_2D, 0);

        gl.glTexImage2D(GL3.GL_TEXTURE_2D, 0, pixelFormat, width, height, 0, pixelFormat, pixelType, null);
        gl.glGenerateMipmap(GL3.GL_TEXTURE_2D);
        gl.glFlush();
    }
}
