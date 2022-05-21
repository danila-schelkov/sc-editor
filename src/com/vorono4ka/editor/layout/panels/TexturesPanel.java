package com.vorono4ka.editor.layout.panels;

import com.vorono4ka.editor.layout.components.Table;
import com.vorono4ka.editor.layout.listeners.DisplayObjectSelectionListener;
import com.vorono4ka.editor.layout.listeners.TextureSelectionListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TexturesPanel extends JPanel {
    private final Table table;

    public TexturesPanel() {
        this.table = new Table("Index", "Width", "Height", "Type");

        this.table.addSelectionListener(new TextureSelectionListener(this.table));

        setLayout(new BorderLayout());
        this.add(new JScrollPane(this.table), BorderLayout.CENTER);
    }

    public Table getTable() {
        return table;
    }
}
