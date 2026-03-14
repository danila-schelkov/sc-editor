package dev.donutquine.editor.layout.components.listeners;

import java.util.List;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import dev.donutquine.editor.layout.panels.info.MovieClipInfoPanel;
import dev.donutquine.renderer.impl.swf.objects.MovieClip;
import dev.donutquine.swf.movieclips.MovieClipFrameElement;

public class FrameSelectionListener implements ListSelectionListener {
    private final JTable table;
    private final MovieClipInfoPanel panel;
	private final MovieClip movieClip;

    public FrameSelectionListener(JTable table, MovieClipInfoPanel panel, MovieClip movieClip) {
        this.table = table;
        this.panel = panel;
        this.movieClip = movieClip;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) return;

        int selectedRow = this.table.getSelectedRow();
        if (selectedRow == -1) return;

        this.panel.clearFrameElements();

        int index = (int) this.table.getValueAt(selectedRow, 0);

        List<MovieClipFrameElement> frameElements = movieClip.getFrames().get(index).getElements();
        for (int i = 0; i < frameElements.size(); i++) {
            MovieClipFrameElement frameElement = frameElements.get(i);
            this.panel.addFrameElement(i, frameElement.childIndex(), frameElement.matrixIndex(), frameElement.colorTransformIndex());
        }
    }
}
