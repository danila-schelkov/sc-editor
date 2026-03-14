package dev.donutquine.editor.layout.contextmenus;

import java.util.function.Function;
import dev.donutquine.editor.layout.SupercellSWFLayoutController;
import dev.donutquine.editor.layout.components.Table;
import dev.donutquine.editor.layout.components.TablePopupMenuListener;
import dev.donutquine.renderer.impl.swf.objects.DisplayObject;
import dev.donutquine.renderer.impl.swf.objects.MovieClip;

public class ChildrenTableContextMenu extends ContextMenu {
    private final Table table;
    private final SupercellSWFLayoutController swfLayoutController;

    public ChildrenTableContextMenu(Table table, SupercellSWFLayoutController swfLayoutController) {
        super(table, null);

        this.table = table;
        this.swfLayoutController = swfLayoutController;

        this.add("Toggle visibility", event -> this.changeVisibility(child -> !child.isVisible()));
        this.add("Enable", event -> this.changeVisibility(child -> true));
        this.add("Disable", event -> this.changeVisibility(child -> false));

        this.popupMenu.addPopupMenuListener(new TablePopupMenuListener(this.popupMenu, table, rowIndex -> setMainComponentsEnabled(rowIndex != -1)));
    }

    public void changeVisibility(Function<DisplayObject, Boolean> visibilityFunction) {
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
        DisplayObject selectedObject = this.swfLayoutController.getSelectedObject();
        assert selectedObject.isMovieClip();

        return (MovieClip) selectedObject;
    }
}
