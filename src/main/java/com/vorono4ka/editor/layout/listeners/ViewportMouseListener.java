package com.vorono4ka.editor.layout.listeners;

import com.vorono4ka.editor.renderer.Camera;
import com.vorono4ka.editor.renderer.Stage;
import com.vorono4ka.math.Rect;
import com.vorono4ka.swf.displayobjects.DisplayObject;
import com.vorono4ka.swf.displayobjects.StageSprite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ViewportMouseListener extends MouseAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ViewportMouseListener.class);

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        Stage stage = Stage.getInstance();
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

        LOGGER.info("{} ({}) touched at ({}, {}).", touchedObject.getClass().getSimpleName(), touchedObject.getIndexInParent(), x, y);
    }
}
