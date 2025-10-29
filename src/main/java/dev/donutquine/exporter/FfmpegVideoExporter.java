package dev.donutquine.exporter;

import dev.donutquine.utilities.ImageUtils;
import dev.donutquine.utilities.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

public class FfmpegVideoExporter implements VideoExporter {
    private static final Logger LOGGER = LoggerFactory.getLogger(FfmpegVideoExporter.class);

    private final VideoFormat format;
    private final Path filepath;
    private final int fps;
    private final Path framesDirectory;

    private boolean isClosed;

    public FfmpegVideoExporter(VideoFormat format, Path path, int fps) {
        this.format = format;
        this.fps = fps;

        filepath = path;
        framesDirectory = path.getParent().resolve(String.join("_", path.getFileName().toString(), "frames"));

        framesDirectory.toFile().mkdirs();
    }

    public void encodeFrame(BufferedImage image, int frameIndex) {
        Path framePath = this.framesDirectory.resolve(String.join(".", String.valueOf(frameIndex), "png"));
        ImageUtils.saveImage(framePath, image);
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
                try (Stream<Path> files = Files.walk(framesDirectory)) {
                    files.sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        );

        isClosed = true;
    }
}
