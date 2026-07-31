package dev.donutquine.editor.gui.layout.components.listeners;

import java.util.function.IntConsumer;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import dev.donutquine.editor.gui.layout.components.tables.MovieClipFramesTableModel;

public class FrameSelectionListener implements ListSelectionListener {
    private final JTable table;
    private final MovieClipFramesTableModel tableModel;

	private final IntConsumer currentFrameSetter;

    public FrameSelectionListener(JTable table, IntConsumer currentFrameSetter) {
        this.table = table;
        this.tableModel = (MovieClipFramesTableModel) this.table.getModel();
        this.currentFrameSetter = currentFrameSetter;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) return;

        int selectedRow = this.table.getSelectedRow();
        if (selectedRow == -1) return;

        if (this.tableModel.isAppendRow(selectedRow)) return;

        int index = (int) this.tableModel.getValueAt(selectedRow, MovieClipFramesTableModel.COLUMN_INDEX);
        this.currentFrameSetter.accept(index);
    }
}
