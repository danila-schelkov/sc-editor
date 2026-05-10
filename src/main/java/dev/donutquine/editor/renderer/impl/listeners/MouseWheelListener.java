package dev.donutquine.editor.renderer.impl.listeners;

import dev.donutquine.editor.renderer.Camera;
import dev.donutquine.editor.renderer.CameraZoom;
import dev.donutquine.editor.renderer.impl.EditorStage;

import java.awt.event.InputEvent;
import java.awt.event.MouseWheelEvent;

public class MouseWheelListener implements java.awt.event.MouseWheelListener {
    public static final float MOUSE_MOVE_SENSITIVE = 15;
    public static final int SENSITIVE = 1;

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        EditorStage stage = EditorStage.getInstance();
        Camera camera = stage.getCamera();
        CameraZoom zoom = camera.getZoom();

        int modifiersEx = e.getModifiersEx();
        int wheelRotation = e.getWheelRotation();
        if ((modifiersEx & InputEvent.ALT_DOWN_MASK) != 0) {
            int deltaStep = wheelRotation * SENSITIVE;
            int step = zoom.getScaleStep() - deltaStep;
            if (step < 0) return;

            zoom.zoomTo(step);
        } else if ((modifiersEx & InputEvent.SHIFT_DOWN_MASK) != 0) {
            camera.addOffset(wheelRotation * MOUSE_MOVE_SENSITIVE / zoom.getPointSize(), 0);
        } else {
            camera.addOffset(0, wheelRotation * MOUSE_MOVE_SENSITIVE / zoom.getPointSize());
        }

        stage.doInRenderThread(stage::updatePMVMatrix);
    }
}
