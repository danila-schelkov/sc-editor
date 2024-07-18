package com.vorono4ka.editor.layout.components;

import com.vorono4ka.utilities.ArrayUtilities;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;

public class TablePopupMenuListener implements PopupMenuListener {
    private final JPopupMenu popupMenu;
    private final Table table;
    private final RowSelectionAction rowSelectionAction;

    public TablePopupMenuListener(JPopupMenu popupMenu, Table table, RowSelectionAction rowSelectionAction) {
        this.popupMenu = popupMenu;
        this.table = table;
        this.rowSelectionAction = rowSelectionAction;
    }

    @Override
    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        SwingUtilities.invokeLater(() -> {
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

    private boolean shouldUpdateValue(int rowAtPoint) {
        boolean shouldUpdateValue = true;

        int selectionMode = table.getSelectionModel().getSelectionMode();
        if (selectionMode == ListSelectionModel.SINGLE_INTERVAL_SELECTION) {
            int[] selectedRows = table.getSelectedRows();
            if (ArrayUtilities.contains(selectedRows, rowAtPoint)) {
                shouldUpdateValue = false;
            }
        }
        return shouldUpdateValue;
    }

    @FunctionalInterface
    public interface RowSelectionAction {
        void onRowSelected(int rowIndex);
    }
}
