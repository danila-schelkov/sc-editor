package com.vorono4ka.editor.layout.windows;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import com.vorono4ka.editor.layout.components.EditorCanvas;
import com.vorono4ka.editor.layout.components.Table;
import com.vorono4ka.editor.layout.menubar.EditorMenuBar;
import com.vorono4ka.editor.layout.panels.DisplayObjectListPanel;
import com.vorono4ka.editor.layout.panels.StatusBar;
import com.vorono4ka.editor.layout.panels.TexturesPanel;
import com.vorono4ka.editor.layout.panels.TimelinePanel;
import com.vorono4ka.editor.layout.panels.info.EditorInfoPanel;
import com.vorono4ka.editor.layout.panels.info.MovieClipInfoPanel;
import com.vorono4ka.editor.layout.panels.info.ShapeInfoPanel;
import com.vorono4ka.swf.MovieClipFrame;
import com.vorono4ka.swf.displayObjects.DisplayObject;
import com.vorono4ka.swf.displayObjects.MovieClip;
import com.vorono4ka.swf.displayObjects.Shape;
import com.vorono4ka.swf.displayObjects.ShapeDrawBitmapCommand;

import javax.swing.*;
import java.awt.*;

public class EditorWindow extends Window {
    public static final Dimension CANVAS_SIZE = new Dimension(680, 640);
    public static final Dimension SIDE_PANEL_SIZE = new Dimension(300, 0);
    public static final Dimension MINIMUM_SIZE = new Dimension(CANVAS_SIZE);

    private EditorMenuBar menubar;
    private EditorCanvas canvas;
    private EditorInfoPanel infoPanel;
    private TexturesPanel texturesPanel;
    private DisplayObjectListPanel displayObjectPanel;
    private TimelinePanel timelinePanel;
    private JSplitPane timelineSplitPane;
    private JTabbedPane tabbedPane;
    private StatusBar statusBar;
    private FPSAnimator fpsAnimator;
    private int targetFps;

    private static JPanel createInfoPanel(DisplayObject displayObject) {
        if (displayObject.isMovieClip()) {
            MovieClip movieClip = (MovieClip) displayObject;

            MovieClipInfoPanel movieClipInfoPanel = new MovieClipInfoPanel();

            DisplayObject[] timelineChildren = movieClip.getTimelineChildren();
            String[] timelineChildrenNames = movieClip.getTimelineChildrenNames();
            for (int i = 0; i < timelineChildren.length; i++) {
                movieClipInfoPanel.addTimelineChild(i, timelineChildren[i].getId(), timelineChildren[i].getClass().getSimpleName(), timelineChildrenNames[i], true);
            }

            MovieClipFrame[] frames = movieClip.getFrames();
            for (int i = 0; i < frames.length; i++) {
                movieClipInfoPanel.addFrame(i, movieClip.getFrameLabel(i));
            }

            movieClipInfoPanel.setTextInfo(
                "Export name: " + movieClip.getExportName(),
                "FPS: " + movieClip.getFps(),
                String.format("Duration: %.2fs", movieClip.getDuration())
            );

            return movieClipInfoPanel;
        } else if (displayObject.isShape()) {
            ShapeInfoPanel shapeInfoPanel = new ShapeInfoPanel();

            Shape shape = (Shape) displayObject;

            for (int i = 0; i < shape.getCommandCount(); i++) {
                ShapeDrawBitmapCommand command = shape.getCommand(i);
                shapeInfoPanel.addCommandInfo(i, command.getTexture().getIndex(), command.getTag());
            }

            return shapeInfoPanel;
        }

        return null;
    }

    public void initialize(String title) {
        this.frame = new JFrame(title);

        this.menubar = new EditorMenuBar(this);

        final GLProfile profile = GLProfile.get(GLProfile.GL3);
        GLCapabilities capabilities = new GLCapabilities(profile);

        this.displayObjectPanel = new DisplayObjectListPanel();
        this.texturesPanel = new TexturesPanel();
        this.infoPanel = new EditorInfoPanel();
        this.tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
        this.canvas = new EditorCanvas(capabilities);
        this.timelinePanel = new TimelinePanel();
        this.timelineSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, this.canvas, this.timelinePanel);

        this.fpsAnimator = new FPSAnimator(this.canvas, 60);
        this.targetFps = fpsAnimator.getFPS();

        MINIMUM_SIZE.width += SIDE_PANEL_SIZE.width;
        this.tabbedPane.setPreferredSize(SIDE_PANEL_SIZE);
        this.canvas.setPreferredSize(CANVAS_SIZE);
        this.timelineSplitPane.setPreferredSize(CANVAS_SIZE);

        this.timelinePanel.setVisible(false);

        this.tabbedPane.add("Objects", this.displayObjectPanel);
        this.tabbedPane.add("Info", this.infoPanel);
        this.tabbedPane.add("Textures", this.texturesPanel);

        statusBar = new StatusBar();

        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setJMenuBar(this.menubar);

        this.frame.getContentPane().add(this.tabbedPane, BorderLayout.WEST);
        this.frame.getContentPane().add(this.timelineSplitPane);
        this.frame.getContentPane().add(statusBar, BorderLayout.SOUTH);
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

    public int getTargetFps() {
        return targetFps;
    }

    public void setTargetFps(int fps) {
        if (fps == targetFps) return;

        targetFps = fps;

        fpsAnimator.stop();
        fpsAnimator.setFPS(fps);
        fpsAnimator.start();
    }

    public void updateInfoPanel(DisplayObject displayObject) {
        EditorInfoPanel infoBlock = this.infoPanel;
        infoBlock.setPanel(createInfoPanel(displayObject));

        if (displayObject.isMovieClip()) {
            MovieClipInfoPanel panel = (MovieClipInfoPanel) infoBlock.getPanel();
            int currentFrame = ((MovieClip) displayObject).getCurrentFrame();

            panel.getFramesTable().select(currentFrame);
        }
    }

    public StatusBar getStatusBar() {
        return this.statusBar;
    }
}
