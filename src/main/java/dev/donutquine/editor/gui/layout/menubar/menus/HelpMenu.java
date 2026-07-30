package dev.donutquine.editor.gui.layout.menubar.menus;

import dev.donutquine.editor.gui.layout.dialogs.AboutDialog;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class HelpMenu extends JMenu {
    public final JMenuItem aboutMenuItem;

    public HelpMenu(JFrame frame) {
        super("Help");

        this.setMnemonic(KeyEvent.VK_H);

        this.aboutMenuItem = new JMenuItem("About", KeyEvent.VK_A);
        this.aboutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));

        this.aboutMenuItem.addActionListener((e) -> AboutDialog.showAboutDialog(frame));

        this.add(this.aboutMenuItem);
    }
}
