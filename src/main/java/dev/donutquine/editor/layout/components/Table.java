package dev.donutquine.editor.layout.components;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class Table extends JTable {
    public Table(Object[][] data, Object[] columnNames) {
        this.setModel(new DefaultTableModel(data, columnNames) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0)
                    return Integer.class;
                return super.getColumnClass(columnIndex);
            }
        });
        this.tableHeader.setReorderingAllowed(false);

        this.selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setColumnSelectionAllowed(true);
    }

    public Table(Object... columnNames) {
        this.setModel(new DefaultTableModel(columnNames, 0));

        this.tableHeader.setReorderingAllowed(false);

        this.selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setColumnSelectionAllowed(true);
    }

    public void clear() {
        DefaultTableModel model = (DefaultTableModel) this.dataModel;
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
    }

    public void addRow(Object... rowData) {
        DefaultTableModel dataModel = (DefaultTableModel) this.dataModel;
        dataModel.addRow(rowData);
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

    public boolean isCellEditable(int row, int column){
        return false;
    }
}
