package dev.donutquine.editor.gui.layout.components.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import dev.donutquine.editor.gui.layout.SupercellSWFLayoutController;
import dev.donutquine.editor.gui.layout.components.tables.MovieClipChildrenTableModel;
import dev.donutquine.editor.renderer.BlendMode;

public class ChildrenListMouseListener extends MouseAdapter {
    private final JTable table;
    private final SupercellSWFLayoutController controller;

    public ChildrenListMouseListener(JTable table, SupercellSWFLayoutController controller) {
        this.table = table;
        this.controller = controller;
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
        if (column == MovieClipChildrenTableModel.COLUMN_ID_INDEX) {
            int id = (int) this.table.getValueAt(selectedRow, column);

            this.controller.selectObject(id, null);
        } else if (column == MovieClipChildrenTableModel.COLUMN_BLEND_MODE_INDEX) {
            boolean isShiftDown = (e.getModifiersEx() & MouseEvent.SHIFT_DOWN_MASK) != 0;

            BlendMode blendMode = (BlendMode) this.table.getValueAt(selectedRow, column);
            BlendMode newBlendMode = BlendMode.ALL[(blendMode.ordinal() + (isShiftDown ? BlendMode.ALL.length - 1 : 1)) % BlendMode.ALL.length];

            ((MovieClipChildrenTableModel)this.table.getModel()).setBlendMode(selectedRow, newBlendMode);
        }
    }
}
