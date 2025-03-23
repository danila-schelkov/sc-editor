package com.vorono4ka.editor.renderer.impl.texture;

import com.vorono4ka.editor.renderer.gl.GLConstants;

public enum ImageFilter {
    NEAREST(GLConstants.GL_NEAREST, GLConstants.GL_NEAREST),
    LINEAR(GLConstants.GL_LINEAR, GLConstants.GL_LINEAR),
    LINEAR_NEAREST(GLConstants.GL_LINEAR_MIPMAP_NEAREST, GLConstants.GL_LINEAR),
    LINEAR_LINEAR(GLConstants.GL_LINEAR_MIPMAP_LINEAR, GLConstants.GL_LINEAR),
    ;

    private final int minFilter;
    private final int magFilter;

    ImageFilter(int minFilter, int magFilter) {
        this.minFilter = minFilter;
        this.magFilter = magFilter;
    }

    public int getMinFilter() {
        return minFilter;
    }

    public int getMagFilter() {
        return magFilter;
    }
}
