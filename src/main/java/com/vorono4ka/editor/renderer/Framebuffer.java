package com.vorono4ka.editor.renderer;

import com.vorono4ka.editor.renderer.texture.RenderableTexture;
import com.vorono4ka.utilities.BufferUtils;
import com.vorono4ka.utilities.ImageUtils;

import java.nio.IntBuffer;

public abstract class Framebuffer {
    protected final int width, height;

    public Framebuffer(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Returns pixel array of the framebuffer from the graphics memory.
     *
     * @param flipY whether to flip pixels along the Y axis or not
     * @return integer array of pixels
     */
    public int[] getPixelArray(boolean flipY) {
        RenderableTexture texture = getTexture();

        texture.bind();
        IntBuffer pixels = texture.getPixels(0);
        texture.unbind();

        int[] pixelArray = BufferUtils.toArray(pixels);

        if (flipY) {
            ImageUtils.flipY(width, height, pixelArray);
        }

        return pixelArray;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public abstract int getId();

    public abstract void bind();

    public abstract void unbind();

    public abstract void delete();

    public abstract RenderableTexture getTexture();

    public abstract Renderbuffer getRenderbuffer();

    protected abstract void attachRenderbuffer(Renderbuffer renderbuffer, int attachmentType);

    protected abstract void attachTexture(RenderableTexture texture, int attachmentType);
}
