package dev.donutquine.editor.layout.components.listeners;

import dev.donutquine.editor.Editor;
import dev.donutquine.renderer.impl.swf.objects.DisplayObject;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TextureListMouseListener extends MouseAdapter {
    private final JTable table;
    private final Editor editor;

    public TextureListMouseListener(JTable table, Editor editor) {
        this.table = table;
        this.editor = editor;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int selectedRow = this.table.getSelectedRow();
        if (selectedRow == -1) return;

        int clickCount = e.getClickCount();
        if (clickCount < 2) return;

        int textureIndex = (int) this.table.getValueAt(selectedRow, 0);

        DisplayObject displayObject = editor.getSpriteSheet(textureIndex);

        editor.selectObject(displayObject);
    }
}
