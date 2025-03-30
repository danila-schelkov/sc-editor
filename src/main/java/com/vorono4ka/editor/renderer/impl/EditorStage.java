package com.vorono4ka.editor.renderer.impl;

import com.jogamp.opengl.util.PMVMatrix;
import com.vorono4ka.editor.renderer.*;
import com.vorono4ka.editor.renderer.gl.*;
import com.vorono4ka.editor.renderer.gl.exceptions.ShaderCompilationException;
import com.vorono4ka.editor.renderer.impl.texture.GLImage;
import com.vorono4ka.editor.renderer.impl.texture.ImageFilter;
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
import com.vorono4ka.resources.AssetManager;
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

public class EditorStage implements Stage {
    private static final Logger LOGGER = LoggerFactory.getLogger(EditorStage.class);

    private static final Rect VIEWPORT_RECT = new Rect(-1, -1, 1, 1);
    private static final int[] RECT_INDICES = {0, 1, 2, 0, 2, 3};
    private static final Matrix2x3 DEFAULT_MATRIX = new Matrix2x3();
    private static final ColorTransform DEFAULT_COLOR_TRANSFORM = new ColorTransform();

    private static int STAGE_COUNT;
    private static EditorStage INSTANCE;

    private final ConcurrentLinkedQueue<Runnable> tasks = new ConcurrentLinkedQueue<>();
    private final Map<Integer, GLTexture> textures = new HashMap<>();
    private final List<Batch> batches = new ArrayList<>();
    private final Camera camera = new Camera();
    private final BatchPool batchPool = new BatchPool(this::constructBatch);
    private final StageSprite stageSprite;

    private boolean initialized;
    private Shader shader, screenShader;
    private AssetManager assetManager;
    private GLRendererContext gl;
    private Renderer renderer;

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

    private EditorStage() {
        this.stageSprite = new StageSprite(this);

        EditorStage.STAGE_COUNT++;
    }

    public static EditorStage getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EditorStage();
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
        this.shader = assetManager.getShader(
            "objects.vertex.glsl",
            "objects.fragment.glsl",
            new Attribute(0, 2, Float.BYTES, GLConstants.GL_FLOAT),
            new Attribute(1, 2, Float.BYTES, GLConstants.GL_FLOAT),
            new Attribute(2, 4, Float.BYTES, GLConstants.GL_FLOAT),
            new Attribute(3, 3, Float.BYTES, GLConstants.GL_FLOAT)
        );

        this.screenShader = assetManager.getShader(
            "screen.vertex.glsl",
            "screen.fragment.glsl",
            new Attribute(0, 2, Float.BYTES, GLConstants.GL_FLOAT),
            new Attribute(1, 2, Float.BYTES, GLConstants.GL_FLOAT)
        );

        BufferedImage imageBuffer = assetManager.getImageBuffer("gradient_texture.png");
        assert imageBuffer != null : "Gradient texture not found.";

        if (this.gradientTexture == null) {
            this.gradientTexture = GLImage.createWithFormat(null, null, true, ImageFilter.LINEAR, 256, 2, ImageUtils.getPixelBuffer(imageBuffer), GLConstants.GL_LUMINANCE_ALPHA, GLConstants.GL_UNSIGNED_BYTE);
        }

        this.camera.init(width, height);

        this.renderer.setViewport(x, y, width, height);
        this.framebuffer = new GLFramebuffer(gl, width, height);

        this.screenBatch = initScreenBatch(screenShader, framebuffer.getTexture(), VIEWPORT_RECT);

        this.updatePMVMatrix();

        this.renderer.bindBlendMode(BlendMode.PREMULTIPLIED_ALPHA);

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

    @Override
    public void render(float deltaTime) {
        this.stageSprite.render(DEFAULT_MATRIX, DEFAULT_COLOR_TRANSFORM, 0, deltaTime);

        renderToFramebuffer(this.framebuffer);

        renderScreen();
    }

