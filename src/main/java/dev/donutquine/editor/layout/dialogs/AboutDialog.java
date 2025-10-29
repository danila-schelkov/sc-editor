package dev.donutquine.editor.layout.dialogs;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLightLaf;
import dev.donutquine.editor.Editor;
import dev.donutquine.editor.layout.components.LinkLabel;
import dev.donutquine.editor.layout.windows.EditorWindow;

import javax.swing.*;
import java.awt.*;
import java.time.Year;

public class AboutDialog {
    public static void showAboutDialog(Component parent) {
        JLabel titleLabel = new JLabel(EditorWindow.TITLE);
        titleLabel.putClientProperty(FlatClientProperties.STYLE_CLASS, "h1");

        JOptionPane.showMessageDialog(
            parent,
            new Object[]{
                titleLabel,
                new LinkLabel(Editor.REPO_URL),
                "Copyright © 2022-" + Year.now() + " Danila Schelkov"
            },
            "About",
            JOptionPane.PLAIN_MESSAGE
        );
    }

    public static void main(String[] args) {
        FlatLightLaf.setup();
        showAboutDialog(null);
    }
}
