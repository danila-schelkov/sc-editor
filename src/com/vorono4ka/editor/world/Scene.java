package com.vorono4ka.editor.world;

import com.vorono4ka.editor.Main;
import com.vorono4ka.editor.world.objects.DisplayObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class Scene {
    private final List<DisplayObject> displayObjectList;

    public Scene() {
        this.displayObjectList = new ArrayList<>();
    }

    public void add(DisplayObject displayObject) {
        this.displayObjectList.add(displayObject);

        JTable table = Main.editor.getWindow().getTable();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(new Object[] {
            displayObjectList.size() - 1,
            displayObject.getTypeName(),
            "[" + displayObject.getTypeName() + "]"
        });
    }

    public DisplayObject get(int index) {
        if (index < 0 || index >= displayObjectList.size()) return null;
        return displayObjectList.get(index);
    }
}
