package dev.donutquine.editor.layout.panels.info;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import dev.donutquine.editor.layout.SupercellSWFLayoutController;
import dev.donutquine.editor.layout.components.tables.Table;
import dev.donutquine.editor.layout.contextmenus.DrawCommandContextMenu;
import dev.donutquine.renderer.impl.swf.objects.Shape;
import dev.donutquine.swf.Tag;
import dev.donutquine.swf.shapes.ShapeDrawBitmapCommand;

public class ShapeInfoPanel extends JPanel {
    private static final Object[] COLUMN_NAMES = {"#", "Texture Id", "Tag", "Visible"};
    private static final Class<?>[] COLUMN_CLASSES = {Integer.class, Integer.class, Tag.class, Boolean.class};

    private final JTable drawCommandsTable;

    public ShapeInfoPanel(SupercellSWFLayoutController layoutController, Shape shape) {
        this.setLayout(new GridLayout(0, 1));

        this.drawCommandsTable = createTable(shape);

        DrawCommandContextMenu drawCommandContextMenu = new DrawCommandContextMenu(this.drawCommandsTable, layoutController, shape);

        this.add(new JScrollPane(this.drawCommandsTable), "Commands");

        this.drawCommandsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() != MouseEvent.BUTTON1) {
                    return;
                }

                int column = drawCommandsTable.columnAtPoint(e.getPoint());
                if (column == 3) {
                    drawCommandContextMenu.changeVisibility(visible -> !visible);
                }
            }
        });
    }

    public Component add(JComponent comp, String title) {
        comp.setBorder(BorderFactory.createTitledBorder(title));
        return super.add(comp);
    }

    private static Table createTable(Shape shape) {
        Object[][] data = new Object[shape.getCommandCount()][];
        for (int i = 0; i < data.length; i++) {
            ShapeDrawBitmapCommand command = shape.getCommand(i);
            data[i] = new Object[] {i, command.getTextureIndex(), command.getTag(), true};
        }

        return new Table(data, COLUMN_NAMES, COLUMN_CLASSES);
    }
}
