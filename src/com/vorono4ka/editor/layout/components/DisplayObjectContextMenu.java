package com.vorono4ka.editor.layout.components;

import com.vorono4ka.editor.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class DisplayObjectContextMenu extends ContextMenu {
    private final Table table;

    public DisplayObjectContextMenu(Table table) {
        super(table, null);

        this.table = table;

        JMenuItem findUsagesButton = this.add("Find usages");
        findUsagesButton.addActionListener(this::findUsages);

        this.addSeparator();

        this.add("Properties");

        this.popupMenu.addPopupMenuListener(new TablePopupMenuListener(this.popupMenu, table));
    }

    private void findUsages(ActionEvent event) {
        int selectedRow = this.table.getSelectedRow();
        if (selectedRow == -1) return;

        int displayObjectId = (int) this.table.getValueAt(selectedRow, 0);
        String displayObjectName = (String) this.table.getValueAt(selectedRow, 1);

        Main.editor.findUsages(displayObjectId, displayObjectName);
    }
}
