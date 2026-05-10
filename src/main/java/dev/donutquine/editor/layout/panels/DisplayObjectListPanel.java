package dev.donutquine.editor.layout.panels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import dev.donutquine.editor.layout.SupercellSWFLayoutController;
import dev.donutquine.editor.layout.components.tables.Table;
import dev.donutquine.editor.layout.components.listeners.DisplayObjectListMouseListener;
import dev.donutquine.editor.layout.contextmenus.DisplayObjectContextMenu;

public class DisplayObjectListPanel extends JPanel {
    private static final Object[] COLUMN_NAMES = {"Id", "Name", "Type"};
    private static final Class<?>[] COLUMN_CLASSES = {Integer.class, String.class, String.class};
    private static final String OPEN_SELECTED_ROW = "openSelectedRow";

    private final SupercellSWFLayoutController controller;

    private final TableRowSorter<TableModel> sorter;
    private final Table table;

    private final JTextField searchField;

    public DisplayObjectListPanel(SupercellSWFLayoutController controller, Object[][] data) {
        this.controller = controller;

        this.table = new Table(data, COLUMN_NAMES, COLUMN_CLASSES);
        this.table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        this.sorter = new TableRowSorter<>(this.table.getModel());
        table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), OPEN_SELECTED_ROW);

        table.getActionMap().put(OPEN_SELECTED_ROW, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                handleRowSelected();
            }
        });

        new DisplayObjectContextMenu(this.table, controller);
        this.table.addMouseListener(new DisplayObjectListMouseListener(this::handleRowSelected));
        this.table.setRowSorter(this.sorter);

        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(1, SortOrder.DESCENDING));
        this.sorter.setSortKeys(sortKeys);

        this.searchField = new JTextField();

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        panel.add(this.searchField);

        this.searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                find(searchField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                find(searchField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

        setLayout(new BorderLayout());
        this.add(panel, BorderLayout.SOUTH);
        this.add(new JScrollPane(this.table), BorderLayout.CENTER);
    }

    public void resetFilter() {
        this.sorter.setRowFilter(null);
        this.searchField.setText(null);
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
        this.searchField.requestFocus();
    }

    private void find(String text) {
        if (text.trim().isEmpty()) {
            this.resetFilter();
            return;
        }

        // Sets case-insensitive filter
        this.sorter.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(text)));
    }

    private void handleRowSelected() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }

        int id = (int) table.getValueAt(selectedRow, 0);
        String name = (String) table.getValueAt(selectedRow, 1);

        controller.selectObject(id, name);
    }
}
