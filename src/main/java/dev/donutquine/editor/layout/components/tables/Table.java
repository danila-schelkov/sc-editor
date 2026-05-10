package dev.donutquine.editor.layout.components.tables;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class Table extends JTable {
    public Table(Object[][] data, Object[] columnNames, Class<?>[] columnClasses) {
        // FIXME: enable assertion after migrating to Java 25+
        // assert columnClasses.length == columnNames.length;
        this(new DefaultTableModel(data, columnNames) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnClasses[columnIndex];
            }
        });
    }

    public Table(TableModel tableModel) {
        this.setModel(tableModel);
        this.tableHeader.setReorderingAllowed(false);

        this.selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setColumnSelectionAllowed(true);
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
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
