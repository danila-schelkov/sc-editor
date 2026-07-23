package dev.donutquine.editor.renderer.impl;

import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import dev.donutquine.editor.renderer.Camera;
import dev.donutquine.editor.renderer.Framebuffer;
import dev.donutquine.editor.renderer.Stage;
import dev.donutquine.editor.renderer.gl.GLFramebuffer;
import dev.donutquine.exporter.VideoExporter;
import dev.donutquine.exporter.VideoFormat;
import dev.donutquine.math.ReadonlyRect;
import dev.donutquine.math.Rect;
import dev.donutquine.renderer.impl.swf.objects.DisplayObject;
import dev.donutquine.renderer.impl.swf.objects.MovieClip;
import dev.donutquine.swf.ColorTransform;
import dev.donutquine.swf.Matrix2x3;
import dev.donutquine.swf.movieclips.MovieClipState;
import dev.donutquine.utilities.ImageUtils;
import dev.donutquine.utilities.MovieClipHelper;

public final class RendererHelper {
    private RendererHelper() {}

    public static Framebuffer prepareStageForRendering(EditorStage stage, int framebufferWidth, int framebufferHeight) {
        return prepareStageForRendering(stage, framebufferWidth, framebufferHeight, 0, 0);
    }

    public static Framebuffer prepareStageForRendering(EditorStage stage, int framebufferWidth, int framebufferHeight, float translateX, float translateY) {
        return prepareStageForRendering(stage, framebufferWidth, framebufferHeight, null, translateX, translateY);
    }

    public static Framebuffer prepareStageForRendering(EditorStage stage, ReadonlyRect bounds) {
        int framebufferWidth = (int) Math.ceil(bounds.getWidth());
        int framebufferHeight = (int) Math.ceil(bounds.getHeight());

        return prepareStageForRendering(stage, framebufferWidth, framebufferHeight, bounds, 0, 0);
    }

    public static Framebuffer prepareStageForRendering(EditorStage stage, int framebufferWidth, int framebufferHeight, ReadonlyRect bounds, float translateX, float translateY) {
        Camera camera = stage.getCamera();
        camera.reset();
        camera.init(framebufferWidth, framebufferHeight);
        if (bounds != null) {
            camera.moveToFit(bounds);
        } else {
            camera.addOffset(translateX, translateY);
        }

        stage.updatePMVMatrix();

        Framebuffer framebuffer = new GLFramebuffer(stage.getGlContext(), framebufferWidth, framebufferHeight);
        stage.getRendererContext().setViewport(0, 0, framebuffer.getWidth(), framebuffer.getHeight());
        return framebuffer;
    }

    public static void rollbackRenderer(EditorStage stage, ReadonlyRect viewport) {
        stage.getRendererContext().setViewport(0, 0, (int) viewport.getWidth(), (int) viewport.getHeight());
        stage.getCamera().init(viewport.getLeft(), viewport.getTop(), viewport.getRight(), viewport.getBottom());

        stage.getCamera().reset();
        stage.updatePMVMatrix();
    }

    public static void exportAsImage(DisplayObject displayObject, Path filepath, float pixelSize, boolean shouldPreserveStageCenter) {
        EditorStage stage = EditorStage.getInstance();

        Rect bounds = getRenderBounds(stage.calculateBoundsForAllFrames(displayObject), shouldPreserveStageCenter);
        bounds.scale(pixelSize);

        Matrix2x3 matrix = new Matrix2x3();
        matrix.scaleMultiply(pixelSize, pixelSize);

        exportAsImage(displayObject, matrix, new ColorTransform(), filepath, bounds);
    }

    public static void exportAsImage(DisplayObject displayObject, Matrix2x3 matrix, ColorTransform colorTransform, Path filepath, ReadonlyRect bounds) {
        EditorStage stage = EditorStage.getInstance();
        Framebuffer framebuffer = RendererHelper.prepareStageForRendering(stage, bounds);

        exportAsImage(displayObject, matrix, colorTransform, filepath, framebuffer);
    }

    /// NOTE: Must be called within Stage rendering content
    /// NOTE: input Framebuffer will be deleted
    public static void exportAsImage(DisplayObject displayObject, Matrix2x3 matrix, ColorTransform colorTransform, Path filepath, Framebuffer framebuffer) {
        EditorStage stage = EditorStage.getInstance();
        boolean parentSet = attachToStage(displayObject, stage);
        displayObject.render(matrix, colorTransform, 0, 0);
        detachFromStage(displayObject, parentSet);

        stage.renderToFramebuffer(framebuffer);

        BufferedImage screenshot = ImageUtils.createBufferedImageFromPixels(framebuffer.getWidth(), framebuffer.getHeight(), framebuffer.getPixelArray(true), false);
        ImageUtils.saveImage(filepath, screenshot);

        framebuffer.delete();
    }

