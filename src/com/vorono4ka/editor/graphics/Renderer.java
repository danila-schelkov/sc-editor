package com.vorono4ka.editor.graphics;

import com.jogamp.opengl.GL2;
import com.vorono4ka.editor.Main;
import com.vorono4ka.editor.graphics.objects.DisplayObject;

public class Renderer {
    public static final float unitsWide = 2;

    private DisplayObject displayObject;

    private boolean initialized;
    private GL2 gl;

    public Renderer() {}

    public void initialize(GL2 gl) {
        this.gl = gl;

        this.initialized = true;
    }

    public void render() {
        if (!this.initialized) return;

        this.gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        if (this.displayObject == null) return;
        this.displayObject.render(this.gl);
    }

    public GL2 getGl() {
        return gl;
    }

    public void setDisplayObject(DisplayObject displayObject) {
        this.displayObject = displayObject;
    }

    public int getWidth() {
        return Main.editor.getWindow().getCanvas().getWidth();
    }

    public int getHeight() {
        return Main.editor.getWindow().getCanvas().getHeight();
    }

    public float getAspectRatio() {
        return getWidth() / (float) getHeight();
    }
}
