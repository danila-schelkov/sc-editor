package dev.donutquine.editor.layout.components.listeners;

import java.util.function.IntFunction;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import dev.donutquine.editor.layout.components.tables.MovieClipFrameElementsTableModel;
import dev.donutquine.swf.movieclips.MovieClipFrame;

public class FrameSelectionListener implements ListSelectionListener {
    private final JTable table;
    private final MovieClipFrameElementsTableModel frameElementsTableModel;
    private final IntFunction<MovieClipFrame> frameGetter;

    public FrameSelectionListener(JTable table, MovieClipFrameElementsTableModel frameElementsTable, IntFunction<MovieClipFrame> frameGetter) {
        this.table = table;
        this.frameElementsTableModel = frameElementsTable;
        this.frameGetter = frameGetter;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) return;

        int selectedRow = this.table.getSelectedRow();
        if (selectedRow == -1) return;

        int index = (int) this.table.getValueAt(selectedRow, 0);

        MovieClipFrame movieClipFrame = this.frameGetter.apply(index);
        this.frameElementsTableModel.setFrame(movieClipFrame);
    }
}
