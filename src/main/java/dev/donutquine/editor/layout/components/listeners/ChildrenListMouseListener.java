package dev.donutquine.editor.layout.components.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import dev.donutquine.editor.layout.SupercellSWFLayoutController;
import dev.donutquine.editor.renderer.BlendMode;

public class ChildrenListMouseListener extends MouseAdapter {
    private final JTable table;
    private final SupercellSWFLayoutController controller;

    public ChildrenListMouseListener(JTable table, SupercellSWFLayoutController controller) {
        this.table = table;
        this.controller = controller;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int selectedRow = this.table.getSelectedRow();
        if (selectedRow == -1)
            return;

        if (e.getButton() != MouseEvent.BUTTON1) {
            return;
        }

        int column = this.table.columnAtPoint(e.getPoint());
        if (column == 5) {
            boolean[] results = this.controller.changeVisibility(new int[] {selectedRow}, child -> !child.isVisible());
            this.table.setValueAt(results[0], selectedRow, column);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int selectedRow = this.table.getSelectedRow();
        if (selectedRow == -1)
            return;

        if ((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) == 0) {
            return;
        }

        int clickCount = e.getClickCount();
        if (clickCount < 2)
            return;

        int column = this.table.columnAtPoint(e.getPoint());
        if (column == 1) {
            int id = (int) this.table.getValueAt(selectedRow, column);

            this.controller.selectObject(id, null);
        } else if (column == 4) {
            boolean isShiftDown = (e.getModifiersEx() & MouseEvent.SHIFT_DOWN_MASK) != 0;
            BlendMode newBlendMode = BlendMode.values()[(this.controller.getBlendMode(selectedRow).ordinal() + (isShiftDown ? BlendMode.values().length - 1 : 1)) % BlendMode.values().length];
            this.controller.setBlendMode(selectedRow, newBlendMode);
            this.table.setValueAt(newBlendMode, selectedRow, column);
        }
    }
}
