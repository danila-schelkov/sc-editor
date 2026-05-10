package dev.donutquine.editor.layout.components.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DisplayObjectListMouseListener extends MouseAdapter {
    private final Runnable handleRowSelected;

    public DisplayObjectListMouseListener(Runnable handleRowSelected) {
        this.handleRowSelected = handleRowSelected;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int clickCount = e.getClickCount();
        if (clickCount < 2) {
            return;
        }

        this.handleRowSelected.run();
    }
}
