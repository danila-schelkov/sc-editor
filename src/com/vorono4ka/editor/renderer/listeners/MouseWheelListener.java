package com.vorono4ka.editor.renderer.listeners;

import com.vorono4ka.editor.Main;
import com.vorono4ka.editor.renderer.Camera;
import com.vorono4ka.editor.renderer.CameraZoom;
import com.vorono4ka.editor.renderer.Stage;

import java.awt.event.InputEvent;
import java.awt.event.MouseWheelEvent;

public class MouseWheelListener implements java.awt.event.MouseWheelListener {
    public static final float MOUSE_MOVE_SENSITIVE = 15;
    public static final int SENSITIVE = 1;

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        Stage stage = Stage.getInstance();
        Camera camera = stage.getCamera();
        CameraZoom zoom = camera.getZoom();

        int modifiersEx = e.getModifiersEx();
        if ((modifiersEx & InputEvent.ALT_DOWN_MASK) != 0) {
            int deltaStep = e.getWheelRotation() * SENSITIVE;
            int step = zoom.getScaleStep() - deltaStep;
            if (step < 0) return;

            zoom.zoomTo(step);
        } else if ((modifiersEx & InputEvent.SHIFT_DOWN_MASK) != 0) {
            camera.addOffset(e.getWheelRotation() * MOUSE_MOVE_SENSITIVE / zoom.getPointSize(), 0);
        } else {
            camera.addOffset(0, e.getWheelRotation() * MOUSE_MOVE_SENSITIVE / zoom.getPointSize());
        }

        stage.doInRenderThread(stage::updatePMVMatrix);
    }
}
