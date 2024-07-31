package com.vorono4ka.swf.originalObjects;

import com.vorono4ka.math.Point;
import com.vorono4ka.streams.ByteStream;
import com.vorono4ka.swf.constants.Tag;

import java.util.Arrays;
import java.util.function.Function;

public class ShapeDrawBitmapCommand {
    private Tag tag;

    private int textureIndex;
    private int vertexCount;
    private Point[] shapePoints;
    private Point[] sheetPoints;

    private SWFTexture texture;

    public void load(ByteStream stream, Tag tag, Function<Integer, SWFTexture> imageFunction) {
        this.tag = tag;

        this.textureIndex = stream.readUnsignedChar();
        this.texture = imageFunction.apply(textureIndex);

        this.vertexCount = 4;
        if (tag != Tag.SHAPE_DRAW_BITMAP_COMMAND) {
            this.vertexCount = stream.readUnsignedChar();
        }

        this.shapePoints = new Point[this.vertexCount];
        for (int i = 0; i < this.vertexCount; i++) {
            float x = stream.readTwip();
            float y = stream.readTwip();

            this.shapePoints[i] = new Point(x, y);
        }

        this.sheetPoints = new Point[this.vertexCount];
        for (int i = 0; i < this.vertexCount; i++) {
            float u = stream.readShort();
            float v = stream.readShort();

            if (tag == Tag.SHAPE_DRAW_BITMAP_COMMAND) {
                u *= 65535f / this.texture.getWidth();
                v *= 65535f / this.texture.getHeight();
            } else if (tag != Tag.SHAPE_DRAW_BITMAP_COMMAND_3) {
                u /= 65535f * this.texture.getWidth();
                v /= 65535f * this.texture.getHeight();
            }

            this.sheetPoints[i] = new Point(u, v);
        }
    }

    public void save(ByteStream stream) {
        stream.writeUnsignedChar(this.textureIndex);

        if (this.tag != Tag.SHAPE_DRAW_BITMAP_COMMAND) {
            stream.writeUnsignedChar(this.vertexCount);
        }

        for (Point point : this.shapePoints) {
            stream.writeTwip(point.getX());
            stream.writeTwip(point.getY());
        }

        for (Point point : this.sheetPoints) {
            float u = point.getX();
            float v = point.getY();

            if (this.tag != Tag.SHAPE_DRAW_BITMAP_COMMAND_3) {
                u *= 65535f / this.texture.getWidth();
                v *= 65535f / this.texture.getHeight();
            }

            stream.writeShort((int) u);
            stream.writeShort((int) v);
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

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public int getTextureIndex() {
        return textureIndex;
    }

    public SWFTexture getTexture() {
        return texture;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;

        if (other instanceof ShapeDrawBitmapCommand command) {

            return Arrays.equals(command.shapePoints, this.shapePoints) &&
                Arrays.equals(command.sheetPoints, this.sheetPoints);
        }

        return false;
    }
}
