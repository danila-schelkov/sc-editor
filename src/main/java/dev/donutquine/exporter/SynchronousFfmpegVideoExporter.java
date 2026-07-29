package dev.donutquine.exporter;

import java.io.IOException;
import java.nio.file.Path;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * Synchronous alternative to {@link dev.donutquine.exporter.FfmpegVideoExporter}. 
 */
public class SynchronousFfmpegVideoExporter extends FfmpegVideoExporter {
	private static final Logger LOGGER = LoggerFactory.getLogger(SynchronousFfmpegVideoExporter.class);

    public SynchronousFfmpegVideoExporter(int width, int height, int fps, @NotNull VideoFormat format, @Nullable String filterComplex, @NotNull Path filepath) throws IOException {
		super(width, height, fps, format, filterComplex, filepath);
	}

    @Override
    public void close() throws IOException {
        this.stdin.close();

        try {
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                LOGGER.error("ffmpeg exited with code {}", exitCode);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
