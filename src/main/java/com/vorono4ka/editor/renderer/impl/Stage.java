package com.vorono4ka.editor.renderer.impl;

import com.jogamp.opengl.util.PMVMatrix;
import com.vorono4ka.editor.renderer.*;
import com.vorono4ka.editor.renderer.gl.GLBatch;
import com.vorono4ka.editor.renderer.gl.GLConstants;
import com.vorono4ka.editor.renderer.gl.GLFramebuffer;
import com.vorono4ka.editor.renderer.gl.GLRendererContext;
import com.vorono4ka.editor.renderer.gl.exceptions.ShaderCompilationException;
import com.vorono4ka.editor.renderer.impl.texture.GLImage;
import com.vorono4ka.editor.renderer.impl.texture.sctx.SctxPixelType;
import com.vorono4ka.editor.renderer.shader.Attribute;
import com.vorono4ka.editor.renderer.shader.Shader;
import com.vorono4ka.editor.renderer.texture.GLTexture;
import com.vorono4ka.editor.renderer.texture.RenderableTexture;
import com.vorono4ka.math.ReadonlyRect;
import com.vorono4ka.math.Rect;
import com.vorono4ka.renderer.impl.swf.objects.DisplayObject;
import com.vorono4ka.renderer.impl.swf.objects.MovieClip;
import com.vorono4ka.renderer.impl.swf.objects.StageSprite;
import com.vorono4ka.resources.Assets;
import com.vorono4ka.sctx.FlatSctxTextureLoader;
import com.vorono4ka.sctx.SctxTexture;
import com.vorono4ka.swf.ColorTransform;
import com.vorono4ka.swf.Matrix2x3;
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

public class Stage implements Renderer {
    private static final Logger LOGGER = LoggerFactory.getLogger(Stage.class);

    private static final Rect VIEWPORT_RECT = new Rect(-1, -1, 1, 1);
    private static final int[] RECT_INDICES = {0, 1, 2, 0, 2, 3};

    private static int STAGE_COUNT;
    private static Stage INSTANCE;

    private final ConcurrentLinkedQueue<Runnable> tasks = new ConcurrentLinkedQueue<>();
    private final Map<Integer, GLTexture> textures = new HashMap<>();
    private final List<Batch> batches = new ArrayList<>();
    private final Camera camera = new Camera();
    private final BatchPool batchPool = new BatchPool(this::constructBatch);
    private final StageSprite stageSprite;

    private boolean initialized;
    private Shader shader, screenShader;
    private GLRendererContext gl;

    private Batch currentBatch;
    private Batch screenBatch;
    private GLTexture gradientTexture;
    private Framebuffer framebuffer;

    private Rect bounds;
    private Rect maskBounds;
    private boolean isCalculatingBounds;
    private boolean isCalculatingMaskBounds;
    private boolean isApplyingMaskBounds;
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

    public void init(int x, int y, int width, int height) throws ShaderCompilationException {
        this.shader = Assets.getShader(
            this.gl,
            "objects.vertex.glsl",
            "objects.fragment.glsl",
            new Attribute(0, 2, Float.BYTES, GLConstants.GL_FLOAT),
            new Attribute(1, 2, Float.BYTES, GLConstants.GL_FLOAT),
            new Attribute(2, 4, Float.BYTES, GLConstants.GL_FLOAT),
            new Attribute(3, 3, Float.BYTES, GLConstants.GL_FLOAT)
        );
        this.screenShader = Assets.getShader(
            this.gl,
            "screen.vertex.glsl",
            "screen.fragment.glsl",
            new Attribute(0, 2, Float.BYTES, GLConstants.GL_FLOAT),
            new Attribute(1, 2, Float.BYTES, GLConstants.GL_FLOAT)
        );

        BufferedImage imageBuffer = Assets.getImageBuffer("gradient_texture.png");
        assert imageBuffer != null : "Gradient texture not found.";

        if (this.gradientTexture == null) {
            this.gradientTexture = GLImage.createWithFormat(null, null, true, 1, 256, 2, ImageUtils.getPixelBuffer(imageBuffer), GLConstants.GL_LUMINANCE_ALPHA, GLConstants.GL_UNSIGNED_BYTE);
        }

        this.camera.init(width, height);

        gl.glViewport(x, y, width, height);
        this.framebuffer = new GLFramebuffer(gl, width, height);

        this.screenBatch = initScreenBatch(screenShader, framebuffer.getTexture(), VIEWPORT_RECT);

        this.updatePMVMatrix();

        gl.glEnable(GLConstants.GL_BLEND);
        gl.glBlendEquation(GLConstants.GL_FUNC_ADD);
        gl.glBlendFunc(GLConstants.GL_ONE, GLConstants.GL_ONE_MINUS_SRC_ALPHA);

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

        float deltaTime = 1f / 60; // TODO: calculate delta time more precisely

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

        if (this.gradientTexture != null) {
            this.gradientTexture.delete();
            this.gradientTexture = null;
        }

        this.clearBatches();

        this.initialized = false;
        this.gl.glDisableVertexAttribArray(0);
        this.gl.glDisableVertexAttribArray(1);
        this.gl.glBindBuffer(GLConstants.GL_ARRAY_BUFFER, 0);
        this.gl.glBindBuffer(GLConstants.GL_ELEMENT_ARRAY_BUFFER, 0);

        this.gl.glDisable(GLConstants.GL_BLEND);
    }

