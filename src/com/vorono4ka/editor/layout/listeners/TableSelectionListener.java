package com.vorono4ka.editor.layout.listeners;

import com.vorono4ka.editor.Main;
import com.vorono4ka.editor.layout.Table;
import com.vorono4ka.editor.layout.Window;
import com.vorono4ka.swf.MovieClipFrame;
import com.vorono4ka.swf.SupercellSWF;
import com.vorono4ka.swf.displayObjects.DisplayObject;
import com.vorono4ka.swf.displayObjects.MovieClip;
import com.vorono4ka.swf.displayObjects.original.DisplayObjectOriginal;
import com.vorono4ka.swf.exceptions.UnableToFindObjectException;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class TableSelectionListener implements ListSelectionListener {
    private final JTable table;

    public TableSelectionListener(JTable table) {
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
        JPanel infoBlock = window.getInfoBlock();
        try {
            DisplayObjectOriginal displayObjectOriginal = swf.getOriginalDisplayObject(id, name);

            DisplayObject displayObject = displayObjectOriginal.clone(swf, null);
            if (displayObject.isMovieClip()) {
                MovieClip movieClip = (MovieClip) displayObject;

                infoBlock.removeAll();

                infoBlock.add(new JLabel("Children"));

                Table timelineChildrenTable = new Table("#", "id", "name");
                JScrollPane tableScrollPane = new JScrollPane(timelineChildrenTable);
                tableScrollPane.setPreferredSize(new Dimension(300, infoBlock.getHeight() / 3));
                infoBlock.add(tableScrollPane);

                DisplayObject[] timelineChildren = movieClip.getTimelineChildren();
                String[] timelineChildrenNames = movieClip.getTimelineChildrenNames();
                for (int i = 0; i < timelineChildren.length; i++) {
//                    DisplayObject timelineChild = timelineChildren[i];
                    timelineChildrenTable.addRow(i, "", timelineChildrenNames[i]);
                }

                infoBlock.add(new JLabel("Frames"));

                Table framesTable = new Table("#", "name");
                JScrollPane framesTableScrollPane = new JScrollPane(framesTable);
                framesTableScrollPane.setPreferredSize(new Dimension(300, infoBlock.getHeight() / 3));
                infoBlock.add(framesTableScrollPane);

                MovieClipFrame[] frames = movieClip.getFrames();
                for (int i = 0; i < frames.length; i++) {
                    MovieClipFrame frame = frames[i];
                    framesTable.addRow(i, frame.getName());
                }

                infoBlock.revalidate();
                infoBlock.updateUI();
            }
            Main.editor.updateCanvas();
        } catch (UnableToFindObjectException ex) {
            ex.printStackTrace();
        }
    }
}
