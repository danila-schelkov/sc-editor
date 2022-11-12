package com.vorono4ka.editor.layout.menubar.menus;

import com.vorono4ka.editor.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class OptionsMenu extends JMenu {
    private final JCheckBoxMenuItem renderPolygonsCheckBox;

    public OptionsMenu() {
        super("Options");

        setMnemonic(KeyEvent.VK_O);

        this.renderPolygonsCheckBox = new JCheckBoxMenuItem("Render polygons");
        this.renderPolygonsCheckBox.setMnemonic(KeyEvent.VK_P);
        this.renderPolygonsCheckBox.addActionListener(this::togglePolygonRendering);

        this.add(this.renderPolygonsCheckBox);
    }

    private void togglePolygonRendering(ActionEvent event) {
        Main.editor.setShouldDisplayPolygons(this.renderPolygonsCheckBox.getState());
        Main.editor.updateCanvas();
    }
}
