package dev.donutquine.editor.gui.layout.components.tables;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
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

    @Override
    public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
        if (this.dataModel instanceof RowAppendableTableModel rowAppendableTableModel && rowAppendableTableModel.isAppendRow(rowIndex)) {
            return;
        }

        if (this.dataModel instanceof PendingRowTableModel pendingRowTableModel && pendingRowTableModel.isPendingRow(rowIndex)) {
            return;
        }

        super.changeSelection(rowIndex, columnIndex, toggle, extend);
    }

    @Override
    public void editingCanceled(ChangeEvent e) {
        super.editingCanceled(e);
        // NOTE: won't work for multiple pending rows
        if (this.dataModel instanceof PendingRowTableModel pendingRowTableModel && pendingRowTableModel.hasPendingRow()) {
            pendingRowTableModel.clearPendingRows();
        }
    }

    @Override
    public void editingStopped(ChangeEvent e) {
        super.editingStopped(e);
        // NOTE: won't work for multiple pending rows
        if (this.dataModel instanceof PendingRowTableModel pendingRowTableModel && pendingRowTableModel.hasPendingRow()) {
            pendingRowTableModel.clearPendingRows();
        }
    }

    /**
     * @see JTable#selectAll()
     */
    @Override
    public void selectAll() {
        if (!(this.dataModel instanceof RowAppendableTableModel)) {
            super.selectAll();
            return;
        }

        // If I'm currently editing, then I should stop editing
        if (isEditing()) {
            removeEditor();
        }

        if (this.dataModel instanceof PendingRowTableModel pendingRowTableModel && pendingRowTableModel.hasPendingRow()) {
            pendingRowTableModel.clearPendingRows();
        }

        if (getRowCount() > 0 && getColumnCount() > 0) {
            int oldLead;
            int oldAnchor;
            ListSelectionModel selModel;

            selModel = selectionModel;
            selModel.setValueIsAdjusting(true);
            oldLead = getAdjustedIndex(selModel.getLeadSelectionIndex(), true);
            oldAnchor = getAdjustedIndex(selModel.getAnchorSelectionIndex(), true);

            // NOTE: the only difference from super method is this -2 to exclude append row from selection
            setRowSelectionInterval(0, getRowCount()-2);

            // this is done to restore the anchor and lead
            setLeadAnchorWithoutSelection(selModel, oldLead, oldAnchor);

            selModel.setValueIsAdjusting(false);

            selModel = columnModel.getSelectionModel();
            selModel.setValueIsAdjusting(true);
            oldLead = getAdjustedIndex(selModel.getLeadSelectionIndex(), false);
            oldAnchor = getAdjustedIndex(selModel.getAnchorSelectionIndex(), false);

            setColumnSelectionInterval(0, getColumnCount()-1);

            // this is done to restore the anchor and lead
            setLeadAnchorWithoutSelection(selModel, oldLead, oldAnchor);

            selModel.setValueIsAdjusting(false);
        }
    }

    /**
     * @see JTable#getAdjustedIndex(int, boolean)
     */
    private int getAdjustedIndex(int index, boolean row) {
        int compare = row ? getRowCount() : getColumnCount();
        return index < compare ? index : -1;
    }

    /**
     * Set the lead and anchor without affecting selection.
     *
     * @see sun.swing.SwingUtilities2#setLeadAnchorWithoutSelection(javax.swing.ListSelectionModel, int, int)
     */
    private static void setLeadAnchorWithoutSelection(ListSelectionModel model, int lead, int anchor) {
        if (anchor == -1) {
            anchor = lead;
        }
        if (lead == -1) {
            model.setAnchorSelectionIndex(-1);
            model.setLeadSelectionIndex(-1);
        } else {
            if (model.isSelectedIndex(lead)) {
                model.addSelectionInterval(lead, lead);
            } else {
                model.removeSelectionInterval(lead, lead);
            }
            model.setAnchorSelectionIndex(anchor);
        }
    }
}
