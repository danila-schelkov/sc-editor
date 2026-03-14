package dev.donutquine.editor.layout.panels.info;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import dev.donutquine.editor.layout.SupercellSWFLayoutController;
import dev.donutquine.editor.layout.components.Table;
import dev.donutquine.editor.layout.contextmenus.DrawCommandContextMenu;
import dev.donutquine.renderer.impl.swf.objects.Shape;

public class ShapeInfoPanel extends JPanel {
    private final Table drawCommandsTable;

    public ShapeInfoPanel(SupercellSWFLayoutController layoutController, Shape shape) {
        this.setLayout(new GridLayout(0, 1));

        this.drawCommandsTable = new Table("#", "Texture Id", "Tag", "Visible");
        DrawCommandContextMenu drawCommandContextMenu = new DrawCommandContextMenu(this.drawCommandsTable, layoutController, shape);

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
