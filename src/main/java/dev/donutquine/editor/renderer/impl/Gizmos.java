package dev.donutquine.editor.renderer.impl;

import dev.donutquine.editor.renderer.DrawApi;
import dev.donutquine.editor.renderer.Renderer;
import dev.donutquine.editor.renderer.Stage;
import dev.donutquine.math.Rect;
import dev.donutquine.renderer.impl.swf.objects.DisplayObject;
import dev.donutquine.renderer.impl.swf.objects.Shape;
import dev.donutquine.renderer.impl.swf.objects.StageSprite;
import dev.donutquine.swf.shapes.ShapeDrawBitmapCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class Gizmos {
    private static final Logger LOGGER = LoggerFactory.getLogger(Gizmos.class);

    private final Stage stage;

    private Renderer renderer;
    private DrawApi drawApi;
    private float mouseX, mouseY;
    private boolean mousePressed;
    private int commandIndex;
    private int pointIndex;

    public Gizmos(Stage stage) {
        this.stage = stage;
    }

    public void setRenderer(Renderer renderer, DrawApi drawApi) {
        this.renderer = renderer;
        this.drawApi = drawApi;
    }

    public void setMousePosition(float x, float y) {
        this.mouseX = x;
        this.mouseY = y;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public void mouseClicked(float worldX, float worldY) {
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

        LOGGER.info("{} at {}, {}", touchedObject, worldX, worldY);
    }

    public void render() {
        this.renderer.beginRendering();

        DisplayObject child = stage.getStageSprite().getChild(0);
        if (child.isShape()) {
            this.drawShapeWireframe((Shape) child);
        }

        this.renderer.endRendering();
    }

    public static void drawWireframe(DrawApi drawApi, Shape shape, Color wireframeColor, float thickness) {
        boolean useStrip = (shape.getRenderConfigBits() & 0x8000) != 0;

        for (int i = 0; i < shape.getCommandCount(); i++) {
            ShapeDrawBitmapCommand command = shape.getCommand(i);
            drawCommandWireframe(drawApi, command, wireframeColor, thickness, useStrip);
        }
    }

    public static void drawCommandWireframe(DrawApi drawApi, ShapeDrawBitmapCommand command, Color wireframeColor, float thickness, boolean useStrip) {
        if (command.getVertexCount() < 3) return;

        drawApi.drawLine(command.getX(0), command.getY(0), command.getX(1), command.getY(1), thickness, wireframeColor);

        for (int j = 2; j < command.getVertexCount(); j++) {
            drawApi.drawLine(command.getX(useStrip ? j - 2 : 0), command.getY(useStrip ? j - 2 : 0), command.getX(j), command.getY(j), thickness, wireframeColor);
            drawApi.drawLine(command.getX(j - 1), command.getY(j - 1), command.getX(j), command.getY(j), thickness, wireframeColor);
        }
    }

    private void drawShapeWireframe(Shape shape) {
        Color wireframeColor = Color.RED;

        float pixelSize = 1 / stage.getPixelSize();
        float thickness = 4 * pixelSize;

        drawWireframe(this.drawApi, shape, wireframeColor, thickness);

        if (!mousePressed) {
            commandIndex = -1;
            pointIndex = -1;
        }

        // TODO: make the points functional
        float size = 10 * pixelSize;
        for (int i = 0; i < shape.getCommandCount(); i++) {
            ShapeDrawBitmapCommand command = shape.getCommand(i);
            if (command.getVertexCount() < 3) continue;
            for (int j = 0; j < command.getVertexCount(); j++) {
                float x = command.getX(j);
                float y = command.getY(j);
                float left = x - size / 2;
                float top = y - size / 2;
                Rect bounds = new Rect(left - size * 0.2f, top - size * 0.2f, left + size * 1.2f, top + size * 1.2f);
                this.drawApi.drawRectangle(bounds, Color.BLACK);
                if (bounds.containsPoint(mouseX, mouseY) && commandIndex == -1) {
                    this.drawApi.drawRectangle(new Rect(left, top, left + size, top + size), Color.RED);
                    commandIndex = i;
                    pointIndex = j;
                } else {
                    this.drawApi.drawRectangle(new Rect(left, top, left + size, top + size), Color.WHITE);
                }
            }
        }

        if (pointIndex != -1 && this.mousePressed) {
            ShapeDrawBitmapCommand command = shape.getCommand(commandIndex);
            command.setXY(pointIndex, mouseX, mouseY);
        }

    }
}
