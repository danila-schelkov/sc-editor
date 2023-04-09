package com.vorono4ka.editor.renderer.listeners;

import com.vorono4ka.editor.Main;
import com.vorono4ka.editor.renderer.Camera;
import com.vorono4ka.editor.renderer.Stage;
import com.vorono4ka.math.MathHelper;

import java.awt.event.MouseWheelEvent;

public class MouseWheelListener implements java.awt.event.MouseWheelListener {
    public static final int SENSITIVE = 1;

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        Stage stage = Stage.getInstance();
        Camera camera = stage.getCamera();

        int step = camera.getScaleStep() - e.getWheelRotation() * SENSITIVE;
        if (step < 0) return;

        float scale = 2.5f;
        for (int index = 0; index < step; index++) {
            scale += MathHelper.round(scale, 1) / 10f;
        }

        if (scale > 1000f) {
            camera.setPointSize(10);
            return;
        }

        camera.setScaleStep(step);
        camera.setPointSize(scale / 100f);

        stage.doInRenderThread(stage::updatePMVMatrix);
        Main.editor.updateCanvas();
    }
}
