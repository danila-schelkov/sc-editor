package dev.donutquine.editor.renderer.impl;

import dev.donutquine.editor.renderer.Camera;
import dev.donutquine.editor.renderer.DrawApi;
import dev.donutquine.editor.renderer.Renderer;
import dev.donutquine.math.Rect;
import dev.donutquine.renderer.impl.swf.objects.Shape;
import dev.donutquine.swf.shapes.ShapeDrawBitmapCommand;

import java.awt.*;

public class Gizmos {
    private final Camera camera;

    private Renderer renderer;
    private DrawApi drawApi;
    private float mouseX, mouseY;
    private boolean mousePressed;
    private int commandIndex;
    private int pointIndex;

    public Gizmos(Camera camera) {
        this.camera = camera;
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

    public void drawShapeWireframe(Shape shape) {
        Color wireframeColor = Color.RED;

        float pixelSize = 1 / camera.getZoom().getPointSize();
        float thickness = 4 * pixelSize;

        this.renderer.beginRendering();
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

        this.renderer.endRendering();
    }

    public static void drawWireframe(DrawApi drawApi, Shape shape, Color wireframeColor, float thickness) {
        boolean useStrip = (shape.getRenderConfigBits() & 0x8000) != 0;

        for (int i = 0; i < shape.getCommandCount(); i++) {
            ShapeDrawBitmapCommand command = shape.getCommand(i);
            if (command.getVertexCount() < 3) continue;

            drawApi.drawLine(command.getX(0), command.getY(0), command.getX(1), command.getY(1), thickness, wireframeColor);

            for (int j = 2; j < command.getVertexCount(); j++) {
                drawApi.drawLine(command.getX(useStrip ? j - 2 : 0), command.getY(useStrip ? j - 2 : 0), command.getX(j), command.getY(j), thickness, wireframeColor);
                drawApi.drawLine(command.getX(j - 1), command.getY(j - 1), command.getX(j), command.getY(j), thickness, wireframeColor);
            }
        }
    }
}
