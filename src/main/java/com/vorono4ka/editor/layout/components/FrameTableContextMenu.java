package com.vorono4ka.editor.layout.components;

import com.vorono4ka.editor.Main;
import com.vorono4ka.swf.displayobjects.DisplayObject;
import com.vorono4ka.swf.displayobjects.MovieClip;

import java.awt.event.ActionEvent;

public class FrameTableContextMenu extends ContextMenu {
    private final Table table;

    public FrameTableContextMenu(Table table) {
        super(table, null);

        this.table = table;

        this.add("Goto and play", this::gotoAndPlay);
        this.add("Goto and stop", this::gotoAndStop);

        this.popupMenu.addPopupMenuListener(new TablePopupMenuListener(this.popupMenu, table, rowIndex -> setMainComponentsEnabled(rowIndex != -1)));
    }

    private void gotoAndPlay(ActionEvent event) {
        MovieClip movieClip = this.getMovieClip();
        if (movieClip == null) return;

        int frameIndex = this.table.getSelectedRow();
        String frameLabel = (String) this.table.getValueAt(frameIndex, 1);
        if (frameLabel != null) {
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
        DisplayObject selectedObject = Main.editor.getSelectedObject();
        if (selectedObject.isMovieClip()) {
            return (MovieClip) selectedObject;
        }

        return null;
    }
}
