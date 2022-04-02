package com.vorono4ka.editor.layout.listeners;

import com.vorono4ka.editor.Main;
import com.vorono4ka.editor.layout.Window;
import com.vorono4ka.editor.layout.components.blocks.EditorInfoPanel;
import com.vorono4ka.editor.layout.components.blocks.MovieClipInfoPanel;
import com.vorono4ka.swf.MovieClipFrame;
import com.vorono4ka.swf.SupercellSWF;
import com.vorono4ka.swf.displayObjects.DisplayObject;
import com.vorono4ka.swf.displayObjects.MovieClip;
import com.vorono4ka.swf.displayObjects.original.DisplayObjectOriginal;
import com.vorono4ka.swf.exceptions.UnableToFindObjectException;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class DisplayObjectSelectionListener implements ListSelectionListener {
    private final JTable table;

    public DisplayObjectSelectionListener(JTable table) {
        this.table = table;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) return;

        int selectedRow = this.table.getSelectedRow();
        if (selectedRow == -1) return;

        int id = (int) this.table.getValueAt(selectedRow, 0);
        String name = (String) this.table.getValueAt(selectedRow, 1);

        SupercellSWF swf = Main.editor.getSwf();
        Window window = Main.editor.getWindow();
        EditorInfoPanel infoBlock = window.getInfoBlock();
        try {
            DisplayObjectOriginal displayObjectOriginal = swf.getOriginalDisplayObject(id, name);

            DisplayObject displayObject = displayObjectOriginal.clone(swf, null);
            if (displayObject.isMovieClip()) {
                MovieClip movieClip = (MovieClip) displayObject;

                MovieClipInfoPanel movieClipInfoPanel = new MovieClipInfoPanel();

                DisplayObject[] timelineChildren = movieClip.getTimelineChildren();
                int[] timelineChildrenIds = movieClip.getTimelineChildrenIds();
                String[] timelineChildrenNames = movieClip.getTimelineChildrenNames();
                for (int i = 0; i < timelineChildren.length; i++) {
                    movieClipInfoPanel.addTimelineChild(i, timelineChildrenIds[i], timelineChildrenNames[i]);
                }

                MovieClipFrame[] frames = movieClip.getFrames();
                for (int i = 0; i < frames.length; i++) {
                    MovieClipFrame frame = frames[i];
                    movieClipInfoPanel.addFrame(i, frame.getName());
                }

                infoBlock.setPanel(movieClipInfoPanel);
            }

            Main.editor.setSelectedObject(displayObject);
            Main.editor.updateCanvas();
        } catch (UnableToFindObjectException ex) {
            ex.printStackTrace();
        }
    }
}
