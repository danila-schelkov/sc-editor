package dev.donutquine.editor.gui.layout.components.listeners;

import java.util.function.IntConsumer;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class FrameSelectionListener implements ListSelectionListener {
    private final JTable table;
	private final IntConsumer currentFrameSetter;

    public FrameSelectionListener(JTable table, IntConsumer currentFrameSetter) {
        this.table = table;
        this.currentFrameSetter = currentFrameSetter;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) return;

        int selectedRow = this.table.getSelectedRow();
        if (selectedRow == -1) return;

        int index = (int) this.table.getValueAt(selectedRow, 0);
        this.currentFrameSetter.accept(index);
    }
}
