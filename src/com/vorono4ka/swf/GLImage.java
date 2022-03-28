package com.vorono4ka.swf;

import com.jogamp.opengl.GL3;
import com.vorono4ka.editor.Main;
import com.vorono4ka.swf.displayObjects.original.SWFTexture;

public class GLImage {
    public static void createWithFormat(SWFTexture swfTexture, boolean a2, int a3) {
        GL3 gl = Main.editor.getRenderer().getGl();

        int[] ids = new int[1];
        gl.glGenTextures(1, ids, 0);
        int textureId = ids[0];

        gl.glBindTexture(GL3.GL_TEXTURE_2D, textureId);

        int magFilter;
        int minFilter;
        switch (a3) {
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

        int wrap = GL3.GL_CLAMP_TO_EDGE;
        if (!a2) {
            wrap = GL3.GL_REPEAT;
        }

        gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_WRAP_S, wrap);
        gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_WRAP_T, wrap);
        gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MAG_FILTER, magFilter);
        gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MIN_FILTER, minFilter);
        gl.glPixelStorei(GL3.GL_UNPACK_ALIGNMENT, 4);
    }
}
