package dev.donutquine.exporter;

import dev.donutquine.utilities.PathUtils;
import dev.donutquine.utilities.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;

public class FfmpegVideoExporter extends FrameVideoExporter {
    private static final Logger LOGGER = LoggerFactory.getLogger(FfmpegVideoExporter.class);

    private final VideoFormat format;
    private final int fps;

    private boolean isClosed;

    public FfmpegVideoExporter(VideoFormat format, Path filepath, int fps) {
        super(filepath);

        this.format = format;
        this.fps = fps;
    }

    @Override
    public void close() {
        if (isClosed) return;

        SystemUtils.waitProcessInSwing(
            () -> SystemUtils.runProcess(
                "ffmpeg",
                "-y",
                "-hide_banner",
                "-loglevel", "panic",
                "-framerate", fps,
                "-i", framesDirectory.resolve("%d.png").toAbsolutePath(),
                "-pattern_type", "glob",
                "-c:v", format.codec(),
                "-pix_fmt", format.pixelFormat(),
                "-lossless", 1,
                filepath.toAbsolutePath()
            ),
            (process) -> LOGGER.info("Waiting for ffmpeg to do its work..."),
            (process) -> {
                LOGGER.info("ffmpeg done its work with code: {}", process.exitValue());

                if (process.exitValue() != 0) {
                    try {
                        String output = new String(process.getInputStream().readAllBytes());
                        if (!output.isEmpty()) {
                            LOGGER.error(output);
                        }

                        String errorOutput = new String(process.getErrorStream().readAllBytes());
                        if (!errorOutput.isEmpty()) {
                            LOGGER.error(errorOutput);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return;
                }

                // TODO: Let user decide whether delete files or not
                PathUtils.deleteDirectory(this.framesDirectory);
            }
        );

        isClosed = true;
    }
}
