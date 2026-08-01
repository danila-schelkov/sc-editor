package dev.donutquine.editor.gui.layout.contextmenus;

import java.util.function.Function;
import javax.swing.Action;
import javax.swing.JTable;
import dev.donutquine.editor.gui.layout.components.tables.JTablePopupMenuListener;
import dev.donutquine.editor.gui.layout.components.tables.MovieClipChildrenTableModel;
import dev.donutquine.editor.renderer.BlendMode;
import dev.donutquine.renderer.impl.swf.objects.DisplayObject;

public class ChildrenTableContextMenu extends ContextMenu {
    private final JTable table;

    public ChildrenTableContextMenu(JTable table, Action duplicateAction) {
        super(table, null);

        this.table = table;

        // TODO: hide blend modes that are not exportable to an SC file
        //  Maybe leave an option to unhide them back, but then decide how to map those modes when exporting.
        for (BlendMode blendMode : BlendMode.ALL) {
            this.add("Set " + blendMode.toString() + " blend mode", event -> this.setBlendMode(blendMode));
        }

        this.addSeparator();

        this.add("Toggle visibility", event -> this.changeVisibility(child -> !child.isVisible()));
        this.add("Enable", event -> this.changeVisibility(child -> true));
        this.add("Disable", event -> this.changeVisibility(child -> false));
        this.addSeparator();
        this.add(duplicateAction);

        this.popupMenu.addPopupMenuListener(new JTablePopupMenuListener(this.popupMenu, table, rowIndex -> setMainComponentsEnabled(rowIndex != -1)));
    }

    private void changeVisibility(Function<DisplayObject, Boolean> visibilityFunction) {
        MovieClipChildrenTableModel model = (MovieClipChildrenTableModel) this.table.getModel();

        int[] selectedRows = this.table.getSelectedRows();
        for (int childIndex : selectedRows) {
            model.changeVisibility(childIndex, visibilityFunction);
        }
    }

    private void setBlendMode(BlendMode blendMode) {
        MovieClipChildrenTableModel model = (MovieClipChildrenTableModel) this.table.getModel();

        int[] selectedRows = this.table.getSelectedRows();
        for (int childIndex : selectedRows) {
            model.setBlendMode(childIndex, blendMode);
        }
    }
}
