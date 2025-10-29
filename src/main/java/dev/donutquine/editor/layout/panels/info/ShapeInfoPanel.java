package dev.donutquine.editor.layout.panels.info;

import dev.donutquine.editor.Editor;
import dev.donutquine.editor.layout.components.Table;
import dev.donutquine.editor.layout.contextmenus.DrawCommandContextMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ShapeInfoPanel extends JPanel {
    private final Table drawCommandsTable;

    public ShapeInfoPanel(Editor editor) {
        this.setLayout(new GridLayout(0, 1));

        this.drawCommandsTable = new Table("#", "Texture Id", "Tag", "Visible");
        DrawCommandContextMenu drawCommandContextMenu = new DrawCommandContextMenu(this.drawCommandsTable, editor);

        this.add(new JScrollPane(this.drawCommandsTable), "Commands");

        this.drawCommandsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() < 2) {
                    return;
                }

                int column = drawCommandsTable.columnAtPoint(e.getPoint());
                if (column == 3) {
                    drawCommandContextMenu.changeVisibility(visible -> !visible);
                }
            }
        });
    }

    public void addCommandInfo(Object... rowData) {
        this.drawCommandsTable.addRow(rowData);
    }

    public Component add(JComponent comp, String title) {
        comp.setBorder(BorderFactory.createTitledBorder(title));
        return super.add(comp);
    }
}
