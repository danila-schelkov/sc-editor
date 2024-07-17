package com.vorono4ka.editor.layout.panels.info;

import com.vorono4ka.editor.layout.components.Table;

import javax.swing.*;
import java.awt.*;

public class ShapeInfoPanel extends JPanel {
    private final Table drawCommandsTable;
    private final JPanel textInfoPanel;

    public ShapeInfoPanel() {
        this.setLayout(new GridLayout(0, 1));

        this.drawCommandsTable = new Table("#", "Texture Id", "Tag");
        this.textInfoPanel = new JPanel();

        this.textInfoPanel.setLayout(new BoxLayout(this.textInfoPanel, BoxLayout.Y_AXIS));

        this.add(new JScrollPane(this.drawCommandsTable), "Commands");
        this.add(this.textInfoPanel, "Info");
    }

    public void addCommandInfo(Object... rowData) {
        this.drawCommandsTable.addRow(rowData);
    }

    public void setTextInfo(String... lines) {
        this.textInfoPanel.removeAll();
        for (String line : lines) {
            this.addTextLine(line);
        }
    }

    public void addTextLine(String line) {
        this.textInfoPanel.add(new JLabel(line));
    }

    public Component add(JComponent comp, String title) {
        comp.setBorder(BorderFactory.createTitledBorder(title));
        return super.add(comp);
    }
}
