package com.vorono4ka.swf;

import java.util.ArrayList;
import java.util.List;

public class ScMatrixBank {
    private List<Matrix2x3> matrices;
    private List<ColorTransform> colorTransforms;

    public ScMatrixBank() {

    }

    public void init(int matricesCount, int colorTransformsCount) {
        this.matrices = new ArrayList<>(matricesCount);
        for (int i = 0; i < matricesCount; i++) {
            this.matrices.add(i, new Matrix2x3());
        }

        this.colorTransforms = new ArrayList<>(colorTransformsCount);
        for (int i = 0; i < colorTransformsCount; i++) {
            this.colorTransforms.add(i, new ColorTransform());
        }
    }

    public List<Matrix2x3> getMatrices() {
        return this.matrices;
    }

    public List<ColorTransform> getColorTransforms() {
        return colorTransforms;
    }

    public Matrix2x3 getMatrix(int index) {
        return this.matrices.get(index);
    }

    public ColorTransform getColorTransforms(int index) {
        return this.colorTransforms.get(index);
    }

    public int getMatricesCount() {
        return this.matrices.size();
    }

    public int getColorTransformsCount() {
        return this.colorTransforms.size();
    }
}
