package com.vorono4ka.editor.renderer;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.PMVMatrix;
import com.vorono4ka.editor.Main;
import com.vorono4ka.math.Rect;
import com.vorono4ka.resources.Assets;
import com.vorono4ka.swf.ColorTransform;
import com.vorono4ka.swf.GLImage;
import com.vorono4ka.swf.Matrix2x3;
import com.vorono4ka.swf.SupercellSWF;
import com.vorono4ka.swf.displayObjects.DisplayObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Stage {
    private static Stage INSTANCE;

    private final ConcurrentLinkedQueue<Runnable> tasks;
    private final List<Batch> batches;

    private boolean initialized;
    private Shader shader;
    private GL3 gl;

    private Rect viewport;
    private int scaleStep;
    private float scale;
    private float offsetX;
    private float offsetY;

    private Batch currentBatch;

    public Stage() {
        this.tasks = new ConcurrentLinkedQueue<>();
        this.batches = new ArrayList<>();

        this.scaleStep = 39;
        this.scale = 1.0f;
    }

    public static Stage getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Stage();
        }

        return INSTANCE;
    }

    public void init(GL3 gl, int x, int y, int width, int height) {
        this.shader = Assets.getShader(gl, "vertex.glsl", "fragment.glsl");
        this.gl = gl;

        this.viewport = new Rect(-(width / 2f), -(height / 2f), width / 2f, height / 2f);

        gl.glViewport(x, y, width, height);

        this.updatePMVMatrix();

        gl.glEnable(GL3.GL_BLEND);
        gl.glBlendEquation(GL3.GL_FUNC_ADD);
        gl.glBlendFunc(GL3.GL_SRC_ALPHA, GL3.GL_ONE_MINUS_SRC_ALPHA);

        this.initialized = true;
    }

    public void render() {
        if (!this.initialized) return;

        Iterator<Runnable> iterator = this.tasks.iterator();
        while (iterator.hasNext()) {
            Runnable task = iterator.next();
            task.run();

            iterator.remove();
        }

        this.gl.glClearColor(.5f, .5f, .5f, 1);
        this.gl.glClear(GL3.GL_COLOR_BUFFER_BIT);

        DisplayObject selectedObject = Main.editor.getSelectedObject();
        if (selectedObject == null) return;

        float deltaTime = 0;

        FPSAnimator animator = Main.editor.getAnimator();
        if (animator != null && animator.isAnimating()) {
            deltaTime = 1f / animator.getFPS();
        }

        selectedObject.render(new Matrix2x3(), new ColorTransform(), 0, deltaTime);

        this.shader.bind();

        this.renderBuckets();

        this.shader.unbind();
    }

    public void unbindRender() {
        if (!this.initialized) return;

        this.clearBatches();

        this.initialized = false;
        this.gl.glDisableVertexAttribArray(0);
        this.gl.glDisableVertexAttribArray(1);
        this.gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, 0);
        this.gl.glBindBuffer(GL3.GL_ELEMENT_ARRAY_BUFFER, 0);

        this.gl.glDisable(GL3.GL_BLEND);
    }

    private void renderBuckets() {
        for (Batch batch : this.batches) {
            batch.render(this.gl);
        }

        for (Batch batch : this.batches) {
            batch.reset();
        }
    }

    public void clearBatches() {
        for (Batch batch : this.batches) {
            batch.delete();
        }

        this.batches.clear();
    }

    public boolean startShape(float left, float top, float right, float bottom, GLImage image, int renderConfigBits) {
        this.currentBatch = null;
        for (Batch batch : this.batches) {
            if (batch.getImage() == image) {
                this.currentBatch = batch;
                break;
            }
        }

        if (this.currentBatch == null) {
            this.currentBatch = new Batch(image);
            this.currentBatch.init(this.gl);
            this.batches.add(this.currentBatch);
        }

        if (this.currentBatch != null) {
            return this.currentBatch.startShape(left, top, right, bottom, image, renderConfigBits);
        }

        return false;
    }

    public void addTriangles(int count, int[] indices) {
        if (this.currentBatch == null) return;

        this.currentBatch.addTriangles(count, indices);
    }

    public void addVertex(float x, float y, float u, float v) {
        if (this.currentBatch == null) return;

        this.currentBatch.addVertex(x, y, u, v);
    }

    public void updatePMVMatrix() {
        PMVMatrix matrix = new PMVMatrix();
        matrix.glLoadIdentity();
        matrix.glOrthof(
            // left
            (this.viewport.getMinX() / this.scale + this.offsetX),
            // right
            (this.viewport.getMaxX() / this.scale + this.offsetX),
            // bottom
            (this.viewport.getMaxY() / this.scale + this.offsetY),
            // top
            (this.viewport.getMinY() / this.scale + this.offsetY),
            // near
            -1,
            // far
            1
        );

        this.shader.bind();
        this.gl.glUniformMatrix4fv(this.shader.getUniformLocation("pmv"), 1, false, matrix.glGetMatrixf());
        this.shader.unbind();
    }

    public void doInRenderThread(Runnable task) {
        this.tasks.add(task);
    }

    public int getScaleStep() {
        return scaleStep;
    }

    public void setScaleStep(int scaleStep) {
        this.scaleStep = scaleStep;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void addOffset(float x, float y) {
        this.offsetX += x;
        this.offsetY += y;
    }

    public void setOffset(float x, float y) {
        this.offsetX = x;
        this.offsetY = y;
    }

    public GL3 getGl() {
        return this.gl;
    }
}
