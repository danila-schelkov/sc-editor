package dev.donutquine.editor.layout.contextmenus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public abstract class ContextMenu {
    protected final JPopupMenu popupMenu;
    protected final List<JComponent> mainComponents = new ArrayList<>();

    public ContextMenu(String label) {
        popupMenu = new JPopupMenu(label);
    }

    public ContextMenu(Component component, String label) {
        this(label);

        this.linkTo(component);
    }

    public JMenu addMenu(String name) {
        JMenu menu = new JMenu(name);
        mainComponents.add(menu);
        popupMenu.add(menu);
        return menu;
    }

    public JMenu addMenu(String name, int mnemonic) {
        JMenu menu = this.addMenu(name);
        menu.setMnemonic(mnemonic);
        return menu;
    }

    public JMenuItem add(String label) {
        JMenuItem item = new JMenuItem(label);
        mainComponents.add(item);
        popupMenu.add(item);
        return item;
    }

    public JMenuItem add(String label, int mnemonic) {
        JMenuItem item = this.add(label);
        item.setMnemonic(mnemonic);
        return item;
    }

    public JMenuItem add(JComponent parent, String label) {
        JMenuItem item = new JMenuItem(label);
        parent.add(item);
        return item;
    }

    public JMenuItem add(JComponent parent, String label, int mnemonic) {
        JMenuItem item = this.add(parent, label);
        item.setMnemonic(mnemonic);
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
        popupMenu.addSeparator();
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

    protected void setMainComponentsEnabled(boolean enabled) {
        mainComponents.forEach(component -> component.setEnabled(enabled));
    }
}
