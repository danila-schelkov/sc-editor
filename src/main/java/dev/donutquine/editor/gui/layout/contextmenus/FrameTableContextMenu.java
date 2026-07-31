package dev.donutquine.editor.gui.layout.contextmenus;

import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.JTable;
import dev.donutquine.editor.gui.layout.SupercellSWFLayoutController;
import dev.donutquine.editor.gui.layout.components.tables.JTablePopupMenuListener;
import dev.donutquine.editor.gui.layout.components.tables.MovieClipFramesTableModel;
import dev.donutquine.renderer.impl.swf.objects.DisplayObject;
import dev.donutquine.renderer.impl.swf.objects.MovieClip;

public class FrameTableContextMenu extends ContextMenu {
    private final JTable table;
	private final MovieClipFramesTableModel tableModel;
    private final SupercellSWFLayoutController swfLayoutController;

    public FrameTableContextMenu(JTable table, SupercellSWFLayoutController swfLayoutController, Action duplicateAction, Action insertBeforeAction, Action insertAfterAction, Action deleteAction) {
        super(table, null);

        this.table = table;
        this.tableModel = (MovieClipFramesTableModel) this.table.getModel();
        this.swfLayoutController = swfLayoutController;

        this.add("Goto and play", this::gotoAndPlay);
        this.add("Goto and stop", this::gotoAndStop);
        this.addSeparator();
        this.add(duplicateAction);
        this.add(insertBeforeAction);
        this.add(insertAfterAction);
        this.addSeparator();
        this.add(deleteAction);

        this.popupMenu.addPopupMenuListener(new JTablePopupMenuListener(this.popupMenu, table, rowIndex -> {
            // NOTE: setting enabled to items as we have to revert the state for actions when leave, but not for items
            setMainComponentsEnabled(rowIndex != -1 && !this.tableModel.isAppendRow(rowIndex));
        }));
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

    private MovieClip getMovieClip() {
        DisplayObject selectedObject = swfLayoutController.getSelectedObject();
        assert selectedObject.isMovieClip(); 

        return (MovieClip) selectedObject;
    }
}
