package com.vorono4ka.editor.renderer.gl;

import com.vorono4ka.editor.renderer.BlendMode;
import com.vorono4ka.editor.renderer.RenderStencilState;
import com.vorono4ka.editor.renderer.Renderer;
import com.vorono4ka.math.ReadonlyRect;
import com.vorono4ka.math.Rect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GLRenderer implements Renderer {
    private static final Logger LOGGER = LoggerFactory.getLogger(GLRenderer.class);

    protected final GLRendererContext gl;

    private Rect viewport;
    private BlendMode blendMode;
    private boolean blendingEnabled;

    public GLRenderer(GLRendererContext context) {
        this.gl = context;
    }

    @Override
    public void printInfo() {
        LOGGER.debug("Rendering API: OpenGL");
        LOGGER.debug("GL {} = {}", "Version", gl.glGetString(GLConstants.GL_VERSION));
        LOGGER.debug("GL {} = {}", "Vendor", gl.glGetString(GLConstants.GL_VENDOR));
        LOGGER.debug("GL {} = {}", "Renderer", gl.glGetString(GLConstants.GL_RENDERER));
        LOGGER.debug("GL {} = {}", "Extensions", gl.glGetString(GLConstants.GL_EXTENSIONS));
    }

    @Override
    public void setViewport(int x, int y, int width, int height) {
        gl.glViewport(x, y, width, height);

        this.viewport = new Rect(x, y, width, height);
    }

    @Override
    public ReadonlyRect getViewport() {
        return viewport;
    }

    public void setRenderStencilState(RenderStencilState state) {
        switch (state) {
            case NONE -> {}
            case SCISSORS -> {
                // TODO:
            }
            case ENABLED -> {
                this.gl.glEnable(GLConstants.GL_STENCIL_TEST);
                this.gl.glStencilFunc(GLConstants.GL_ALWAYS, 1, 0xFF); // каждый фрагмент обновит трафаретный буфер
                this.gl.glStencilOp(GLConstants.GL_KEEP, GLConstants.GL_KEEP, GLConstants.GL_REPLACE);
                this.gl.glStencilMask(0xFF); // включить запись в трафаретный буфер
                this.gl.glColorMask(false, false, false, false);

                this.gl.glDepthMask(false);
                this.gl.glClear(GLConstants.GL_STENCIL_BUFFER_BIT); // Clear stencil buffer (0 by default)
            }
            case RENDERING_MASKED -> {
                this.gl.glStencilFunc(GLConstants.GL_EQUAL, 1, 0xFF);
                this.gl.glStencilMask(0x00); // отключить запись в трафаретный буфер
                this.gl.glColorMask(true, true, true, true);
            }
            case DISABLED -> this.gl.glDisable(GLConstants.GL_STENCIL_TEST);
            case RENDERING_UNMASKED -> {
                this.gl.glStencilFunc(GLConstants.GL_NOTEQUAL, 1, 0xFF);
                this.gl.glStencilMask(0x00); // отключить запись в трафаретный буфер
                this.gl.glColorMask(true, true, true, true);
            }
        }
    }

    @Override
    public boolean bindBlendMode(BlendMode blendMode) {
        if (blendMode == BlendMode.DISABLED) {
            this.blendMode = BlendMode.DISABLED;

            if (this.blendingEnabled) {
                this.blendingEnabled = false;
                this.gl.glDisable(GLConstants.GL_BLEND);
            }

            return true;
        }

        boolean stateChanged = false;
        if (!this.blendingEnabled) {
            this.gl.glEnable(GLConstants.GL_BLEND);
            this.gl.glBlendEquation(GLConstants.GL_FUNC_ADD);
            this.blendingEnabled = true;

            stateChanged = true;
        }

        if (this.blendMode == blendMode) {
            return stateChanged;
        }

        this.blendMode = blendMode;

        int sFactor = switch (blendMode) {
            case ADDITIVE, MULTIPLY, PREMULTIPLIED_ALPHA -> GLConstants.GL_ONE;
            case ALPHA_DARKEN -> GLConstants.GL_ONE_MINUS_SRC_ALPHA;
            case ALPHA -> GLConstants.GL_SRC_ALPHA;
            case DISABLED -> throw new IllegalStateException("Unexpected value: " + blendMode);
        };

        int dFactor = switch (blendMode) {
            case ADDITIVE -> GLConstants.GL_ONE;
            case ALPHA_DARKEN, PREMULTIPLIED_ALPHA, ALPHA -> GLConstants.GL_ONE_MINUS_SRC_ALPHA;
            case MULTIPLY -> GLConstants.GL_ONE_MINUS_SRC_COLOR;
            case DISABLED -> throw new IllegalStateException("Unexpected value: " + blendMode);
        };

        this.gl.glBlendFunc(sFactor, dFactor);

        return true;
    }

    @Override
    public void clear(int flags) {
        this.gl.glClear(flags);
    }

    @Override
    public void clearColor(float r, float g, float b, float a) {
        this.gl.glClearColor(r, g, b, a);
    }

    @Override
    public void clearStencil() {
        this.gl.glStencilMask(0xFF);
        this.gl.glClearStencil(0);
        this.gl.glStencilMask(0);
    }
}
