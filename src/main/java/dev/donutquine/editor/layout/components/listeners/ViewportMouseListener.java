package dev.donutquine.editor.layout.components.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import org.jetbrains.annotations.Nullable;
import dev.donutquine.editor.assets.SupercellSWFAssetFile;
import dev.donutquine.editor.displayObjects.SpriteSheet;
import dev.donutquine.editor.gizmos.Gizmos;
import dev.donutquine.editor.layout.LayoutController;
import dev.donutquine.editor.layout.ScalingUtils;
import dev.donutquine.editor.layout.SupercellSWFLayoutController;
import dev.donutquine.editor.layout.windows.EditorWindow;
import dev.donutquine.editor.layout.windows.UsagesWindow;
import dev.donutquine.editor.renderer.Camera;
import dev.donutquine.editor.renderer.impl.EditorStage;
import dev.donutquine.math.Point;
import dev.donutquine.renderer.impl.swf.objects.DisplayObject;
import dev.donutquine.renderer.impl.swf.objects.StageSprite;
import dev.donutquine.swf.exceptions.UnableToFindObjectException;
import dev.donutquine.swf.shapes.ShapeDrawBitmapCommand;
import dev.donutquine.swf.shapes.ShapeOriginal;
import dev.donutquine.utilities.SpriteSheetHelper;

public class ViewportMouseListener extends MouseAdapter {
    private final EditorWindow window;

    public ViewportMouseListener(EditorWindow window) {
        this.window = window;
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

        Point point = getWorldMousePosition(e);
        assert point != null : "Stage is not in a valid state";
        
        if (e.isPopupTrigger()) {
            // TODO: refactor completely
            List<ShapeDrawBitmapCommand> hoveroverCommands = Collections.emptyList();

            EditorStage stage = EditorStage.getInstance();
            StageSprite stageSprite = stage.getStageSprite();
            for (int i = 0; i < stageSprite.getChildrenCount(); i++) {
                DisplayObject child = stageSprite.getChild(i);
                if (child instanceof SpriteSheet spriteSheet) {
                    hoveroverCommands = SpriteSheetHelper.getHoveroverCommands(spriteSheet, point.getX(), point.getY());
                    break;
                }
            }

            final List<ShapeDrawBitmapCommand> finalHoveroverCommands = hoveroverCommands;

            if (hoveroverCommands.size() > 0) {
                JPopupMenu popupMenu = new JPopupMenu();

                LayoutController<?> layoutController = window.getLayoutController();
                assert layoutController instanceof SupercellSWFLayoutController;

                SupercellSWFLayoutController swfLayoutController = (SupercellSWFLayoutController) layoutController;
                SupercellSWFAssetFile assetFile = swfLayoutController.getAssetFile();

                List<Integer> shapesUsedIn = new ArrayList<>();

                int[] shapeIds = assetFile.asset.getShapeIds();
                for (int shapeId : shapeIds) {
                    try {
                        ShapeOriginal shape = (ShapeOriginal) assetFile.asset.getOriginalDisplayObject(shapeId & 0xFFFF, null);
                        if (shape.getCommands().stream().anyMatch(command -> finalHoveroverCommands.stream().anyMatch(hc -> hc.equals(command)))) {
                            shapesUsedIn.add(shapeId);
                        }
                    } catch (UnableToFindObjectException ex) {
                        continue;  // this is a very illegal state actually
                    }
                }

                if (shapesUsedIn.size() == 1) {
                    JMenuItem goToUsage = new JMenuItem();
                    goToUsage.setAction(new AbstractAction() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            swfLayoutController.selectObject(shapesUsedIn.get(0), null);
                        }
                    });
                    goToUsage.setText("Go to Usage");
                    popupMenu.add(goToUsage);
                } else if (shapesUsedIn.size() > 1) {
                    JMenuItem showUsages = new JMenuItem();
                    showUsages.setAction(new AbstractAction() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            List<Object[]> usagesRows = new ArrayList<>(shapesUsedIn.size());

                            for (int shapeId : shapesUsedIn) {
                                usagesRows.add(new Object[] {shapeId, null, "Shape"});
                            }

                            UsagesWindow usagesWindow = new UsagesWindow("Usages", usagesRows, swfLayoutController);
                            usagesWindow.show();
                        }
                    });
                    showUsages.setText("Show Usages");
                    popupMenu.add(showUsages);
                }

                popupMenu.show(e.getComponent(), e.getX(), e.getY());
                return;
            }
        }
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
}
