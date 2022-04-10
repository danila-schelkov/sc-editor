package com.vorono4ka.editor.layout.menubar.menus;

import com.vorono4ka.editor.Main;
import com.vorono4ka.editor.renderer.Stage;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ViewMenu extends JMenu {
    public ViewMenu() {
        super("View");

        JMenuItem reset = new JMenuItem("Reset");

        reset.addActionListener(ViewMenu::resetView);

        this.add(reset);
    }

    private static void resetView(ActionEvent e) {
        Stage stage = Stage.INSTANCE;

        stage.setScaleStep(39);
        stage.setScale(1);

        stage.setOffset(0, 0);

        stage.doInRenderThread(stage::updatePMVMatrix);
        Main.editor.updateCanvas();
    }
}
