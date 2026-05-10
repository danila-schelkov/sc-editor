package dev.donutquine.editor.layout.contextmenus;

import java.util.function.Function;
import javax.swing.JTable;
import dev.donutquine.editor.layout.SupercellSWFLayoutController;
import dev.donutquine.editor.layout.components.tables.JTablePopupMenuListener;
import dev.donutquine.editor.renderer.BlendMode;
import dev.donutquine.renderer.impl.swf.objects.DisplayObject;

public class ChildrenTableContextMenu extends ContextMenu {
    private final JTable table;
    private final SupercellSWFLayoutController swfLayoutController;

    public ChildrenTableContextMenu(JTable table, SupercellSWFLayoutController swfLayoutController) {
        super(table, null);

        this.table = table;
        this.swfLayoutController = swfLayoutController;

        // TODO: hide blend modes that are not exportable to an SC file
        //  Maybe leave an option to unhide them back, but then decide how to map those modes when exporting.
        for (BlendMode blendMode : BlendMode.values()) {
            this.add("Set " + blendMode.toString() + " blend mode", event -> this.setBlendMode(blendMode));
        }

        this.addSeparator();

        this.add("Toggle visibility", event -> this.changeVisibility(child -> !child.isVisible()));
        this.add("Enable", event -> this.changeVisibility(child -> true));
        this.add("Disable", event -> this.changeVisibility(child -> false));

        this.popupMenu.addPopupMenuListener(new JTablePopupMenuListener(this.popupMenu, table, rowIndex -> setMainComponentsEnabled(rowIndex != -1)));
    }

    public void changeVisibility(Function<DisplayObject, Boolean> visibilityFunction) {
        int[] selectedRows = this.table.getSelectedRows();
        boolean[] results = this.swfLayoutController.changeVisibility(selectedRows, visibilityFunction);

        for (int i = 0; i < results.length; i++) {
            int childIndex = selectedRows[i];
            this.table.setValueAt(results[i], childIndex, 5);
        }
    }

    public void setBlendMode(BlendMode blendMode) {
        int[] selectedRows = this.table.getSelectedRows();
        for (int childIndex : selectedRows) {
            this.swfLayoutController.setBlendMode(childIndex, blendMode);
            this.table.setValueAt(blendMode, childIndex, 4);
        }
    }
}
