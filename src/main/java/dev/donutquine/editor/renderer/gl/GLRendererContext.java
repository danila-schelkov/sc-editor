package dev.donutquine.editor.renderer.gl;

import dev.donutquine.editor.renderer.BlendMode;
import dev.donutquine.editor.renderer.RenderStencilState;
import dev.donutquine.editor.renderer.RendererContext;
import dev.donutquine.math.ReadonlyRect;
import dev.donutquine.math.Rect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GLRendererContext implements RendererContext {
    private static final Logger LOGGER = LoggerFactory.getLogger(GLRendererContext.class);

    protected final GLContext gl;

    private Rect viewport;
    private BlendMode blendMode;
    private boolean blendingEnabled;

    private int stencilRenderingDepth;

    public GLRendererContext(GLContext context) {
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

    @Override
    public void setRenderStencilState(RenderStencilState state) {
        switch (state) {
            case ENABLED -> {
                stencilRenderingDepth++;
                this.gl.glEnable(GLConstants.GL_STENCIL_TEST);
                this.gl.glStencilFunc(stencilRenderingDepth > 1 ? GLConstants.GL_EQUAL : GLConstants.GL_ALWAYS,
                        stencilRenderingDepth - 1, 0xFF);
                this.gl.glStencilOp(GLConstants.GL_KEEP, GLConstants.GL_KEEP, GLConstants.GL_INCR);
                this.gl.glStencilMask(0xFF);
                this.gl.glColorMask(false, false, false, false);
                this.gl.glDepthMask(false);
            }
            case RENDERING_MASKED -> {
                this.gl.glColorMask(true, true, true, true);
                if (stencilRenderingDepth != 0) {
                    this.gl.glStencilFunc(GLConstants.GL_EQUAL, stencilRenderingDepth, 0xFF);
                    this.gl.glStencilMask(0x00);
                }
            }
            case DISABLED -> {
                stencilRenderingDepth--;
                this.gl.glColorMask(false, false, false, false);
                if (stencilRenderingDepth != 0) {
                    this.gl.glStencilFunc(GLConstants.GL_EQUAL, stencilRenderingDepth + 1, 0xFF);
                    this.gl.glStencilOp(GLConstants.GL_KEEP, GLConstants.GL_KEEP, GLConstants.GL_DECR);
                    this.gl.glStencilMask(0xFF);
                } else {
                    this.gl.glStencilMask(0xFF);
                    this.gl.glClear(GLConstants.GL_STENCIL_BUFFER_BIT);
                    this.gl.glStencilMask(0);
                    this.gl.glDisable(GLConstants.GL_STENCIL_TEST);
                }
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
        assert r >= 0 && r <= 1;
        assert g >= 0 && g <= 1;
        assert b >= 0 && b <= 1;
        assert a >= 0 && a <= 1;
        this.gl.glClearColor(r, g, b, a);
    }

    @Override
    public void clearStencil() {
        this.gl.glStencilMask(0xFF);
        this.gl.glClearStencil(0);
        this.gl.glClear(GLConstants.GL_STENCIL_BUFFER_BIT);
        this.gl.glStencilMask(0);
        stencilRenderingDepth = 0;
    }
}
