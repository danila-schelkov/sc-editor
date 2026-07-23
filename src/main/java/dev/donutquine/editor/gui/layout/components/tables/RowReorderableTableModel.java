package dev.donutquine.editor.gui.layout.components.tables;

public interface RowReorderableTableModel {
    void reorderRows(int firstRow, int rowCount, int targetRow);
}
