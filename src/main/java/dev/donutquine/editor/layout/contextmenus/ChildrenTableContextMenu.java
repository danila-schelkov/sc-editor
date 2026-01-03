package dev.donutquine.editor.layout.contextmenus;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.util.function.Function;

import dev.donutquine.editor.Editor;
import dev.donutquine.editor.layout.components.Table;
import dev.donutquine.editor.layout.components.TablePopupMenuListener;
import dev.donutquine.renderer.impl.swf.objects.DisplayObject;
import dev.donutquine.renderer.impl.swf.objects.MovieClip;

public class ChildrenTableContextMenu extends ContextMenu {
    private final Table table;
    private final Editor editor;

    public ChildrenTableContextMenu(Table table, Editor editor) {
        super(table, null);

        this.table = table;
        this.editor = editor;

        this.add("Toggle visibility", event -> this.changeVisibility(child -> !child.isVisible()));
        this.add("Enable", event -> this.changeVisibility(child -> true));
        this.add("Disable", event -> this.changeVisibility(child -> false));
        this.add("Copy child name", event -> this.copyChildName());

        this.popupMenu.addPopupMenuListener(new TablePopupMenuListener(this.popupMenu, table, rowIndex -> setMainComponentsEnabled(rowIndex != -1)));
    }

    private void copyChildName() {
        int selectedRow = this.table.getSelectedRow();

        Object exportName = this.table.getValueAt(selectedRow, this.table.getColumnIndexByName("Name"));
        if (exportName != null) {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                new StringSelection(exportName.toString()),
                null
            );
        }
    }

    // FEATURE IDEA: copy any cell value to clipboard
    private void copyValueToClipboard() {
        MovieClip movieClip = this.getMovieClip();
        if (movieClip == null) return;

        int selectedRow = this.table.getSelectedRow();
        int selectedColumn = this.table.getSelectedColumn();
        String cellName = this.table.getValueAt(selectedRow, selectedColumn).toString();
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
            new StringSelection(cellName),
            null
        );
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
        DisplayObject selectedObject = editor.getSelectedObject();
        if (selectedObject.isMovieClip()) {
            return (MovieClip) selectedObject;
        }

        return null;
    }
}
