package dev.donutquine.exporter;

import java.io.IOException;

public interface VideoExporter extends AutoCloseable {
    void encodeFrame(int[] pixelArray, int frameIndex) throws IOException;

    @Override
    void close() throws IOException;
}
