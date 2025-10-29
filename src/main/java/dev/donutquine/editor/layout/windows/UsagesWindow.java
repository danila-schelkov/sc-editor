package dev.donutquine.editor.layout.windows;

import dev.donutquine.editor.Editor;
import dev.donutquine.editor.layout.components.Table;
import dev.donutquine.editor.layout.panels.DisplayObjectListPanel;

import javax.swing.*;
import java.awt.*;

public class UsagesWindow extends Window {
    public static final Dimension CANVAS_SIZE = new Dimension(300, 0);
    public static final Dimension MINIMUM_SIZE = new Dimension(CANVAS_SIZE);

    private final Editor editor;

    private DisplayObjectListPanel displayObjectPanel;

    public UsagesWindow(Editor editor) {
        this.editor = editor;
    }

    public void initialize(String title) {
        this.frame = new JFrame(title);

        this.frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        this.displayObjectPanel = new DisplayObjectListPanel(editor);
        this.frame.getContentPane().add(this.displayObjectPanel);
        this.frame.setMinimumSize(MINIMUM_SIZE);
        this.frame.setSize(this.frame.getContentPane().getPreferredSize());
    }

    public Table getObjectsTable() {
        return this.displayObjectPanel.getTable();
    }
}
