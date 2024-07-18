package com.vorono4ka.utilities;

import javax.swing.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class SystemUtils {
    public static boolean canExecute(Object... commandArgs) {
        try {
            Process process = SystemUtils.runProcess(commandArgs);

            int exitCode = process.waitFor();
            if (exitCode != 0) return false;
        } catch (IOException | InterruptedException e) {
            return false;
        }

        return true;
    }

    public static Process runProcess(Object... commandArgs) throws IOException {
        String[] stringArgs = new String[commandArgs.length];
        for (int i = 0; i < commandArgs.length; i++) {
            stringArgs[i] = commandArgs[i].toString();
        }

        return Runtime.getRuntime().exec(stringArgs);
    }

    public static void waitProcessInSwing(Callable<Process> createProcess, Consumer<Process> startAction, Consumer<Process> exitAction) {
        Process process;
        try {
            process = createProcess.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Note: The process hangs if you don't do it.
        SwingWorker<Integer, Integer> worker = new SwingWorker<>() {
            @Override
            protected Integer doInBackground() throws InterruptedException {
                startAction.accept(process);

                // Note: The following code waits for the end of the process by reading from the streams.
                // Otherwise, you can simply disable output to the console using the loglevel constraint. But it won't work forever.

                SystemUtils.wait(process);
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

    public static void waitByReadingStreams(Process process) throws IOException {
        while (process.isAlive()) {
            String errorString = new String(process.getErrorStream().readAllBytes(), StandardCharsets.UTF_8);
            String inputString = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            // TODO: log errors
        }
    }

    public static int wait(Process process) throws InterruptedException {
        return process.waitFor();
    }
}
