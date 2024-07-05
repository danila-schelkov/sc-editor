package com.vorono4ka.editor.layout.components;

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
                table.setRowSelectionInterval(rowAtPoint, rowAtPoint);

                if (rowSelectionAction != null) {
                    rowSelectionAction.onRowSelected(rowAtPoint);
                }
            }
        });
    }

    @Override
    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

    }

    @Override
    public void popupMenuCanceled(PopupMenuEvent e) {

    }

    @FunctionalInterface
    public interface RowSelectionAction {
        void onRowSelected(int rowIndex);
    }
}
