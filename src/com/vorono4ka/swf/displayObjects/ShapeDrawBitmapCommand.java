package com.vorono4ka.swf.displayObjects;

import com.vorono4ka.editor.renderer.Renderer;
import com.vorono4ka.math.Point;
import com.vorono4ka.swf.ColorTransform;
import com.vorono4ka.swf.Matrix2x3;
import com.vorono4ka.swf.SupercellSWF;
import com.vorono4ka.swf.constants.Tag;
import com.vorono4ka.swf.displayObjects.original.SWFTexture;

public class ShapeDrawBitmapCommand {
    private int vertexCount;
    private Point[] shapePoints;
    private Point[] sheetPoints;

    public void load(SupercellSWF swf, Tag tag) {
        int textureId = swf.readUnsignedChar();

        this.vertexCount = 4;
        if (tag != Tag.SHAPE_DRAW_BITMAP_COMMAND) {
            this.vertexCount = swf.readUnsignedChar();
        }

        SWFTexture texture = swf.getTexture(textureId);

        this.shapePoints = new Point[this.vertexCount];
        for (int i = 0; i < this.vertexCount; i++) {
            int x = swf.readTwip();
            int y = swf.readTwip();

            this.shapePoints[i] = new Point(x, y);
        }

        this.sheetPoints = new Point[this.vertexCount];
        for (int i = 0; i < this.vertexCount; i++) {
            int u = swf.readShort();
            int v = swf.readShort();

            if (tag != Tag.SHAPE_DRAW_BITMAP_COMMAND_3) {
                u /= 0xFFFF * texture.getWidth();  // width
                v /= 0xFFFF * texture.getHeight();  // height
            }

            this.sheetPoints[i] = new Point(u, v);
        }
    }

    public void render(Renderer renderer, Matrix2x3 matrix, ColorTransform colorTransform, int a3) {
        float left = 0,
            top = 0,
            right = 0,
            bottom = 0;

        float[] transformedPoints = new float[this.vertexCount * 2];
        for (int i = 0; i < this.vertexCount; i++) {
            Point point = this.shapePoints[i];

            float x = (point.getX() * matrix.getScaleX()) + (point.getY() * matrix.getSkewY()) + matrix.getX();
            float y = (point.getX() * matrix.getSkewX()) + (point.getY() * matrix.getScaleY()) + matrix.getY();

            transformedPoints[i * 2] = x;
            transformedPoints[i * 2 + 1] = x;

            if (i == 0) {
                left = x;
                top = y;
                right = x;
                bottom = y;
                continue;
            }

            if ( x >= left ) {
                if ( x > right )
                    right = x;
            } else {
                left = x;
            }

            if ( y >= top ) {
                if ( y > bottom )
                    bottom = y;
            } else {
                top = y;
            }
        }

        System.out.printf("Bounds rect: %f %f %f %f, Size: (%f, %f)%n", left, top, right, bottom, right - left, bottom - top);
        for (int i = 0; i < this.vertexCount; i++) {
            System.out.printf("Point %d: (%f, %f)%n", i, transformedPoints[i * 2], transformedPoints[i * 2 + 1]);
        }
    }

    public void collisionRender(Renderer renderer, Matrix2x3 matrix, ColorTransform colorTransform) {
        this.render(renderer, matrix, colorTransform, 0);
    }

    public float getX(int pointIndex) {
        return this.shapePoints[pointIndex].getX();
    }

    public float getY(int pointIndex) {
        return this.shapePoints[pointIndex].getY();
    }

    public void setXY(int pointIndex, float x, float y) {
        Point point = this.shapePoints[pointIndex];

        point.setX(x);
        point.setY(y);
    }

    public float getU(int pointIndex) {
        return this.sheetPoints[pointIndex].getX() / 65535f;
    }

    public float getV(int pointIndex) {
        return this.sheetPoints[pointIndex].getY() / 65535f;
    }

    public void setUV(int pointIndex, float u, float v) {
        Point point = this.sheetPoints[pointIndex];

        point.setX(u * 65535f);
        point.setY(v * 65535f);
    }

    public int getVertexCount() {
        return vertexCount;
    }
}
