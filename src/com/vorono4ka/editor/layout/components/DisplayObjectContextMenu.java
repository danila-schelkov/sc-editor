package com.vorono4ka.editor.layout.components;

public class DisplayObjectContextMenu extends ContextMenu {
    public DisplayObjectContextMenu(Table table) {
        super(table, null);

//        this.add("Copy as bytes");

        this.addSeparator();

        this.add("Properties");

        this.popupMenu.addPopupMenuListener(new TablePopupMenuListener(this.popupMenu, table));
    }
}
