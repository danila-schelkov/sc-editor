package com.vorono4ka.editor.layout;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import com.vorono4ka.editor.layout.components.Table;
import com.vorono4ka.editor.layout.menubar.EditorMenuBar;
import com.vorono4ka.editor.layout.panels.DisplayObjectListPanel;
import com.vorono4ka.editor.layout.panels.TexturesPanel;
import com.vorono4ka.editor.layout.panels.TimelinePanel;
import com.vorono4ka.editor.layout.panels.info.EditorInfoPanel;

import javax.swing.*;
import java.awt.*;

public class EditorWindow extends Window {
    public static final Dimension CANVAS_SIZE = new Dimension(680, 640);
    public static final Dimension SIDE_PANEL_SIZE = new Dimension(300, 0);
    public static final Dimension MINIMUM_SIZE = new Dimension(CANVAS_SIZE);

    private EditorMenuBar menubar;
    private GLCanvas canvas;
    private EditorInfoPanel infoPanel;
    private TexturesPanel texturesPanel;
    private DisplayObjectListPanel displayObjectPanel;
    private TimelinePanel timelinePanel;
    private JSplitPane timelineSplitPane;
    private JTabbedPane tabbedPane;

    public void initialize(String title) {
        this.frame = new JFrame(title);

        this.menubar = new EditorMenuBar(this);

        final GLProfile profile = GLProfile.get(GLProfile.GL3);
        GLCapabilities capabilities = new GLCapabilities(profile);

        this.displayObjectPanel = new DisplayObjectListPanel();
        this.texturesPanel = new TexturesPanel();
        this.infoPanel = new EditorInfoPanel();
        this.tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
        this.canvas = new Canvas(capabilities);
        this.timelinePanel = new TimelinePanel();
        this.timelineSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, this.canvas, this.timelinePanel);

        new FPSAnimator(this.canvas, 30);

        MINIMUM_SIZE.width += SIDE_PANEL_SIZE.width;
        this.tabbedPane.setPreferredSize(SIDE_PANEL_SIZE);
        this.canvas.setPreferredSize(CANVAS_SIZE);
        this.timelineSplitPane.setPreferredSize(CANVAS_SIZE);

        this.timelinePanel.setVisible(false);

        this.tabbedPane.add("Objects", this.displayObjectPanel);
        this.tabbedPane.add("Info", this.infoPanel);
        this.tabbedPane.add("Textures", this.texturesPanel);

        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setJMenuBar(this.menubar);

        this.frame.getContentPane().add(this.tabbedPane, BorderLayout.WEST);
        this.frame.getContentPane().add(this.timelineSplitPane);
        this.frame.setMinimumSize(MINIMUM_SIZE);
        this.frame.setSize(this.frame.getContentPane().getPreferredSize());
    }

    public void show() {
        super.show();

        this.canvas.getAnimator().start();
    }

    public EditorMenuBar getMenubar() {
        return menubar;
    }

    public GLCanvas getCanvas() {
        return this.canvas;
    }

    public TimelinePanel getTimelinePanel() {
        return this.timelinePanel;
    }

    public JSplitPane getTimelineSplitPane() {
        return timelineSplitPane;
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public EditorInfoPanel getInfoPanel() {
        return this.infoPanel;
    }

    public Table getTexturesTable() {
        return texturesPanel.getTable();
    }

    public Table getObjectsTable() {
        return this.displayObjectPanel.getTable();
    }

    public DisplayObjectListPanel getDisplayObjectPanel() {
        return this.displayObjectPanel;
    }
}
