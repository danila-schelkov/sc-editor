package com.vorono4ka.editor.renderer.listeners;

import com.vorono4ka.editor.Main;
import com.vorono4ka.editor.renderer.Renderer;

import java.awt.event.MouseWheelEvent;

public class MouseWheelListener implements java.awt.event.MouseWheelListener {
    public static final float SENSITIVE = 0.05f;

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        Renderer renderer = Main.editor.getRenderer();

        if (renderer.getScale() <= 0.5f && e.getWheelRotation() < 0) return;
        renderer.setScale(renderer.getScale() + SENSITIVE * e.getWheelRotation());
        Main.editor.updateCanvas();
    }
}
