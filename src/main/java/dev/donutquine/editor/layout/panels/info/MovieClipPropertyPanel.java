package dev.donutquine.editor.layout.panels.info;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DropMode;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import dev.donutquine.editor.layout.SupercellSWFLayoutController;
import dev.donutquine.editor.layout.components.tables.Table;
import dev.donutquine.editor.layout.components.tables.MovieClipFrameElementsTableModel;
import dev.donutquine.editor.layout.components.tables.RowReorderTransferHandler;
import dev.donutquine.editor.layout.components.listeners.ChildrenListMouseListener;
import dev.donutquine.editor.layout.components.listeners.FrameSelectionListener;
import dev.donutquine.editor.layout.contextmenus.ChildrenTableContextMenu;
import dev.donutquine.editor.layout.contextmenus.FrameElementTableContextMenu;
import dev.donutquine.editor.layout.contextmenus.FrameTableContextMenu;
import dev.donutquine.editor.renderer.BlendMode;
import dev.donutquine.renderer.impl.swf.objects.DisplayObject;
import dev.donutquine.renderer.impl.swf.objects.MovieClip;
import dev.donutquine.swf.movieclips.MovieClipFrame;

public class MovieClipPropertyPanel extends JPanel {
    private final JTable timelineChildrenTable;
    private final JTable framesTable;
    private final JTable frameElementsTable;
    private final JPanel textInfoPanel;

    public MovieClipPropertyPanel(SupercellSWFLayoutController swfLayoutController, MovieClip movieClip) {
        this.setLayout(new GridLayout(0, 1));

        this.timelineChildrenTable = createTimelineChildrenTable(movieClip);

        this.timelineChildrenTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        this.timelineChildrenTable.addMouseListener(new ChildrenListMouseListener(this.timelineChildrenTable, swfLayoutController));
        new ChildrenTableContextMenu(this.timelineChildrenTable, swfLayoutController);

        // TODO: handle empty movie clips properly
        assert !movieClip.getFrames().isEmpty();
        MovieClipFrameElementsTableModel tableModel = new MovieClipFrameElementsTableModel(movieClip.getFrames().get(0));

        this.frameElementsTable = createFrameElementsTable(tableModel);
        new FrameElementTableContextMenu(this.frameElementsTable, tableModel);

        Table framesTable = createFramesTable(movieClip);
        framesTable.addSelectionListener(new FrameSelectionListener(framesTable, tableModel, movieClip.getFrames()::get));
        new FrameTableContextMenu(framesTable, swfLayoutController);
        this.framesTable = framesTable;

        this.textInfoPanel = new JPanel();

        this.setTextInfo(
            "Export name: " + movieClip.getExportName(),
            "FPS: " + movieClip.getFps(),
            String.format("Duration: %.2fs", movieClip.getDuration())
        );

        this.textInfoPanel.setLayout(new BoxLayout(this.textInfoPanel, BoxLayout.Y_AXIS));

        this.add(new JScrollPane(this.timelineChildrenTable), "Children");
        this.add(new JScrollPane(this.framesTable), "Frames");
        this.add(new JScrollPane(this.frameElementsTable), "Frame elements");
        this.add(this.textInfoPanel, "Info");
    }

    private Table createFrameElementsTable(MovieClipFrameElementsTableModel tableModel) {
        Table table = new Table(tableModel);
        table.setDragEnabled(true);
        table.setDropMode(DropMode.INSERT_ROWS);
        table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        table.setTransferHandler(new RowReorderTransferHandler(table, tableModel));

        return table;
    }

    private static Table createFramesTable(MovieClip movieClip) {
        List<MovieClipFrame> frames = movieClip.getFrames();
        Object[][] data = new Object[frames.size()][];
        for (int i = 0; i < data.length; i++) {
            MovieClipFrame frame = frames.get(i);

            data[i] = new Object[] {i, frame.getLabel()};
        }

        return new Table(
            data, 
            new Object[] {"#", "Name"}, 
            new Class<?>[] {Integer.class, String.class}
        );
    }

    private static Table createTimelineChildrenTable(MovieClip movieClip) {
        DisplayObject[] timelineChildren = movieClip.getTimelineChildren();
        String[] timelineChildrenNames = movieClip.getTimelineChildrenNames();
        assert timelineChildrenNames == null || timelineChildrenNames.length == 0 || timelineChildren.length == timelineChildrenNames.length;

        Object[][] timelineChildrenData = new Object[timelineChildren.length][];
        for (int i = 0; i < timelineChildrenData.length; i++) {
            Object childName = timelineChildrenNames != null && timelineChildrenNames.length > 0 ? timelineChildrenNames[i] : null;
            DisplayObject timelineChild = timelineChildren[i];
            timelineChildrenData[i] = new Object[] {i, timelineChild.getId(), timelineChild.getClass().getSimpleName(), childName, timelineChild.getBlendMode(), timelineChild.isVisible()};
        }

        return new Table(
            timelineChildrenData, 
            new Object[] {"#", "Id", "Type", "Name", "Blend Mode", "Visible"}, 
            new Class<?>[] {Integer.class, Integer.class, String.class, String.class, BlendMode.class, Boolean.class}
        );
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
