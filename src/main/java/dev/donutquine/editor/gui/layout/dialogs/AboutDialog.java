package dev.donutquine.editor.gui.layout.dialogs;

import java.awt.Component;
import java.time.Year;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import com.formdev.flatlaf.FlatClientProperties;
import dev.donutquine.editor.Version;
import dev.donutquine.editor.gui.Editor;
import dev.donutquine.editor.gui.layout.components.LinkLabel;
import dev.donutquine.editor.gui.layout.windows.EditorWindow;

public class AboutDialog {
    public static void showAboutDialog(Component parent) {
        JLabel titleLabel = new JLabel("%s %s".formatted(EditorWindow.TITLE, Version.getVersion()));
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
}
