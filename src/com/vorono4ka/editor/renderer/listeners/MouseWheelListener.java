package com.vorono4ka.editor.renderer.listeners;

import com.vorono4ka.editor.Main;
import com.vorono4ka.editor.renderer.Camera;
import com.vorono4ka.editor.renderer.Stage;
import com.vorono4ka.math.MathHelper;

import java.awt.event.InputEvent;
import java.awt.event.MouseWheelEvent;

public class MouseWheelListener implements java.awt.event.MouseWheelListener {
    public static final float MOUSE_MOVE_SENSITIVE = 15;
    public static final int SENSITIVE = 1;

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        Stage stage = Stage.getInstance();
        Camera camera = stage.getCamera();

        int modifiersEx = e.getModifiersEx();
        if ((modifiersEx & InputEvent.ALT_DOWN_MASK) != 0) {
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
        } else if ((modifiersEx & InputEvent.SHIFT_DOWN_MASK) != 0) {
            camera.addOffset(e.getWheelRotation() * MOUSE_MOVE_SENSITIVE / camera.getPointSize(), 0);
        } else {
            camera.addOffset(0, e.getWheelRotation() * MOUSE_MOVE_SENSITIVE / camera.getPointSize());
        }

        stage.doInRenderThread(stage::updatePMVMatrix);
        Main.editor.updateCanvas();
    }
}
