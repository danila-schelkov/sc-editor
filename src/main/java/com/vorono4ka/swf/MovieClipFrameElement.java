package com.vorono4ka.swf;

public class MovieClipFrameElement {
    private final int childIndex;
    private final int matrixIndex;
    private final int colorTransformIndex;

    public MovieClipFrameElement(int childIndex, int matrixIndex, int colorTransformIndex) {
        this.childIndex = childIndex;
        this.matrixIndex = matrixIndex;
        this.colorTransformIndex = colorTransformIndex;
    }

    public int getChildIndex() {
        return childIndex;
    }

    public int getMatrixIndex() {
        return matrixIndex;
    }

    public int getColorTransformIndex() {
        return colorTransformIndex;
    }
}
