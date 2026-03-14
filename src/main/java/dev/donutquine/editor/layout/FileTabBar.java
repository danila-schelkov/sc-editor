package dev.donutquine.editor.layout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import dev.donutquine.editor.assets.AssetFile;
import dev.donutquine.editor.assets.AssetFileManager;

public final class FileTabBar extends JPanel {
    private static final int TAB_BAR_HEIGHT = 28;

    private final AssetFileManager manager;

    public FileTabBar(AssetFileManager manager) {
        this.manager = manager;

        setLayout(new FlowLayout(FlowLayout.LEFT, 4, 2));
        setPreferredSize(new Dimension(0, TAB_BAR_HEIGHT));
        setMinimumSize(new Dimension(0, TAB_BAR_HEIGHT));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, TAB_BAR_HEIGHT));
    }

    public void rebuild() {
        removeAll();

        for (AssetFile<?> file : manager.getFiles()) {
            FileTab<?> tab = new FileTab<>(file, () -> selectFile(file), () -> closeFile(file));
            add(tab);
        }

        updateSelectionHighlight();
        revalidate();
        repaint();
    }

    private void selectFile(AssetFile<?> file) {
        manager.setActiveFile(file);
        updateSelectionHighlight();
    }

    private void closeFile(AssetFile<?> file) {
        manager.closeFile(file);
        rebuild();
    }

    private void updateSelectionHighlight() {
        for (Component c : getComponents()) {
            if (c instanceof FileTab tab) {
                boolean active = tab.getFile() == manager.getActiveFile();
                tab.setBackground(active ? Color.LIGHT_GRAY : null);
                tab.setOpaque(active);
            } else {
                assert false;
            }
        }
    }
}
