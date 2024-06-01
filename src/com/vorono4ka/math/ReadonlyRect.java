package com.vorono4ka.math;

public interface ReadonlyRect {
    float getWidth();
    float getHeight();

    float getMidX();
    float getMidY();

    float getLeft();
    float getTop();
    float getRight();
    float getBottom();

    boolean overlaps(Rect other);
}
