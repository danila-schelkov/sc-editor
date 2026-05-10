package dev.donutquine.exporter;

import java.awt.image.BufferedImage;

public interface VideoExporter extends AutoCloseable {
    void encodeFrame(BufferedImage image, int frameIndex);

    @Override
    void close();
}