    public void renderToFramebuffer(Framebuffer framebuffer) {
        framebuffer.bind();
        renderDisplayObject();
        framebuffer.unbind();

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

        this.renderer.bindBlendMode(BlendMode.DISABLED);
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
    public void addVertex(float x, float y, float u, float v, float redMul, float greenMul, float blueMul, float alpha, float redAdd, float greenAdd, float blueAdd) {
        if (this.currentBatch == null) return;

        this.currentBatch.addVertex(x, y, u, v, redMul, greenMul, blueMul, alpha, redAdd, greenAdd, blueAdd);
    }

    @Override
    public void setStencilRenderingState(RenderStencilState state) {
        if (this.isCalculatingBounds) {
            switch (state) {
                case ENABLED -> {
                    this.isCalculatingMaskBounds = true;
                    this.maskBounds = new Rect();
                }
                case RENDERING_MASKED -> {
                    this.isCalculatingMaskBounds = false;
                    this.isApplyingMaskBounds = true;
                }
                case DISABLED -> this.isApplyingMaskBounds = false;
            }

            return;
        }

        this.batches.add(this.batchPool.createOrPopBatch(shader, null, state));
    }

    @Override
    public GLTexture getTextureByIndex(int index) {
        return this.textures.get(index);
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

        FloatBuffer matrixBuffer = BufferUtils.allocateDirectFloat(16);
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

    public void setFramebuffer(Framebuffer framebuffer) {
        if (framebuffer == null) {
            throw new RuntimeException("Attempt to set null framebuffer");
        }

        this.framebuffer = framebuffer;
        this.screenBatch = initScreenBatch(screenShader, framebuffer.getTexture(), VIEWPORT_RECT);
    }

    public StageSprite getStageSprite() {
        return stageSprite;
    }

    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public GLRendererContext getGlContext() {
        return gl;
    }

    public void setGlContext(GLRendererContext glRendererContext) {
        gl = glRendererContext;

        this.renderer = new GLRenderer(glRendererContext);
        this.renderer.printInfo();
    }

    public Renderer getRenderer() {
        return renderer;
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

        GLTexture image = GLImage.createWithFormat(ktxData, sctxTexture, true, ImageFilter.values()[texture.getInitialTag().getTextureFilter()], texture.getWidth(), texture.getHeight(), texture.getPixels(), texture.getTextureInfo().pixelFormat(), texture.getTextureInfo().pixelType());
        this.textures.put(texture.getIndex(), image);

        return image;
    }

    public GLTexture createGLTexture(SctxTexture texture, int index) {
        GLTexture image = GLImage.createWithFormat(null, texture, true, ImageFilter.LINEAR, texture.getWidth(), texture.getHeight(), null, SctxPixelType.getFormat(texture.getPixelType()), SctxPixelType.getPixelType(texture.getPixelType()));
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

        // Note: Passing own sprite as parent to provide Stage reference
        boolean parentSet = false;
        if (displayObject.getParent() == null) {
            displayObject.setParent(this.stageSprite);
            parentSet = true;
        }

        displayObject.render(DEFAULT_MATRIX, DEFAULT_COLOR_TRANSFORM, 0, 0);

        if (parentSet) {
            displayObject.setParent(null);
        }

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
        this.renderer.clearColor(0, 0, 0, 0);
        this.renderer.clear(GLConstants.GL_COLOR_BUFFER_BIT | GLConstants.GL_DEPTH_BUFFER_BIT | GLConstants.GL_STENCIL_BUFFER_BIT);

        this.renderer.clearStencil();

        this.shader.bind();
        this.renderBuckets();
        this.shader.unbind();
    }

    private void renderScreen() {
        this.renderer.clearColor(.5f, .5f, .5f, 1);
        this.renderer.clear(GLConstants.GL_COLOR_BUFFER_BIT | GLConstants.GL_DEPTH_BUFFER_BIT | GLConstants.GL_STENCIL_BUFFER_BIT);

        this.screenShader.bind();
        this.screenBatch.render();
        this.screenShader.unbind();
    }

    private void renderBuckets() {
        for (Batch batch : this.batches) {
            this.renderer.setRenderStencilState(batch.getStencilRenderingState());
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

    private Batch initScreenBatch(Shader shader, RenderableTexture texture, Rect rect) {
        Batch screenBatch = constructBatch(shader, texture, RenderStencilState.NONE);
        screenBatch.init();

        screenBatch.addTriangles(2, RECT_INDICES);

        screenBatch.addVertex(rect.getLeft(), rect.getTop(), 0, 0);
        screenBatch.addVertex(rect.getLeft(), rect.getBottom(), 0, 1);
        screenBatch.addVertex(rect.getRight(), rect.getBottom(), 1, 1);
        screenBatch.addVertex(rect.getRight(), rect.getTop(), 1, 0);

        return screenBatch;
    }

    private Batch constructBatch(Shader shader, RenderableTexture texture, RenderStencilState stencilRenderingState) {
        return new GLBatch(shader, texture, stencilRenderingState, this.gl);
    }
}
