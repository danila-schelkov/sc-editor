package dev.donutquine.editor.renderer.impl;

import dev.donutquine.editor.renderer.BatchPool.BatchConstructor;
import dev.donutquine.editor.renderer.BatchedRenderer;

public class StageBatchedRenderer extends BatchedRenderer implements StageSpecificRenderer {
    public StageBatchedRenderer(BatchConstructor constructBatch) {
        super(constructBatch);
    }

    @Override
    public void addVertex(float x, float y, float u, float v) {
        if (this.currentBatch == null) return;
        this.currentBatch.startAddingVertex(4);
        this.currentBatch.addVertexParameter(x);
        this.currentBatch.addVertexParameter(y);
        this.currentBatch.addVertexParameter(u);
        this.currentBatch.addVertexParameter(v);
    }

    @Override
    public void addVertex(float x, float y, float u, float v, float redMul, float greenMul, float blueMul, float alpha, float redAdd, float greenAdd, float blueAdd) {
        if (this.currentBatch == null) return;
        this.currentBatch.startAddingVertex(11);
        this.currentBatch.addVertexParameter(x);
        this.currentBatch.addVertexParameter(y);
        this.currentBatch.addVertexParameter(u);
        this.currentBatch.addVertexParameter(v);
        this.currentBatch.addVertexParameter(redMul);
        this.currentBatch.addVertexParameter(greenMul);
        this.currentBatch.addVertexParameter(blueMul);
        this.currentBatch.addVertexParameter(alpha);
        this.currentBatch.addVertexParameter(redAdd);
        this.currentBatch.addVertexParameter(greenAdd);
        this.currentBatch.addVertexParameter(blueAdd);
    }
}