    public void clearBatches() {
        for (Batch batch : this.batches) {
            batch.delete();
        }

        this.batches.clear();
    }

    @Override
    public boolean startShape(Rect rect, RenderableTexture texture, int renderConfigBits) {
        return startShape(shader, rect, texture, renderConfigBits, camera.getClipArea());
    }

    @Override
    public boolean startShape(Shader shader, Rect rect, RenderableTexture texture, int renderConfigBits, ReadonlyRect clipArea) {
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
            this.currentBatch = this.batchPool.createOrPopBatch(shader, texture, 0);
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
    public void addVertex(float x, float y, float u, float v, float redMul, float greenMul, float blueMul, float alpha, float redAdd, float greenAdd, float blueAdd) {
        if (this.currentBatch == null) return;

        this.currentBatch.addVertex(x, y, u, v, redMul, greenMul, blueMul, alpha, redAdd, greenAdd, blueAdd);
    }

    @Override
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

        this.batches.add(this.batchPool.createOrPopBatch(shader, null, state));
    }

    @Override
    public GLTexture getTextureByIndex(int index) {
        return this.textures.get(index);
    }

    @Override
    public RenderableTexture getGradientTexture() {
        return this.gradientTexture;
    }

    public int getTextureCount() {
        return this.textures.size();
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
        this.shader.setUniformMatrix4f("pmv", matrixBuffer);
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

    public Camera getCamera() {
        return camera;
    }

    public Framebuffer getFramebuffer() {
        return framebuffer;
    }

    public StageSprite getStageSprite() {
        return stageSprite;
    }

    public GLRendererContext getGlContext() {
        return gl;
    }

    public void setGlContext(GLRendererContext glRendererContext) {
        gl = glRendererContext;
    }

    public GLTexture createGLTexture(SWFTexture texture, Path directory) throws TextureFileNotFound {
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

        GLTexture image = GLImage.createWithFormat(ktxData, sctxTexture, true, texture.getTag().getTextureFilter(), texture.getWidth(), texture.getHeight(), texture.getPixels(), texture.getTextureInfo().pixelFormat(), texture.getTextureInfo().pixelType());
        this.textures.put(texture.getIndex(), image);

        return image;
    }

    public GLTexture createGLTexture(SctxTexture texture, int index) {
        GLTexture image = GLImage.createWithFormat(null, texture, true, 1, texture.getWidth(), texture.getHeight(), null, SctxPixelType.getFormat(texture.getPixelType()), SctxPixelType.getPixelType(texture.getPixelType()));
        this.textures.put(index, image);

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
        this.gl.glClear(GLConstants.GL_COLOR_BUFFER_BIT | GLConstants.GL_DEPTH_BUFFER_BIT | GLConstants.GL_STENCIL_BUFFER_BIT);

        this.gl.glStencilMask(0xFF);
        this.gl.glClearStencil(0);
        this.gl.glStencilMask(0);

        this.shader.bind();
        this.renderBuckets();
        this.shader.unbind();
    }

    private void renderScreen() {
        this.gl.glClearColor(.5f, .5f, .5f, 1);
        this.gl.glClear(GLConstants.GL_COLOR_BUFFER_BIT | GLConstants.GL_DEPTH_BUFFER_BIT | GLConstants.GL_STENCIL_BUFFER_BIT);

        this.screenShader.bind();
        this.screenBatch.render();
        this.screenShader.unbind();
    }

    private void renderBuckets() {
        for (Batch batch : this.batches) {
            setRenderStencilState(batch.getStencilRenderingState());
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

    private void setRenderStencilState(int state) {
        switch (state) {
            case 1 -> {
                // scissors
            }
            case 2 -> {
                this.gl.glEnable(GLConstants.GL_STENCIL_TEST);
                this.gl.glStencilFunc(GLConstants.GL_ALWAYS, 1, 0xFF); // каждый фрагмент обновит трафаретный буфер
                this.gl.glStencilOp(GLConstants.GL_KEEP, GLConstants.GL_KEEP, GLConstants.GL_REPLACE);
                this.gl.glStencilMask(0xFF); // включить запись в трафаретный буфер
                this.gl.glColorMask(false, false, false, false);

                this.gl.glDepthMask(false);
                this.gl.glClear(GLConstants.GL_STENCIL_BUFFER_BIT); // Clear stencil buffer (0 by default)
            }
            case 3 -> {
                this.gl.glStencilFunc(GLConstants.GL_EQUAL, 1, 0xFF);
                this.gl.glStencilMask(0x00); // отключить запись в трафаретный буфер
                this.gl.glColorMask(true, true, true, true);
            }
            case 4 -> this.gl.glDisable(GLConstants.GL_STENCIL_TEST);
            case 5 -> {
                this.gl.glStencilFunc(GLConstants.GL_NOTEQUAL, 1, 0xFF);
                this.gl.glStencilMask(0x00); // отключить запись в трафаретный буфер
                this.gl.glColorMask(true, true, true, true);
            }
        }
    }

    private Batch initScreenBatch(Shader shader, RenderableTexture texture, Rect rect) {
        Batch screenBatch = new GLBatch(shader, texture, 0, gl);
        screenBatch.init();

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

    private Batch constructBatch(Shader shader1, RenderableTexture texture, int stencilRenderingState) {
        return new GLBatch(shader1, texture, stencilRenderingState, this.gl);
    }
}
