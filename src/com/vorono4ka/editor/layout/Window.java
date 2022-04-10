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
    private EditorInfoPanel infoBlock;
    private DisplayObjectListPanel displayObjectPanel;

    public void initialize(String title) {
        this.frame = new JFrame(title);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.displayObjectPanel = new DisplayObjectListPanel();
        this.displayObjectPanel.setPreferredSize(new Dimension(300, 0));
        this.canvas = createCanvas();
        this.infoBlock = createInfoBlock();
        this.infoBlock.setPreferredSize(new Dimension(300, 0));

        this.menubar = new MenuBar(this);
        this.frame.setJMenuBar(this.menubar);

        this.frame.getContentPane().add(this.displayObjectPanel, BorderLayout.WEST);
        this.frame.getContentPane().add(this.canvas);
        this.frame.getContentPane().add(this.infoBlock, BorderLayout.EAST);
        this.frame.setMinimumSize(new Dimension(1000, 640));
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


    public JFrame getFrame() {
        return frame;
    }

    public MenuBar getMenubar() {
        return menubar;
    }

    public GLCanvas getCanvas() {
        return this.canvas;
    }

    public EditorInfoPanel getInfoBlock() {
        return this.infoBlock;
    }

    public Table getObjectsTable() {
        return this.displayObjectPanel.getTable();
    }

    public DisplayObjectListPanel getDisplayObjectPanel() {
        return this.displayObjectPanel;
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
        glCanvas.setSize(1200, 800);

        FPSAnimator animator = new FPSAnimator(glCanvas, 60);
        animator.start();

        return glCanvas;
    }

    private EditorInfoPanel createInfoBlock() {
        return new EditorInfoPanel();
    }
}
