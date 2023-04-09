package com.vorono4ka.editor.layout.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class ContextMenu {
    protected final JPopupMenu popupMenu;

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

    public JMenuItem add(String label, ActionListener listener) {
        JMenuItem item = this.add(label);

        if (listener != null) {
            item.addActionListener(listener);
        }

        return item;
    }

    public void addSeparator() {
        this.popupMenu.addSeparator();
    }

    public void linkTo(Component component) {
        component.addMouseListener(new MouseAdapter() {
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

    public JPopupMenu getPopupMenu() {
        return popupMenu;
    }
}
