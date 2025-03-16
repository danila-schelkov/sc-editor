package com.vorono4ka.editor.renderer;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.util.PMVMatrix;
import com.vorono4ka.editor.Main;
import com.vorono4ka.editor.renderer.exceptions.ShaderCompilationException;
import com.vorono4ka.editor.renderer.texture.GLImage;
import com.vorono4ka.editor.renderer.texture.Texture;
import com.vorono4ka.math.ReadonlyRect;
import com.vorono4ka.math.Rect;
import com.vorono4ka.resources.Assets;
import com.vorono4ka.sctx.FlatSctxTextureLoader;
import com.vorono4ka.sctx.SctxTexture;
import com.vorono4ka.swf.ColorTransform;
import com.vorono4ka.swf.Matrix2x3;
import com.vorono4ka.swf.displayobjects.DisplayObject;
import com.vorono4ka.swf.displayobjects.MovieClip;
import com.vorono4ka.swf.displayobjects.StageSprite;
import com.vorono4ka.swf.exceptions.TextureFileNotFound;
import com.vorono4ka.swf.file.compression.Zstandard;
import com.vorono4ka.swf.movieclips.MovieClipState;
import com.vorono4ka.swf.textures.SWFTexture;
import com.vorono4ka.utilities.BufferUtils;
import com.vorono4ka.utilities.ImageUtils;
import com.vorono4ka.utilities.MovieClipHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Stage {
    private static final Logger LOGGER = LoggerFactory.getLogger(Stage.class);

    private static final Rect VIEWPORT_RECT = new Rect(-1, -1, 1, 1);
    private static final int[] RECT_INDICES = {0, 1, 2, 0, 2, 3};

    private static int STAGE_COUNT;
    private static Stage INSTANCE;

    private final ConcurrentLinkedQueue<Runnable> tasks = new ConcurrentLinkedQueue<>();
    private final Map<Integer, GLImage> images = new HashMap<>();
    private final List<Batch> batches = new ArrayList<>();
    private final Camera camera = new Camera();
    private final BatchPool batchPool = new BatchPool();
    private final StageSprite stageSprite;

    private boolean initialized;
    private Shader shader, screenShader;
    private GL3 gl;

    private Batch currentBatch;
    private Batch screenBatch;
    private GLImage gradientTexture;
    private Framebuffer framebuffer;

    private boolean isCalculatingBounds;
    private Rect bounds;
    private boolean isAnimationPaused;
    private boolean isCalculatingMaskBounds;
    private Rect maskBounds;
    private boolean isApplyingMaskBounds;

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

    private static byte[] getTextureFileBytes(Path directory, String compressedTextureFilename) throws TextureFileNotFound {
        Path filepath = directory.resolve(compressedTextureFilename);

        byte[] compressedData;
        try (FileInputStream fis = new FileInputStream(filepath.toFile())) {
            compressedData = fis.readAllBytes();
        } catch (FileNotFoundException e) {
            throw new TextureFileNotFound(filepath.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return compressedData;
    }

    public void init(GL3 gl, int x, int y, int width, int height) throws ShaderCompilationException {
        this.gl = gl;

        this.shader = Assets.getShader(
            this.gl,
            "objects.vertex.glsl",
            "objects.fragment.glsl",
            new Attribute(0, 2, Float.BYTES, GL3.GL_FLOAT),
            new Attribute(1, 2, Float.BYTES, GL3.GL_FLOAT),
            new Attribute(2, 4, Float.BYTES, GL3.GL_FLOAT),
            new Attribute(3, 3, Float.BYTES, GL3.GL_FLOAT)
        );
        this.screenShader = Assets.getShader(
            this.gl,
            "screen.vertex.glsl",
            "screen.fragment.glsl",
            new Attribute(0, 2, Float.BYTES, GL3.GL_FLOAT),
            new Attribute(1, 2, Float.BYTES, GL3.GL_FLOAT)
        );

        BufferedImage imageBuffer = Assets.getImageBuffer("gradient_texture.png");
        assert imageBuffer != null : "Gradient texture not found.";

        if (this.gradientTexture == null) {
            this.gradientTexture = new GLImage();
        }

        this.gradientTexture.createWithFormat(null, null, true, 1, 256, 2, ImageUtils.getPixelBuffer(imageBuffer), GL3.GL_LUMINANCE_ALPHA, GL3.GL_UNSIGNED_BYTE);

        this.camera.init(width, height);

        gl.glViewport(x, y, width, height);
        this.framebuffer = new Framebuffer(gl, width, height);

        this.screenBatch = initScreenBatch(screenShader, framebuffer.getTexture(), VIEWPORT_RECT);

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
                LOGGER.error("An error occurred in the Render Thread", e);
            }

            iterator.remove();
        }

        float deltaTime = 1f / Main.editor.getWindow().getTargetFps(); // TODO: calculate delta time more precisely

        if (isAnimationPaused) {
            deltaTime = 0;
        }

        render(deltaTime);
    }

    public void render(float deltaTime) {
        this.stageSprite.render(new Matrix2x3(), new ColorTransform(), 0, deltaTime);

        this.framebuffer.bind();
        renderDisplayObject();
        this.framebuffer.unbind();

        this.unloadBatchesToPool();

        renderScreen();

        this.unloadBatchesToPool();
    }

    public Rect calculateBoundsForAllFrames(DisplayObject displayObject) {
        if (displayObject.isMovieClip()) {
            MovieClip movieClip = (MovieClip) displayObject;

            // FIXME: very inefficient way out of recursion
            if (movieClip.getFrameCountRecursive() > 1) {
                Rect bounds = new Rect();

                // Saving last movie clip state
                int loopFrame = movieClip.getLoopFrame();
                int currentFrame = movieClip.getCurrentFrame();
                MovieClipState state = movieClip.getState();

                // Calculating bounds for all frames
                MovieClipHelper.doForAllFrames(movieClip, (frameIndex) -> bounds.mergeBounds(getDisplayObjectBounds(movieClip)));

                // Rolling movie clip state back
                movieClip.gotoAbsoluteTimeRecursive(currentFrame * movieClip.getMsPerFrame());
                movieClip.gotoAndPlayFrameIndex(currentFrame, loopFrame, state);

                return bounds;
            }
        }

        return getDisplayObjectBounds(displayObject);
    }

    public void unbindRender() {
        if (!this.initialized) return;

        if (this.framebuffer != null) {
            this.framebuffer.delete();
        }

        this.screenBatch.delete();
        this.screenBatch = null;

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

    public void clearBatches() {
        for (Batch batch : this.batches) {
            batch.delete();
        }

        this.batches.clear();
    }

    public boolean startShape(Rect rect, Texture texture, int renderConfigBits) {
        return startShape(shader, rect, texture, renderConfigBits, camera.getClipArea());
    }

    public boolean startShape(Shader shader, Rect rect, Texture texture, int renderConfigBits, ReadonlyRect clipArea) {
        if (this.isCalculatingBounds) {
            if (this.isCalculatingMaskBounds) {
                this.maskBounds.mergeBounds(rect);
                return false;
            }

            if (this.bounds != null) {
                if (this.isApplyingMaskBounds) {
                    rect.clamp(this.maskBounds);
                }

                this.bounds.mergeBounds(rect);
            }

            return false;
        }

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
            this.currentBatch = this.batchPool.createOrPopBatch(gl, shader, texture, 0);
            this.batches.add(this.currentBatch);
        }

        return this.currentBatch.startShape(renderConfigBits);
    }

    public void addTriangles(int count, int[] indices) {
        if (this.currentBatch == null) return;

        this.currentBatch.addTriangles(count, indices);
    }

    public void addVertex(float x, float y, float u, float v, float redMul, float greenMul, float blueMul, float alpha, float redAdd, float greenAdd, float blueAdd) {
        if (this.currentBatch == null) return;

        this.currentBatch.addVertex(x, y, u, v, redMul, greenMul, blueMul, alpha, redAdd, greenAdd, blueAdd);
    }

    public void addVertex(Float... parameters) {
        if (this.currentBatch == null) return;

        this.currentBatch.addVertex(parameters);
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

        FloatBuffer matrixBuffer = BufferUtils.allocateDirect(16 * Float.BYTES).asFloatBuffer();
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
        if (this.isCalculatingBounds) {
            switch (state) {
                case 2 -> {
                    this.isCalculatingMaskBounds = true;
                    this.maskBounds = new Rect();
                }
                case 3 -> {
                    this.isCalculatingMaskBounds = false;
                    this.isApplyingMaskBounds = true;
                }
                case 4 -> this.isApplyingMaskBounds = false;
            }

            return;
        }
        this.batches.add(this.batchPool.createOrPopBatch(gl, shader, null, state));
    }

    public Camera getCamera() {
        return camera;
    }

    public Framebuffer getFramebuffer() {
        return framebuffer;
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

    public GLImage getImageByIndex(int index) {
        return this.images.get(index);
    }

    public GLImage createGLImage(SWFTexture texture, Path directory) throws TextureFileNotFound {
        byte[] ktxData = texture.getKtxData();
        String textureFilename = texture.getTextureFilename();
        SctxTexture sctxTexture = null;

        if (ktxData == null && textureFilename != null) {
            if (textureFilename.endsWith(".zktx")) {
                byte[] compressedData = getTextureFileBytes(directory, textureFilename);
                ktxData = Zstandard.decompress(compressedData, 0);
            } else if (textureFilename.endsWith(".sctx")) {
                sctxTexture = loadSctxTexture(directory, textureFilename);
            }
        }

        GLImage image = new GLImage();
        image.createWithFormat(ktxData, sctxTexture, false, texture.getTag().getTextureFilter(), texture.getWidth(), texture.getHeight(), texture.getPixels(), texture.getTextureInfo().pixelFormat(), texture.getTextureInfo().pixelType());
        this.images.put(texture.getIndex(), image);

        return image;
    }

    private static SctxTexture loadSctxTexture(Path directory, String textureFilename) {
        try (FileInputStream inputStream = new FileInputStream(directory.resolve(textureFilename).toFile())) {
            return FlatSctxTextureLoader.getInstance().load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    private void renderDisplayObject() {
        this.gl.glClearColor(0, 0, 0, 0);
        this.gl.glClear(GL3.GL_COLOR_BUFFER_BIT | GL3.GL_DEPTH_BUFFER_BIT | GL3.GL_STENCIL_BUFFER_BIT);

        this.gl.glStencilMask(0xFF);
        this.gl.glClearStencil(0);
        this.gl.glStencilMask(0);

        this.shader.bind();
        this.renderBuckets();
        this.shader.unbind();
    }

    private void renderScreen() {
        this.gl.glClearColor(.5f, .5f, .5f, 1);
        this.gl.glClear(GL3.GL_COLOR_BUFFER_BIT | GL3.GL_DEPTH_BUFFER_BIT | GL3.GL_STENCIL_BUFFER_BIT);

        this.screenShader.bind();
        this.screenBatch.render(this.gl);
        this.screenShader.unbind();
    }

    private void renderBuckets() {
        for (Batch batch : this.batches) {
            setRenderStencilState(batch.getStencilRenderingState());
            batch.render(this.gl);
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

    private Batch initScreenBatch(Shader shader, Texture texture, Rect rect) {
        Batch screenBatch = new Batch(shader, texture, 0);
        screenBatch.init(this.gl);

        screenBatch.addTriangles(2, RECT_INDICES);

        screenBatch.addVertex(rect.getLeft(), rect.getTop(), 0, 0);
        screenBatch.addVertex(rect.getLeft(), rect.getBottom(), 0, 1);
        screenBatch.addVertex(rect.getRight(), rect.getBottom(), 1, 1);
        screenBatch.addVertex(rect.getRight(), rect.getTop(), 1, 0);

        return screenBatch;
    }

    public void setFramebuffer(Framebuffer framebuffer) {
        if (framebuffer == null) {
            throw new RuntimeException("Attempt to set null framebuffer");
        }

        this.framebuffer = framebuffer;
    }
}
