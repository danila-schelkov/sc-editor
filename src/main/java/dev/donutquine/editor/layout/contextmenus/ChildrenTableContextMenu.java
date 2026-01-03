package dev.donutquine.editor.layout.contextmenus;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.util.function.Function;

import dev.donutquine.editor.Editor;
import dev.donutquine.editor.ModConfiguration;
import dev.donutquine.editor.ModFunctionality;
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

        if (ModConfiguration.copyAnyCell) {
            this.add(ModFunctionality.COPY_VALUE_TO_CLIPBOARD, event -> ModFunctionality.copyValueToClipboard(editor, table));
            this.addSeparator();
        }
        this.add("Toggle visibility", event -> this.changeVisibility(child -> !child.isVisible()));
        this.add("Enable", event -> this.changeVisibility(child -> true));
        this.add("Disable", event -> this.changeVisibility(child -> false));
        this.add("Copy child name", event -> this.copyChildName());

        this.popupMenu.addPopupMenuListener(new TablePopupMenuListener(this.popupMenu, table, rowIndex -> setMainComponentsEnabled(rowIndex != -1)));
    }

    private void copyChildName() {
        int selectedRow = this.table.getSelectedRow();

        Object childName = this.table.getValueAt(selectedRow, this.table.getColumnIndexByName("Name"));
        if (childName != null) {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                new StringSelection(childName.toString()),
                null
            );
        }
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
