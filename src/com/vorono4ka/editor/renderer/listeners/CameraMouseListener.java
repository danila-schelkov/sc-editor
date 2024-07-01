package com.vorono4ka.editor.renderer.listeners;

import com.vorono4ka.editor.Main;
import com.vorono4ka.editor.renderer.Camera;
import com.vorono4ka.editor.renderer.Stage;

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

        this.startX = e.getX();
        this.startY = e.getY();
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

        Stage stage = Stage.getInstance();
        float x = this.startX + this.previousX - e.getX();
        float y = this.startY + this.previousY - e.getY();

        Camera camera = stage.getCamera();
        camera.addOffset(x / camera.getZoom().getPointSize(), y / camera.getZoom().getPointSize());

        stage.doInRenderThread(stage::updatePMVMatrix);

        this.previousX = e.getX();
        this.previousY = e.getY();
        this.startX = 0;
        this.startY = 0;
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
