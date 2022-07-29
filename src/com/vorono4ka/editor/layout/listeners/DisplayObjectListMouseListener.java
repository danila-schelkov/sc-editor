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

public class DisplayObjectListMouseListener extends MouseAdapter {
    private final JTable table;

    public DisplayObjectListMouseListener(JTable table) {
        this.table = table;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int selectedRow = this.table.getSelectedRow();
        if (selectedRow == -1) return;

        int clickCount = e.getClickCount();
        if (clickCount < 2) return;

        int id = (int) this.table.getValueAt(selectedRow, 0);
        String name = (String) this.table.getValueAt(selectedRow, 1);

        SupercellSWF swf = Main.editor.getSwf();
        try {
            DisplayObjectOriginal displayObjectOriginal = swf.getOriginalDisplayObject(id, name);
            DisplayObject displayObject = displayObjectOriginal.clone(swf, null);

            Main.editor.selectObject(displayObject);
            Main.editor.updateCanvas();
        } catch (UnableToFindObjectException exception) {
            exception.printStackTrace();
        }
    }
}
