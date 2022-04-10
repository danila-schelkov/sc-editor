package com.vorono4ka.editor.layout;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import com.vorono4ka.editor.layout.components.Table;
import com.vorono4ka.editor.layout.menubar.MenuBar;
import com.vorono4ka.editor.layout.panels.DisplayObjectListPanel;
import com.vorono4ka.editor.layout.panels.info.EditorInfoPanel;
import com.vorono4ka.editor.renderer.listeners.EventListener;
import com.vorono4ka.editor.renderer.listeners.MouseListener;
import com.vorono4ka.editor.renderer.listeners.MouseWheelListener;

import javax.swing.*;
import java.awt.*;

public class Window {
    private JFrame frame;
    private MenuBar menubar;
    private GLCanvas canvas;
    private EditorInfoPanel infoPanel;
    private DisplayObjectListPanel displayObjectPanel;
    private JPanel timelinePanel;
    private JSplitPane timelineSplitPane;
    private JSplitPane infoSplitPane;

    public void initialize(String title) {
        this.frame = new JFrame(title);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.displayObjectPanel = new DisplayObjectListPanel();
        this.displayObjectPanel.setPreferredSize(new Dimension(300, 0));
        this.canvas = createCanvas();
        this.infoPanel = new EditorInfoPanel();
        this.infoPanel.setMinimumSize(new Dimension(300, 0));

        this.timelinePanel = new JPanel();
        this.timelinePanel.setBorder(BorderFactory.createTitledBorder("Timeline"));
        this.timelinePanel.setMinimumSize(new Dimension(0, 300));
        this.timelinePanel.setVisible(false);

        this.timelineSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, this.canvas, this.timelinePanel);
        this.timelineSplitPane.setMinimumSize(this.canvas.getMinimumSize());
        this.infoSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.timelineSplitPane, this.infoPanel);
        this.infoSplitPane.setDividerLocation(this.canvas.getMinimumSize().width);

        this.menubar = new MenuBar(this);
        this.frame.setJMenuBar(this.menubar);

        this.frame.getContentPane().add(this.displayObjectPanel, BorderLayout.WEST);
        this.frame.getContentPane().add(this.infoSplitPane);
        this.frame.setMinimumSize(new Dimension(1300, 640));
        this.frame.setSize(this.frame.getContentPane().getPreferredSize());
    }

    public void show() {
        this.frame.pack();
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
    }

    public void setTitle(String title) {
        this.frame.setTitle(title);
    }


    private GLCanvas createCanvas() {
        final GLProfile profile = GLProfile.get(GLProfile.GL3);
        GLCapabilities capabilities = new GLCapabilities(profile);

        GLCanvas glCanvas = new GLCanvas(capabilities);
        glCanvas.addGLEventListener(new EventListener());
        MouseListener mouseListener = new MouseListener();
        glCanvas.addMouseListener(mouseListener);
        glCanvas.addMouseMotionListener(mouseListener);
        glCanvas.addMouseWheelListener(new MouseWheelListener());
        glCanvas.setMinimumSize(new Dimension(684, 595));

        FPSAnimator animator = new FPSAnimator(glCanvas, 60);
        animator.start();

        return glCanvas;
    }


    public JFrame getFrame() {
        return frame;
    }

    public MenuBar getMenubar() {
        return menubar;
    }

    public GLCanvas getCanvas() {
        return this.canvas;
    }

    public JPanel getTimelinePanel() {
        return this.timelinePanel;
    }

    public JSplitPane getTimelineSplitPane() {
        return timelineSplitPane;
    }

    public JSplitPane getInfoSplitPane() {
        return infoSplitPane;
    }

    public EditorInfoPanel getInfoPanel() {
        return this.infoPanel;
    }

    public Table getObjectsTable() {
        return this.displayObjectPanel.getTable();
    }

    public DisplayObjectListPanel getDisplayObjectPanel() {
        return this.displayObjectPanel;
    }
}
