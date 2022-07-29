package com.vorono4ka.editor.layout.listeners;

import com.vorono4ka.editor.Main;
import com.vorono4ka.editor.displayObjects.SpriteSheet;
import com.vorono4ka.swf.SupercellSWF;
import com.vorono4ka.swf.displayObjects.DisplayObject;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class TextureSelectionListener implements ListSelectionListener {
    private final JTable table;

    public TextureSelectionListener(JTable table) {
        this.table = table;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) return;

        int selectedRow = this.table.getSelectedRow();
        if (selectedRow == -1) return;

        int textureIndex = (int) this.table.getValueAt(selectedRow, 0);

        SupercellSWF swf = Main.editor.getSwf();
        DisplayObject displayObject = new SpriteSheet(swf.getTexture(textureIndex));

        Main.editor.selectObject(displayObject);
        Main.editor.updateCanvas();
    }
}
