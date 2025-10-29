package dev.donutquine.editor.layout.menubar.menus;

import dev.donutquine.editor.layout.dialogs.AboutDialog;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class HelpMenu extends JMenu {
    public HelpMenu(JFrame frame) {
        super("Help");

        this.setMnemonic(KeyEvent.VK_H);

        JMenuItem about = new JMenuItem("About", KeyEvent.VK_A);
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));

        about.addActionListener((e) -> AboutDialog.showAboutDialog(frame));

        this.add(about);
    }
}
