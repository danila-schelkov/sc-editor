package com.vorono4ka.editor.graphics.objects;

import com.jogamp.opengl.GL2;

public class Square extends DisplayObject {
    @Override
    public void render(GL2 gl) {
        gl.glBegin(GL2.GL_TRIANGLES);

        gl.glVertex2f(-0.5f, -0.5f);
        gl.glVertex2f(-0.5f, 0.5f);
        gl.glVertex2f(0.5f, 0.5f);

        gl.glVertex2f(-0.5f, -0.5f);
        gl.glVertex2f(0.5f, 0.5f);
        gl.glVertex2f(0.5f, -0.5f);

        gl.glEnd();
    }

    @Override
    public String getTypeName() {
        return "Square";
    }
}
