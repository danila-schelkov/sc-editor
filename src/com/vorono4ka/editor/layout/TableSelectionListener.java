package com.vorono4ka.editor.layout;

import com.vorono4ka.editor.Main;
import com.vorono4ka.swf.SupercellSWF;
import com.vorono4ka.swf.displayObjects.DisplayObject;
import com.vorono4ka.swf.displayObjects.original.DisplayObjectOriginal;
import com.vorono4ka.swf.exceptions.UnableToFindObjectException;

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
        String name = (String) this.table.getValueAt(selectedRow, 1);

        SupercellSWF swf = Main.editor.getSwf();
        try {
            DisplayObjectOriginal displayObjectOriginal = swf.getOriginalDisplayObject(id, name);

            DisplayObject displayObject = displayObjectOriginal.clone(swf, null);
            Main.editor.updateCanvas();
        } catch (UnableToFindObjectException ex) {
            ex.printStackTrace();
        }
    }
}
