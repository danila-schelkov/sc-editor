package com.vorono4ka.editor.layout.menubar.menus;

import com.formdev.flatlaf.FlatClientProperties;
import com.vorono4ka.editor.layout.components.LinkLabel;
import com.vorono4ka.editor.layout.windows.EditorWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.time.Year;

public class HelpMenu extends JMenu {
    private final JFrame frame;

    public HelpMenu(JFrame frame) {
        super("Help");

        this.frame = frame;

        this.setMnemonic(KeyEvent.VK_H);

        JMenuItem about = new JMenuItem("About", KeyEvent.VK_A);

        about.addActionListener(this::openAboutPopup);

        this.add(about);
    }

    private void openAboutPopup(ActionEvent e) {
        JLabel titleLabel = new JLabel(EditorWindow.TITLE);
        titleLabel.putClientProperty(FlatClientProperties.STYLE_CLASS, "h1");

        JOptionPane.showMessageDialog(
            this.frame,
            new Object[]{
                titleLabel,
                new LinkLabel("https://github.com/danila-schelkov/sc-editor"),
                "Copyright 2022-" + Year.now() + " Danila Schelkov"
            },
            "About",
            JOptionPane.PLAIN_MESSAGE
        );
    }
}
