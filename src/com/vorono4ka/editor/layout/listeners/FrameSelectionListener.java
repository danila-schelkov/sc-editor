package com.vorono4ka.editor.layout.listeners;

import com.vorono4ka.editor.Main;
import com.vorono4ka.editor.layout.panels.info.MovieClipInfoPanel;
import com.vorono4ka.swf.MovieClipFrameElement;
import com.vorono4ka.swf.displayObjects.DisplayObject;
import com.vorono4ka.swf.displayObjects.MovieClip;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class FrameSelectionListener implements ListSelectionListener {
    private final JTable table;

    public FrameSelectionListener(JTable table) {
        this.table = table;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) return;

        int selectedRow = this.table.getSelectedRow();
        if (selectedRow == -1) return;

        DisplayObject selectedObject = Main.editor.getSelectedObject();
        if (!selectedObject.isMovieClip()) return;

        MovieClip movieClip = (MovieClip) selectedObject;

        MovieClipInfoPanel panel = (MovieClipInfoPanel) Main.editor.getWindow().getInfoPanel().getPanel();
        panel.clearFrameElements();

        int index = (int) this.table.getValueAt(selectedRow, 0);

        MovieClipFrameElement[] frameElements = movieClip.getFrames()[index].getElements();
        for (int i = 0; i < frameElements.length; i++) {
            MovieClipFrameElement frameElement = frameElements[i];
            panel.addFrameElement(i, frameElement.getChildIndex(), frameElement.getMatrixIndex(), frameElement.getColorTransformIndex());
        }
    }
}
