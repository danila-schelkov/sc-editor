package dev.donutquine.editor.layout.contextmenus;

import java.awt.event.ActionEvent;
import javax.swing.JTable;
import dev.donutquine.editor.layout.SupercellSWFLayoutController;
import dev.donutquine.editor.layout.components.tables.JTablePopupMenuListener;
import dev.donutquine.renderer.impl.swf.objects.DisplayObject;
import dev.donutquine.renderer.impl.swf.objects.MovieClip;

public class FrameTableContextMenu extends ContextMenu {
    private final JTable table;
    private final SupercellSWFLayoutController swfLayoutController;

    public FrameTableContextMenu(JTable table, SupercellSWFLayoutController swfLayoutController) {
        super(table, null);

        this.table = table;
        this.swfLayoutController = swfLayoutController;

        this.add("Goto and play", this::gotoAndPlay);
        this.add("Goto and stop", this::gotoAndStop);

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

    private MovieClip getMovieClip() {
        DisplayObject selectedObject = swfLayoutController.getSelectedObject();
        assert selectedObject.isMovieClip(); 

        return (MovieClip) selectedObject;
    }
}
