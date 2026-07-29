package dev.donutquine.exporter;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dev.donutquine.utilities.ArrayUtils;
import dev.donutquine.utilities.ImageUtils;
import dev.donutquine.utilities.SystemUtils;

public class FfmpegVideoExporter implements VideoExporter {
    private static final Logger LOGGER = LoggerFactory.getLogger(FfmpegVideoExporter.class);

    private boolean isClosed;

	protected final Process process;
	protected final OutputStream stdin;

    public FfmpegVideoExporter(int width, int height, int fps, @NotNull VideoFormat format, @Nullable String filterComplex, @NotNull Path filepath) throws IOException {
        Object[] command = ArrayUtils.concat(
            new Object[] {
                "ffmpeg",

                // Generic options (https://ffmpeg.org/ffmpeg.html#toc-Generic-options)
                "-y",
                "-hide_banner",
                "-loglevel", "panic",
            }, 
            new Object[] {
                // Input options
                // Main options (https://ffmpeg.org/ffmpeg.html#toc-Main-options)
                "-f", "rawvideo",
                // Advanced video options (https://ffmpeg.org/ffmpeg.html#toc-Advanced-Video-options)
                "-pix_fmt", "rgba",
                // Video options (https://ffmpeg.org/ffmpeg.html#toc-Video-Options)
                "-s", String.format("%dx%d", width, height),
                "-r", fps, // -framerate
                "-i", "-",  // stdin
            }, 
            // Output options
            format.codec() != null ? new Object[] {
                "-c:v", format.codec(),
                "-lossless", 1,
            } : new Object[0],
            format.pixelFormat() != null ? new Object[] {
                "-pix_fmt", format.pixelFormat(),
            } : new Object[0],
            filterComplex != null ? new Object[] {
                "-filter_complex", filterComplex,
                "-loop", 0,
            } : new Object[0],
            // Output path
            new Object[] {
                filepath.toAbsolutePath()
            }
        );

        this.process = SystemUtils.runProcess(command);
        this.stdin = process.getOutputStream();
    }

    @Override
	public void encodeFrame(int[] pixelArray, int frameIndex) throws IOException {
        byte[] pixelBuffer = ImageUtils.intArrayToByteArray(pixelArray);
        this.stdin.write(pixelBuffer);
        this.stdin.flush();
	}

    @Override
    public void close() throws IOException {
        if (isClosed) return;

        SystemUtils.waitProcessInSwing(
            () -> {
                this.stdin.close();
                return this.process;
            },
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
            }
        );

        isClosed = true;
    }
}
