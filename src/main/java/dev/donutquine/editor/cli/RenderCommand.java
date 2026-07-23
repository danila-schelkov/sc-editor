package dev.donutquine.editor.cli;

import java.nio.file.Path;
import java.util.List;
import org.kohsuke.args4j.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jogamp.opengl.GLOffscreenAutoDrawable;
import dev.donutquine.editor.renderer.Framebuffer;
import dev.donutquine.editor.renderer.impl.EditorStage;
import dev.donutquine.editor.renderer.impl.RendererHelper;
import dev.donutquine.exporter.SynchronousFfmpegVideoExporter;
import dev.donutquine.exporter.VideoFormat;
import dev.donutquine.exporter.VideoFormats;
import dev.donutquine.math.ReadonlyRect;
import dev.donutquine.math.Rect;
import dev.donutquine.renderer.impl.swf.objects.DisplayObject;
import dev.donutquine.renderer.impl.swf.objects.MovieClip;
import dev.donutquine.swf.ColorTransform;
import dev.donutquine.swf.Export;
import dev.donutquine.swf.Matrix2x3;
import dev.donutquine.swf.exceptions.UnableToFindObjectException;

/**
 * Represents command-line arguments.
 *
 * <p>{@code --scale} applies a geometry transform to the rendered asset
 * (so {@code --scale 1,-1} flips it vertically); {@code --pixel-scale}
 * uniformly scales both the framebuffer and the rendered geometry,
 * matching the GUI's "pixel size" zoom.
 */
public class RenderCommand extends SwfCliCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(RenderCommand.class);

    @Option(name = "--exports", required = true)
    private List<String> exports;

    @Option(name = "--output", aliases = {"-o"}, required = true)
    private Path outputDirectory;
    @Option(name = "--format", aliases = {"-f"})
    private VideoFormats.Format format = VideoFormats.Format.WEBM;

    @Option(name = "--width", aliases = {"-w"})
    private int width = -1;
    @Option(name = "--height", aliases = {"-h"})
    private int height = -1;

    @Option(name = "--translate-x", aliases = {"-tx"})
    private float translateX;
    @Option(name = "--translate-y", aliases = {"-ty"})
    private float translateY;

    @Option(name = "--scale-x", aliases = {"-sx"})
    private float scaleX = 1.0f;
    @Option(name = "--scale-y", aliases = {"-sy"})
    private float scaleY = 1.0f;

    @Option(name = "--pixel-scale")
    private float pixelSize = 1.0f;

    @Option(name = "--start-frame")
    private int startFrame = -1;
    @Option(name = "--end-frame")
    private int endFrame = -1;

	private GLOffscreenAutoDrawable drawable;

    @Override
    public int execute() {
        int exitCode = super.execute();
        if (exitCode != 0) {
            return exitCode;
        }

        // Second display() drains the texture creation tasks queued by the asset file's constructor.
        drawable.display();

        EditorStage stage = EditorStage.getInstance();
        boolean shouldPreserveStageCenter = true;

        try {
            for (String exportName : exports) {
                DisplayObject displayObject = createByExportName(exportName);

                Rect bounds = RendererHelper.getRenderBounds(stage.calculateBoundsForAllFrames(displayObject), shouldPreserveStageCenter);
                bounds.scale(pixelSize);

                ReadonlyRect ceilBounds = RendererHelper.roundBounds(bounds, false);

                int framebufferWidth = width != -1 ? width : (int) Math.ceil(ceilBounds.getWidth());
                int framebufferHeight = height != -1 ? height : (int) Math.ceil(ceilBounds.getHeight());

                VideoFormat videoFormat = format.format;
                if (videoFormat.requiresSizeDividableByTwo()) {
                    if (framebufferWidth % 2 != 0) framebufferWidth++;
                    if (framebufferHeight % 2 != 0) framebufferHeight++;
                }

                int finalFramebufferWidth = framebufferWidth, finalFramebufferHeight = framebufferHeight;

                Matrix2x3 matrix = new Matrix2x3();
                matrix.scaleMultiply(pixelSize * scaleX, pixelSize * scaleY);

                if (displayObject instanceof MovieClip movieClip && getFrameCount(movieClip) > 1 && (startFrame == -1 || endFrame == -1 || (endFrame - startFrame + 1) > 1)) {
                    Path outputPath = outputDirectory.resolve(exportName + "." + videoFormat.name());

                    stage.doInRenderThread(() -> {
                        Framebuffer framebuffer = RendererHelper.prepareStageForRendering(stage, finalFramebufferWidth, finalFramebufferHeight, translateX != 0 || translateY != 0 ? null : bounds, translateX, translateY);
                        RendererHelper.exportAsVideo(movieClip, matrix, new ColorTransform(), framebuffer, new SynchronousFfmpegVideoExporter(videoFormat, outputPath, movieClip.getFps()));
                        LOGGER.info("Saved video to {}", outputPath);
                    });
                } else {
                    Path outputPath = outputDirectory.resolve(exportName + ".png");

                    stage.doInRenderThread(() -> {
                        Framebuffer framebuffer = RendererHelper.prepareStageForRendering(stage, finalFramebufferWidth, finalFramebufferHeight, translateX != 0 || translateY != 0 ? null : bounds, translateX, translateY);
                        RendererHelper.exportAsImage(displayObject, matrix, new ColorTransform(), outputPath, framebuffer);
                        LOGGER.info("Saved image to {}", outputPath);
                    });
                }
            }
        } catch (Throwable t) {
            LOGGER.error("Export failed", t);
        }

        // Synchronously drains the queued export work.
        drawable.display();

        return 0;
    }

    @Override
    public void setDrawable(GLOffscreenAutoDrawable drawable) {
        this.drawable = drawable;
    }

    private DisplayObject createByExportName(String name) throws UnableToFindObjectException {
        for (Export export : assetFile.asset.getExports()) {
            if (name.equals(export.name())) {
                return assetFile.getOrCreate(export.id(), name);
            }
        }

        throw new IllegalArgumentException("Export name not found in the SWF: " + name);
    }

    private static int getFrameCount(MovieClip movieClip) {
        int frameCount;
        if (movieClip.getFrameCount() > 1) {
            frameCount = movieClip.getFrameCount();
        } else {
            frameCount = movieClip.getFrameCountRecursive();
        }
        return frameCount;
    }


    // private static void setTargetFrame(DisplayObject displayObject, String targetFrame) {
    //     if (targetFrame == null || !displayObject.isMovieClip()) {
    //         return;
    //     }
    //     MovieClip movieClip = (MovieClip) displayObject;
    //     int maxFrames = movieClip.getFrameCount() > 1 ? movieClip.getFrameCount() : movieClip.getFrameCountRecursive();
    //     int targetIndex = CliOptions.FRAME_LAST.equalsIgnoreCase(targetFrame) ? Math.max(0, maxFrames - 1) : 0;
    //     movieClip.gotoAbsoluteTimeRecursive(targetIndex * movieClip.getMsPerFrame());
    // }
}
