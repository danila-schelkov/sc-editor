package dev.donutquine.editor.layout.panels;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import dev.donutquine.editor.layout.SupercellSWFLayoutController;
import dev.donutquine.editor.layout.components.Table;
import dev.donutquine.editor.layout.components.listeners.DisplayObjectListMouseListener;
import dev.donutquine.editor.layout.contextmenus.DisplayObjectContextMenu;

public class DisplayObjectListPanel extends JPanel {
    private static final Object[] COLUMN_NAMES = {"Id", "Name", "Type"};
    private static final Class<?>[] COLUMN_CLASSES = {Integer.class, String.class, String.class};

    private final TableRowSorter<TableModel> sorter;
    private final Table table;

    private final JTextField textField;

    public DisplayObjectListPanel(SupercellSWFLayoutController controller, Object[][] data) {
        this.table = new Table(data, COLUMN_NAMES, COLUMN_CLASSES);
        this.table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        this.sorter = new TableRowSorter<>(this.table.getModel());

        new DisplayObjectContextMenu(this.table, controller);
        this.table.addMouseListener(new DisplayObjectListMouseListener(this.table, controller));
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

    public void selectObjectById(int id) {
        int row = this.table.indexOf(id, 0);
        if (row == -1) {
            this.resetFilter();

            row = this.table.indexOf(id, 0);
        }

        this.table.select(row);
    }

    public void setFocusOnTextField() {
        this.textField.requestFocus();
    }
}
