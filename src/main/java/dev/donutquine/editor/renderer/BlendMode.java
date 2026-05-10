package dev.donutquine.editor.renderer;

public enum BlendMode {
    // GL_ONE, GL_ONE_MINUS_SRC_ALPHA
    NORMAL,
    // GL_ONE, GL_ONE
    ADDITIVE,
    // GL_DST_COLOR, GL_ONE_MINUS_SRC_ALPHA
    MULTIPLY,
    // GL_ONE, GL_ONE_MINUS_SRC_COLOR
    SCREEN,
    // GL_ONE, GL_ONE_MINUS_SRC_ALPHA
    PREMULTIPLIED_ALPHA,
    // GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA
    ALPHA,
    /**
    * The blending is disabled in the graphics library at all.
    */
    DISABLED
}
