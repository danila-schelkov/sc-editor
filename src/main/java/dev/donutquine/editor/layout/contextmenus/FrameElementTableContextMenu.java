package dev.donutquine.editor.layout.contextmenus;

import java.awt.event.ActionEvent;
import javax.swing.JTable;
import dev.donutquine.editor.layout.components.tables.JTablePopupMenuListener;
import dev.donutquine.editor.layout.components.tables.MovieClipFrameElementsTableModel;

public class FrameElementTableContextMenu extends ContextMenu {
    private final JTable table;
	private final MovieClipFrameElementsTableModel tableModel;

    public FrameElementTableContextMenu(JTable table, MovieClipFrameElementsTableModel tableModel) {
        super(table, null);

        this.table = table;
        this.tableModel = tableModel;

        this.add("Delete", this::delete);

        this.popupMenu.addPopupMenuListener(new JTablePopupMenuListener(this.popupMenu, table, rowIndex -> setMainComponentsEnabled(rowIndex != -1)));
    }

    private void delete(ActionEvent event) {
        int firstIndex = this.table.getSelectedRow();
        int elementCount = this.table.getSelectedRowCount();

        this.tableModel.delete(firstIndex, elementCount);
    }
}
