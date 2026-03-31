package dev.donutquine.editor.renderer;

import dev.donutquine.editor.renderer.gl.GLConstants;
import dev.donutquine.editor.renderer.shader.Attribute;
import dev.donutquine.editor.renderer.shader.Shader;
import dev.donutquine.editor.renderer.texture.RenderableTexture;
import dev.donutquine.math.Point;
import dev.donutquine.math.ReadonlyRect;
import dev.donutquine.math.Rect;
import dev.donutquine.resources.AssetManager;

import java.awt.*;
import java.nio.FloatBuffer;
import java.util.Iterator;

public class BasicDrawApi implements DrawApi {
    private static final int[] RECT_INDICES = {0, 1, 2, 1, 2, 3};  // FAN order

    private final Renderer renderer;
    private final Shader textureShader;
    private final Shader colorShader;

    public BasicDrawApi(Renderer renderer, AssetManager assetManager) {
        this.renderer = renderer;

        this.textureShader = assetManager.getShader(
            "texture.vertex.glsl",
            "texture.fragment.glsl",
            new Attribute(0, 2, Float.BYTES, GLConstants.GL_FLOAT),
            new Attribute(1, 2, Float.BYTES, GLConstants.GL_FLOAT)
        );

        this.colorShader = assetManager.getShader(
            "color.vertex.glsl",
            "color.fragment.glsl",
            new Attribute(0, 2, Float.BYTES, GLConstants.GL_FLOAT),
            new Attribute(1, 4, Float.BYTES, GLConstants.GL_FLOAT)
        );
    }

    @Override
    public void drawTexture(RenderableTexture texture, ReadonlyRect rect) {
        if (this.renderer.startShape(textureShader, rect, texture, 0, null)) {
            this.renderer.addTriangles(2, RECT_INDICES);

            this.renderer.addVertex(rect.getLeft(), rect.getTop(), 0, 0);
            this.renderer.addVertex(rect.getLeft(), rect.getBottom(), 0, 1);
            this.renderer.addVertex(rect.getRight(), rect.getTop(), 1, 0);
            this.renderer.addVertex(rect.getRight(), rect.getBottom(), 1, 1);
        }
    }

    @Override
    public void drawTextureFlipped(RenderableTexture texture, ReadonlyRect rect) {
        if (this.renderer.startShape(textureShader, rect, texture, 0, null)) {
            this.renderer.addTriangles(2, RECT_INDICES);

            this.renderer.addVertex(rect.getLeft(), rect.getTop(), 0, 1);
            this.renderer.addVertex(rect.getLeft(), rect.getBottom(), 0, 0);
            this.renderer.addVertex(rect.getRight(), rect.getTop(), 1, 1);
            this.renderer.addVertex(rect.getRight(), rect.getBottom(), 1, 0);
        }
    }

    @Override
    public void drawRectangle(ReadonlyRect rect, Color color) {
        if (this.renderer.startShape(colorShader, rect, null, 0, null)) {
            this.renderer.addTriangles(2, RECT_INDICES);

            float[] rgba = new float[4];
            rgba[3] = 1;
            color.getColorComponents(rgba);

            this.renderer.addVertex(rect.getLeft(), rect.getTop(), rgba[0], rgba[1], rgba[2], rgba[3]);
            this.renderer.addVertex(rect.getLeft(), rect.getBottom(), rgba[0], rgba[1], rgba[2], rgba[3]);
            this.renderer.addVertex(rect.getRight(), rect.getTop(), rgba[0], rgba[1], rgba[2], rgba[3]);
            this.renderer.addVertex(rect.getRight(), rect.getBottom(), rgba[0], rgba[1], rgba[2], rgba[3]);
        }
    }

    @Override
    public void drawRectangleLines(ReadonlyRect rect, Color color, float thickness) {
        this.drawLine(rect.getLeft(), rect.getTop(), rect.getRight(), rect.getTop(), thickness, color);
        this.drawLine(rect.getLeft(), rect.getBottom(), rect.getLeft(), rect.getTop(), thickness, color);
        this.drawLine(rect.getLeft(), rect.getBottom(), rect.getRight(), rect.getBottom(), thickness, color);
        this.drawLine(rect.getRight(), rect.getTop(), rect.getRight(), rect.getBottom(), thickness, color);
    }

