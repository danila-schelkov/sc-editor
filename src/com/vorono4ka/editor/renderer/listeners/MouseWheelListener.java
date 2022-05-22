package com.vorono4ka.editor.renderer.listeners;

import com.vorono4ka.editor.Main;
import com.vorono4ka.editor.renderer.Stage;
import com.vorono4ka.math.MathHelper;

import java.awt.event.MouseWheelEvent;

public class MouseWheelListener implements java.awt.event.MouseWheelListener {
    public static final int SENSITIVE = 1;

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        Stage stage = Stage.getInstance();

        int step = stage.getScaleStep() - e.getWheelRotation() * SENSITIVE;
        if (step < 0) return;

        float scale = 2.5f;
        for (int index = 0; index < step; index++) {
            scale += MathHelper.round(scale, 1) / 10f;
        }

        if (scale > 1000f) {
            stage.setScale(10);
            return;
        }

        stage.setScaleStep(step);
        stage.setScale(scale / 100f);

        stage.doInRenderThread(stage::updatePMVMatrix);
        Main.editor.updateCanvas();
    }
}
