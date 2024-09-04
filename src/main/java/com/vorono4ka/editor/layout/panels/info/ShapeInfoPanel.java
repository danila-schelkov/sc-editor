package com.vorono4ka.editor.layout.panels.info;

import com.vorono4ka.editor.layout.components.Table;

import javax.swing.*;
import java.awt.*;

public class ShapeInfoPanel extends JPanel {
    private final Table drawCommandsTable;

    public ShapeInfoPanel() {
        this.setLayout(new GridLayout(0, 1));

        this.drawCommandsTable = new Table("#", "Texture Id", "Tag");

        this.add(new JScrollPane(this.drawCommandsTable), "Commands");
    }

    public void addCommandInfo(Object... rowData) {
        this.drawCommandsTable.addRow(rowData);
    }

    public Component add(JComponent comp, String title) {
        comp.setBorder(BorderFactory.createTitledBorder(title));
        return super.add(comp);
    }
}
