package dev.donutquine.editor.layout.components.tables;

public interface RowReorderableTableModel {
    void reorderRows(int firstRow, int rowCount, int targetRow);
}
