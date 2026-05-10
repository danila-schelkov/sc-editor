package dev.donutquine.editor.layout.components.tables;

import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.TransferHandler;

public final class RowReorderTransferHandler extends TransferHandler {
    private final JTable table;
    private final RowReorderableTableModel tableModel;

    private int firstDraggedRow = -1;
    private int draggedRowCount = 0;

    public RowReorderTransferHandler(
        JTable table,
        RowReorderableTableModel tableModel
    ) {
        this.table = table;
        this.tableModel = tableModel;
    }

    @Override
    protected Transferable createTransferable(JComponent component) {
        int selectedRowCount = table.getSelectedRowCount();

        if (selectedRowCount == 0) {
            return null;
        }

        this.firstDraggedRow = table.getSelectedRow();
        this.draggedRowCount = selectedRowCount;

        return new StringSelection("");
    }

    @Override
    public int getSourceActions(JComponent component) {
        return MOVE;
    }

    @Override
    public boolean canImport(TransferSupport support) {
        return support.isDrop()
                && support.getComponent() == table
                && firstDraggedRow >= 0
                && draggedRowCount > 0;
    }

    @Override
    public boolean importData(TransferSupport support) {
        if (!canImport(support)) {
            return false;
        }

        JTable.DropLocation dropLocation = (JTable.DropLocation) support.getDropLocation();

        int targetRow = dropLocation.getRow();

        if (targetRow >= firstDraggedRow && targetRow <= firstDraggedRow + draggedRowCount) {
            return false;
        }

        this.tableModel.reorderRows(firstDraggedRow, draggedRowCount, targetRow);
        selectMovedRows(firstDraggedRow, draggedRowCount, targetRow);

        return true;
    }

    @Override
    protected void exportDone(JComponent source, Transferable data, int action) {
        firstDraggedRow = -1;
        draggedRowCount = 0;
    }

    private void selectMovedRows(int firstRow, int rowCount, int targetRow) {
        int newFirstRow = targetRow;

        if (targetRow > firstRow) {
            newFirstRow -= rowCount;
        }

        table.clearSelection();
        table.setRowSelectionInterval(newFirstRow, newFirstRow + rowCount - 1);
    }
}
