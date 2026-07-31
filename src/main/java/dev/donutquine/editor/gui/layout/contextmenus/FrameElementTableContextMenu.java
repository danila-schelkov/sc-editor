package dev.donutquine.editor.gui.layout.contextmenus;

import javax.swing.Action;
import javax.swing.JTable;
import dev.donutquine.editor.gui.layout.components.tables.JTablePopupMenuListener;
import dev.donutquine.editor.gui.layout.components.tables.MovieClipFrameElementsTableModel;

public class FrameElementTableContextMenu extends ContextMenu {
    public FrameElementTableContextMenu(JTable table, Action deleteAction) {
        super(table, null);

        MovieClipFrameElementsTableModel tableModel = (MovieClipFrameElementsTableModel) table.getModel();

        this.add(deleteAction);

        this.popupMenu.addPopupMenuListener(new JTablePopupMenuListener(this.popupMenu, table, rowIndex -> {
            // NOTE: setting enabled to items as we have to revert the state for actions when leave, but not for items
            setMainComponentsEnabled(rowIndex != -1 && !tableModel.isAppendRow(rowIndex));
        }));
    }
}
