package dev.donutquine.editor.renderer;

public abstract class Renderbuffer {
    protected final int width, height;

    public Renderbuffer(int width, int height) {
        this.width = width;
        this.height = height;
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
}
