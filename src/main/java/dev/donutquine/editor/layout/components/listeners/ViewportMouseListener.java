package dev.donutquine.editor.layout.components.listeners;

import dev.donutquine.editor.layout.ScalingUtils;
import dev.donutquine.editor.renderer.Camera;
import dev.donutquine.editor.renderer.impl.EditorStage;
import dev.donutquine.editor.gizmos.Gizmos;
import dev.donutquine.math.Point;
import org.jetbrains.annotations.Nullable;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ViewportMouseListener extends MouseAdapter {
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
        Gizmos gizmos = EditorStage.getInstance().getGizmos();
        gizmos.setMousePressed(true);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Gizmos gizmos = EditorStage.getInstance().getGizmos();
        gizmos.setMousePressed(false);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point point = getWorldMousePosition(e);
        assert point != null : "Stage is not in a valid state";

        Gizmos gizmos = EditorStage.getInstance().getGizmos();
        gizmos.mouseClicked(point.getX(), point.getY(), e.getClickCount());
    }

    private static void updateMousePosition(MouseEvent e) {
        Point point = getWorldMousePosition(e);
        if (point == null) return;

        Gizmos gizmos = EditorStage.getInstance().getGizmos();
        gizmos.setMousePosition(point.getX(), point.getY());
    }

    private static @Nullable Point getWorldMousePosition(MouseEvent e) {
        float dpiScalingFactor = ScalingUtils.getDpiScalingFactor();
        // Ensure applying component scaling, e.g. macOS scaling
        int x = (int) (e.getX() * dpiScalingFactor * ScalingUtils.getScaleX(e.getComponent()));
        int y = (int) (e.getY() * dpiScalingFactor * ScalingUtils.getScaleY(e.getComponent()));

        Camera camera = EditorStage.getInstance().getCamera();
        if (camera.getViewport() == null) {
            return null;
        }

        float worldX = camera.getWorldX(x);
        float worldY = camera.getWorldY(y);
        return new Point(worldX, worldY);
    }
}
