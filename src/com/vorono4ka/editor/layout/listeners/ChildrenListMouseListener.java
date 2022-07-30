package com.vorono4ka.editor.layout.listeners;

import com.vorono4ka.editor.Main;
import com.vorono4ka.editor.layout.Window;
import com.vorono4ka.editor.layout.components.Table;
import com.vorono4ka.swf.SupercellSWF;
import com.vorono4ka.swf.displayObjects.DisplayObject;
import com.vorono4ka.swf.exceptions.UnableToFindObjectException;
import com.vorono4ka.swf.originalObjects.DisplayObjectOriginal;

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

        int id = (int) this.table.getValueAt(selectedRow, selectedColumn);

        SupercellSWF swf = Main.editor.getSwf();
        try {
            DisplayObjectOriginal displayObjectOriginal = swf.getOriginalDisplayObject(id, null);
            DisplayObject displayObject = displayObjectOriginal.clone(swf, null);

            Main.editor.selectObject(displayObject);
            Main.editor.selectObjectInTable(Main.editor.getSelectedObject());
            Main.editor.updateCanvas();
        } catch (UnableToFindObjectException exception) {
            exception.printStackTrace();
        }
    }
}
