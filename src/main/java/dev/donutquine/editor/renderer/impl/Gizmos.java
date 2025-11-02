package dev.donutquine.editor.renderer.impl;

import dev.donutquine.editor.renderer.Camera;
import dev.donutquine.editor.renderer.DrawApi;
import dev.donutquine.editor.renderer.Renderer;
import dev.donutquine.renderer.impl.swf.objects.Shape;
import dev.donutquine.swf.shapes.ShapeDrawBitmapCommand;

import java.awt.*;

public class Gizmos {
    private final Camera camera;
    private Renderer renderer;
    private DrawApi drawApi;

    public Gizmos(Camera camera) {
        this.camera = camera;
    }

    public void drawShapeWireframe(Shape shape) {
        boolean useStrip = (shape.getRenderConfigBits() & 0x8000) != 0;

        Color wireframeColor = Color.RED;

        float pixelSize = 1 / camera.getZoom().getPointSize();
        float thickness = 4*pixelSize;
        float size = 10*pixelSize;

        this.renderer.beginRendering();
        for (int i = 0; i < shape.getCommandCount(); i++) {
            ShapeDrawBitmapCommand command = shape.getCommand(i);
            if (command.getVertexCount() < 3) continue;

            this.drawApi.drawLine(
                command.getX(0),
                command.getY(0),
                command.getX(1),
                command.getY(1),
                thickness,
                wireframeColor
            );

            for (int j = 2; j < command.getVertexCount(); j++) {
                this.drawApi.drawLine(
                    command.getX(useStrip ? j - 2 : 0),
                    command.getY(useStrip ? j - 2 : 0),
                    command.getX(j),
                    command.getY(j),
                    thickness,
                    wireframeColor
                );
                this.drawApi.drawLine(
                    command.getX(j - 1),
                    command.getY(j - 1),
                    command.getX(j),
                    command.getY(j),
                    thickness,
                    wireframeColor
                );
            }

            // TODO: make the points functional
            // for (int j = 0; j < command.getVertexCount(); j++) {
            //     float x = command.getX(j);
            //     float y = command.getY(j);
            //     float left = x - size/2;
            //     float top = y - size/2;
            //     this.drawApi.drawRectangle(new Rect(left - size*0.2f, top - size*0.2f, left + size*1.2f, top + size*1.2f), Color.BLACK);
            //     this.drawApi.drawRectangle(new Rect(left, top, left + size, top + size), Color.WHITE);
            // }
        }

        this.renderer.endRendering();
    }

    public void setRenderer(Renderer renderer, DrawApi drawApi) {
        this.renderer = renderer;
        this.drawApi = drawApi;
    }
}
