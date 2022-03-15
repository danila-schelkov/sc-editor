package com.vorono4ka.editor.graphics;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.vorono4ka.editor.Main;
import com.vorono4ka.editor.layout.Window;

public class EventListener implements GLEventListener {
    private Renderer renderer;

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();

        gl.glClearColor(.5f, .5f, .5f, 1);

        this.renderer = Main.editor.getRenderer();
        this.renderer.initialize(gl);
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        this.renderer.render();
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {
        GL2 gl = glAutoDrawable.getGL().getGL2();

        Window window = Main.editor.getWindow();
        float unitsTall = Renderer.unitsWide / window.getAspectRatio();

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-Renderer.unitsWide / 2f,
                Renderer.unitsWide / 2f,
                -unitsTall / 2f,
                unitsTall / 2f, -1, 1); // left, right, bottom, top, near, far

        gl.glMatrixMode(GL2.GL_MODELVIEW);
    }
}
