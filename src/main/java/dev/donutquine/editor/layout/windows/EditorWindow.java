package dev.donutquine.editor.layout.windows;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import dev.donutquine.editor.Editor;
import dev.donutquine.editor.layout.EditorDropTarget;
import dev.donutquine.editor.layout.GestureUtilities;
import dev.donutquine.editor.layout.components.EditorCanvas;
import dev.donutquine.editor.layout.components.Table;
import dev.donutquine.editor.layout.menubar.EditorMenuBar;
import dev.donutquine.editor.layout.panels.DisplayObjectListPanel;
import dev.donutquine.editor.layout.panels.StatusBar;
import dev.donutquine.editor.layout.panels.TexturesPanel;
import dev.donutquine.editor.layout.panels.TimelinePanel;
import dev.donutquine.editor.layout.panels.info.EditorInfoPanel;
import dev.donutquine.editor.layout.panels.info.MovieClipInfoPanel;
import dev.donutquine.editor.layout.panels.info.ShapeInfoPanel;
import dev.donutquine.editor.renderer.Camera;
import dev.donutquine.editor.renderer.CameraZoom;
import dev.donutquine.editor.renderer.impl.EditorStage;
import dev.donutquine.renderer.impl.swf.objects.DisplayObject;
import dev.donutquine.renderer.impl.swf.objects.MovieClip;
import dev.donutquine.renderer.impl.swf.objects.Shape;
import dev.donutquine.swf.movieclips.MovieClipFrame;
import dev.donutquine.swf.shapes.ShapeDrawBitmapCommand;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.util.List;

public class EditorWindow extends Window {
    public static final String TITLE = "SC Editor";

    private static final Dimension CANVAS_SIZE = new Dimension(680, 640);
    private static final Dimension SIDE_PANEL_SIZE = new Dimension(300, 0);
    private static final Dimension MINIMUM_SIZE = new Dimension(CANVAS_SIZE);

    private final Editor editor;

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

    public EditorWindow(Editor editor) {
        this.editor = editor;
    }

    public void initialize(String title) {
        this.frame = new JFrame(title);

        this.menubar = new EditorMenuBar(editor);

        final GLProfile profile = GLProfile.get(GLProfile.GL3);
        GLCapabilities capabilities = new GLCapabilities(profile);

        this.displayObjectPanel = new DisplayObjectListPanel(editor);
        this.texturesPanel = new TexturesPanel(editor);
        this.infoPanel = new EditorInfoPanel();
        this.tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
        this.canvas = new EditorCanvas(capabilities);

        // Note: Apple's GestureUtilities works only with JComponent because of component client properties.
        JPanel canvasPane = new JPanel();
        canvasPane.setLayout(new BorderLayout());
        canvasPane.add(canvas, BorderLayout.CENTER);

        GestureUtilities.addMagnificationListenerTo(canvasPane, event -> {
            EditorStage stage = EditorStage.getInstance();
            Camera camera = stage.getCamera();
            CameraZoom zoom = camera.getZoom();

            float pointSize = zoom.getPointSize() + (float) event.getMagnification() * 2;
            if (pointSize < 0) return;

            zoom.setScaleStep(CameraZoom.estimateCurrentScaleStep(pointSize));
            zoom.setPointSize(pointSize);

            stage.doInRenderThread(stage::updatePMVMatrix);
        });

        new DropTarget(this.frame, DnDConstants.ACTION_COPY, new EditorDropTarget(editor));

        this.timelinePanel = new TimelinePanel();

        this.timelineSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, canvasPane, this.timelinePanel);

        this.fpsAnimator = new FPSAnimator(this.canvas, 60);
        this.targetFps = fpsAnimator.getFPS();

        MINIMUM_SIZE.width += SIDE_PANEL_SIZE.width;
        this.tabbedPane.setMinimumSize(SIDE_PANEL_SIZE);
        this.tabbedPane.setPreferredSize(SIDE_PANEL_SIZE);
        this.timelineSplitPane.setPreferredSize(CANVAS_SIZE);

        this.timelinePanel.setVisible(false);

        this.tabbedPane.add("Objects", this.displayObjectPanel);
        this.tabbedPane.add("Info", this.infoPanel);
        this.tabbedPane.add("Textures", this.texturesPanel);

        statusBar = new StatusBar();

        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setJMenuBar(this.menubar);

        // Fix of canvas resizing issue. Many thanks to https://jvm-gaming.org/t/using-multiple-canvases/20962/12
        // We need to create a Dimension object for the JPanel minimum size to fix a GLCanvas resize bug.
        // The GLCanvas normally won't receive resize events that shrink a JPanel controlled by a JSplitPane.
        this.canvas.setMinimumSize(new Dimension());
        canvasPane.setMinimumSize(new Dimension());
        this.timelineSplitPane.setMinimumSize(new Dimension());

        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.tabbedPane, timelineSplitPane);

        this.frame.getContentPane().add(mainSplitPane);
        this.frame.getContentPane().add(statusBar, BorderLayout.SOUTH);
        this.frame.setMinimumSize(MINIMUM_SIZE);
        this.frame.setSize(this.frame.getContentPane().getPreferredSize());
        this.frame.pack();
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

    private JPanel createInfoPanel(DisplayObject displayObject) {
        if (displayObject.isMovieClip()) {
            MovieClip movieClip = (MovieClip) displayObject;

            MovieClipInfoPanel movieClipInfoPanel = new MovieClipInfoPanel(editor);

            DisplayObject[] timelineChildren = movieClip.getTimelineChildren();
            String[] timelineChildrenNames = movieClip.getTimelineChildrenNames();
            for (int i = 0; i < timelineChildren.length; i++) {
                Object childName = timelineChildrenNames != null && timelineChildrenNames.length > 0 ? timelineChildrenNames[i] : null;
                movieClipInfoPanel.addTimelineChild(i, timelineChildren[i].getId(), timelineChildren[i].getClass().getSimpleName(), childName, true);
            }

            List<MovieClipFrame> frames = movieClip.getFrames();
            for (int i = 0; i < frames.size(); i++) {
                movieClipInfoPanel.addFrame(i, movieClip.getFrameLabel(i));
            }

            movieClipInfoPanel.setTextInfo(
                "Export name: " + movieClip.getExportName(),
                "FPS: " + movieClip.getFps(),
                String.format("Duration: %.2fs", movieClip.getDuration())
            );

            return movieClipInfoPanel;
        } else if (displayObject.isShape()) {
            ShapeInfoPanel shapeInfoPanel = new ShapeInfoPanel(editor);

            Shape shape = (Shape) displayObject;

            for (int i = 0; i < shape.getCommandCount(); i++) {
                ShapeDrawBitmapCommand command = shape.getCommand(i);
                shapeInfoPanel.addCommandInfo(i, command.getTextureIndex(), command.getTag(), true);
            }

            return shapeInfoPanel;
        }

        return null;
    }
}
