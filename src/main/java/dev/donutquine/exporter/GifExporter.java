package dev.donutquine.exporter;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

public class GifExporter implements AutoCloseable {
    private final ImageOutputStream output;
    private final GifSequenceWriter writer;

    public GifExporter(Path path, int fps) throws IOException {
        this.output = ImageIO.createImageOutputStream(path.toFile());
        this.writer = new GifSequenceWriter(output, BufferedImage.TYPE_INT_ARGB, 1000 / fps, true);
    }

    public void addFrame(BufferedImage image) throws IOException {
        writer.writeToSequence(image);
    }

    public void finish() throws IOException {
        try {
            writer.close();
        } catch (IllegalStateException ignored) {
        }
        try {
            output.close();
        } catch (IOException ignored) {}
    }


    @Override
    public void close() throws IOException {
        finish();
    }
}
