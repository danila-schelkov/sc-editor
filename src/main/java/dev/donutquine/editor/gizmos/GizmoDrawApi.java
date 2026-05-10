package dev.donutquine.editor.gizmos;

import java.awt.Color;
import dev.donutquine.editor.renderer.DrawApi;
import dev.donutquine.editor.renderer.Stage;
import dev.donutquine.editor.renderer.texture.RenderableTexture;
import dev.donutquine.math.Point;
import dev.donutquine.math.ReadonlyRect;

public class GizmoDrawApi implements DrawApi {
    private final Stage stage;
    private final DrawApi drawApi;

    public GizmoDrawApi(DrawApi drawApi, Stage stage) {
        this.drawApi = drawApi;
        this.stage = stage;
    }

    @Override
    public void drawTexture(RenderableTexture texture, ReadonlyRect rect) {
        this.drawApi.drawTexture(texture, rect);
    }

    @Override
    public void drawTextureFlipped(RenderableTexture texture, ReadonlyRect rect) {
        this.drawApi.drawTextureFlipped(texture, rect);
    }

    @Override
    public void drawRectangle(ReadonlyRect rect, Color color) {
        this.drawApi.drawRectangle(rect, color);
    }

    @Override
    public void drawRectangleLines(ReadonlyRect rect, Color color, float thickness) {
        this.drawApi.drawRectangleLines(rect, color, thickness / stage.getPixelSize());
    }

    @Override
    public void drawLine(Point p1, Point p2, float thickness, Color color) {
        this.drawApi.drawLine(p1, p2, thickness / stage.getPixelSize(), color);
    }

    @Override
    public void drawLine(float x1, float y1, float x2, float y2, float thickness, Color color) {
        this.drawApi.drawLine(x1, y1, x2, y2, thickness / stage.getPixelSize(), color);
    }

    @Override
    public void drawDashedLine(Point p1, Point p2, float thickness, float dashLength, Color color) {
        float pixelSize = stage.getPixelSize();
        this.drawApi.drawDashedLine(p1, p2, thickness / pixelSize, dashLength / pixelSize, color);
    }

    @Override
    public void drawDashedLine(float x1, float y1, float x2, float y2, float thickness, float dashLength, Color color) {
        float pixelSize = stage.getPixelSize();
        this.drawApi.drawDashedLine(x1, y1, x2, y2, thickness / pixelSize, dashLength / pixelSize, color);
    }

    @Override
    public void drawDashedLine(float x1, float y1, float x2, float y2, float thickness, float dashLength, float gapLength, Color color) {
        float pixelSize = stage.getPixelSize();
        this.drawApi.drawDashedLine(x1, y1, x2, y2, thickness / pixelSize, dashLength / pixelSize, gapLength / pixelSize, color);
    }

    @Override
    public float drawDashedLine(float x1, float y1, float x2, float y2, float thickness, float dashLength, float gapLength, float pathStart, Color color) {
        float pixelSize = stage.getPixelSize();
        return this.drawApi.drawDashedLine(x1, y1, x2, y2, thickness / pixelSize, dashLength / pixelSize, gapLength / pixelSize, pathStart, color);
    }

    @Override
    public void drawPath(Iterable<Point> points, float thickness, Color color) {
        this.drawApi.drawPath(points, thickness / stage.getPixelSize(), color);
    }

    @Override
    public void drawDashedPath(Iterable<Point> points, float thickness, float dashLength, Color color) {
        this.drawApi.drawDashedPath(points, thickness / stage.getPixelSize(), dashLength / stage.getPixelSize(), color);
    }
}
