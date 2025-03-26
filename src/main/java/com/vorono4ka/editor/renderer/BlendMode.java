package com.vorono4ka.editor.renderer;

public enum BlendMode {
    /**
     * Adds source and destination colors together.
     * Commonly used for glowing effects. <br>
     * OpenGL: glBlendFunc(GL_ONE, GL_ONE)<br>
     * Also known as Linear Dodge or Glow
     */
    ADDITIVE,
    /**
     * Darkening alpha blending: Both source and destination colors are multiplied by (1 - srcAlpha),
     * which can result in a darker output. <br>
     * OpenGL: <code>glBlendFunc(GL_ONE_MINUS_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)</code>
     */
    ALPHA_DARKEN,
    /**
     * Multiplicative blending: The source color is multiplied by the inverse of the destination color,
     * creating a darkening effect. <br>
     * OpenGL: <code>glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_COLOR)</code>
     */
    MULTIPLY,
    /**
     * Assumes the source color is already multiplied by its alpha,
     * leading to better edge blending. <br>
     * This avoids some artifacts on the borders of transparent objects and improves blending quality. <br>
     * OpenGL: <code>glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA)</code>
     */
    PREMULTIPLIED_ALPHA,
    /**
     * The source color is blended based on its alpha value, producing smooth transparency. <br>
     * This allows you to correctly combine translucent and opaque objects,
     * providing a realistic representation of transparency. <br>
     * OpenGL: <code>glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)</code> <br>
     * Also known as Interpolated Blending or Normal
     */
    ALPHA,
    /**
     * The blending is disabled in the graphics library at all.
     */
    DISABLED
}

