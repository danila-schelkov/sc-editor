package dev.donutquine.editor.layout.components.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import dev.donutquine.editor.layout.SupercellSWFLayoutController;

public class ChildrenListMouseListener extends MouseAdapter {
    private final JTable table;
    private final SupercellSWFLayoutController controller;

    public ChildrenListMouseListener(JTable table, SupercellSWFLayoutController controller) {
        this.table = table;
        this.controller = controller;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int selectedRow = this.table.getSelectedRow();
        if (selectedRow == -1)
            return;

        int selectedColumn = this.table.getSelectedColumn();
        if (selectedColumn != 1)
            return;

        int clickCount = e.getClickCount();
        if (clickCount < 2)
            return;

        int id = (int) this.table.getValueAt(selectedRow, selectedColumn);

        this.controller.selectObject(id, null);
    }
}
