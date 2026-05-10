package dev.donutquine.editor.renderer.impl;

import java.awt.image.BufferedImage;
import java.nio.FloatBuffer;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jogamp.opengl.util.PMVMatrix;
import dev.donutquine.editor.gizmos.Gizmos;
import dev.donutquine.editor.renderer.BasicDrawApi;
import dev.donutquine.editor.renderer.Batch;
import dev.donutquine.editor.renderer.BatchedRenderer;
import dev.donutquine.editor.renderer.BlendMode;
import dev.donutquine.editor.renderer.Camera;
import dev.donutquine.editor.renderer.DrawApi;
import dev.donutquine.editor.renderer.Framebuffer;
import dev.donutquine.editor.renderer.RenderStencilState;
import dev.donutquine.editor.renderer.Renderer;
import dev.donutquine.editor.renderer.RendererContext;
import dev.donutquine.editor.renderer.Stage;
import dev.donutquine.editor.renderer.VertexBuffer;
import dev.donutquine.editor.renderer.gl.GLConstants;
import dev.donutquine.editor.renderer.gl.GLContext;
import dev.donutquine.editor.renderer.gl.GLFramebuffer;
import dev.donutquine.editor.renderer.gl.GLRendererContext;
import dev.donutquine.editor.renderer.gl.GLVertexBuffer;
import dev.donutquine.editor.renderer.gl.exceptions.ShaderCompilationException;
import dev.donutquine.editor.renderer.gl.texture.GLTexture;
import dev.donutquine.editor.renderer.impl.texture.GLImage;
import dev.donutquine.editor.renderer.impl.texture.ImageFilter;
import dev.donutquine.editor.renderer.shader.Attribute;
import dev.donutquine.editor.renderer.shader.Shader;
import dev.donutquine.editor.renderer.texture.RenderableTexture;
import dev.donutquine.math.ReadonlyRect;
import dev.donutquine.math.Rect;
import dev.donutquine.renderer.impl.swf.objects.DisplayObject;
import dev.donutquine.renderer.impl.swf.objects.MovieClip;
import dev.donutquine.renderer.impl.swf.objects.StageSprite;
import dev.donutquine.resources.AssetManager;
import dev.donutquine.swf.ColorTransform;
import dev.donutquine.swf.Matrix2x3;
import dev.donutquine.swf.movieclips.MovieClipState;
import dev.donutquine.utilities.BufferUtils;
import dev.donutquine.utilities.ImageUtils;
import dev.donutquine.utilities.MovieClipHelper;

public class EditorStage implements Stage {
    private static final Logger LOGGER = LoggerFactory.getLogger(EditorStage.class);

    private static final Matrix2x3 DEFAULT_MATRIX = new Matrix2x3();
    private static final ColorTransform DEFAULT_COLOR_TRANSFORM = new ColorTransform();

    private static EditorStage INSTANCE;

    private final ConcurrentLinkedQueue<Runnable> tasks = new ConcurrentLinkedQueue<>();
    private final Camera camera = new Camera();
    private final StageSprite stageSprite = new StageSprite(this);
    private final Gizmos gizmos = new Gizmos(this);

    private boolean initialized;
    private Shader shader;
    private AssetManager assetManager;
    private GLContext gl;
    private RendererContext rendererContext;
    private Renderer renderer;
    private DrawApi drawApi;

    private GLTexture gradientTexture;
    private Framebuffer framebuffer;

    private Rect bounds;
    private Rect maskBounds;
    private boolean isCalculatingBounds;
    private boolean isCalculatingMaskBounds;
    private boolean isApplyingMaskBounds;
    private boolean isAnimationPaused;
    private boolean isWireframeEnabled;
    private Consumer<FloatBuffer> extraPMVMatrixConsumer;

    private int stencilIdRangeStart = 0, stencilIdRangeEnd = 0xFF;
    private Deque<RenderStencilState> renderStencilStateStack = new ArrayDeque<>();
    private int renderStencilStateStackMaxDepth;
    private int stencilId;

