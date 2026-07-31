package dev.donutquine.editor.gui.layout.components.tables;

public interface PendingRowTableModel {
    void insertPendingRow(int row);

    void removePendingRow(int row);

    void clearPendingRows();

    int offsetNonPendingRow(int nonPendingRow);

    boolean isPendingRow(int row);

    default boolean hasPendingRow() {
        return getPendingRowCount() > 0;
    }

    int getPendingRowCount();

    @FunctionalInterface
    public interface OnPendingRowInsertedListener {
        void handlePendingRowInserted(int row);
    }
}
