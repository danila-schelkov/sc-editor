package com.vorono4ka.editor.layout.menubar.menus;

import com.vorono4ka.editor.layout.dialogs.AboutDialog;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class HelpMenu extends JMenu {
    public HelpMenu(JFrame frame) {
        super("Help");

        this.setMnemonic(KeyEvent.VK_H);

        JMenuItem about = new JMenuItem("About", KeyEvent.VK_A);

        about.addActionListener((e) -> AboutDialog.showAboutDialog(frame));

        this.add(about);
    }
}
