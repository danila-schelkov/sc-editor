package dev.donutquine.editor.layout.panels.info;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import dev.donutquine.editor.layout.SupercellSWFLayoutController;
import dev.donutquine.editor.layout.components.Table;
import dev.donutquine.editor.layout.components.listeners.ChildrenListMouseListener;
import dev.donutquine.editor.layout.components.listeners.FrameSelectionListener;
import dev.donutquine.editor.layout.contextmenus.ChildrenTableContextMenu;
import dev.donutquine.editor.layout.contextmenus.FrameTableContextMenu;
import dev.donutquine.editor.renderer.BlendMode;
import dev.donutquine.renderer.impl.swf.objects.DisplayObject;
import dev.donutquine.renderer.impl.swf.objects.MovieClip;

public class MovieClipInfoPanel extends JPanel {
    private final Table timelineChildrenTable;
    private final Table framesTable;
    private final Table frameElementsTable;
    private final JPanel textInfoPanel;

    public MovieClipInfoPanel(SupercellSWFLayoutController swfLayoutController, MovieClip movieClip) {
        this.setLayout(new GridLayout(0, 1));

        this.timelineChildrenTable = createTimelineChildrenTable(movieClip);

        this.timelineChildrenTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        this.timelineChildrenTable.addMouseListener(new ChildrenListMouseListener(this.timelineChildrenTable, swfLayoutController));
        new ChildrenTableContextMenu(this.timelineChildrenTable, swfLayoutController);

        this.framesTable = createFramesTable(movieClip);
        this.framesTable.addSelectionListener(new FrameSelectionListener(this.framesTable, this, movieClip));
        new FrameTableContextMenu(this.framesTable, swfLayoutController);
        this.frameElementsTable = new Table(new Object[0][], new Object[] {"#", "Child #", "Matrix", "Color Transform"}, new Class<?>[] {Integer.class, Integer.class, Integer.class, Integer.class});

        this.selectFrame(movieClip.getCurrentFrame());

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

    private static Table createFramesTable(MovieClip movieClip) {
        Object[][] data = new Object[movieClip.getFrameCount()][];
        for (int i = 0; i < data.length; i++) {
            data[i] = new Object[] {i, movieClip.getFrameLabel(i)};
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

            timelineChildrenData[i] = new Object[] {i, timelineChildren[i].getId(), timelineChildren[i].getClass().getSimpleName(), childName, timelineChildren[i].getBlendMode(), true};
        }

        return new Table(
            timelineChildrenData, 
            new Object[] {"#", "Id", "Type", "Name", "Blend Mode", "Visible"}, 
            new Class<?>[] {Integer.class, Integer.class, String.class, String.class, BlendMode.class, Boolean.class}
        );
    }

    public void selectFrame(int frameIndex) {
        this.framesTable.select(frameIndex);
    }

    // TODO: rewrite with setDataVector
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
