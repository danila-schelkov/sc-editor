package com.vorono4ka.editor.layout;

import com.vorono4ka.editor.layout.components.Table;
import com.vorono4ka.editor.layout.listeners.DisplayObjectSelectionListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DisplayObjectListPanel extends JPanel {
    private final TableRowSorter<TableModel> sorter;
    private final Table table;

    private final JTextField textField;

    public DisplayObjectListPanel() {
        this.table = new Table("Id", "Name", "Type");
        this.sorter = new TableRowSorter<>(this.table.getModel());

        this.table.addSelectionListener(new DisplayObjectSelectionListener(this.table));
        this.table.setRowSorter(this.sorter);

        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(1, SortOrder.DESCENDING));
        this.sorter.setSortKeys(sortKeys);

        this.textField = new JTextField();

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        panel.add(this.textField);

        this.textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                find(textField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                find(textField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

        setLayout(new BorderLayout());
        this.add(panel, BorderLayout.SOUTH);
        this.add(new JScrollPane(this.table), BorderLayout.CENTER);
    }

    private void find(String text) {
        if (text.trim().length() == 0) {
            this.resetFilter();
            return;
        }

        this.sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
    }

    public void resetFilter() {
        this.sorter.setRowFilter(null);
    }

    public Table getTable() {
        return table;
    }

    public void setFocusOnTextField() {
        this.textField.requestFocus();
    }
}
