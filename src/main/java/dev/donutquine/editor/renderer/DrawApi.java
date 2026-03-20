package dev.donutquine.editor.renderer;

import dev.donutquine.editor.renderer.texture.RenderableTexture;
import dev.donutquine.math.Point;
import dev.donutquine.math.ReadonlyRect;

import java.awt.Color;

// Note: actually I don't like separation of DrawApi and Renderer, but then I have to rename Renderer to something
public interface DrawApi {
    void drawTexture(RenderableTexture texture, ReadonlyRect rect);

    void drawTextureFlipped(RenderableTexture texture, ReadonlyRect rect);

    void drawRectangle(ReadonlyRect rect, Color color);

    void drawRectangleLines(ReadonlyRect rect, Color color, float thickness);

    void drawLine(Point p1, Point p2, float thickness, Color color);

    void drawLine(float x1, float y1, float x2, float y2, float thickness, Color color);

    void drawDashedLine(Point p1, Point p2, float thickness, float dashLength, Color color);

    void drawDashedLine(float x1, float y1, float x2, float y2, float thickness, float dashLength, Color color);

    void drawDashedLine(float x1, float y1, float x2, float y2, float thickness, float dashLength, float gapLength, Color color);

    float drawDashedLine(float x1, float y1, float x2, float y2, float thickness, float dashLength, float gapLength, float pathStart, Color color);

    void drawPath(Iterable<Point> points, float thickness, Color color);

    void drawDashedPath(Iterable<Point> points, float thickness, float dashLength, Color color);
}
