package com.vorono4ka.editor.layout.listeners;

import com.vorono4ka.editor.renderer.Camera;
import com.vorono4ka.editor.renderer.Stage;
import com.vorono4ka.math.Rect;
import com.vorono4ka.swf.displayObjects.DisplayObject;
import com.vorono4ka.swf.displayObjects.StageSprite;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ViewportMouseListener extends MouseAdapter {
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

        System.out.println(touchedObject);
    }
}
