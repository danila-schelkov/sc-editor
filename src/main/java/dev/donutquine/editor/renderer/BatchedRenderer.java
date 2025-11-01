package dev.donutquine.editor.renderer;

import dev.donutquine.editor.renderer.shader.Shader;
import dev.donutquine.editor.renderer.texture.RenderableTexture;
import dev.donutquine.math.ReadonlyRect;
import dev.donutquine.math.Rect;

import java.util.ArrayList;
import java.util.List;

public class BatchedRenderer implements Renderer, TrueRenderer {
    private final List<Batch> batches = new ArrayList<>();
    private final BatchPool batchPool;
    private final Renderer renderer;

    private Batch currentBatch;

    public BatchedRenderer(BatchPool.BatchConstructor constructBatch, Renderer innerRenderer) {
        batchPool = new BatchPool(constructBatch);
        renderer = innerRenderer;
    }

    public void clearBatches() {
        for (Batch batch : this.batches) {
            batch.delete();
        }

        this.batches.clear();
    }

    @Override
    public void printInfo() {
        this.renderer.printInfo();
    }

    @Override
    public void setViewport(int x, int y, int width, int height) {
        this.renderer.setViewport(x, y, width, height);
    }

    @Override
    public ReadonlyRect getViewport() {
        return this.renderer.getViewport();
    }

    @Override
    public void setRenderStencilState(RenderStencilState state) {
        this.renderer.setRenderStencilState(state);
    }

    @Override
    public boolean bindBlendMode(BlendMode blendMode) {
        return this.renderer.bindBlendMode(blendMode);
    }

    @Override
    public void beginRendering() {

    }

    @Override
    public void endRendering() {
        this.unloadBatchesToPool();
    }

    @Override
    public void clear(int flags) {
        this.renderer.clear(flags);
    }

    @Override
    public void clearColor(float r, float g, float b, float a) {
        this.renderer.clearColor(r, g, b, a);
    }

    @Override
    public void clearStencil() {
        this.renderer.clearStencil();
    }

    @Override
    public void reset() {
        this.clearBatches();
    }

    @Override
    public boolean startShape(Shader shader, Rect rect, RenderableTexture texture, int renderConfigBits, ReadonlyRect clipArea) {
        if (clipArea != null && !clipArea.overlaps(rect)) {
            return false;
        }

        this.currentBatch = null;

        if (!this.batches.isEmpty()) {
            Batch lastBatch = this.batches.get(this.batches.size() - 1);
            if (lastBatch.hasSame(shader, texture)) {
                this.currentBatch = lastBatch;
            }
        }

        if (this.currentBatch == null) {
            this.currentBatch = this.batchPool.createOrPopBatch(shader, texture, RenderStencilState.NONE);
            this.batches.add(this.currentBatch);
        }

        return this.currentBatch.startShape(renderConfigBits);
    }

    @Override
    public void addTriangles(int count, int[] indices) {
        if (this.currentBatch == null) return;

        this.currentBatch.addTriangles(count, indices);
    }

    @Override
    public void addVertex(float... parameters) {
        if (this.currentBatch == null) return;

        this.currentBatch.addVertex(parameters);
    }

    @Override
    public void setStencilRenderingState(Shader shader, RenderStencilState state) {
        this.batches.add(this.batchPool.createOrPopBatch(shader, null, state));
    }

    @Override
    public void flush() {
        this.renderBuckets();
    }

    private void unloadBatchesToPool() {
        for (Batch batch : this.batches) {
            batch.reset();
        }

        this.batchPool.pullBatches(this.batches);
        this.batches.clear();

        this.currentBatch = null;
    }

    private void renderBuckets() {
        for (Batch batch : this.batches) {
            this.renderer.setRenderStencilState(batch.getStencilRenderingState());
            batch.render();
        }
    }
}
