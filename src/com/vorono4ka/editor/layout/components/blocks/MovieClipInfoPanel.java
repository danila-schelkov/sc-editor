package com.vorono4ka.editor.layout.components.blocks;

import com.vorono4ka.editor.layout.components.Table;
import com.vorono4ka.editor.layout.listeners.FrameSelectionListener;

import javax.swing.*;
import java.awt.*;

public class MovieClipInfoPanel extends JPanel {
    private final Table timelineChildrenTable;
    private final Table framesTable;
    private final Table frameElementsTable;

    public MovieClipInfoPanel() {
        this.setLayout(new GridLayout(0, 1));

        this.timelineChildrenTable = new Table("#", "Id", "Name");
        this.framesTable = new Table("#", "Name");
        this.framesTable.addSelectionListener(new FrameSelectionListener(this.framesTable));
        this.frameElementsTable = new Table("#", "Child #", "Matrix", "Color Transform");

        this.add(new JScrollPane(this.timelineChildrenTable), "Children");
        this.add(new JScrollPane(this.framesTable), "Frames");
        this.add(new JScrollPane(this.frameElementsTable), "Frame elements");
    }

    public void addTimelineChild(Object... rowData) {
        this.timelineChildrenTable.addRow(rowData);
    }

    public void addFrame(Object... rowData) {
        this.framesTable.addRow(rowData);
    }

    public void addFrameElement(Object... rowData) {
        this.frameElementsTable.addRow(rowData);
    }

    public void clearFrameElements() {
        this.frameElementsTable.clear();
    }

    public Component add(JComponent comp, String title) {
        comp.setBorder(BorderFactory.createTitledBorder(title));
        return super.add(comp);
    }
}