    public static void exportAsVideo(MovieClip movieClip, VideoExporter videoExporter, VideoFormat format, float pixelSize, boolean shouldPreserveStageCenter) {
        EditorStage stage = EditorStage.getInstance();

        Rect bounds = getRenderBounds(stage.calculateBoundsForAllFrames(movieClip), shouldPreserveStageCenter);
        bounds.scale(pixelSize);

        ReadonlyRect ceilBounds = roundBounds(bounds, format.requiresSizeDividableByTwo());

        Matrix2x3 matrix = new Matrix2x3();
        matrix.scaleMultiply(pixelSize, pixelSize);

        exportAsVideo(movieClip, matrix, new ColorTransform(), ceilBounds, videoExporter);
    }

    /// NOTE: Must be called within Stage rendering content
    public static void exportAsVideo(MovieClip movieClip, Matrix2x3 matrix, ColorTransform colorTransform, ReadonlyRect bounds, VideoExporter videoExporter) {
        EditorStage stage = EditorStage.getInstance();

        Framebuffer framebuffer = RendererHelper.prepareStageForRendering(stage, bounds);
        exportAsVideo(movieClip, matrix, colorTransform, framebuffer, videoExporter);
    }

    /// NOTE: Must be called within Stage rendering content
    /// NOTE: input Framebuffer will be deleted
    public static void exportAsVideo(MovieClip movieClip, Matrix2x3 matrix, ColorTransform colorTransform, Framebuffer framebuffer, VideoExporter videoExporter) {
        EditorStage stage = EditorStage.getInstance();

        MovieClipState state = movieClip.getState();
        int loopFrame = movieClip.getLoopFrame();
        int startFrame = movieClip.getCurrentFrame();

        boolean parentSet = attachToStage(movieClip, stage);

        try (VideoExporter exporter = videoExporter) {
            MovieClipHelper.doForAllFrames(movieClip, (frameIndex) -> {
                // Note: it's necessary to set frame index using this method,
                // because absolute time frame setting may skip frames.
                movieClip.gotoAbsoluteTimeRecursive(frameIndex * movieClip.getMsPerFrame());
                if (loopFrame != -1 && frameIndex >= loopFrame) {
                    movieClip.setFrame(loopFrame);
                } else if (state == MovieClipState.STOPPED) {
                    movieClip.setFrame(startFrame);
                }

                movieClip.render(matrix, colorTransform, 0, 0);
                stage.renderToFramebuffer(framebuffer);

                BufferedImage image = ImageUtils.createBufferedImageFromPixels(framebuffer.getWidth(), framebuffer.getHeight(), framebuffer.getPixelArray(true), false);
                exporter.encodeFrame(image, frameIndex);
            });
        } finally {
            detachFromStage(movieClip, parentSet);
            framebuffer.delete();
        }
    }

    public static Rect getRenderBounds(Rect objectBounds, boolean shouldPreserveStageCenter) {
        if (shouldPreserveStageCenter) {
            float maxHorizontal = Math.max(Math.abs(objectBounds.getLeft()), Math.abs(objectBounds.getRight()));
            float maxVertical = Math.max(Math.abs(objectBounds.getTop()), Math.abs(objectBounds.getBottom()));

            return new Rect(
                -maxHorizontal,
                -maxVertical,
                maxHorizontal,
                maxVertical
            );
        }

        return objectBounds;
    }

    public static @NotNull ReadonlyRect roundBounds(@NotNull ReadonlyRect bounds, boolean requiresSizeDividableByTwo) {
        int left = (int) Math.floor(bounds.getLeft());
        int right = (int) Math.ceil(bounds.getRight());
        int top = (int) Math.floor(bounds.getTop());
        int bottom = (int) Math.ceil(bounds.getBottom());

        int width = right - left;
        if (requiresSizeDividableByTwo && width % 2 != 0) {
            right++;
        }

        int height = bottom - top;
        if (requiresSizeDividableByTwo && height % 2 != 0) {
            bottom++;
        }

        if (width != bounds.getWidth() || height != bounds.getHeight()) {
            return new Rect(left, top, right, bottom);
        }

        return bounds;
    }

    // Note: Passing own sprite as parent to provide Stage reference
    private static boolean attachToStage(DisplayObject movieClip, Stage stage) {
        boolean parentSet = false;
        if (movieClip.getParent() == null) {
            movieClip.setParent(stage.getStageSprite());
            parentSet = true;
        }
        return parentSet;
    }

    private static void detachFromStage(DisplayObject movieClip, boolean parentSet) {
        if (parentSet) {
            movieClip.setParent(null);
        }
    }
}
