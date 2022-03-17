package com.vorono4ka.swf;

public class ScMatrixBank {
    private Matrix2x3[] matrices;
    private ColorTransform[] colorTransforms;

    public ScMatrixBank() {

    }

    public void init(int matricesCount, int colorTransformsCount) {
        this.matrices = new Matrix2x3[matricesCount];
        for (int i = 0; i < this.matrices.length; i++) {
            this.matrices[i] = new Matrix2x3();
        }

        this.colorTransforms = new ColorTransform[colorTransformsCount];
        for (int i = 0; i < this.colorTransforms.length; i++) {
            this.colorTransforms[i] = new ColorTransform();
        }
    }
    
    public Matrix2x3 getMatrix(int index) {
        return this.matrices[index];
    }
    
    public ColorTransform getColorTransforms(int index) {
        return this.colorTransforms[index];
    }

    public int getMatricesCount() {
        return this.matrices.length;
    }

    public int getColorTransformsCount() {
        return this.colorTransforms.length;
    }
}
