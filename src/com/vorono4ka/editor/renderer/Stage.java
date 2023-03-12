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
import com.vorono4ka.swf.displayObjects.DisplayObject;
import com.vorono4ka.swf.displayObjects.StageSprite;
import com.vorono4ka.utilities.Utilities;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Stage {
    private static int STAGE_COUNT;
    private static Stage INSTANCE;

    private final ConcurrentLinkedQueue<Runnable> tasks;
    private final List<Batch> batches;
    private final BatchPool batchPool;
    private final StageSprite stageSprite;

    private boolean initialized;
    private Shader shader;
    private GL3 gl;

    private Rect viewport;
    private Rect clipArea;
    private int scaleStep;
    private float pointSize;
    private float offsetX;
    private float offsetY;

    private Batch currentBatch;
    private GLImage gradientTexture;

    public Stage() {
        this.tasks = new ConcurrentLinkedQueue<>();
        this.batches = new ArrayList<>();

        this.batchPool = new BatchPool();

        this.scaleStep = 39;
        this.pointSize = 1.0f;

        this.stageSprite = new StageSprite(this);

        Stage.STAGE_COUNT++;
    }

    public static Stage getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Stage();
        }

        return INSTANCE;
    }

    public static int getStageCount() {
        return STAGE_COUNT;
    }

    public void init(GL3 gl, int x, int y, int width, int height) {
        this.shader = Assets.getShader(gl, "vertex.glsl", "fragment.glsl");
        this.gl = gl;

        BufferedImage imageBuffer = Assets.getImageBuffer("gradient_texture.png");
        assert imageBuffer != null : "Gradient texture not found.";

        this.gradientTexture = new GLImage();
        GLImage.createWithFormat(this.gradientTexture, true, 1, 256, 2, Utilities.getPixelBuffer(imageBuffer), GL3.GL_LUMINANCE_ALPHA, GL3.GL_UNSIGNED_BYTE);

        this.viewport = new Rect(-(width / 2f), -(height / 2f), width / 2f, height / 2f);
        this.clipArea = new Rect(this.viewport);

        gl.glViewport(x, y, width, height);

        this.updatePMVMatrix();

        gl.glEnable(GL3.GL_BLEND);
        gl.glBlendEquation(GL3.GL_FUNC_ADD);
        gl.glBlendFunc(GL3.GL_ONE, GL3.GL_ONE_MINUS_SRC_ALPHA);

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
        this.gl.glClear(GL3.GL_COLOR_BUFFER_BIT | GL3.GL_DEPTH_BUFFER_BIT | GL3.GL_STENCIL_BUFFER_BIT);

        this.gl.glStencilMask(0xFF);
        this.gl.glClearStencil(0);
        this.gl.glStencilMask(0);

//        DisplayObject selectedObject = Main.editor.getSelectedObject();
//        if (selectedObject == null) return;

        float deltaTime = 0;

        FPSAnimator animator = Main.editor.getAnimator();
        if (animator != null && animator.isAnimating()) {
            deltaTime = 1f / animator.getFPS();
        }

        this.stageSprite.render(new Matrix2x3(), new ColorTransform(), 0, deltaTime);

        this.shader.bind();

        this.renderBuckets();
        this.unloadBatchesToPool();

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

    private void unloadBatchesToPool() {
        this.batchPool.pullBatches(this.batches);
        this.batches.clear();
    }

    public boolean startShape(Rect rect, GLImage image, int renderConfigBits) {
        if (rect.getLeft() > this.clipArea.getRight() ||
            rect.getTop() > this.clipArea.getBottom() ||
            rect.getRight() < this.clipArea.getLeft() ||
            rect.getBottom() < this.clipArea.getTop()) {
            return false;
        }

        this.currentBatch = null;

        if (this.batches.size() > 0) {
            Batch lastBatch = this.batches.get(this.batches.size() - 1);
            if (lastBatch.getImage() == image) {
                this.currentBatch = lastBatch;
            }
        }

        if (this.currentBatch == null) {
            this.currentBatch = this.batchPool.createOrPopBatch(this.gl, image);
            this.batches.add(this.currentBatch);
        }

        return this.currentBatch.startShape(image, renderConfigBits);
    }

    public void addTriangles(int count, int[] indices) {
        if (this.currentBatch == null) return;

        this.currentBatch.addTriangles(count, indices);
    }

    public void addVertex(float x, float y, float u, float v, float redMul, float greenMul, float blueMul, float redAdd, float greenAdd, float blueAdd, float alpha) {
        if (this.currentBatch == null) return;

        this.currentBatch.addVertex(x, y, u, v, redMul, greenMul, blueMul, redAdd, greenAdd, blueAdd, alpha);
    }

    public void updatePMVMatrix() {
        this.clipArea = new Rect(
            (this.viewport.getLeft() / this.pointSize + this.offsetX),
            (this.viewport.getTop() / this.pointSize + this.offsetY),
            (this.viewport.getRight() / this.pointSize + this.offsetX),
            (this.viewport.getBottom() / this.pointSize + this.offsetY)
        );

        PMVMatrix matrix = new PMVMatrix();
        matrix.glLoadIdentity();
        matrix.glOrthof(
            this.clipArea.getLeft(),
            this.clipArea.getRight(),
            this.clipArea.getBottom(),
            this.clipArea.getTop(),
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

    public void addChild(DisplayObject displayObject) {
        this.stageSprite.addChild(displayObject);
    }

    public void removeChild(DisplayObject displayObject) {
        this.stageSprite.removeChild(displayObject);
    }

    public void removeAllChildren() {
        this.stageSprite.removeAllChildren();
    }

    public void setStencilRenderingState(int state) {  // TODO: split into separate batch buffer
        switch (state) {
            case 1 -> {
                // scissors
            }
            case 2 -> {
                this.gl.glEnable(GL3.GL_STENCIL_TEST);
                this.gl.glStencilFunc(GL3.GL_ALWAYS, 1, 0xFF); // каждый фрагмент обновит трафаретный буфер
                this.gl.glStencilOp(GL3.GL_KEEP, GL3.GL_KEEP, GL3.GL_REPLACE);
                this.gl.glStencilMask(0xFF); // включить запись в трафаретный буфер

                this.gl.glDepthMask(false);
                this.gl.glClear(GL3.GL_STENCIL_BUFFER_BIT); // Clear stencil buffer (0 by default)
            }
            case 3 -> {
                this.gl.glStencilFunc(GL3.GL_EQUAL, 1, 0xFF);
                this.gl.glStencilMask(0x00); // отключить запись в трафаретный буфер
            }
            case 4 -> this.gl.glDisable(GL3.GL_STENCIL_TEST);
            case 5 -> {
                this.gl.glStencilFunc(GL3.GL_NOTEQUAL, 1, 0xFF);
                this.gl.glStencilMask(0x00); // отключить запись в трафаретный буфер
            }
        }
    }

    public int getScaleStep() {
        return scaleStep;
    }

    public void setScaleStep(int scaleStep) {
        this.scaleStep = scaleStep;
    }

    public float getPointSize() {
        return pointSize;
    }

    public void setPointSize(float pointSize) {
        this.pointSize = pointSize;
        if (pointSize == 0) {
            this.viewport = null;
        }
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

    public GLImage getGradientTexture() {
        return this.gradientTexture;
    }
}
