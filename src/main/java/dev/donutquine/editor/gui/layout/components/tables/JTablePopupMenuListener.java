package dev.donutquine.editor.gui.layout.components.tables;

import dev.donutquine.utilities.ArrayUtils;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;

public class JTablePopupMenuListener implements PopupMenuListener {
    private final JPopupMenu popupMenu;
    private final JTable table;
    private final RowSelectionAction rowSelectionAction;

    public JTablePopupMenuListener(JPopupMenu popupMenu, JTable table, RowSelectionAction rowSelectionAction) {
        this.popupMenu = popupMenu;
        this.table = table;
        this.rowSelectionAction = rowSelectionAction;
    }

    @Override
    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        SwingUtilities.invokeLater(() -> {
            if (table.isEditing()) {
                table.removeEditor();

                if (table.getModel() instanceof PendingRowTableModel pendingRowTableModel && pendingRowTableModel.hasPendingRow()) {
                    pendingRowTableModel.clearPendingRows();
                }
            }

            int rowAtPoint = table.rowAtPoint(SwingUtilities.convertPoint(popupMenu, new Point(0, 0), table));
            if (rowAtPoint > -1) {
                boolean shouldUpdateValue = shouldUpdateValue(rowAtPoint);

                if (shouldUpdateValue) {
                    table.setRowSelectionInterval(rowAtPoint, rowAtPoint);
                }
            }

            if (rowSelectionAction != null) {
                rowSelectionAction.onRowSelected(rowAtPoint);
            }
        });
    }

    @Override
    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

    }

    @Override
    public void popupMenuCanceled(PopupMenuEvent e) {

    }

    protected boolean shouldUpdateValue(int rowAtPoint) {
        // NOTE: append row must not be selectable
        if (table.getModel() instanceof RowAppendableTableModel rowAppendableTableModel && rowAppendableTableModel.isAppendRow(rowAtPoint)) {
            return false;
        }

        assert !(table.getModel() instanceof PendingRowTableModel pendingRowTableModel) || !pendingRowTableModel.hasPendingRow() : "invalid state, no pending row is allowed after editor removed";

        return !ArrayUtils.contains(table.getSelectedRows(), rowAtPoint);
    }

    @FunctionalInterface
    public interface RowSelectionAction {
        void onRowSelected(int rowIndex);
    }
}
