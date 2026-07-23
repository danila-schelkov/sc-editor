package dev.donutquine.exporter;

import java.io.IOException;
import java.nio.file.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dev.donutquine.utilities.PathUtils;
import dev.donutquine.utilities.SystemUtils;

/** 
 * Synchronous alternative to {@link dev.donutquine.exporter.FfmpegVideoExporter}. 
 */
public class SynchronousFfmpegVideoExporter extends FrameVideoExporter {
    private static final Logger LOGGER = LoggerFactory.getLogger(SynchronousFfmpegVideoExporter.class);

    private final VideoFormat format;
    private final int fps;

    public SynchronousFfmpegVideoExporter(VideoFormat format, Path filepath, int fps) {
        super(filepath);

        this.format = format;
        this.fps = fps;
    }

    @Override
    public void close() {
        Process process;
        try {
            process = SystemUtils.runProcess(
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
                this.filepath.toAbsolutePath()
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to start ffmpeg", e);
        }

        try {
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                LOGGER.error("ffmpeg exited with code {}", exitCode);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            PathUtils.deleteDirectory(framesDirectory);
        }
    }
}
