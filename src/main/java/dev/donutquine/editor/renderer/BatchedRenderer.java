package dev.donutquine.editor.renderer;

import java.util.ArrayList;
import java.util.List;
import dev.donutquine.editor.renderer.shader.Shader;
import dev.donutquine.editor.renderer.texture.RenderableTexture;
import dev.donutquine.math.ReadonlyRect;

public abstract class BatchedRenderer implements Renderer {
    private final List<Batch> batches = new ArrayList<>();
    private final BatchPool batchPool;

    protected Batch currentBatch;

    public BatchedRenderer(BatchPool.BatchConstructor constructBatch) {
        batchPool = new BatchPool(constructBatch);
    }

    @Override
    public void beginRendering() {

    }

    @Override
    public void endRendering() {
        this.flush();
        this.unloadBatchesToPool();
    }

    @Override
    public void reset() {
        for (Batch batch : this.batches) {
            batch.delete();
        }

        this.batches.clear();
    }
    @Override
    public boolean startShape(Shader shader, ReadonlyRect rect, RenderableTexture texture, int renderConfigBits, ReadonlyRect clipArea) {
        return startShape(shader, rect.getLeft(), rect.getTop(), rect.getRight(), rect.getBottom(), texture, renderConfigBits, clipArea);
    }

    @Override
    public boolean startShape(Shader shader, float left, float top, float right, float bottom, RenderableTexture texture, int renderConfigBits, ReadonlyRect clipArea) {
        // PERF: inlined Rect.overlaps
        boolean overlaps = clipArea != null && clipArea.overlaps(left, top, right, bottom);

        if (clipArea != null && !overlaps) {
            return false;
        }

        this.currentBatch = null;

        if (!this.batches.isEmpty()) {
            Batch lastBatch = this.batches.get(this.batches.size() - 1);
            if (lastBatch.hasSame(shader, texture, renderConfigBits)) {
                this.currentBatch = lastBatch;
            }
        }

        if (this.currentBatch == null) {
            this.currentBatch = this.batchPool.createOrPopBatch(shader, texture, renderConfigBits, RenderStencilState.NONE);
            this.batches.add(this.currentBatch);
        }

        return this.currentBatch.startShape();
    }

    @Override
    public void addTriangles(int count, Triangulator triangulator) {
        if (this.currentBatch == null) return;

        this.currentBatch.addTriangles(count, triangulator);
    }

    @Override
    @Deprecated(since = "1.7.0")
    public void addVertex(float... parameters) {
        if (this.currentBatch == null) return;

        this.currentBatch.addVertex(parameters);
    }

    @Override
    public void setStencilRenderingState(Shader shader, RenderStencilState state) {
        this.batches.add(this.batchPool.createOrPopBatch(shader, null, 0, state));
    }

    @Override
    public void flush() {
        for (Batch batch : this.batches) {
            batch.render();
        }
    }

    private void unloadBatchesToPool() {
        for (Batch batch : this.batches) {
            batch.reset();
        }

        this.batchPool.pullBatches(this.batches);
        this.batches.clear();

        this.currentBatch = null;
    }
}
