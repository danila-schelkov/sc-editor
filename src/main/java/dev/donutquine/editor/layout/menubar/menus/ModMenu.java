package dev.donutquine.editor.layout.menubar.menus;

import javax.swing.*;

import java.awt.event.KeyEvent;
import java.nio.file.Path;

import dev.donutquine.editor.Editor;
import dev.donutquine.editor.ModConfiguration;
import dev.donutquine.editor.ModFunctionality;
import dev.donutquine.editor.layout.contextmenus.DisplayObjectContextMenu;
import dev.donutquine.editor.layout.contextmenus.FrameTableContextMenu;
import dev.donutquine.editor.layout.contextmenus.TextureTableContextMenu;
import dev.donutquine.editor.layout.panels.info.MovieClipInfoPanel;
import dev.donutquine.editor.renderer.Camera;
import dev.donutquine.editor.renderer.CameraZoom;
import dev.donutquine.editor.renderer.impl.EditorStage;

public class ModMenu extends JMenu {
    private final JCheckBoxMenuItem copyCell;

    public ModMenu(Editor editor) {
        super("Mod Menu");

        this.setMnemonic(KeyEvent.VK_H);
        
        this.copyCell = new JCheckBoxMenuItem("Enable copy any cell");
        this.copyCell.addActionListener((event) -> {
            ModConfiguration.copyAnyCell = this.copyCell.isSelected();
            this.copyCellClicked(editor);
        });

        JCheckBoxMenuItem zoomHack = new JCheckBoxMenuItem("Enable infinite zoom");
        zoomHack.addActionListener((event) -> {
            ModConfiguration.infiniteZoom = zoomHack.isSelected();
            EditorStage stage = EditorStage.getInstance();
            stage.updatePMVMatrix();
        });

        this.add(this.copyCell);
        this.add(zoomHack);
        this.addSeparator();
        JMenuItem utils = new JMenu("Utils");
        JMenuItem reloadFile = new JMenuItem("Reload File", null);
        reloadFile.addActionListener((event) -> {
            try {
                String path = editor.getPath();
                editor.closeFile();
                editor.openFile(Path.of(path));
            } catch (Exception e) {
                ;
            }
        });
        utils.add(reloadFile);
        this.add(utils);
        this.addSeparator();

        JFrame frame = editor.getWindow().getFrame();

        JMenuItem aboutMe = new JMenuItem("About Mod", null);
        aboutMe.addActionListener((e) -> {
            ModFunctionality.showAboutModDialog(frame);
        });
        this.add(aboutMe);

    }

    private void copyCellClicked(Editor editor) {
        // Refresh Tables
        try { 
            editor.selectObject(editor.getSelectedObject());
        } catch (Exception ignored) {} 
        try {
            new DisplayObjectContextMenu(editor.getWindow().getDisplayObjectPanel().getTable(), editor);
        } catch (Exception e) {}
        try {
            new FrameTableContextMenu(((MovieClipInfoPanel) editor.getWindow().getInfoPanel().getPanel()).getFramesTable(), editor);
        } catch (Exception e) {}
        try {
            new TextureTableContextMenu(editor.getWindow().getTexturesTable(), editor);
        } catch (Exception e) {}
    }
}
