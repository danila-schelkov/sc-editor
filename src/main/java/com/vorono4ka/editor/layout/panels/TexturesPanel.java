package com.vorono4ka.editor.layout.panels;

import com.vorono4ka.editor.layout.components.Table;
import com.vorono4ka.editor.layout.contextmenus.TextureTableContextMenu;
import com.vorono4ka.editor.layout.components.listeners.TextureListMouseListener;

import javax.swing.*;
import java.awt.*;

public class TexturesPanel extends JPanel {
    private final Table table;

    public TexturesPanel() {
        this.table = new Table("Index", "Width", "Height", "Type");

        new TextureTableContextMenu(this.table);
        this.table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        this.table.addMouseListener(new TextureListMouseListener(this.table));

        setLayout(new BorderLayout());
        this.add(new JScrollPane(this.table), BorderLayout.CENTER);
    }

    public Table getTable() {
        return table;
    }
}
