package dev.donutquine.editor.gui.layout.components.tables;

/**
 * don't forget to add virtual row to {@link javax.swing.table.TableModel#getRowCount}
 *
 * <pre>
 *   // NOTE: display append row as empty row
 *   if (isAppendRow(row)) {
 *       return null;
 *   }
 * </pre>
 * to {@link javax.swing.table.TableModel#getValueAt(int, int)}
 * 
 * <pre>
 *   if (isAppendRow(row)) {
 *       // TODO: insertion code
 *       return;
 *   }
 * </pre>
 * to {@link javax.swing.table.TableModel#setValueAt(int, int)}
 *
 */
public interface RowAppendableTableModel {
    boolean isAppendRow(int row);
}
