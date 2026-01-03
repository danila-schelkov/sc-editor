package dev.donutquine.editor;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.time.Year;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.formdev.flatlaf.FlatClientProperties;

import dev.donutquine.editor.layout.components.LinkLabel;
import dev.donutquine.editor.layout.components.Table;
import dev.donutquine.editor.layout.windows.EditorWindow;

public class ModFunctionality {
    public static final String COPY_VALUE_TO_CLIPBOARD = "★ Copy cell value";

    public static final void copyValueToClipboard(Editor editor, Table table) {
        try {
            int selectedRow = table.getSelectedRow();
            int selectedColumn = table.getSelectedColumn();
            String cellName = table.getValueAt(selectedRow, selectedColumn).toString();
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                new StringSelection(cellName),
                null
            );
        } catch (Exception e) {
        }
    }

    public static void showAboutModDialog(Component parent) {
        JLabel titleLabel = new JLabel("..:: SC Editor ::..");
        titleLabel.putClientProperty(FlatClientProperties.STYLE_CLASS, "h1");

        JOptionPane.showMessageDialog(
            parent,
            new Object[]{
                titleLabel,
                "Mod made for fun and to quickly understand java basics by kubune",
                new LinkLabel("https://github.com/kubune/sc-editor"),
                "\n\nOriginal repo: ", new LinkLabel(Editor.REPO_URL),
                "Original: Copyright © 2022-" + Year.now() + " Danila Schelkov"
            },
            "About Mod",
            JOptionPane.PLAIN_MESSAGE
        );
    }
}