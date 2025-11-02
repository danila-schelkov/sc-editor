package dev.donutquine.editor.layout.components.listeners;

import dev.donutquine.editor.layout.ScalingUtils;
import dev.donutquine.editor.renderer.Camera;
import dev.donutquine.editor.renderer.impl.EditorStage;
import dev.donutquine.editor.renderer.impl.Gizmos;
import dev.donutquine.math.Rect;
import dev.donutquine.renderer.impl.swf.objects.DisplayObject;
import dev.donutquine.renderer.impl.swf.objects.StageSprite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ViewportMouseListener extends MouseAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ViewportMouseListener.class);

    @Override
    public void mouseMoved(MouseEvent e) {
        updateMousePosition(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        updateMousePosition(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        EditorStage stage = EditorStage.getInstance();
        Gizmos gizmos = stage.getGizmos();
        gizmos.setMousePressed(true);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        EditorStage stage = EditorStage.getInstance();
        Gizmos gizmos = stage.getGizmos();
        gizmos.setMousePressed(false);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        float dpiScalingFactor = ScalingUtils.getDpiScalingFactor();
        // Ensure applying component scaling, e.g. macOS scaling
        int x = (int) (e.getX() * dpiScalingFactor * ScalingUtils.getScaleX(e.getComponent()));
        int y = (int) (e.getY() * dpiScalingFactor * ScalingUtils.getScaleY(e.getComponent()));

        EditorStage stage = EditorStage.getInstance();
        Camera camera = stage.getCamera();

        float worldX = camera.getWorldX(x);
        float worldY = camera.getWorldY(y);

        StageSprite stageSprite = stage.getStageSprite();

        DisplayObject touchedObject = null;

        int childrenCount = stageSprite.getChildrenCount();
        for (int i = childrenCount - 1; i >= 0; i--) {
            DisplayObject child = stageSprite.getChild(i);
            Rect bounds = stage.getDisplayObjectBounds(child);

            if (bounds.containsPoint(worldX, worldY)) {
                touchedObject = child;
                break;
            }
        }

        Gizmos gizmos = stage.getGizmos();
        gizmos.setMousePosition(worldX, worldY);

        if (touchedObject == null) return;

        LOGGER.info("{} ({}) touched at screen ({}, {}), sprite point ({}, {}).", touchedObject.getClass().getSimpleName(), touchedObject.getIndexInParent(), x, y, worldX, worldY);
    }

    private static void updateMousePosition(MouseEvent e) {
        float dpiScalingFactor = ScalingUtils.getDpiScalingFactor();
        // Ensure applying component scaling, e.g. macOS scaling
        int x = (int) (e.getX() * dpiScalingFactor * ScalingUtils.getScaleX(e.getComponent()));
        int y = (int) (e.getY() * dpiScalingFactor * ScalingUtils.getScaleY(e.getComponent()));

        EditorStage stage = EditorStage.getInstance();
        Camera camera = stage.getCamera();
        if (camera.getViewport() == null) return;

        float worldX = camera.getWorldX(x);
        float worldY = camera.getWorldY(y);

        stage.getGizmos().setMousePosition(worldX, worldY);
    }
}
