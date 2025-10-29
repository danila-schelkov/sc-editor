package dev.donutquine.editor.renderer;

import dev.donutquine.editor.renderer.shader.Shader;
import dev.donutquine.editor.renderer.texture.RenderableTexture;

import java.util.ArrayList;
import java.util.List;

public class BatchPool {
    private final List<Batch> batches = new ArrayList<>();

    private final BatchConstructor batchConstructor;

    public BatchPool(BatchConstructor batchConstructor) {
        this.batchConstructor = batchConstructor;
    }

    public void pullBatches(List<Batch> batches) {
        this.batches.addAll(batches);
    }

    public Batch createOrPopBatch(Shader shader, RenderableTexture texture, RenderStencilState stencilRenderingState) {
        Batch targetBatch = null;

        for (Batch batch : this.batches) {
            if (batch.hasSame(shader, texture, stencilRenderingState)) {
                targetBatch = batch;
                break;
            }
        }

        this.batches.remove(targetBatch);

        if (targetBatch == null) {
            targetBatch = batchConstructor.construct(shader, texture, stencilRenderingState);
            targetBatch.init();
        }

        return targetBatch;
    }

    @FunctionalInterface
    public interface BatchConstructor {
        Batch construct(Shader shader, RenderableTexture texture, RenderStencilState stencilRenderingState);
    }
}
