package com.vorono4ka.swf.originalObjects;

import com.vorono4ka.streams.ByteStream;
import com.vorono4ka.swf.constants.Tag;

import java.util.function.Function;
import java.util.function.IntFunction;

public class ShapeDrawBitmapCommand {
    private transient Tag tag;

    private int unk;
    private int textureIndex;
    private int pointCount;
    private int startingPointIndex;

    private transient int vertexCount;
    private transient ShapePoint[] shapePoints;

    private transient SWFTexture texture;

    private transient IntFunction<int[]> triangulator;
    private transient int[] indices;

    public void load(ByteStream stream, Tag tag, Function<Integer, SWFTexture> imageFunction) {
        this.tag = tag;

        this.textureIndex = stream.readUnsignedChar();
        this.texture = imageFunction.apply(textureIndex);

        this.vertexCount = 4;
        if (tag != Tag.SHAPE_DRAW_BITMAP_COMMAND) {
            this.vertexCount = stream.readUnsignedChar();
        }

        this.shapePoints = new ShapePoint[this.vertexCount];
        for (int i = 0; i < this.shapePoints.length; i++) {
            this.shapePoints[i] = new ShapePoint();
        }

        for (int i = 0; i < this.vertexCount; i++) {
            ShapePoint shapePoint = this.shapePoints[i];
            shapePoint.setX(stream.readTwip());
            shapePoint.setY(stream.readTwip());
        }

        for (int i = 0; i < this.vertexCount; i++) {
            ShapePoint shapePoint = this.shapePoints[i];
            shapePoint.setU(stream.readShort());
            shapePoint.setV(stream.readShort());
        }
    }

    public void save(ByteStream stream) {
        stream.writeUnsignedChar(this.textureIndex);

        if (this.tag != Tag.SHAPE_DRAW_BITMAP_COMMAND) {
            stream.writeUnsignedChar(this.vertexCount);
        }

        for (ShapePoint point : this.shapePoints) {
            stream.writeTwip(point.getX());
            stream.writeTwip(point.getY());
        }

        for (ShapePoint point : this.shapePoints) {
            stream.writeShort(point.getU());
            stream.writeShort(point.getV());
        }
    }

    public float getX(int pointIndex) {
        return this.shapePoints[pointIndex].getX();
    }

    public float getY(int pointIndex) {
        return this.shapePoints[pointIndex].getY();
    }

    public void setXY(int pointIndex, float x, float y) {
        ShapePoint point = this.shapePoints[pointIndex];

        point.setX(x);
        point.setY(y);
    }

    public float getU(int pointIndex) {
        return this.shapePoints[pointIndex].getU() / 65535f;
    }

    public float getV(int pointIndex) {
        return this.shapePoints[pointIndex].getV() / 65535f;
    }

    public void setUV(int pointIndex, float u, float v) {
        ShapePoint point = this.shapePoints[pointIndex];

        point.setU((int) (u * 65535f));
        point.setV((int) (v * 65535f));
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

    public int getPointCount() {
        return pointCount;
    }

    public int getStartingPointIndex() {
        return startingPointIndex;
    }

    public void setPoints(ShapePoint[] points) {
        this.shapePoints = points;
    }

    public SWFTexture getTexture() {
        return texture;
    }

    public int getVertexCount() {
        return shapePoints.length;
    }

    public int getTriangleCount() {
        return this.getVertexCount() - 2;
    }

    public void setTriangulator(IntFunction<int[]> triangulator) {
        this.triangulator = triangulator;
    }

    public int[] getIndices() {
        if (indices != null) {
            return indices;
        }

        return indices = triangulator.apply(getTriangleCount());
    }
}
