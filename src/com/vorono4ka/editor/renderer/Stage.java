package com.vorono4ka.editor.renderer;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.PMVMatrix;
import com.vorono4ka.editor.Main;
import com.vorono4ka.math.MathHelper;
import com.vorono4ka.math.Rect;
import com.vorono4ka.resources.Assets;
import com.vorono4ka.swf.ColorTransform;
import com.vorono4ka.swf.GLImage;
import com.vorono4ka.swf.Matrix2x3;
import com.vorono4ka.swf.displayObjects.DisplayObject;
import com.vorono4ka.swf.displayObjects.MovieClip;
import com.vorono4ka.swf.displayObjects.StageSprite;
import com.vorono4ka.utilities.BufferUtils;
import com.vorono4ka.utilities.ImageData;
import com.vorono4ka.utilities.ImageUtils;
import com.vorono4ka.utilities.Utilities;

import java.awt.image.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Stage {
    private static final Rect VIEWPORT_RECT = new Rect(-1, -1, 1, 1);
    private static final int[] RECT_INDICES = {0, 1, 2, 0, 2, 3};

    private static int STAGE_COUNT;
    private static Stage INSTANCE;

    private final ConcurrentLinkedQueue<Runnable> tasks = new ConcurrentLinkedQueue<>();
    private final List<Batch> batches = new ArrayList<>();
    private final Camera camera = new Camera();
    private final BatchPool batchPool = new BatchPool();
    private final StageSprite stageSprite;

    private boolean initialized;
    private Shader shader, screenShader;
    private GL3 gl;

    private Batch currentBatch;
    private GLImage gradientTexture;
    private Framebuffer framebuffer;

    private boolean isCalculatingBounds;
    private Rect bounds;
    private boolean isAnimationPaused;

    private Stage() {
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

    private static void flipY(int framebufferWidth, int framebufferHeight, int[] pixelArray) {
        for (int x = 0; x < framebufferWidth; x++) {
            for (int y = 0; y < framebufferHeight / 2; y++) {
                int pixelIndex = x + y * framebufferWidth;
                int flippedIndex = x + (framebufferHeight - y) * framebufferWidth;

                int oldPixel = pixelArray[pixelIndex];
                pixelArray[pixelIndex] = pixelArray[flippedIndex];
                pixelArray[flippedIndex] = oldPixel;
            }
        }
    }

    public void init(GL3 gl, int x, int y, int width, int height) {
        this.shader = Assets.getShader(gl, "objects.vertex.glsl", "objects.fragment.glsl");
        this.screenShader = Assets.getShader(gl, "screen.vertex.glsl", "screen.fragment.glsl");
        this.gl = gl;

        BufferedImage imageBuffer = Assets.getImageBuffer("gradient_texture.png");
        assert imageBuffer != null : "Gradient texture not found.";

        if (this.gradientTexture == null) {
            this.gradientTexture = new GLImage();
        }

        this.gradientTexture.createWithFormat(null, true, 1, 256, 2, Utilities.getPixelBuffer(imageBuffer), GL3.GL_LUMINANCE_ALPHA, GL3.GL_UNSIGNED_BYTE);

        this.camera.init(width, height);

        gl.glViewport(x, y, width, height);
        this.framebuffer = new Framebuffer(gl, width, height);

        this.updatePMVMatrix();

        gl.glEnable(GL3.GL_BLEND);
        gl.glBlendEquation(GL3.GL_FUNC_ADD);
        gl.glBlendFunc(GL3.GL_ONE, GL3.GL_ONE_MINUS_SRC_ALPHA);

        this.initialized = true;
    }

    public void update() {
        if (!this.initialized) return;

        Iterator<Runnable> iterator = this.tasks.iterator();
        while (iterator.hasNext()) {
            Runnable task = iterator.next();
            try {
                task.run();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            iterator.remove();
        }

        float deltaTime = 0;

        FPSAnimator animator = Main.editor.getAnimator();
        if (animator != null && animator.isAnimating()) {
            deltaTime = 1f / animator.getFPS(); // TODO: calculate delta time more precisely
        }

        if (isAnimationPaused) {
            deltaTime = 0;
        }

        render(deltaTime);
    }

    public void render(float deltaTime) {
        this.stageSprite.render(new Matrix2x3(), new ColorTransform(), 0, deltaTime);

        this.framebuffer.bind();
        this.gl.glClearColor(0, 0, 0, 0);
        this.gl.glClear(GL3.GL_COLOR_BUFFER_BIT | GL3.GL_DEPTH_BUFFER_BIT | GL3.GL_STENCIL_BUFFER_BIT);

        this.gl.glStencilMask(0xFF);
        this.gl.glClearStencil(0);
        this.gl.glStencilMask(0);

        this.shader.bind();
        this.renderBuckets();
        this.shader.unbind();
        this.framebuffer.unbind();

        this.unloadBatchesToPool();

        this.screenShader.bind();

        this.gl.glClearColor(.5f, .5f, .5f, 1);
        this.gl.glClear(GL3.GL_COLOR_BUFFER_BIT | GL3.GL_DEPTH_BUFFER_BIT | GL3.GL_STENCIL_BUFFER_BIT);

        renderRect(VIEWPORT_RECT, this.framebuffer.getTexture());

        this.renderBuckets();

        this.screenShader.unbind();

        this.unloadBatchesToPool();
    }

    public void takeScreenshot() {
        Rect bounds = getDisplayObjectBounds(this.stageSprite);
        ImageData imageData = getCroppedFramebufferData(bounds);

        BufferedImage image = ImageUtils.createBufferedImageFromPixels(imageData.width(), imageData.height(), imageData.pixels());

        Path path = getScreenshotPath();
        ImageUtils.saveImage(path, image);
    }

    public ImageData getCroppedFramebufferData(Rect bounds) {
        int framebufferWidth = framebuffer.getWidth();
        int framebufferHeight = framebuffer.getHeight();
        int width = (int) Math.ceil(bounds.getWidth() * camera.getPointSize());
        int height = (int) Math.ceil(bounds.getHeight() * camera.getPointSize());

        width = MathHelper.clamp(width, 1, framebufferWidth);
        height = MathHelper.clamp(height, 1, framebufferHeight);

        int[] pixelArray = getFramebufferPixelArray();

        int[] croppedPixelArray = ImageUtils.cropPixelArray(
            pixelArray,
            framebufferWidth,
            framebufferHeight,
            width,
            height,
            (int) ((bounds.getLeft() - camera.getOffsetX()) * camera.getPointSize()),
            (int) ((bounds.getTop() - camera.getOffsetY()) * camera.getPointSize())
        );

        return new ImageData(width, height, croppedPixelArray);
    }

    public int[] getFramebufferPixelArray() {
        framebuffer.bind();
        IntBuffer pixels = framebuffer.getTexture().getPixels();
        framebuffer.unbind();

        int[] pixelArray = BufferUtils.toArray(pixels);

        flipY(framebuffer.getWidth(), framebuffer.getHeight(), pixelArray);
        return pixelArray;
    }

    private Path getScreenshotPath() {
        Path path;

        DisplayObject child = stageSprite.getChild(0);
        if (child.isMovieClip()) {
            MovieClip movieClip = (MovieClip) child;

            if (movieClip.getFrames().length > 1) {
                int currentFrame = movieClip.getCurrentFrame();
                String frameLabel = movieClip.getFrameLabel(currentFrame);
                String frameName = String.valueOf(currentFrame);
                if (frameLabel != null) {
                    frameName = String.join("-", frameName, frameLabel);
                }

                path = Path.of("screenshots", String.valueOf(child.getId()), frameName + ".png");
                return path;
            }
        }

        path = Path.of("screenshots", child.getId() + ".png");

        return path;
}

    public void unbindRender() {
        if (!this.initialized) return;

        if (this.framebuffer != null) {
            this.framebuffer.delete();
        }

        if (this.gradientTexture != null && this.gradientTexture.getTexture() != null) {
            this.gradientTexture.getTexture().delete();
            this.gradientTexture = null;
        }

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
            setRenderStencilState(batch.getStencilRenderingState());
            batch.render(this.gl);
        }
    }

    public void clearBatches() {
        for (Batch batch : this.batches) {
            batch.delete();
        }

        this.batches.clear();
    }

    private void unloadBatchesToPool() {
        for (Batch batch : this.batches) {
            batch.reset();
        }

        this.batchPool.pullBatches(this.batches);
        this.batches.clear();
    }

    public boolean startShape(Rect rect, Texture texture, int renderConfigBits) {
        if (this.isCalculatingBounds) {
            if (this.bounds != null) {
                this.bounds.mergeBounds(rect);
            }

            return false;
        }

        if (!this.camera.getClipArea().overlaps(rect)) {
            return false;
        }

        this.currentBatch = null;

        if (!this.batches.isEmpty()) {
            Batch lastBatch = this.batches.get(this.batches.size() - 1);
            if (lastBatch.getTexture() == texture) {
                this.currentBatch = lastBatch;
            }
        }

        if (this.currentBatch == null) {
            this.currentBatch = this.batchPool.createOrPopBatch(this.gl, texture, 0);
            this.batches.add(this.currentBatch);
        }

        return this.currentBatch.startShape(texture, renderConfigBits);
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
        Rect clipArea = this.camera.updateClipArea();

        PMVMatrix matrix = new PMVMatrix();
        matrix.glLoadIdentity();
        matrix.glOrthof(
            clipArea.getLeft(),
            clipArea.getRight(),
            clipArea.getBottom(),
            clipArea.getTop(),
            // near
            -1,
            // far
            1
        );

        FloatBuffer matrixBuffer = FloatBuffer.allocate(16);
        matrix.glGetFloatv(matrix.glGetMatrixMode(), matrixBuffer);

        this.shader.bind();
        this.gl.glUniformMatrix4fv(this.shader.getUniformLocation("pmv"), 1, false, matrixBuffer);
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

    public void setStencilRenderingState(int state) {
        this.batches.add(this.batchPool.createOrPopBatch(this.gl, null, state));
    }

    private void setRenderStencilState(int state) {
        switch (state) {
            case 1 -> {
                // scissors
            }
            case 2 -> {
                this.gl.glEnable(GL3.GL_STENCIL_TEST);
                this.gl.glStencilFunc(GL3.GL_ALWAYS, 1, 0xFF); // каждый фрагмент обновит трафаретный буфер
                this.gl.glStencilOp(GL3.GL_KEEP, GL3.GL_KEEP, GL3.GL_REPLACE);
                this.gl.glStencilMask(0xFF); // включить запись в трафаретный буфер
                this.gl.glColorMask(false, false, false, false);

                this.gl.glDepthMask(false);
                this.gl.glClear(GL3.GL_STENCIL_BUFFER_BIT); // Clear stencil buffer (0 by default)
            }
            case 3 -> {
                this.gl.glStencilFunc(GL3.GL_EQUAL, 1, 0xFF);
                this.gl.glStencilMask(0x00); // отключить запись в трафаретный буфер
                this.gl.glColorMask(true, true, true, true);
            }
            case 4 -> this.gl.glDisable(GL3.GL_STENCIL_TEST);
            case 5 -> {
                this.gl.glStencilFunc(GL3.GL_NOTEQUAL, 1, 0xFF);
                this.gl.glStencilMask(0x00); // отключить запись в трафаретный буфер
                this.gl.glColorMask(true, true, true, true);
            }
        }
    }

    public Camera getCamera() {
        return camera;
    }

    public GL3 getGl() {
        return this.gl;
    }

    public GLImage getGradientTexture() {
        return this.gradientTexture;
    }

    public StageSprite getStageSprite() {
        return stageSprite;
    }

    public Rect getDisplayObjectBounds(DisplayObject displayObject) {
        Rect bounds = new Rect();

        this.isCalculatingBounds = true;
        this.bounds = bounds;

        displayObject.render(new Matrix2x3(), new ColorTransform(), 0, 0);

        this.isCalculatingBounds = false;
        this.bounds = null;

        return bounds;
    }

    public void pauseAnimation() {
        isAnimationPaused = true;
    }

    public void resumeAnimation() {
        isAnimationPaused = false;
    }

    public boolean renderRect(Rect rect, Texture texture) {
        if (this.startShape(rect, texture, 0)) {
            this.addTriangles(2, RECT_INDICES);

            this.addVertex(rect.getLeft(), rect.getTop(), 0, 0, 1, 1, 1, 0, 0, 0, 1);
            this.addVertex(rect.getLeft(), rect.getBottom(), 0, 1, 1, 1, 1, 0, 0, 0, 1);
            this.addVertex(rect.getRight(), rect.getBottom(), 1, 1, 1, 1, 1, 0, 0, 0, 1);
            this.addVertex(rect.getRight(), rect.getTop(), 1, 0, 1, 1, 1, 0, 0, 0, 1);

            return true;
        }

        return false;
    }
}
