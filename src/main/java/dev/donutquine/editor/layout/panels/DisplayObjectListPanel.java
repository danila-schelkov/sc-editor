package dev.donutquine.editor.layout.panels;

import dev.donutquine.editor.Editor;
import dev.donutquine.editor.layout.components.Table;
import dev.donutquine.editor.layout.components.listeners.DisplayObjectListMouseListener;
import dev.donutquine.editor.layout.contextmenus.DisplayObjectContextMenu;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class DisplayObjectListPanel extends JPanel {
    private final TableRowSorter<TableModel> sorter;
    private final Table table;

    private final JTextField textField;

    public DisplayObjectListPanel(Editor editor) {
        this.table = new Table("Id", "Name", "Type");
        this.table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        this.sorter = new TableRowSorter<>(this.table.getModel());

        new DisplayObjectContextMenu(this.table, editor);
        this.table.addMouseListener(new DisplayObjectListMouseListener(this.table, editor));
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
        if (text.trim().isEmpty()) {
            this.resetFilter();
            return;
        }

        // Sets case-insensitive filter
        this.sorter.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(text)));
    }

    public void resetFilter() {
        this.sorter.setRowFilter(null);
        this.textField.setText(null);
    }

    public Table getTable() {
        return table;
    }

    public void setFocusOnTextField() {
        this.textField.requestFocus();
    }
}
