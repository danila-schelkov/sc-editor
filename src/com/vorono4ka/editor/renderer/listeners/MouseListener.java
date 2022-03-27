package com.vorono4ka.editor.renderer.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseListener implements java.awt.event.MouseListener, MouseMotionListener {
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println(e.getButton());
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        System.out.println(e.getX());
        System.out.println(e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
