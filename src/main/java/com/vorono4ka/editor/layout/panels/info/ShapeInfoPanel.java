package com.vorono4ka.editor.layout.panels.info;

import com.vorono4ka.editor.Editor;
import com.vorono4ka.editor.layout.components.Table;
import com.vorono4ka.editor.layout.contextmenus.DrawCommandContextMenu;

import javax.swing.*;
import java.awt.*;

public class ShapeInfoPanel extends JPanel {
    private final Table drawCommandsTable;

    public ShapeInfoPanel(Editor editor) {
        this.setLayout(new GridLayout(0, 1));

        this.drawCommandsTable = new Table("#", "Texture Id", "Tag");
        new DrawCommandContextMenu(this.drawCommandsTable, editor);

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
