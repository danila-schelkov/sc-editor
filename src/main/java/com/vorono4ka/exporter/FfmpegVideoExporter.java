package com.vorono4ka.exporter;

import com.vorono4ka.editor.Main;
import com.vorono4ka.editor.layout.panels.StatusBar;
import com.vorono4ka.utilities.ImageUtils;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class FfmpegVideoExporter implements VideoExporter {
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

    private static Process runProcess(Object... commandArgs) {
        String[] stringArgs = new String[commandArgs.length];
        for (int i = 0; i < commandArgs.length; i++) {
            stringArgs[i] = commandArgs[i].toString();
        }

        try {
            return Runtime.getRuntime().exec(stringArgs);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void runFfmpeg(Consumer<Process> startAction, Consumer<Process> exitAction, Object... commandArgs) {
        Process process = runProcess(commandArgs);

        // Note: The process hangs if you don't do it.
        //noinspection CommentedOutCode
        SwingWorker<Integer, Integer> worker = new SwingWorker<>() {
            @Override
            protected Integer doInBackground() throws InterruptedException {
                startAction.accept(process);

                // Note: The following code waits for the end of the process by reading from the streams.
                // Otherwise, you can simply disable output to the console using the loglevel constraint. But it won't work forever.

                //while (process.isAlive()) {
                //    String errorString = new String(process.getErrorStream().readAllBytes(), StandardCharsets.UTF_8);
                //    String inputString = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
                //    // TODO: log ffmpeg errors
                //}
                process.waitFor();
                return 0;
            }

            @Override
            protected void done() {
                super.done();

                exitAction.accept(process);
            }
        };

        worker.execute();
    }

    public void encodeFrame(BufferedImage image, int frameIndex) {
        Path framePath = this.framesDirectory.resolve(String.join(".", String.valueOf(frameIndex), "png"));
        ImageUtils.saveImage(framePath, image);
    }

    @Override
    public void close() {
        if (isClosed) return;

        StatusBar statusBar = Main.editor.getWindow().getStatusBar();

        runFfmpeg(
            (process) -> statusBar.setStatus("Waiting for ffmpeg to do its work..."),
            (process) -> {
                statusBar.setStatus("ffmpeg done its work with code: " + process.exitValue());

                try (Stream<Path> files = Files.walk(framesDirectory)) {
                    files.sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            },
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
        );

        isClosed = true;
    }
}
