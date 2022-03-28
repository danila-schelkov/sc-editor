package com.vorono4ka.editor.layout;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class Table extends JTable {
    public Table(Object... columnNames) {
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(columnNames);

        this.setModel(tableModel);

        this.tableHeader.setReorderingAllowed(false);

        this.selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public void addRow(Object... rowData) {
        DefaultTableModel dataModel = (DefaultTableModel) this.dataModel;
        dataModel.addRow(rowData);
    }

    public void addSelectionListener(ListSelectionListener listener) {
        this.selectionModel.addListSelectionListener(listener);
    }

    public boolean isCellEditable(int row, int column){
        return false;
    }
}
