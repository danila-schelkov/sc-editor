package com.vorono4ka.editor.layout.menubar.menus;

import com.vorono4ka.editor.layout.panels.TimelinePanel;
import com.vorono4ka.editor.layout.windows.EditorWindow;
import com.vorono4ka.editor.renderer.Camera;
import com.vorono4ka.editor.renderer.impl.EditorStage;
import com.vorono4ka.math.Rect;
import com.vorono4ka.renderer.impl.swf.objects.StageSprite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ViewMenu extends JMenu {
    private final EditorWindow editorWindow;

    private final JCheckBoxMenuItem timelineToggle;

    public ViewMenu(EditorWindow editorWindow) {
        super("View");

        this.editorWindow = editorWindow;

        setMnemonic(KeyEvent.VK_V);

        this.timelineToggle = new JCheckBoxMenuItem("Timeline");

        initializeZoomMenu();
        initializeToolsMenu();

        JMenuItem reset = new JMenuItem("Reset", KeyEvent.VK_R);
        reset.addActionListener(ViewMenu::resetView);
        add(reset);
    }

    private static void updateCamera(Consumer<Camera> function) {
        updateCamera((stage, camera) -> function.accept(camera));
    }

    private static void updateCamera(BiConsumer<EditorStage, Camera> function) {
        EditorStage stage = EditorStage.getInstance();
        Camera camera = stage.getCamera();

        function.accept(stage, camera);

        stage.doInRenderThread(stage::updatePMVMatrix);
    }

    private static void resetView(ActionEvent e) {
        updateCamera(Camera::reset);
    }

    private static void zoomIn(ActionEvent actionEvent) {
        updateCamera(camera -> camera.getZoom().zoomIn(1));
    }

    private static void zoomOut(ActionEvent actionEvent) {
        updateCamera(camera -> camera.getZoom().zoomOut(1));
    }

    private static void zoomToFit(ActionEvent actionEvent) {
        updateCamera((stage, camera) -> {
            StageSprite stageSprite = stage.getStageSprite();
            if (stageSprite.getChildrenCount() == 0) return;

            Rect bounds = stage.calculateBoundsForAllFrames(stageSprite.getChild(0));

            camera.zoomToFit(bounds);
        });
    }

    private void initializeZoomMenu() {
        JMenuItem zoom = new JMenu("Zoom");
        zoom.setMnemonic(KeyEvent.VK_Z);

        JMenuItem zoomIn = new JMenuItem("Zoom in", KeyEvent.VK_I);
        zoomIn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ADD, InputEvent.CTRL_DOWN_MASK));
        zoomIn.addActionListener(ViewMenu::zoomIn);
        zoom.add(zoomIn);

        JMenuItem zoomOut = new JMenuItem("Zoom out", KeyEvent.VK_O);
        zoomOut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SUBTRACT, InputEvent.CTRL_DOWN_MASK));
        zoomOut.addActionListener(ViewMenu::zoomOut);
        zoom.add(zoomOut);

        JMenuItem zoomToFit = new JMenuItem("Zoom to fit", KeyEvent.VK_F);
        zoomToFit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, InputEvent.CTRL_DOWN_MASK));
        zoomToFit.addActionListener(ViewMenu::zoomToFit);
        zoom.add(zoomToFit);

        JMenuItem reset = new JMenuItem("Reset zoom", KeyEvent.VK_R);
        reset.addActionListener(ViewMenu::resetZoom);
        zoom.add(reset);
        this.add(zoom);
    }

    private static void resetZoom(ActionEvent actionEvent) {
        updateCamera(camera -> camera.getZoom().reset());
    }

    private void initializeToolsMenu() {
        this.timelineToggle.addActionListener(this::toggleTimeline);

        JMenuItem tools = new JMenu("Tools");
        tools.add(this.timelineToggle);
        this.add(tools);
    }

    private void toggleTimeline(ActionEvent actionEvent) {
        boolean visible = this.timelineToggle.getState();

        JFrame frame = editorWindow.getFrame();
        Dimension minimumSize = frame.getMinimumSize();
        frame.setMinimumSize(minimumSize);

        TimelinePanel timelinePanel = editorWindow.getTimelinePanel();
        timelinePanel.setVisible(visible);

        JSplitPane timelineSplitPane = editorWindow.getTimelineSplitPane();
        timelineSplitPane.setDividerLocation(0.7f);
    }
}
