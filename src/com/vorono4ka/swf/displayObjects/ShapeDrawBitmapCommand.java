package com.vorono4ka.swf.displayObjects;

import com.vorono4ka.math.Point;
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
