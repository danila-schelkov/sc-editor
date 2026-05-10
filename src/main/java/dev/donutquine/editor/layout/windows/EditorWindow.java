package dev.donutquine.editor.layout.windows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import dev.donutquine.editor.Editor;
import dev.donutquine.editor.assets.exceptions.AssetLoadingException;
import dev.donutquine.editor.layout.EditorDropTarget;
import dev.donutquine.editor.layout.FileTabBar;
import dev.donutquine.editor.layout.GestureUtilities;
import dev.donutquine.editor.layout.LayoutController;
import dev.donutquine.editor.layout.SwingThreadUtils;
import dev.donutquine.editor.layout.components.EditorCanvas;
import dev.donutquine.editor.layout.cursor.Cursors;
import dev.donutquine.editor.layout.dialogs.ExceptionDialog;
import dev.donutquine.editor.layout.menubar.EditorMenuBar;
import dev.donutquine.editor.navigation.NavigableAsset;
import dev.donutquine.editor.navigation.NavigationHistory;
import dev.donutquine.editor.renderer.Camera;
import dev.donutquine.editor.renderer.CameraZoom;
import dev.donutquine.editor.renderer.impl.EditorStage;

public class EditorWindow extends Window {
    private static final Logger LOGGER = LoggerFactory.getLogger(EditorWindow.class);

    public static final String TITLE = "SC Editor";

    private static final Dimension CANVAS_SIZE = new Dimension(680, 640);
    private static final Dimension SIDE_PANEL_SIZE = new Dimension(300, 0);
    private static final Dimension MINIMUM_SIZE = new Dimension(CANVAS_SIZE);

    private final Editor editor;

    private EditorMenuBar menubar;
    private EditorCanvas canvas;
    private JSplitPane canvasSplitPane;
    private JTabbedPane tabbedPane;
    // private StatusBar statusBar;
    private FPSAnimator fpsAnimator;
    private int targetFps;

    private FileTabBar tabBar;
    private final ExecutorService backgroundExecutor = Executors.newSingleThreadExecutor((runnable) -> {
        Thread thread = new Thread(runnable, "asset-loader");
        thread.setDaemon(true);
        return thread;
    });

    private LayoutController<?> layoutController;

    public EditorWindow(Editor editor) {
        this.editor = editor;
    }

    public void initialize() {
        this.frame = new JFrame(EditorWindow.TITLE);

        this.menubar = new EditorMenuBar(this);

        final GLProfile profile = GLProfile.get(GLProfile.GL3);
        GLCapabilities capabilities = new GLCapabilities(profile);

        this.tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
        this.tabbedPane.setVisible(false);

        this.canvas = new EditorCanvas(capabilities, this);

        // Note: Apple's GestureUtilities works only with JComponent because of
        // component client properties.
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

        EditorStage.getInstance().getGizmos().setCursorListener(Cursors.getListener(this.canvas));

        new DropTarget(this.frame, DnDConstants.ACTION_COPY, new EditorDropTarget(this));

        this.canvasSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, canvasPane, null);

        this.fpsAnimator = new FPSAnimator(this.canvas, 60);
        this.targetFps = fpsAnimator.getFPS();

        MINIMUM_SIZE.width += SIDE_PANEL_SIZE.width;
        this.tabbedPane.setMinimumSize(SIDE_PANEL_SIZE);
        this.tabbedPane.setPreferredSize(SIDE_PANEL_SIZE);
        this.canvasSplitPane.setPreferredSize(CANVAS_SIZE);

        // statusBar = new StatusBar();

        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setJMenuBar(this.menubar);

        // Fix of canvas resizing issue. Many thanks to https://jvm-gaming.org/t/using-multiple-canvases/20962/12
        // We need to create a Dimension object for the JPanel minimum size to fix a GLCanvas resize bug.
        // The GLCanvas normally won't receive resize events that shrink a JPanel
        // controlled by a JSplitPane.
        this.canvas.setMinimumSize(new Dimension());
        canvasPane.setMinimumSize(new Dimension());
        this.canvasSplitPane.setMinimumSize(new Dimension());

        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.tabbedPane, canvasSplitPane);

        tabBar = new FileTabBar(this.editor.getAssetFileManager());

        this.frame.getContentPane().add(tabBar, BorderLayout.NORTH);
        this.frame.getContentPane().add(mainSplitPane);
        // this.frame.getContentPane().add(statusBar, BorderLayout.SOUTH);
        this.frame.setMinimumSize(MINIMUM_SIZE);
        this.frame.setSize(this.frame.getContentPane().getPreferredSize());
        this.frame.pack();

        this.editor.getAssetFileManager().registerOpenedEventListener((e) -> {
            SwingThreadUtils.runOnUiThread(() -> {
                if (e.file() instanceof NavigableAsset navigableAsset) {
                    NavigationHistory<?> navigationHistory = navigableAsset.getNavigationHistory();
                    navigationHistory.registerNavigationListener((navigationEvent) -> {
                        SwingThreadUtils.runOnUiThread(() -> this.menubar.getEditMenu().updateNavigationButtons(navigationHistory));
                    });
                }
            });
        });

        this.editor.getAssetFileManager().registerSelectedEventListener((e) -> {
            SwingThreadUtils.runOnUiThread(() -> {
                String title = EditorWindow.TITLE;
                if (e.file() != null) {
                    title += " - " + e.file().getName();
                }
                this.setTitle(title);

                this.menubar.getFileMenu().checkCanSave();

                NavigationHistory<?> navigationHistory = null;
                if (e.file() instanceof NavigableAsset navigableAsset) {
                    navigationHistory = navigableAsset.getNavigationHistory();
                }

                this.menubar.getEditMenu().updateNavigationButtons(navigationHistory);

                LayoutController<?> layoutController = LayoutControllerFactory.createLayoutForFile(this, e.file());
                if (this.layoutController != null) {
                    this.layoutController.finish();
                }

                this.layoutController = layoutController;
                if (this.layoutController != null) {
                    this.layoutController.start();
                }
            });
        });
    }

    public void openFileInBackground(Path path) {
        this.backgroundExecutor.submit(() -> {
            try {
                this.editor.getAssetFileManager().openFile(path);
            } catch (AssetLoadingException e) {
                LOGGER.error("An error occurred while loading the file: {}", path, e);
                SwingThreadUtils.runOnUiThread(() -> this.showErrorDialog(e.getMessage()));
            } catch (Throwable e) {
                SwingThreadUtils.runOnUiThread(() -> ExceptionDialog.showExceptionDialog(Thread.currentThread(), e));
            }
        });
    }

    public void openFile(Path path) {
        if (!SwingThreadUtils.isUiThread()) {
            SwingThreadUtils.runOnUiThread(() -> this.openFile(path));
            return;
        }

        this.openFileInBackground(path);
    }

    public void show() {
        super.show();

        this.canvas.getAnimator().start();
    }

    @Override
    public void close() {
        this.backgroundExecutor.shutdownNow();
        super.close();
    }

    public EditorMenuBar getMenubar() {
        return menubar;
    }

    public GLCanvas getCanvas() {
        return this.canvas;
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public JSplitPane getCanvasSplitPane() {
        return canvasSplitPane;
    }

    public int getTargetFps() {
        return targetFps;
    }

    public void setTargetFps(int fps) {
        if (fps == targetFps)
            return;

        targetFps = fps;

        fpsAnimator.stop();
        fpsAnimator.setFPS(fps);
        fpsAnimator.start();
    }

    public LayoutController<?> getLayoutController() {
        return this.layoutController;
    }

    public Editor getEditor() {
        return this.editor;
    }

}
