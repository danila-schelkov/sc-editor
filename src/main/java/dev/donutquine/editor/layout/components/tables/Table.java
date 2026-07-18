package dev.donutquine.editor.layout.components.tables;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;

public class Table extends JTable {
    private static final String EDIT_SELECTED_CELL = "editSelectedCell";

    public Table(Object[][] data, Object[] columnNames, Class<?>[] columnClasses) {
        // FIXME: enable assertion after migrating to Java 25+
        // assert columnClasses.length == columnNames.length;
        this(new DefaultTableModel(data, columnNames) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnClasses[columnIndex];
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }

    public Table(TableModel tableModel) {
        this.setModel(tableModel);
        this.tableHeader.setReorderingAllowed(false);

        this.selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setColumnSelectionAllowed(true);
                
        getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), EDIT_SELECTED_CELL);

        getActionMap().put(EDIT_SELECTED_CELL, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (Table.this.getSelectedRowCount() > 1 || Table.this.getSelectedColumnCount() > 1) return;

                int row = Table.this.getSelectedRow();
                int column = Table.this.getSelectedColumn();

                if (row < 0 || column < 0) return;

                if (Table.this.editCellAt(row, column)) {
                    Component editorComponent = Table.this.getEditorComponent();

                    if (editorComponent instanceof JTextComponent textComponent) {
                        textComponent.requestFocusInWindow();
                        // NOTE: absolutely unnecessary as we already do it in prepareEditor
                        // textComponent.selectAll();
                    }
                }
            }
        });
    }

    public int indexOf(int value, int column) {
        for (int i = 0; i < this.getRowCount(); i++) {
            if (((int) this.getValueAt(i, column)) == value) {
                return i;
            }
        }

        return -1;
    }

    public void select(int row) {
        this.setRowSelectionInterval(row, row);
        this.scrollRectToVisible(this.getCellRect(row, 0, true));
    }

    public void addSelectionListener(ListSelectionListener listener) {
        this.selectionModel.addListSelectionListener(listener);
    }

    @Override
    public Component prepareEditor(TableCellEditor editor, int row, int column) {
        Component c = super.prepareEditor(editor, row, column);

        if (c instanceof JTextComponent textComponent) {
            SwingUtilities.invokeLater(textComponent::selectAll);
        }

        return c;
    }
}
