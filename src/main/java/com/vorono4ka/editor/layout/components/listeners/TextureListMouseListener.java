package com.vorono4ka.editor.layout.components.listeners;

import com.vorono4ka.editor.Main;
import com.vorono4ka.renderer.impl.swf.objects.DisplayObject;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TextureListMouseListener extends MouseAdapter {
    private final JTable table;

    public TextureListMouseListener(JTable table) {
        this.table = table;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int selectedRow = this.table.getSelectedRow();
        if (selectedRow == -1) return;

        int clickCount = e.getClickCount();
        if (clickCount < 2) return;

        int textureIndex = (int) this.table.getValueAt(selectedRow, 0);

        DisplayObject displayObject = Main.editor.getSpriteSheet(textureIndex);

        Main.editor.selectObject(displayObject);
    }
}
