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
    public void setRenderStencilState(RenderStencilState state, int ref, int mask, int previousOrRenderDepthStencilMask, int currentNestedStencilRefMask) {
        switch (state) {
            case NONE, SCISSORS -> {}
            case ENABLED -> {
                this.gl.glEnable(GLConstants.GL_STENCIL_TEST);
                this.gl.glStencilFunc(currentNestedStencilRefMask == 0 ? GLConstants.GL_ALWAYS : GLConstants.GL_EQUAL, ref, mask & ~currentNestedStencilRefMask);
                this.gl.glStencilOp(GLConstants.GL_KEEP, GLConstants.GL_KEEP, GLConstants.GL_REPLACE);
                this.gl.glStencilMask(0xFF);
                this.gl.glColorMask(false, false, false, false);
            }
            case RENDERING_MASKED -> {
                this.gl.glStencilFunc(GLConstants.GL_EQUAL, ref, mask & ~previousOrRenderDepthStencilMask | currentNestedStencilRefMask);
                this.gl.glStencilMask(0x00);
                this.gl.glColorMask(true, true, true, true);
            }
            case DISABLED -> this.gl.glDisable(GLConstants.GL_STENCIL_TEST);
            case RENDERING_UNMASKED -> {
                this.gl.glStencilFunc(GLConstants.GL_NOTEQUAL, ref, mask & ~previousOrRenderDepthStencilMask | currentNestedStencilRefMask);
                this.gl.glStencilMask(0x00);
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
            case NORMAL -> GLConstants.GL_ONE;
            case ADDITIVE -> GLConstants.GL_ONE;
            case MULTIPLY -> GLConstants.GL_DST_COLOR;
            case SCREEN -> GLConstants.GL_ONE;
            case PREMULTIPLIED_ALPHA -> GLConstants.GL_ONE;
            case ALPHA -> GLConstants.GL_SRC_ALPHA;
            case DISABLED -> throw new IllegalStateException("Unexpected value: " + blendMode);
        };

        int dFactor = switch (blendMode) {
            case NORMAL -> GLConstants.GL_ONE_MINUS_SRC_ALPHA;
            case ADDITIVE -> GLConstants.GL_ONE;
            case MULTIPLY -> GLConstants.GL_ONE_MINUS_SRC_ALPHA;
            case SCREEN -> GLConstants.GL_ONE_MINUS_SRC_COLOR;
            case PREMULTIPLIED_ALPHA -> GLConstants.GL_ONE_MINUS_SRC_ALPHA;
            case ALPHA -> GLConstants.GL_ONE_MINUS_SRC_ALPHA;
            case DISABLED -> throw new IllegalStateException("Unexpected value: " + blendMode);
        };

        this.gl.glBlendFunc(sFactor, dFactor);

        return true;
    }

    @Override
    public void clear(boolean color, boolean depth, boolean stencil) {
        int mask = 0;
        if (color) mask |= GLConstants.GL_COLOR_BUFFER_BIT;
        if (depth) mask |= GLConstants.GL_DEPTH_BUFFER_BIT;
        if (stencil) mask |= GLConstants.GL_STENCIL_BUFFER_BIT;
        this.gl.glClear(mask);
    }

    @Override
    public void clearColor(int argb) {
        this.clearColor(((argb >> 16) & 0xFF) / 255f, ((argb >> 8) & 0xFF) / 255f, ((argb >> 0) & 0xFF) / 255f, ((argb >> 24) & 0xFF) / 255f);
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
        this.gl.glStencilMask(0);
    }
}
