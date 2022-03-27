package com.vorono4ka.editor.renderer.listeners;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.PMVMatrix;
import com.vorono4ka.editor.Main;
import com.vorono4ka.editor.renderer.Renderer;

public class EventListener implements GLEventListener {
    private Renderer renderer;

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL3 gl = glAutoDrawable.getGL().getGL3();

        this.renderer = Main.editor.getRenderer();
        this.renderer.initialize(gl);
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL3 gl = glAutoDrawable.getGL().getGL3();

        this.renderer.render(gl);
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {
        GL3 gl = glAutoDrawable.getGL().getGL3();

        float aspectRatio = width / (float) height;
        float unitsTall = Renderer.unitsWide / aspectRatio;

        gl.glViewport(x, y, width, height);

        PMVMatrix matrix = this.renderer.getPMVMatrix();
        matrix.glLoadIdentity();
        matrix.glOrthof(
            -Renderer.unitsWide / 2f,
            Renderer.unitsWide / 2f,
            -unitsTall / 2f,
            unitsTall / 2f, -1, 1
        ); // left, right, bottom, top, near, far

        this.renderer.setPMVMatrix(matrix);
    }
}