    private int backgroundColor = 0xFF7F7F7F; // in ARGB format

    private EditorStage() {}

    public static EditorStage getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EditorStage();
        }

        return INSTANCE;
    }

    public void init(int x, int y, int width, int height) throws ShaderCompilationException {
        this.shader = assetManager.getShader("objects.vertex.glsl", "objects.fragment.glsl",
                new Attribute(0, 2, Float.BYTES, GLConstants.GL_FLOAT),
                new Attribute(1, 2, Float.BYTES, GLConstants.GL_FLOAT),
                new Attribute(2, 4, Float.BYTES, GLConstants.GL_FLOAT),
                new Attribute(3, 3, Float.BYTES, GLConstants.GL_FLOAT));

        BufferedImage imageBuffer = assetManager.getImageBuffer("gradient_texture.png");
        assert imageBuffer != null : "Gradient texture not found.";

        if (this.gradientTexture == null) {
            this.gradientTexture = GLImage.createWithFormat(256, 2, true, ImageFilter.LINEAR,
                    GLConstants.GL_LUMINANCE_ALPHA, GLConstants.GL_UNSIGNED_BYTE,
                    ImageUtils.getPixelBuffer(imageBuffer), null, null);
        }

        this.camera.init(width, height);

        this.rendererContext.setViewport(x, y, width, height);
        this.framebuffer = new GLFramebuffer(gl, width, height);

        this.updatePMVMatrix();

        this.rendererContext.bindBlendMode(BlendMode.PREMULTIPLIED_ALPHA);
        this.rendererContext.clearColor(0xFF7F7F7F);
        this.rendererContext.clearStencil();

        this.stencilId = this.stencilIdRangeStart;
        this.renderStencilStateStackMaxDepth = 0;
        this.renderStencilStateStack.clear();

        this.initialized = true;
    }

    public void update() {
        if (!this.initialized)
            return;

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

        this.gizmos.render();
    }

    public void renderToFramebuffer(Framebuffer framebuffer) {
        framebuffer.bind();
        if (isWireframeEnabled) {
            gl.glPolygonMode(GLConstants.GL_FRONT_AND_BACK, GLConstants.GL_LINE);
        }
        this.renderer.beginRendering();
        renderDisplayObject();
        this.renderer.endRendering(); // flushes renderer image to screen
        if (isWireframeEnabled) {
            gl.glPolygonMode(GLConstants.GL_FRONT_AND_BACK, GLConstants.GL_FILL);
        }
        framebuffer.unbind();
    }

    public Rect calculateBoundsForAllFrames(DisplayObject displayObject) {
        if (displayObject.isMovieClip()) {
            MovieClip movieClip = (MovieClip) displayObject;

            // FIXME: very inefficient way out of recursion
            if (movieClip.getFrameCountRecursive() > 1) {
                Rect bounds = new Rect(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY,
                        Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);

                // Saving last movie clip state
                int loopFrame = movieClip.getLoopFrame();
                int currentFrame = movieClip.getCurrentFrame();
                MovieClipState state = movieClip.getState();

                // Calculating bounds for all frames
                MovieClipHelper.doForAllFrames(movieClip,
                        (frameIndex) -> bounds.mergeBounds(getDisplayObjectBounds(movieClip)));

                // Rolling movie clip state back
                movieClip.gotoAbsoluteTimeRecursive(currentFrame * movieClip.getMsPerFrame());
                movieClip.gotoAndPlayFrameIndex(currentFrame, loopFrame, state);

                return bounds;
            }
        }

        return getDisplayObjectBounds(displayObject);
    }

    public void unbindRender() {
        if (!this.initialized)
            return;

        if (this.framebuffer != null) {
            this.framebuffer.delete();
        }

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

        this.rendererContext.bindBlendMode(BlendMode.DISABLED);
    }

    @Override
    public void reset() {
        this.gizmos.reset();
        this.renderer.reset();
        this.removeAllChildren();
    }

    @Override
    public boolean startShape(ReadonlyRect rect, RenderableTexture texture, int renderConfigBits) {
        if (this.isCalculatingBounds) {
            if (this.isCalculatingMaskBounds) {
                this.maskBounds.mergeBounds(rect);
                return false;
            }

            if (this.bounds != null) {
                if (this.isApplyingMaskBounds) {
                    Rect copy = new Rect(rect);
                    copy.clamp(this.maskBounds);
                    rect = copy;
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
    public void addVertex(float x, float y, float u, float v, float redMul, float greenMul,
            float blueMul, float alpha, float redAdd, float greenAdd, float blueAdd) {
        this.renderer.addVertex(x, y, u, v, redMul, greenMul, blueMul, alpha, redAdd, greenAdd,
                blueAdd);
    }

    @Override
    public void setStencilRenderingState(RenderStencilState state) {
        if (this.isCalculatingBounds) {
            switch (state) {
                case ENABLED -> {
                    this.isCalculatingMaskBounds = true;
                    this.maskBounds = new Rect(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY,
                            Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);
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
    public void setStencilIdRange(int start, int end) {
        this.stencilIdRangeStart = start;
        this.stencilIdRangeEnd = end;
    }

    @Override
    public void setBackgroundColor(int argb) {
        this.backgroundColor = argb;
        this.rendererContext.clearColor(argb);
    }

    @Override
    public DrawApi getDrawApi() {
        return this.drawApi;
    }

    public void updatePMVMatrix() {
        Rect clipArea = this.camera.updateClipArea();

        PMVMatrix matrix = new PMVMatrix();
        matrix.glLoadIdentity();
        matrix.glOrthof(clipArea.getLeft(), clipArea.getRight(), clipArea.getBottom(),
                clipArea.getTop(),
                // near
                -1,
                // far
                1);

        FloatBuffer matrixBuffer = BufferUtils.allocateDirectFloat(16);
        matrix.glGetFloatv(matrix.glGetMatrixMode(), matrixBuffer);

        this.shader.bind();
        this.shader.setUniformMatrix4f("pmv", matrixBuffer);
        this.shader.unbind();

        if (this.extraPMVMatrixConsumer != null) {
            this.extraPMVMatrixConsumer.accept(matrixBuffer);
        }
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

    @Override
    public StageSprite getStageSprite() {
        return stageSprite;
    }

    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public GLContext getGlContext() {
        return gl;
    }

    public void setGlContext(GLContext glContext) {
        gl = glContext;

        this.rendererContext = new GLRendererContext(glContext);
        this.rendererContext.printInfo();

        this.renderer = new BatchedRenderer(this::constructBatch);
        BasicDrawApi basicDrawApi = new BasicDrawApi(this.renderer, this.assetManager);
        extraPMVMatrixConsumer = basicDrawApi::setPMVMatrix;
        this.drawApi = basicDrawApi;

        this.gizmos.setRenderer(this.renderer, this.drawApi);
    }

    public RendererContext getRendererContext() {
        return rendererContext;
    }

    @Override
    public Rect getDisplayObjectBounds(DisplayObject displayObject) {
        Rect bounds = new Rect(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);

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

    @Override
    public float getPixelSize() {
        return camera.getZoom().getPointSize();
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

    public Gizmos getGizmos() {
        return gizmos;
    }

    private void renderDisplayObject() {
        this.rendererContext.clearColor(0, 0, 0, 0);

        // if a3 which is always true
        // this.gl.glDepthMask(true);
        this.gl.glStencilMask(0xFF);
        this.rendererContext.clear(true, true, true);
        this.gl.glStencilMask(0);
        // this.gl.glDepthMask(false);

        this.stencilId = this.stencilIdRangeStart;

        this.rendererContext.clearStencil();
    }

    private void renderScreen() {
        this.rendererContext.clearColor(this.backgroundColor);
        this.rendererContext.clear(true, true, true);

        this.renderer.beginRendering();
        // Note: OpenGL textures are flipped, so drawing with flipped V coordinates of UV
        this.drawApi.drawTextureFlipped(framebuffer.getTexture(), camera.getClipArea());
        this.renderer.endRendering();
    }

    private Batch constructBatch(Shader shader, RenderableTexture texture, int renderConfigBits, RenderStencilState stencilRenderingState) {
        return new Batch(
            shader, texture, renderConfigBits, stencilRenderingState, 
            this::createDynamicVertexBuffer,
            state -> {
                switch (stencilRenderingState) {
                    case NONE, SCISSORS -> {}
                    case ENABLED -> {
                        this.pushRenderStencilState(stencilRenderingState);
                    }
                    case DISABLED -> this.popRenderStencilState(stencilRenderingState);
                    case RENDERING_MASKED, RENDERING_UNMASKED -> this.setRenderStencilState(stencilRenderingState);
                }
            }, 
            rendererContext::bindBlendMode);
    }

    private void pushRenderStencilState(RenderStencilState state) {
        if (this.renderStencilStateStack.isEmpty()) {
            this.stencilId = Math.min(this.stencilId + 1, this.stencilIdRangeEnd);
        }

        this.renderStencilStateStack.push(state);
        this.renderStencilStateStackMaxDepth = Math.max(this.renderStencilStateStackMaxDepth, this.renderStencilStateStack.size());

        this.setRenderStencilState(state);
    }

    private void popRenderStencilState(RenderStencilState state) {
        this.renderStencilStateStack.pop();

        if (this.renderStencilStateStack.isEmpty()) {
            assert state == RenderStencilState.DISABLED;
            this.setRenderStencilState(state);
            this.renderStencilStateStackMaxDepth = 0;
        } else {
            this.setRenderStencilState(this.renderStencilStateStack.peekLast());
        }
    }

    private void setRenderStencilState(RenderStencilState state) {
        if (!this.renderStencilStateStack.isEmpty()) {
            // Note: Replacing last stack value
            this.renderStencilStateStack.pop();
            this.renderStencilStateStack.push(state);
        }

        int layers = 8;
        int stencilRangeMask = stencilIdRangeEnd;

        for (int layer = 7; layer >= 0; layer--) {
            int stencilIdRangeBit = stencilIdRangeEnd & (1 << layer);

            if (stencilIdRangeBit != 0) {
                // Note: Filling (layer + 1) bits with 0b1
                stencilRangeMask = stencilIdRangeEnd | ((1 << layer) - 1);
                layers = layer + 1;
                break;
            }
        }

        int currentNestedStencilRefMask = 0;
        for (int index = 1; index < renderStencilStateStack.size(); index++) {
            currentNestedStencilRefMask |= 1 << (layers - index);
        }

        int previousOrRenderDepthStencilMask = 0;
        for (int index = 1; index < renderStencilStateStackMaxDepth; index++) {
            previousOrRenderDepthStencilMask |= 1 << (layers - index);
        }

        if ((stencilId | currentNestedStencilRefMask) > this.stencilIdRangeEnd || 
            currentNestedStencilRefMask != 0 && stencilId >= currentNestedStencilRefMask || 
            (stencilId & currentNestedStencilRefMask) != 0) {
            currentNestedStencilRefMask = 0;
            previousOrRenderDepthStencilMask = 0;
        }

        int ref = currentNestedStencilRefMask | stencilId;

        this.rendererContext.setRenderStencilState(state, ref, stencilRangeMask, previousOrRenderDepthStencilMask, currentNestedStencilRefMask);
    }

    private VertexBuffer createDynamicVertexBuffer(Attribute... attributes) {
        return new GLVertexBuffer(this.gl, GLConstants.GL_DYNAMIC_DRAW, attributes);
    }
}
