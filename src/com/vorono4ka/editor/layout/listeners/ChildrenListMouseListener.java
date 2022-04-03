package com.vorono4ka.editor.layout.listeners;

import com.vorono4ka.editor.Main;
import com.vorono4ka.editor.layout.components.Table;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChildrenListMouseListener extends MouseAdapter {
    private final JTable table;

    public ChildrenListMouseListener(JTable table) {
        this.table = table;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int selectedRow = this.table.getSelectedRow();
        if (selectedRow == -1) return;

        int selectedColumn = this.table.getSelectedColumn();
        if (selectedColumn != 1) return;

        int clickCount = e.getClickCount();
        if (clickCount < 2) return;

        int childId = (int) this.table.getValueAt(selectedRow, selectedColumn);

        Table objectsTable = Main.editor.getWindow().getObjectsTable();

        int childRow = -1;
        for (int i = 0; i < objectsTable.getRowCount(); i++) {
            if (((int) objectsTable.getValueAt(i, 0)) == childId) {
                childRow = i;
            }
        }

        if (childRow == -1) return;

        objectsTable.setRowSelectionInterval(childRow, childRow);
    }
}
