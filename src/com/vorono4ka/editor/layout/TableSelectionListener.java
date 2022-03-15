package com.vorono4ka.editor.layout;

import com.vorono4ka.editor.Main;
import com.vorono4ka.editor.graphics.objects.DisplayObject;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class TableSelectionListener implements ListSelectionListener {
    private final JTable table;

    public TableSelectionListener(JTable table) {
        this.table = table;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) return;

        int selectedRow = this.table.getSelectedRow();
        if (selectedRow == -1) return;

        int id = (int) this.table.getValueAt(selectedRow, 0);

        DisplayObject displayObject = Main.editor.getScene().get(id);
        Main.editor.getRenderer().setDisplayObject(displayObject);
        Main.editor.getWindow().getCanvas().display();
    }
}