    @Override
    public void drawLine(Point p1, Point p2, float thickness, Color color) {
        this.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY(), thickness, color);
    }

    @Override
    public void drawLine(float x1, float y1, float x2, float y2, float thickness, Color color) {
        Rect bounds = new Rect(x1, y1, x2, y2);
        if (this.renderer.startShape(colorShader, bounds, null, 0, null)) {
            this.renderer.addTriangles(2, RECT_INDICES);

            float[] rgba = new float[4];
            rgba[3] = 1;
            color.getColorComponents(rgba);

            float dx = x2 - x1;
            float dy = y2 - y1;

            double length = Math.sqrt(dx * dx + dy * dy);
            if (length <= 1e-3) return;

            double halfThickness = thickness / 2.0;

            double dirX = dx / length;
            double dirY = dy / length;

            double scale = halfThickness / length;
            float radiusX = (float) (-dy * scale);
            float radiusY = (float) (dx * scale);

            double extendX = dirX * halfThickness;
            double extendY = dirY * halfThickness;

            float x1e = (float) (x1 - extendX);
            float y1e = (float) (y1 - extendY);
            float x2e = (float) (x2 + extendX);
            float y2e = (float) (y2 + extendY);

            this.renderer.addVertex(x1e - radiusX, y1e - radiusY, rgba[0], rgba[1], rgba[2], rgba[3]);
            this.renderer.addVertex(x1e + radiusX, y1e + radiusY, rgba[0], rgba[1], rgba[2], rgba[3]);
            this.renderer.addVertex(x2e - radiusX, y2e - radiusY, rgba[0], rgba[1], rgba[2], rgba[3]);
            this.renderer.addVertex(x2e + radiusX, y2e + radiusY, rgba[0], rgba[1], rgba[2], rgba[3]);
        }
    }

    @Override
    public void drawDashedLine(Point p1, Point p2, float thickness, float dashLength, Color color) {
        this.drawDashedLine(p1.getX(), p1.getY(), p2.getX(), p2.getY(), thickness, dashLength, color);
    }

    @Override
    public void drawDashedLine(float x1, float y1, float x2, float y2, float thickness, float dashLength, Color color) {
        assert dashLength > 0;

        float dx = x2 - x1;
        float dy = y2 - y1;

        double length = Math.sqrt(dx * dx + dy * dy);

        float gapLength = calculateGapLength(dashLength, length);

        this.drawDashedLine(x1, y1, x2, y2, thickness, dashLength, gapLength, color);
    }

    @Override
    public void drawDashedLine(float x1, float y1, float x2, float y2, float thickness, float dashLength, float gapLength, Color color) {
        drawDashedLine(x1, y1, x2, y2, thickness, dashLength, gapLength, 0, color);
    }

    @Override
    public float drawDashedLine(float x1, float y1, float x2, float y2, float thickness, float dashLength, float gapLength, float pathStart, Color color) {
        float dx = x2 - x1;
        float dy = y2 - y1;

        double length = Math.sqrt(dx * dx + dy * dy);
        if (length <= 1e-3) return 0;

        double dirX = dx / length;
        double dirY = dy / length;

        double segmentLength = dashLength + gapLength;
        double path = pathStart;
        for (; path < length; path += segmentLength) {
            double start = Math.max(0, path);
            double end = Math.min(path + dashLength, length);

            float sx = (float) (x1 + dirX * start);
            float sy = (float) (y1 + dirY * start);

            float ex = (float) (x1 + dirX * end);
            float ey = (float) (y1 + dirY * end);

            this.drawLine(sx, sy, ex, ey, thickness, color);
        }

        double leftover = path - length;
        if (leftover < gapLength) {
            return (float) leftover;
        }

        return (float) (leftover - segmentLength);
    }

    @Override
    public void drawPath(Iterable<Point> points, float thickness, Color color) {
        Iterator<Point> iterator = points.iterator();
        assert iterator.hasNext();

        Point lastPoint = null;
        Point point = iterator.next();

        while (iterator.hasNext()) {
            lastPoint = point;
            point = iterator.next();
            this.drawLine(lastPoint, point, thickness, color);
        }
    }
    
    @Override
    public void drawDashedPath(Iterable<Point> points, float thickness, float dashLength, Color color) {
        Iterator<Point> iterator = points.iterator();
        assert iterator.hasNext();

        Point lastPoint = null;
        Point point = iterator.next();

        double totalLength = 0;
        while (iterator.hasNext()) {
            lastPoint = point;
            point = iterator.next();
            
            float dx = point.getX() - lastPoint.getX();
            float dy = point.getY() - lastPoint.getY();
            double length = Math.sqrt(dx * dx + dy * dy);
            totalLength += length;
        }

        iterator = points.iterator();

        lastPoint = null;
        point = iterator.next();

        float gapLength = calculateGapLength(dashLength, totalLength);

        float leftover = 0;
        while (iterator.hasNext()) {
            lastPoint = point;
            point = iterator.next();
            leftover = this.drawDashedLine(lastPoint.getX(), lastPoint.getY(), point.getX(), point.getY(), thickness, dashLength, gapLength, leftover, color);
        }
    }

    public void setPMVMatrix(FloatBuffer matrixBuffer) {
        this.colorShader.bind();
        this.colorShader.setUniformMatrix4f("pmv", matrixBuffer);
        // this.colorShader.unbind();
        this.textureShader.bind();
        this.textureShader.setUniformMatrix4f("pmv", matrixBuffer);
        this.textureShader.unbind();
    }

    private static float calculateGapLength(float dashLength, double length) {
        int dashCount = (int) (length / (2 * dashLength) + 1);
        if (dashCount == 1) return 0;

        return (float) ((length - dashCount * dashLength) / (dashCount - 1));
    }
}
