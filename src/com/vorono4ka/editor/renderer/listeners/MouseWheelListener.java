package com.vorono4ka.editor.renderer.listeners;

import com.vorono4ka.editor.Main;
import com.vorono4ka.editor.renderer.Stage;

import java.awt.event.MouseWheelEvent;

public class MouseWheelListener implements java.awt.event.MouseWheelListener {
    public static final float SENSITIVE = 0.05f;

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        float scale = Stage.INSTANCE.getScale();
        if (scale <= 0.5f && e.getWheelRotation() < 0) return;
        Stage.INSTANCE.setScale(scale + SENSITIVE * e.getWheelRotation());
        Main.editor.updateCanvas();
    }
}
