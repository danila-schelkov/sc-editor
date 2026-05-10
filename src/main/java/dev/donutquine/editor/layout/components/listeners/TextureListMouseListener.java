package dev.donutquine.editor.layout.components.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import dev.donutquine.editor.layout.TextureLayoutController;

public class TextureListMouseListener extends MouseAdapter {
    private final JTable table;
    private final TextureLayoutController<?> layoutController;

    public TextureListMouseListener(JTable table, TextureLayoutController<?> layoutController) {
        this.table = table;
        this.layoutController = layoutController;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int selectedRow = this.table.getSelectedRow();
        if (selectedRow == -1) return;

        int clickCount = e.getClickCount();
        if (clickCount < 2) return;

        int textureIndex = (int) this.table.getValueAt(selectedRow, 0);

        layoutController.openSpriteSheet(textureIndex);
    }
}
