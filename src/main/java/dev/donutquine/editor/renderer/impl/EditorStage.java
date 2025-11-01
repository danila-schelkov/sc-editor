package dev.donutquine.editor.renderer.impl;

import com.jogamp.opengl.util.PMVMatrix;
import dev.donutquine.editor.renderer.*;
import dev.donutquine.editor.renderer.gl.*;
import dev.donutquine.editor.renderer.gl.exceptions.ShaderCompilationException;
import dev.donutquine.editor.renderer.gl.texture.GLTexture;
import dev.donutquine.editor.renderer.impl.texture.GLImage;
import dev.donutquine.editor.renderer.impl.texture.ImageFilter;
import dev.donutquine.editor.renderer.impl.texture.sctx.SctxPixelType;
import dev.donutquine.editor.renderer.shader.Attribute;
import dev.donutquine.editor.renderer.shader.Shader;
import dev.donutquine.editor.renderer.texture.RenderableTexture;
import dev.donutquine.math.Rect;
import dev.donutquine.renderer.impl.swf.objects.DisplayObject;
import dev.donutquine.renderer.impl.swf.objects.MovieClip;
import dev.donutquine.renderer.impl.swf.objects.StageSprite;
import dev.donutquine.resources.AssetManager;
import dev.donutquine.sctx.FlatSctxTextureLoader;
import dev.donutquine.sctx.SctxTexture;
import dev.donutquine.swf.ColorTransform;
import dev.donutquine.swf.Matrix2x3;
import dev.donutquine.swf.exceptions.TextureFileNotFound;
import dev.donutquine.swf.file.compression.Zstandard;
import dev.donutquine.swf.movieclips.MovieClipState;
import dev.donutquine.swf.textures.SWFTexture;
import dev.donutquine.utilities.BufferUtils;
import dev.donutquine.utilities.ImageUtils;
import dev.donutquine.utilities.MovieClipHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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
    private final Camera camera = new Camera();
    private final StageSprite stageSprite;

    private boolean initialized;
    private Shader shader, screenShader;
    private AssetManager assetManager;
    private GLRendererContext gl;
    private BatchedRenderer renderer;

    private Batch screenBatch;
    private GLTexture gradientTexture;
    private Framebuffer framebuffer;

    private Rect bounds;
    private Rect maskBounds;
    private boolean isCalculatingBounds;
    private boolean isCalculatingMaskBounds;
    private boolean isApplyingMaskBounds;
    private boolean isAnimationPaused;
    private boolean isWireframeEnabled;

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
            this.gradientTexture = GLImage.createWithFormat(256, 2, true, ImageFilter.LINEAR, GLConstants.GL_LUMINANCE_ALPHA, GLConstants.GL_UNSIGNED_BYTE, ImageUtils.getPixelBuffer(imageBuffer), null, null);
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
        this.renderer.beginRendering();
        framebuffer.bind();
        renderDisplayObject();
        if (isWireframeEnabled) {
            gl.glPolygonMode(GLConstants.GL_FRONT_AND_BACK, GLConstants.GL_LINE);
            renderDisplayObject();
            gl.glPolygonMode(GLConstants.GL_FRONT_AND_BACK, GLConstants.GL_FILL);
        }
        framebuffer.unbind();
        this.renderer.endRendering();
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

        this.renderer.reset();

        this.initialized = false;
        this.gl.glDisableVertexAttribArray(0);
        this.gl.glDisableVertexAttribArray(1);
        this.gl.glBindBuffer(GLConstants.GL_ARRAY_BUFFER, 0);
        this.gl.glBindBuffer(GLConstants.GL_ELEMENT_ARRAY_BUFFER, 0);

        this.renderer.bindBlendMode(BlendMode.DISABLED);
    }

    @Override
    public void reset() {
        this.renderer.reset();
        this.removeAllChildren();
    }

    @Override
    public boolean startShape(Rect rect, RenderableTexture texture, int renderConfigBits) {
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

        return renderer.startShape(shader, rect, texture, renderConfigBits, camera.getClipArea());
    }

    @Override
    public void addTriangles(int count, int[] indices) {
        this.renderer.addTriangles(count, indices);
    }

    @Override
    public void addVertex(float x, float y, float u, float v, float redMul, float greenMul, float blueMul, float alpha, float redAdd, float greenAdd, float blueAdd) {
        this.renderer.addVertex(x, y, u, v, redMul, greenMul, blueMul, alpha, redAdd, greenAdd, blueAdd);
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

        renderer.setStencilRenderingState(shader, state);
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

        this.renderer = new BatchedRenderer(this::constructBatch, new GLRenderer(glRendererContext));
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

        GLTexture image = GLImage.createWithFormat(texture.getWidth(), texture.getHeight(), true, ImageFilter.values()[texture.getInitialTag().getTextureFilter()], texture.getType().glFormat, texture.getType().glType, texture.getPixels(), sctxTexture, ktxData);
        this.textures.put(texture.getIndex(), image);

        return image;
    }

    public GLTexture createGLTexture(SctxTexture texture, int index) {
        GLTexture image = GLImage.createWithFormat(texture.getWidth(), texture.getHeight(), true, ImageFilter.LINEAR, SctxPixelType.getFormat(texture.getPixelType()), SctxPixelType.getPixelType(texture.getPixelType()), null, texture, null);
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

    public void setWireframeEnabled(boolean wireframeEnabled) {
        isWireframeEnabled = wireframeEnabled;
    }

    private void renderDisplayObject() {
        this.renderer.clearColor(0, 0, 0, 0);
        this.renderer.clear(GLConstants.GL_COLOR_BUFFER_BIT | GLConstants.GL_DEPTH_BUFFER_BIT | GLConstants.GL_STENCIL_BUFFER_BIT);

        this.renderer.clearStencil();

        this.renderer.flush();
    }

    private void renderScreen() {
        this.renderer.clearColor(.5f, .5f, .5f, 1);
        this.renderer.clear(GLConstants.GL_COLOR_BUFFER_BIT | GLConstants.GL_DEPTH_BUFFER_BIT | GLConstants.GL_STENCIL_BUFFER_BIT);

        // TODO: change to renderer.drawTexture(framebuffer.getTexture(), viewport)
        this.screenShader.bind();
        this.screenBatch.render();
        this.screenShader.unbind();
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
        return new Batch(shader, texture, stencilRenderingState, this::createDynamicVertexBuffer);
    }

    private VertexBuffer createDynamicVertexBuffer(Attribute... attributes) {
        return new GLVertexBuffer(this.gl, GLConstants.GL_DYNAMIC_DRAW, attributes);
    }
}
