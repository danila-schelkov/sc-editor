package dev.donutquine.editor.renderer.impl.listeners;

import dev.donutquine.editor.layout.ScalingUtils;
import dev.donutquine.editor.renderer.Camera;
import dev.donutquine.editor.renderer.impl.EditorStage;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class CameraMouseListener implements MouseListener, MouseMotionListener {
    private int startX;
    private int startY;
    private int previousX;
    private int previousY;

    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() != MouseEvent.BUTTON2) return;

        float dpiScalingFactor = ScalingUtils.getDpiScalingFactor();
        this.startX = (int) (e.getX() * dpiScalingFactor);
        this.startY = (int) (e.getY() * dpiScalingFactor);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() != MouseEvent.BUTTON2) return;

        this.previousX = 0;
        this.previousY = 0;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int modifiers = e.getModifiersEx();
        if ((modifiers & InputEvent.BUTTON2_DOWN_MASK) != InputEvent.BUTTON2_DOWN_MASK) return;

        float dpiScalingFactor = ScalingUtils.getDpiScalingFactor();
        int x = (int) (e.getX() * dpiScalingFactor);
        int y = (int) (e.getY() * dpiScalingFactor);

        EditorStage stage = EditorStage.getInstance();
        float dx = this.startX + this.previousX - x;
        float dy = this.startY + this.previousY - y;

        Camera camera = stage.getCamera();
        camera.addOffset(dx / camera.getZoom().getPointSize(), dy / camera.getZoom().getPointSize());

        stage.doInRenderThread(stage::updatePMVMatrix);

        this.previousX = x;
        this.previousY = y;
        this.startX = 0;
        this.startY = 0;
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
