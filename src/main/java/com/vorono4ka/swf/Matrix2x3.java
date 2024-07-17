package com.vorono4ka.swf;

import com.vorono4ka.streams.ByteStream;
import com.vorono4ka.swf.constants.Tag;
import com.vorono4ka.swf.originalObjects.Savable;

public class Matrix2x3 implements Savable {
    public static final float PRECISE_MULTIPLIER = 65535f;
    public static final float DEFAULT_MULTIPLIER = 1024f;

    /*
    * a b x
    * c d y
    * */
    private float a;  // m00  // previous name: scaleX
    private float b;  // m01  // previous name: shearX
    private float c;  // m10  // previous name: shearY
    private float d;  // m11  // previous name: scaleY
    private float x;  // m02
    private float y;  // m12

    public Matrix2x3() {
        // Setting scale to defaults
        this.a = 1.0f;
        this.d = 1.0f;
    }

    public Matrix2x3(float a, float b, float c, float d, float x, float y) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.x = x;
        this.y = y;
    }

    public Matrix2x3(Matrix2x3 matrix) {
        this.a = matrix.a;
        this.b = matrix.b;
        this.c = matrix.c;
        this.d = matrix.d;
        this.x = matrix.x;
        this.y = matrix.y;
    }

    public void load(ByteStream stream, boolean isPrecise) {
        float divider = isPrecise ? PRECISE_MULTIPLIER : DEFAULT_MULTIPLIER;

        this.a = stream.readInt() / divider;
        this.b = stream.readInt() / divider;
        this.c = stream.readInt() / divider;
        this.d = stream.readInt() / divider;
        this.x = stream.readTwip();
        this.y = stream.readTwip();
    }

    @Override
    public void save(ByteStream stream) {
        float multiplier = isPrecise() ? PRECISE_MULTIPLIER : DEFAULT_MULTIPLIER;

        stream.writeInt((int) (this.a * multiplier));
        stream.writeInt((int) (this.b * multiplier));
        stream.writeInt((int) (this.c * multiplier));
        stream.writeInt((int) (this.d * multiplier));
        stream.writeTwip(this.x);
        stream.writeTwip(this.y);
    }

    @Override
    public Tag getTag() {
        return isPrecise() ? Tag.MATRIX_PRECISE : Tag.MATRIX;
    }

    private boolean isPrecise() {
        return (this.a != 0 && Math.abs(this.a) < 0.0009765f) ||
            (this.d != 0 && Math.abs(this.d) < 0.0009765f) ||
            (this.b != 0 && Math.abs(this.b) < 0.0009765f) ||
            (this.c != 0 && Math.abs(this.c) < 0.0009765f);
    }

    public void multiply(Matrix2x3 matrix) {
        float scaleX = (this.a * matrix.a) + (this.b * matrix.c);
        float shearX = (this.a * matrix.b) + (this.b * matrix.d);
        float scaleY = (this.d * matrix.d) + (this.c * matrix.b);
        float shearY = (this.d * matrix.c) + (this.c * matrix.a);
        float x = matrix.applyX(this.x, this.y);
        float y = matrix.applyY(this.x, this.y);

        this.a = scaleX;
        this.b = shearX;
        this.d = scaleY;
        this.c = shearY;
        this.x = x;
        this.y = y;
    }

    public float applyX(float x, float y) {
        return x * this.a + y * this.c + this.x;
    }

    public float applyY(float x, float y) {
        return y * this.d + x * this.b + this.y;
    }

    public void scaleMultiply(float scaleX, float scaleY) {
        this.a *= scaleX;
        this.b *= scaleY;
        this.c *= scaleX;
        this.d *= scaleY;
    }

    public void setRotation(float angle, float scaleX, float scaleY) {
        this.setRotationRadians((float) Math.toRadians(angle), scaleX, scaleY);
    }

    public void setRotationRadians(float angle, float scaleX, float scaleY) {
        float sin = (float) Math.sin(angle);
        float cos = (float) Math.cos(angle);

        this.a = cos * scaleX;
        this.b = -(sin * scaleY);
        this.c = sin * scaleX;
        this.d = cos * scaleY;
    }

    public void rotate(float angle) {
        this.rotateRadians((float) Math.toRadians(angle));
    }

    public void rotateRadians(float angle) {
        float sin = (float) Math.sin(angle);
        float cos = (float) Math.cos(angle);

        float tmp00 = this.a * cos + this.b * sin;
        float tmp01 = this.a * -sin + this.b * cos;
        float tmp10 = this.c * cos + this.d * sin;
        float tmp11 = this.c * -sin + this.d * cos;

        this.a = tmp00;
        this.b = tmp01;
        this.c = tmp10;
        this.d = tmp11;
    }

    public void inverse() {
        float determinant = getDeterminant();
        if (determinant == 0.0f) return;

        float a = this.a;
        float b = this.b;
        float x = this.x;
        float c = this.c;
        float d = this.d;
        float y = this.y;

        this.x = ((y * c) - (x * d)) / determinant;
        this.y = ((x * b) - (y * a)) / determinant;
        this.a = d / determinant;
        this.b = -b / determinant;
        this.c = -c / determinant;
        this.d = a / determinant;
    }

    private float getDeterminant() {
        return (this.d * this.a) - (this.c * this.b);
    }

    public float getScaleX() {
        return a;
    }

    public float getScaleY() {
        return d;
    }

    public float getShearX() {
        return b;
    }

    public float getShearY() {
        return c;
    }

    public void setXY(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }


    @Override
    public String toString() {
        return "Matrix2x3{" + a + ", " + b + ", " + c + ", " + d + ", " + x + ", " + y + '}';
    }

    public DecomposedMatrix2x3 decompose() {
        double scaleX = Math.hypot(a, b);
        double theta = Math.atan2(b, a);

        double sin = Math.sin(theta);
        double cos = Math.cos(theta);

        // Note: double is too precise structure, sin may be around 0, but not equal to it
        double scaleY = Math.abs(sin) > 0.01f ? c / sin : d / cos;

        return new DecomposedMatrix2x3(scaleX, scaleY, Math.toDegrees(theta), x, y);
    }

    public record DecomposedMatrix2x3(double scaleX, double scaleY, double rotationDegrees, double x, double y) {
    }
}
