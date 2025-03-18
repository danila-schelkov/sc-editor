package com.vorono4ka.editor.layout.components.listeners;

import com.vorono4ka.editor.Editor;
import com.vorono4ka.editor.layout.panels.info.MovieClipInfoPanel;
import com.vorono4ka.renderer.impl.swf.objects.DisplayObject;
import com.vorono4ka.renderer.impl.swf.objects.MovieClip;
import com.vorono4ka.swf.movieclips.MovieClipFrameElement;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.List;

public class FrameSelectionListener implements ListSelectionListener {
    private final JTable table;
    private final Editor editor;

    public FrameSelectionListener(JTable table, Editor editor) {
        this.table = table;
        this.editor = editor;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) return;

        int selectedRow = this.table.getSelectedRow();
        if (selectedRow == -1) return;

        DisplayObject selectedObject = editor.getSelectedObject();
        if (!selectedObject.isMovieClip()) return;

        MovieClip movieClip = (MovieClip) selectedObject;

        MovieClipInfoPanel panel = (MovieClipInfoPanel) editor.getWindow().getInfoPanel().getPanel();
        panel.clearFrameElements();

        int index = (int) this.table.getValueAt(selectedRow, 0);

        List<MovieClipFrameElement> frameElements = movieClip.getFrames().get(index).getElements();
        for (int i = 0; i < frameElements.size(); i++) {
            MovieClipFrameElement frameElement = frameElements.get(i);
            panel.addFrameElement(i, frameElement.childIndex(), frameElement.matrixIndex(), frameElement.colorTransformIndex());
        }
    }
}
