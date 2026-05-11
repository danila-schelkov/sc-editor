package dev.donutquine.editor.cli;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLDrawableFactory;
import com.jogamp.opengl.GLOffscreenAutoDrawable;
import com.jogamp.opengl.GLProfile;

import dev.donutquine.editor.assets.SupercellSWFAssetFile;
import dev.donutquine.editor.assets.SupercellSWFAssetFileLoader;
import dev.donutquine.editor.assets.exceptions.AssetLoadingException;
import dev.donutquine.editor.renderer.Framebuffer;
import dev.donutquine.editor.renderer.impl.EditorStage;
import dev.donutquine.editor.renderer.impl.RendererHelper;
import dev.donutquine.editor.renderer.impl.gl.GLShaderLoader;
import dev.donutquine.editor.renderer.impl.gl.JoglContext;
import dev.donutquine.editor.renderer.impl.texture.GLImage;
import dev.donutquine.editor.renderer.impl.texture.khronos.ExtensionKhronosTextureLoader;
import dev.donutquine.editor.renderer.impl.texture.khronos.KhronosTextureLoaders;
import dev.donutquine.exporter.VideoFormat;
import dev.donutquine.exporter.VideoFormats;
import dev.donutquine.math.Rect;
import dev.donutquine.math.ReadonlyRect;
import dev.donutquine.renderer.impl.swf.objects.DisplayObject;
import dev.donutquine.renderer.impl.swf.objects.MovieClip;
import dev.donutquine.resources.AssetManager;
import dev.donutquine.swf.ColorTransform;
import dev.donutquine.swf.Matrix2x3;
import dev.donutquine.swf.SupercellSWF;
import dev.donutquine.swf.exceptions.UnableToFindObjectException;
import dev.donutquine.swf.movieclips.MovieClipOriginal;
import dev.donutquine.swf.movieclips.MovieClipState;
import dev.donutquine.utilities.ImageUtils;
import dev.donutquine.utilities.MovieClipHelper;
import dev.donutquine.utilities.SystemUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public final class CliExporter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CliExporter.class);
    private static final Path DEFAULT_OUTPUT_DIR = Path.of("exports").toAbsolutePath();
    private static final VideoFormat DEFAULT_VIDEO_FORMAT = VideoFormats.WEBM;

    // Offscreen surface just needs to exist to create a GL context.
    // Actual render targets are FBO-backed and sized per-asset.
    private static final int OFFSCREEN_W = 8;
    private static final int OFFSCREEN_H = 8;

    // The raw GL3 handle — kept so we can call glFinish() directly.
    private static GL3 gl3Global;

    private CliExporter() {}

    // ── Public API ────────────────────────────────────────────────────────────

    public static boolean isCliMode(String[] args) {
        for (String arg : args) {
            if ("--export".equals(arg)) return true;
        }
        return false;
    }

    public static void run(String[] args) {
        CliArgs parsed = CliArgs.parse(args);

        Path scPath = Path.of(parsed.scFile);
        if (!Files.exists(scPath)) {
            System.err.println("[cli] File not found: " + scPath);
            System.exit(1);
        }

        // Load metadata (pure data, no GL) for the list command
        SupercellSWF swf;
        try {
            swf = SupercellSWFAssetFileLoader.loadInternal(scPath);
        } catch (AssetLoadingException e) {
            System.err.println("[cli] Failed to load SC file: " + e.getMessage());
            LOGGER.error("SC load failure", e);
            System.exit(2);
            return;
        }

        if (parsed.exportName == null) {
            listExportNames(swf);
            return;
        }

        // ── Boot real offscreen GL context ────────────────────────────────
        GLOffscreenAutoDrawable offscreen = bootOffscreenGL();
        try {
            offscreen.getContext().makeCurrent();
            gl3Global = offscreen.getGL().getGL3();
            initEditorStage(gl3Global);

            // Load asset file — this queues texture uploads via doInRenderThread.
            // We MUST flush that queue (via stage.update()) before rendering,
            // otherwise all textures stay blank on the GPU.
            SupercellSWFAssetFile assetFile;
            try {
                assetFile = (SupercellSWFAssetFile)
                        new SupercellSWFAssetFileLoader(scPath).load();
            } catch (AssetLoadingException e) {
                System.err.println("[cli] Failed to load asset file: " + e.getMessage());
                LOGGER.error("Asset file load failure", e);
                System.exit(2);
                return;
            }

            // Flush all queued texture upload tasks.
            // stage.update() drains the ConcurrentLinkedQueue<Runnable> that
            // GLImage.createWithFormat posted each texture into.
            flushRenderTasks();

            exportAsset(assetFile, swf, parsed);

        } finally {
            offscreen.getContext().release();
            offscreen.destroy();
        }
    }

    // ── Flush the EditorStage render-thread task queue ────────────────────────
    // Calls stage.update() which iterates and runs every pending Runnable.
    // We call it twice: once right after asset load (texture uploads), and
    // once after each render call (in case render itself posts more tasks).
    private static void flushRenderTasks() {
        EditorStage.getInstance().update();
        // Ensure all GL commands are actually complete before we read pixels.
        gl3Global.glFinish();
    }

    // ── List mode ─────────────────────────────────────────────────────────────

    private static void listExportNames(SupercellSWF swf) {
        List<String> names = collectExportNames(swf);
        if (names.isEmpty()) {
            System.out.println("[cli] No exportable definitions found.");
            return;
        }
        System.out.println("[cli] Exportable definitions (" + names.size() + "):");
        names.forEach(System.out::println);
    }

    static List<String> collectExportNames(SupercellSWF swf) {
        List<String> names = new ArrayList<>();
        for (int id : swf.getMovieClipIds()) {
            try {
                MovieClipOriginal mc = swf.getOriginalMovieClip(id & 0xFFFF, null);
                String name = mc.getExportName();
                if (name != null && !name.isBlank()) names.add(name);
            } catch (UnableToFindObjectException e) {
                LOGGER.warn("Could not read MovieClip id={}", id, e);
            }
        }
        names.sort(Comparator.naturalOrder());
        return names;
    }

    // ── Export mode ───────────────────────────────────────────────────────────

    private static void exportAsset(SupercellSWFAssetFile assetFile,
                                    SupercellSWF swf, CliArgs parsed) {
        // Resolve export name → object ID
        int targetId = -1;
        for (int id : swf.getMovieClipIds()) {
            try {
                MovieClipOriginal mc = swf.getOriginalMovieClip(id & 0xFFFF, null);
                if (parsed.exportName.equals(mc.getExportName())) {
                    targetId = id & 0xFFFF;
                    break;
                }
            } catch (UnableToFindObjectException e) {
                LOGGER.warn("Could not check MovieClip id={}", id, e);
            }
        }

        if (targetId == -1) {
            System.err.println("[cli] Export name not found: \"" + parsed.exportName + "\"");
            System.err.println("[cli] Run without --name to list available names.");
            System.exit(3);
            return;
        }

        DisplayObject displayObject;
        try {
            displayObject = assetFile.getOrCreate(targetId, parsed.exportName);
        } catch (UnableToFindObjectException e) {
            System.err.println("[cli] Could not instantiate display object: " + e.getMessage());
            LOGGER.error("DisplayObject creation failure", e);
            System.exit(4);
            return;
        }

        if (displayObject.isTextField()) {
            System.err.println("[cli] TextField assets cannot be exported.");
            System.exit(5);
            return;
        }

        boolean isAnimated = displayObject.isMovieClip()
                && ((MovieClip) displayObject).getFrameCountRecursive() > 1;

        // Resolve output path
        Path outputPath;
        try {
            if (parsed.outputPath != null) {
                outputPath = Path.of(parsed.outputPath);
            } else {
                Files.createDirectories(DEFAULT_OUTPUT_DIR);
                String ext = isAnimated
                        ? resolveVideoFormat(parsed.formatName).name()
                        : "png";
                outputPath = DEFAULT_OUTPUT_DIR.resolve(parsed.exportName + "." + ext);
            }
            if (outputPath.getParent() != null) {
                Files.createDirectories(outputPath.getParent());
            }
        } catch (IOException e) {
            System.err.println("[cli] Cannot create output directory: " + e.getMessage());
            System.exit(6);
            return;
        }

        EditorStage stage = EditorStage.getInstance();

        if (isAnimated) {
            exportVideo((MovieClip) displayObject, outputPath,
                    resolveVideoFormat(parsed.formatName), stage);
        } else {
            exportImage(displayObject, outputPath, stage);
        }
    }

    // ── Image export ── exact mirror of DisplayObjectContextMenu.exportAsImage ─

    private static void exportImage(DisplayObject displayObject,
                                    Path outputPath, EditorStage stage) {
        final float pixelSize = 1.0f;

        // calculateBoundsForAllFrames drives render() in bounds-calculation mode
        // which does NOT require a real FBO — safe to call before prepareStageForRendering
        Rect bounds = stage.calculateBoundsForAllFrames(displayObject);
        bounds.scale(pixelSize);

        Matrix2x3 matrix = new Matrix2x3();
        matrix.scaleMultiply(pixelSize, pixelSize);

        Framebuffer framebuffer = RendererHelper.prepareStageForRendering(
                stage, roundBounds(bounds, false));

        boolean parentSet = false;
        if (displayObject.getParent() == null) {
            displayObject.setParent(stage.getStageSprite());
            parentSet = true;
        }

        // render() queues geometry into the BatchedRenderer
        displayObject.render(matrix, new ColorTransform(), 0, 0);

        // renderToFramebuffer() flushes (endRendering) the batched geometry
        // into the FBO with a transparent (0,0,0,0) clear — exactly what the GUI does
        stage.renderToFramebuffer(framebuffer);

        // Flush any post-render tasks and wait for GPU completion
        flushRenderTasks();

        if (parentSet) displayObject.setParent(null);

        // getPixelArray(true) reads GL_RGBA bytes, views them as ints, then flips Y.
        // The result is PREMULTIPLIED RGBA in the format the RGBA_MODEL expects.
        // We un-premultiply before saving so PNG viewers get correct straight alpha.
        int[] pixels = framebuffer.getPixelArray(true);
        unPremultiplyAlpha(pixels);

        BufferedImage image = ImageUtils.createBufferedImageFromPixels(
                framebuffer.getWidth(), framebuffer.getHeight(), pixels, false);
        framebuffer.delete();

        ImageUtils.saveImage(outputPath, image);
        System.out.println("[cli] Image saved: " + outputPath.toAbsolutePath());
    }

    // ── Video export ── exact mirror of DisplayObjectContextMenu.exportAsVideo ─

    private static void exportVideo(MovieClip movieClip, Path outputPath,
                                    VideoFormat format, EditorStage stage) {
        final float pixelSize = 1.0f;

        Rect bounds = stage.calculateBoundsForAllFrames(movieClip);
        bounds.scale(pixelSize);

        ReadonlyRect ceilBounds = roundBounds(bounds, format.requiresSizeDividableByTwo());

        Matrix2x3 matrix = new Matrix2x3();
        matrix.scaleMultiply(pixelSize, pixelSize);
        ColorTransform colorTransform = new ColorTransform();

        MovieClipState state  = movieClip.getState();
        int loopFrame  = movieClip.getLoopFrame();
        int startFrame = movieClip.getCurrentFrame();
        int fps        = movieClip.getFps();

        Framebuffer framebuffer = RendererHelper.prepareStageForRendering(stage, ceilBounds);

        boolean parentSet = false;
        if (movieClip.getParent() == null) {
            movieClip.setParent(stage.getStageSprite());
            parentSet = true;
        }

        Path framesDir = outputPath.getParent().resolve(
                outputPath.getFileName().toString() + "_frames");
        framesDir.toFile().mkdirs();

        MovieClipHelper.doForAllFrames(movieClip, (frameIndex) -> {
            movieClip.gotoAbsoluteTimeRecursive(frameIndex * movieClip.getMsPerFrame());
            if (loopFrame != -1) {
                movieClip.setFrame(loopFrame);
            } else if (state == MovieClipState.STOPPED) {
                movieClip.setFrame(startFrame);
            }

            // Queue geometry
            movieClip.render(matrix, colorTransform, 0, 0);

            // Flush geometry into FBO
            stage.renderToFramebuffer(framebuffer);

            // Flush pending GL tasks + GPU sync
            flushRenderTasks();

            int[] pixels = framebuffer.getPixelArray(true);
            unPremultiplyAlpha(pixels);

            BufferedImage image = ImageUtils.createBufferedImageFromPixels(
                    framebuffer.getWidth(), framebuffer.getHeight(), pixels, false);

            ImageUtils.saveImage(framesDir.resolve(frameIndex + ".png"), image);
        });

        if (parentSet) movieClip.setParent(null);
        framebuffer.delete();

        // Blocking ffmpeg — no SwingWorker needed in CLI mode
        runFfmpegBlocking(framesDir, outputPath, format, fps);
        System.out.println("[cli] Video saved: " + outputPath.toAbsolutePath());
    }

    // ── Un-premultiply alpha ───────────────────────────────────────────────────
    // The objects.fragment.glsl shader writes premultiplied RGB:
    //   fragColor = vec4(color.rgb * colorMul.a, color.a)
    // PNG files expect straight (un-premultiplied) alpha.
    // Without this step, semi-transparent edges show as dark/black fringing.
    //
    // Pixel layout from glGetTexImage(GL_RGBA, GL_UNSIGNED_BYTE) on little-endian:
    //   int bits 0-7  = R,  8-15 = G,  16-23 = B,  24-31 = A
    private static void unPremultiplyAlpha(int[] pixels) {
        for (int i = 0; i < pixels.length; i++) {
            int p = pixels[i];
            int a = (p >> 24) & 0xFF;
            if (a == 0)   { pixels[i] = 0; continue; }
            if (a == 255) continue; // fully opaque — nothing to do
            int r = (p       ) & 0xFF;
            int g = (p >>  8) & 0xFF;
            int b = (p >> 16) & 0xFF;
            // Divide premultiplied components back by alpha
            r = Math.min(255, (r * 255) / a);
            g = Math.min(255, (g * 255) / a);
            b = Math.min(255, (b * 255) / a);
            pixels[i] = (a << 24) | (b << 16) | (g << 8) | r;
        }
    }

    // ── Blocking ffmpeg ───────────────────────────────────────────────────────

    private static void runFfmpegBlocking(Path framesDir, Path outputPath,
                                          VideoFormat format, int fps) {
        try {
            Process process = SystemUtils.runProcess(
                    "ffmpeg",
                    "-y",
                    "-hide_banner",
                    "-loglevel", "panic",
                    "-framerate", fps,
                    "-i", framesDir.resolve("%d.png").toAbsolutePath(),
                    "-c:v", format.codec(),
                    "-pix_fmt", format.pixelFormat(),
                    "-lossless", 1,
                    outputPath.toAbsolutePath()
            );

            LOGGER.info("Waiting for ffmpeg...");
            int exitCode = process.waitFor();

            if (exitCode != 0) {
                String err = new String(process.getErrorStream().readAllBytes());
                if (!err.isEmpty()) LOGGER.error("ffmpeg stderr: {}", err);
                System.err.println("[cli] ffmpeg exited with code " + exitCode);
            } else {
                try (Stream<Path> files = Files.walk(framesDir)) {
                    files.sorted(Comparator.reverseOrder())
                         .map(Path::toFile)
                         .forEach(File::delete);
                }
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("[cli] ffmpeg error: " + e.getMessage());
            LOGGER.error("ffmpeg invocation failed", e);
        }
    }

    // ── GL bootstrap ──────────────────────────────────────────────────────────

    private static GLOffscreenAutoDrawable bootOffscreenGL() {
        GLProfile profile = GLProfile.get(GLProfile.GL3);
        GLCapabilities caps = new GLCapabilities(profile);
        caps.setOnscreen(false);
        caps.setDoubleBuffered(false);
        // Request stencil buffer — required for EditorStage stencil mask rendering
        caps.setStencilBits(8);

        GLDrawableFactory factory = GLDrawableFactory.getFactory(profile);
        GLOffscreenAutoDrawable drawable = factory.createOffscreenAutoDrawable(
                null, caps, null, OFFSCREEN_W, OFFSCREEN_H);
        // This call creates the native surface and makes the context current temporarily.
        drawable.display();
        return drawable;
    }

    private static void initEditorStage(GL3 gl3) {
        JoglContext ctx = new JoglContext(gl3);

        // Mirror EventListener.init() exactly
        ExtensionKhronosTextureLoader extLoader = new ExtensionKhronosTextureLoader(ctx);
        KhronosTextureLoaders.registerLoader(extLoader);
        GLImage.khronosTextureLoader = KhronosTextureLoaders.getLoader();

        AssetManager assetManager = new AssetManager(new GLShaderLoader(ctx));

        EditorStage stage = EditorStage.getInstance();
        stage.setAssetManager(assetManager);
        stage.setGlContext(ctx);
        try {
            stage.init(0, 0, OFFSCREEN_W, OFFSCREEN_H);
        } catch (Exception e) {
            System.err.println("[cli] Failed to initialise EditorStage: " + e.getMessage());
            LOGGER.error("EditorStage init failure", e);
            System.exit(7);
        }
    }

    // ── Geometry helpers ──────────────────────────────────────────────────────

    private static ReadonlyRect roundBounds(Rect bounds, boolean requiresDivisibleByTwo) {
        int left   = (int) Math.floor(bounds.getLeft());
        int right  = (int) Math.ceil(bounds.getRight());
        int top    = (int) Math.floor(bounds.getTop());
        int bottom = (int) Math.ceil(bounds.getBottom());
        if (requiresDivisibleByTwo) {
            if ((right  - left) % 2 != 0) right++;
            if ((bottom - top)  % 2 != 0) bottom++;
        }
        return new Rect(left, top, right, bottom);
    }

    private static VideoFormat resolveVideoFormat(String name) {
        if (name == null) return DEFAULT_VIDEO_FORMAT;
        VideoFormat fmt = VideoFormats.getVideoFormatByName(name);
        if (fmt == null) {
            System.err.println("[cli] Unknown format \"" + name
                    + "\", falling back to " + DEFAULT_VIDEO_FORMAT.name());
            return DEFAULT_VIDEO_FORMAT;
        }
        return fmt;
    }

    // ── CLI argument model ────────────────────────────────────────────────────

    static final class CliArgs {
        final String scFile;
        final String exportName;
        final String formatName;
        final String outputPath;

        private CliArgs(String scFile, String exportName,
                        String formatName, String outputPath) {
            this.scFile = scFile;
            this.exportName = exportName;
            this.formatName = formatName;
            this.outputPath = outputPath;
        }

        static CliArgs parse(String[] args) {
            String scFile = null, exportName = null,
                   formatName = null, outputPath = null;

            for (int i = 0; i < args.length; i++) {
                switch (args[i]) {
                    case "--export" -> { if (i + 1 < args.length) scFile     = args[++i]; }
                    case "--name"   -> { if (i + 1 < args.length) exportName = args[++i]; }
                    case "--format" -> { if (i + 1 < args.length) formatName = args[++i]; }
                    case "--out"    -> { if (i + 1 < args.length) outputPath = args[++i]; }
                    default -> LOGGER.debug("Ignoring unknown flag: {}", args[i]);
                }
            }

            if (scFile == null) {
                System.err.println("[cli] --export <file.sc> is required");
                System.err.println("[cli] Usage:");
                System.err.println("  --export <file.sc>                    list names");
                System.err.println("  --export <file.sc> --name <name>      export asset");
                System.err.println("  --export <file.sc> --name <n> --format webm|mp4|hevc|avi");
                System.err.println("  --export <file.sc> --name <n> --out <path>");
                System.exit(1);
            }

            return new CliArgs(scFile, exportName, formatName, outputPath);
        }
    }
}

