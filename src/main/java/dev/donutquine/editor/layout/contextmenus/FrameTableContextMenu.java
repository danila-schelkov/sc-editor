package dev.donutquine.editor.layout.contextmenus;

import java.awt.event.ActionEvent;
import javax.swing.JTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dev.donutquine.editor.layout.SupercellSWFLayoutController;
import dev.donutquine.editor.layout.components.tables.JTablePopupMenuListener;
import dev.donutquine.editor.layout.components.tables.MovieClipFramesTableModel;
import dev.donutquine.renderer.impl.swf.objects.DisplayObject;
import dev.donutquine.renderer.impl.swf.objects.MovieClip;

public class FrameTableContextMenu extends ContextMenu {
    private final Logger LOGGER = LoggerFactory.getLogger(FrameTableContextMenu.class);

    private final JTable table;
	private final MovieClipFramesTableModel tableModel;
    private final SupercellSWFLayoutController swfLayoutController;

    public FrameTableContextMenu(JTable table, MovieClipFramesTableModel tableModel, SupercellSWFLayoutController swfLayoutController) {
        super(table, null);

        this.table = table;
        this.tableModel = tableModel;
        this.swfLayoutController = swfLayoutController;

        this.add("Goto and play", this::gotoAndPlay);
        this.add("Goto and stop", this::gotoAndStop);
        this.addSeparator();
        this.add("Delete", this::delete);

        this.popupMenu.addPopupMenuListener(new JTablePopupMenuListener(this.popupMenu, table, rowIndex -> setMainComponentsEnabled(rowIndex != -1)));
    }

    private void gotoAndPlay(ActionEvent event) {
        MovieClip movieClip = this.getMovieClip();
        if (movieClip == null) return;

        int frameIndex = this.table.getSelectedRow();
        String frameLabel = (String) this.table.getValueAt(frameIndex, 1);
        if (frameLabel != null) {
            // TODO: add patterns of animation frame names to settings
            String endFrameLabel = frameLabel + "End";
            if (frameLabel.endsWith("Start")) {
                endFrameLabel = frameLabel.substring(0, frameLabel.length() - 5) + "End";
            }
            movieClip.gotoAndPlay(frameLabel, endFrameLabel);
        } else {
            movieClip.gotoAndPlayFrameIndex(frameIndex, -1);
        }
    }

    private void gotoAndStop(ActionEvent event) {
        MovieClip movieClip = this.getMovieClip();
        if (movieClip == null) return;

        int frameIndex = this.table.getSelectedRow();
        movieClip.gotoAndStopFrameIndex(frameIndex);
    }

    private void delete(ActionEvent event) {
        int firstIndex = this.table.getSelectedRow();
        int elementCount = this.table.getSelectedRowCount();

        try {
            this.tableModel.delete(firstIndex, elementCount);
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getLocalizedMessage());
        }
    }

    private MovieClip getMovieClip() {
        DisplayObject selectedObject = swfLayoutController.getSelectedObject();
        assert selectedObject.isMovieClip(); 

        return (MovieClip) selectedObject;
    }
}
