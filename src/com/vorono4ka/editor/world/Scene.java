package com.vorono4ka.editor.world;

import com.vorono4ka.editor.Main;
import com.vorono4ka.editor.world.objects.GameObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class Scene {
    private final List<GameObject> gameObjectList;

    public Scene() {
        this.gameObjectList = new ArrayList<>();
    }

    public void add(GameObject gameObject) {
        this.gameObjectList.add(gameObject);

        JTable table = Main.editor.getWindow().getTable();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(new Object[] {
            this.gameObjectList.size() - 1,
            gameObject.getTypeName(),
            "[" + gameObject.getTypeName() + "]"
        });
    }

    public GameObject get(int index) {
        if (index < 0 || index >= gameObjectList.size()) return null;
        return gameObjectList.get(index);
    }
}
