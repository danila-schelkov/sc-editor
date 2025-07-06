package dev.donutquine.editor.layout.panels.info;

import dev.donutquine.editor.Editor;
import dev.donutquine.editor.layout.components.Table;
import dev.donutquine.editor.layout.components.listeners.ChildrenListMouseListener;
import dev.donutquine.editor.layout.components.listeners.FrameSelectionListener;
import dev.donutquine.editor.layout.contextmenus.ChildrenTableContextMenu;
import dev.donutquine.editor.layout.contextmenus.FrameTableContextMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MovieClipInfoPanel extends JPanel {
    private final Table timelineChildrenTable;
    private final Table framesTable;
    private final Table frameElementsTable;
    private final JPanel textInfoPanel;

    public MovieClipInfoPanel(Editor editor) {
        this.setLayout(new GridLayout(0, 1));

        this.timelineChildrenTable = new Table("#", "Id", "Type", "Name", "Visible");
        this.timelineChildrenTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        this.timelineChildrenTable.addMouseListener(new ChildrenListMouseListener(this.timelineChildrenTable, editor));
        ChildrenTableContextMenu childrenTableContextMenu = new ChildrenTableContextMenu(this.timelineChildrenTable, editor);

        this.framesTable = new Table("#", "Name");
        this.framesTable.addSelectionListener(new FrameSelectionListener(this.framesTable, editor));
        new FrameTableContextMenu(this.framesTable, editor);

        this.frameElementsTable = new Table("#", "Child #", "Matrix", "Color Transform");
        this.textInfoPanel = new JPanel();

        this.textInfoPanel.setLayout(new BoxLayout(this.textInfoPanel, BoxLayout.Y_AXIS));

        this.add(new JScrollPane(this.timelineChildrenTable), "Children");
        this.add(new JScrollPane(this.framesTable), "Frames");
        this.add(new JScrollPane(this.frameElementsTable), "Frame elements");
        this.add(this.textInfoPanel, "Info");

        this.timelineChildrenTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() < 2) {
                    return;
                }

                int column = timelineChildrenTable.columnAtPoint(e.getPoint());
                if (column == 4) {
                    childrenTableContextMenu.changeVisibility(child -> !child.isVisible());
                }
            }
        });
    }

    public void addTimelineChild(Object... rowData) {
        this.timelineChildrenTable.addRow(rowData);
    }

    public void addFrame(Object... rowData) {
        this.framesTable.addRow(rowData);
    }

    public Table getFramesTable() {
        return this.framesTable;
    }

    public void addFrameElement(Object... rowData) {
        this.frameElementsTable.addRow(rowData);
    }

    public void clearFrameElements() {
        this.frameElementsTable.clear();
    }

    public void setTextInfo(String... lines) {
        this.textInfoPanel.removeAll();
        for (String line : lines) {
            this.addTextLine(line);
        }
    }

    public void addTextLine(String line) {
        this.textInfoPanel.add(new JLabel(line));
    }

    public Component add(JComponent comp, String title) {
        comp.setBorder(BorderFactory.createTitledBorder(title));
        return super.add(comp);
    }
}
