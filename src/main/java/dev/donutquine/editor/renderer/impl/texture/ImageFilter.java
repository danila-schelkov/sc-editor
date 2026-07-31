package dev.donutquine.editor.renderer.impl.texture;

import dev.donutquine.editor.renderer.gl.GLConstants;

public enum ImageFilter {
    LINEAR(GLConstants.GL_LINEAR, GLConstants.GL_LINEAR),
    NEAREST(GLConstants.GL_NEAREST, GLConstants.GL_NEAREST),
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
