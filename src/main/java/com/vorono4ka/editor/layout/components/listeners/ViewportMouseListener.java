package com.vorono4ka.editor.layout.components.listeners;

import com.vorono4ka.editor.layout.ScalingUtils;
import com.vorono4ka.editor.renderer.Camera;
import com.vorono4ka.editor.renderer.impl.EditorStage;
import com.vorono4ka.math.Rect;
import com.vorono4ka.renderer.impl.swf.objects.DisplayObject;
import com.vorono4ka.renderer.impl.swf.objects.StageSprite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ViewportMouseListener extends MouseAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ViewportMouseListener.class);

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

        if (touchedObject == null) return;

        LOGGER.info("{} ({}) touched at screen ({}, {}), sprite point ({}, {}).", touchedObject.getClass().getSimpleName(), touchedObject.getIndexInParent(), x, y, worldX, worldY);
    }
}
