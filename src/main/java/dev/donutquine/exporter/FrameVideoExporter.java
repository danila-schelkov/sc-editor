package dev.donutquine.exporter;

import java.awt.image.BufferedImage;
import java.nio.file.Path;

import dev.donutquine.utilities.ImageUtils;

public class FrameVideoExporter implements VideoExporter {
    protected final Path filepath;
    protected final Path framesDirectory;

    public FrameVideoExporter(Path filepath) {
        this.filepath = filepath;

        String framesDirectoryName = String.join("_", filepath.getFileName().toString(), "frames");
        this.framesDirectory = filepath.getParent().resolve(framesDirectoryName);

        this.framesDirectory.toFile().mkdirs();
    }

    @Override
    public void encodeFrame(BufferedImage image, int frameIndex) {
        Path framePath = this.framesDirectory.resolve(String.join(".", String.valueOf(frameIndex), "png"));
        ImageUtils.saveImage(framePath, image);
    }

    @Override
    public void close() { }
}
