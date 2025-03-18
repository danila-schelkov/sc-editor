package com.vorono4ka.editor.layout.components.listeners;

import com.vorono4ka.editor.Editor;
import com.vorono4ka.renderer.impl.swf.objects.DisplayObject;
import com.vorono4ka.renderer.impl.swf.objects.DisplayObjectFactory;
import com.vorono4ka.swf.DisplayObjectOriginal;
import com.vorono4ka.swf.SupercellSWF;
import com.vorono4ka.swf.exceptions.UnableToFindObjectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DisplayObjectListMouseListener extends MouseAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(DisplayObjectListMouseListener.class);

    private final JTable table;
    private final Editor editor;

    public DisplayObjectListMouseListener(JTable table, Editor editor) {
        this.editor = editor;
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

        SupercellSWF swf = editor.getSwf();
        try {
            DisplayObjectOriginal displayObjectOriginal = swf.getOriginalDisplayObject(id, name);
            DisplayObject displayObject = DisplayObjectFactory.createFromOriginal(displayObjectOriginal, swf, null);

            editor.selectObject(displayObject);
            editor.selectObjectInTable(editor.getSelectedObject());
        } catch (UnableToFindObjectException exception) {
            LOGGER.error(exception.getMessage(), exception);
        }
    }
}
