package dev.donutquine.editor.gui.layout.contextmenus;

import javax.swing.Action;
import javax.swing.JTable;
import dev.donutquine.editor.gui.layout.components.tables.JTablePopupMenuListener;

public class FrameElementTableContextMenu extends ContextMenu {
    public FrameElementTableContextMenu(JTable table, Action deleteAction) {
        super(table, null);

        this.add(deleteAction);

        this.popupMenu.addPopupMenuListener(new JTablePopupMenuListener(this.popupMenu, table, rowIndex -> setMainComponentsEnabled(rowIndex != -1)));
    }
}
