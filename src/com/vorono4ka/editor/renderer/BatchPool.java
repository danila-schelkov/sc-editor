package com.vorono4ka.editor.renderer;

import com.jogamp.opengl.GL3;
import com.vorono4ka.swf.GLImage;

import java.util.ArrayList;
import java.util.List;

public class BatchPool {
    private final List<Batch> batches;

    public BatchPool() {
        this.batches = new ArrayList<>();
    }

    public void pullBatches(List<Batch> batches) {
        this.batches.addAll(batches);
    }

    public Batch createOrPopBatch(GL3 gl, GLImage image) {
        Batch neededBatch = null;

        for (Batch batch : this.batches) {
            if (batch.getImage() == image) {
                neededBatch = batch;
                break;
            }
        }

        this.batches.remove(neededBatch);

        if (neededBatch == null) {
            neededBatch = new Batch(image);
            neededBatch.init(gl);
        }

        return neededBatch;
    }
}
