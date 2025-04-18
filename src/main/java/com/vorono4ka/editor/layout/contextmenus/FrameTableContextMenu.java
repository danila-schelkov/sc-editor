package com.vorono4ka.editor.layout.contextmenus;

import com.vorono4ka.editor.Editor;
import com.vorono4ka.editor.layout.components.Table;
import com.vorono4ka.editor.layout.components.TablePopupMenuListener;
import com.vorono4ka.renderer.impl.swf.objects.DisplayObject;
import com.vorono4ka.renderer.impl.swf.objects.MovieClip;

import java.awt.event.ActionEvent;

public class FrameTableContextMenu extends ContextMenu {
    private final Table table;
    private final Editor editor;

    public FrameTableContextMenu(Table table, Editor editor) {
        super(table, null);

        this.table = table;
        this.editor = editor;

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
        DisplayObject selectedObject = editor.getSelectedObject();
        if (selectedObject.isMovieClip()) {
            return (MovieClip) selectedObject;
        }

        return null;
    }
}
