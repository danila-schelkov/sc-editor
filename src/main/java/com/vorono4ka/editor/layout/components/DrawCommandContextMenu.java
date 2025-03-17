package com.vorono4ka.editor.layout.components;

import com.vorono4ka.editor.Main;
import com.vorono4ka.editor.displayObjects.SpriteSheet;
import com.vorono4ka.editor.renderer.impl.Stage;
import com.vorono4ka.math.Rect;
import com.vorono4ka.renderer.impl.swf.objects.DisplayObject;
import com.vorono4ka.renderer.impl.swf.objects.Shape;
import com.vorono4ka.swf.shapes.ShapeDrawBitmapCommand;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class DrawCommandContextMenu extends ContextMenu {
    private final Table table;

    public DrawCommandContextMenu(Table table) {
        super(table, null);

        this.table = table;

        JMenuItem showOnAtlasButton = this.add("Show on atlas", KeyEvent.VK_A);
        showOnAtlasButton.addActionListener(this::showOnAtlas);

        this.popupMenu.addPopupMenuListener(new TablePopupMenuListener(this.popupMenu, table, this::onRowSelected));
    }

    private void showOnAtlas(ActionEvent actionEvent) {
        DisplayObject selectedObject = Main.editor.getSelectedObject();
        if (selectedObject == null) return;

        int selectedRow = this.table.getSelectedRow();

        int commandIndex = getCommandIndex(selectedRow);
        int textureIndex = getTextureIndex(selectedRow);

        SpriteSheet spriteSheet = Main.editor.getSpriteSheet(textureIndex);

        Shape shape = (Shape) selectedObject;
        ShapeDrawBitmapCommand command = shape.getCommand(commandIndex);

        Rect bounds = new Rect(100000, 100000, -100000, -100000);
        for (int i = 0; i < command.getVertexCount(); i++) {
            bounds.addPoint((command.getU(i) - 0.5f) * spriteSheet.getWidth(), (command.getV(i) - 0.5f) * spriteSheet.getHeight());
        }

        Stage stage = Stage.getInstance();
        stage.getCamera().zoomToFit(bounds);

        stage.doInRenderThread(() -> {
            Main.editor.selectObject(spriteSheet);
            stage.updatePMVMatrix();
        });
    }

    private void onRowSelected(int rowIndex) {
        setMainComponentsEnabled(rowIndex != -1);
    }

    private int getCommandIndex(int selectedRow) {
        return (int) this.table.getValueAt(selectedRow, 0);
    }

    private int getTextureIndex(int selectedRow) {
        return (int) this.table.getValueAt(selectedRow, 1);
    }
}
