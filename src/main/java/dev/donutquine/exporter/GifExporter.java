package dev.donutquine.exporter;

import dev.donutquine.utilities.PathUtils;
import dev.donutquine.utilities.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Exports animation frames as a transparent-background GIF using ffmpeg's palette-based approach.
 * GIF transparency is binary (on/off per pixel); pixels with alpha < 128 become transparent.
 *
 * @param sourceFps  frame rate at which frames are fed in (original animation fps)
 * @param outputFps  desired GIF playback frame rate; ffmpeg will resample if different from sourceFps
 */
public class GifExporter extends FrameVideoExporter {
    private static final Logger LOGGER = LoggerFactory.getLogger(GifExporter.class);

    private final int sourceFps;
    private final int outputFps;

    private boolean isClosed;

    public GifExporter(Path path, int sourceFps, int outputFps) {
        super(path);

        this.sourceFps = sourceFps;
        this.outputFps = outputFps;
    }

    @Override
    public void close() {
        if (isClosed) return;

        // fps={outputFps}                    → resample to target fps (handles slowdown / skip)
        // palettegen=reserve_transparent=on  → include transparent entry in palette
        // paletteuse alpha_threshold=128     → pixels with alpha < 128 map to transparent
        // -loop 0                            → infinite loop
        final String filterComplex;
        String baseFilter = "split[v][p];" +
            "[p]palettegen=reserve_transparent=on[palette];" +
            "[v][palette]paletteuse=dither=sierra2_4a:alpha_threshold=128";
        if (sourceFps != outputFps) {
            filterComplex = "fps=" + outputFps + "," + baseFilter;
        } else {
            filterComplex = baseFilter;
        }

        SystemUtils.waitProcessInSwing(
            () -> SystemUtils.runProcess(
                "ffmpeg",
                "-y",
                "-hide_banner",
                "-loglevel", "panic",
                "-framerate", sourceFps,
                "-i", framesDirectory.resolve("%d.png").toAbsolutePath(),
                "-filter_complex", filterComplex,
                "-loop", 0,
                filepath.toAbsolutePath()
            ),
            (process) -> LOGGER.info("Waiting for ffmpeg to encode transparent GIF ({} fps -> {} fps)...", sourceFps, outputFps),
            (process) -> {
                LOGGER.info("ffmpeg finished GIF encoding with code: {}", process.exitValue());

                if (process.exitValue() != 0) {
                    try {
                        String errorOutput = new String(process.getErrorStream().readAllBytes());
                        if (!errorOutput.isEmpty()) {
                            LOGGER.error(errorOutput);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return;
                }

                PathUtils.deleteDirectory(this.framesDirectory);
            }
        );

        isClosed = true;
    }
}
