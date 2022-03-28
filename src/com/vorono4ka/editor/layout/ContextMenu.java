package com.vorono4ka.editor.layout;

import com.vorono4ka.editor.renderer.listeners.MouseListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class ContextMenu {
    private final JPopupMenu popupMenu;

    public ContextMenu(String label) {
        this.popupMenu = new JPopupMenu(label);
    }

    public ContextMenu(Component component, String label) {
        this(label);
        this.linkTo(component);
    }

    public JMenuItem add(String label) {
        JMenuItem item = new JMenuItem(label);
        this.popupMenu.add(item);
        return item;
    }

    public void add(String label, ActionListener listener) {
        JMenuItem item = this.add(label);

        if (listener == null) return;
        item.addActionListener(listener);
    }

    public void linkTo(Component component) {
        component.addMouseListener(new MouseListener() {
            public void mousePressed(MouseEvent e) { checkPopup(e); }

            public void mouseClicked(MouseEvent e) { checkPopup(e); }

            public void mouseReleased(MouseEvent e) { checkPopup(e); }

            private void checkPopup(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }
}
