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

    private final Path filepath;
    private final String codecName;
    private final int fps;
    private final Path framesDirectory;

    private boolean isClosed;

    public FfmpegVideoExporter(Path directory, String filename, String formatName, String codecName, int fps) {
        this.codecName = codecName;
        this.fps = fps;

        filepath = directory.resolve(String.join(".", filename, formatName));
        framesDirectory = directory.resolve(String.join("_", filename, "frames"));

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
                "-c:v", codecName,
                "-pix_fmt", "yuva420p",
                "-lossless", 1,
                filepath.toAbsolutePath()
            ),
            (process) -> LOGGER.info("Waiting for ffmpeg to do its work..."),
            (process) -> {
                LOGGER.info("ffmpeg done its work with code: {}", process.exitValue());

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
