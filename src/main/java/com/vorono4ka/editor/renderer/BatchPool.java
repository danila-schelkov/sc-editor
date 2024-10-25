package com.vorono4ka.editor.renderer;

import com.jogamp.opengl.GL3;
import com.vorono4ka.editor.renderer.texture.Texture;

import java.util.ArrayList;
import java.util.List;

public class BatchPool {
    private final List<Batch> batches = new ArrayList<>();

    public void pullBatches(List<Batch> batches) {
        this.batches.addAll(batches);
    }

    public Batch createOrPopBatch(GL3 gl, Shader shader, Texture texture, int stencilRenderingState) {
        Batch targetBatch = null;

        for (Batch batch : this.batches) {
            if (batch.hasSame(shader, texture, stencilRenderingState)) {
                targetBatch = batch;
                break;
            }
        }

        this.batches.remove(targetBatch);

        if (targetBatch == null) {
            targetBatch = new Batch(shader, texture, stencilRenderingState);
            targetBatch.init(gl);
        }

        return targetBatch;
    }
}
