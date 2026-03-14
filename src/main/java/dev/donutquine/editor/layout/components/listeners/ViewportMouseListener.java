package dev.donutquine.editor.layout.components.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.jetbrains.annotations.Nullable;

import dev.donutquine.editor.Editor;
import dev.donutquine.editor.displayObjects.SpriteSheet;
import dev.donutquine.editor.gizmos.Gizmos;
import dev.donutquine.editor.layout.ScalingUtils;
import dev.donutquine.editor.renderer.Camera;
import dev.donutquine.editor.renderer.impl.EditorStage;
import dev.donutquine.math.Point;
import dev.donutquine.swf.SupercellSWF;
import dev.donutquine.swf.shapes.ShapeDrawBitmapCommand;
import dev.donutquine.swf.shapes.ShapeOriginal;

public class ViewportMouseListener extends MouseAdapter {
    private final Editor editor;

    public ViewportMouseListener(Editor editor) {
        this.editor = editor;
    }

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

        // if (editor.getSelectedObject() instanceof SpriteSheet sheet) {
        //     Set<Integer> intersectedShapes = new HashSet<>();
        //
        //     SupercellSWF swf = editor.getSwf();
        //     for (ShapeOriginal shape : swf.getShapes()) {
        //         for (ShapeDrawBitmapCommand command : shape.getCommands()) {
        //             if (command.getTextureIndex() != sheet.getId())
        //                 continue;
        //
        //             if (isClickInside(command, point.getX(), point.getY(), ShapeDrawBitmapCommand::getU,
        //                     ShapeDrawBitmapCommand::getV)) {
        //                 intersectedShapes.add(shape.getId());
        //             }
        //         }
        //     }
        //
        //     if (e.isPopupTrigger()) {
        //         JPopupMenu popupMenu = new JPopupMenu();
        //         popupMenu.add(new JMenuItem("Show usages"));
        //         popupMenu.show(e.getComponent(), e.getX(), e.getY());
        //         return;
        //     }
        // }

        Gizmos gizmos = EditorStage.getInstance().getGizmos();
        if (e.getButton() == MouseEvent.BUTTON1) {
            gizmos.mouseClicked(point.getX(), point.getY(), e.getClickCount());
        }
    }

    private static void updateMousePosition(MouseEvent e) {
        Point point = getWorldMousePosition(e);
        if (point == null)
            return;

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

    public boolean isClickInside(
            ShapeDrawBitmapCommand command, float clickX, float clickY,
            BiFunction<ShapeDrawBitmapCommand, Integer, Float> getX,
            BiFunction<ShapeDrawBitmapCommand, Integer, Float> getY) {
        int n = command.getVertexCount();
        boolean inside = false;

        for (int i = 0, j = n - 1; i < n; j = i++) {
            float xi = getX.apply(command, i), yi = getY.apply(command, i);
            float xj = getX.apply(command, j), yj = getY.apply(command, j);

            boolean intersect = ((yi > clickY) != (yj > clickY)) &&
                    (clickX < (xj - xi) * (clickY - yi) / (double) (yj - yi) + xi);
            if (intersect) {
                inside = !inside;
            }
        }

        return inside;
    }

}
