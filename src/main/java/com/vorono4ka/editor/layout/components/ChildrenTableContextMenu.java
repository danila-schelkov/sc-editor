package com.vorono4ka.editor.layout.components;

import com.vorono4ka.editor.Main;
import com.vorono4ka.swf.displayobjects.DisplayObject;
import com.vorono4ka.swf.displayobjects.MovieClip;

import java.util.function.Function;

public class ChildrenTableContextMenu extends ContextMenu {
    private final Table table;

    public ChildrenTableContextMenu(Table table) {
        super(table, null);

        this.table = table;

        this.add("Toggle visibility", event -> this.changeVisibility(child -> !child.isVisible()));
        this.add("Enable", event -> this.changeVisibility(child -> true));
        this.add("Disable", event -> this.changeVisibility(child -> false));

        this.popupMenu.addPopupMenuListener(new TablePopupMenuListener(this.popupMenu, table, rowIndex -> setMainComponentsEnabled(rowIndex != -1)));
    }

    private void changeVisibility(Function<DisplayObject, Boolean> visibilityFunction) {
        MovieClip movieClip = this.getMovieClip();
        if (movieClip == null) return;

        int[] selectedRows = this.table.getSelectedRows();
        for (int childIndex : selectedRows) {
            DisplayObject child = movieClip.getTimelineChildren()[childIndex];

            child.setVisibleRecursive(visibilityFunction.apply(child));

            this.table.setValueAt(child.isVisible(), childIndex, 4);
        }
    }

    private MovieClip getMovieClip() {
        DisplayObject selectedObject = Main.editor.getSelectedObject();
        if (selectedObject.isMovieClip()) {
            return (MovieClip) selectedObject;
        }

        return null;
    }
}
