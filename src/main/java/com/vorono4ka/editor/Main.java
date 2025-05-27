package com.vorono4ka.editor;

import com.formdev.flatlaf.FlatLightLaf;
import com.vorono4ka.editor.layout.dialogs.ExceptionDialog;
import com.vorono4ka.editor.layout.windows.EditorWindow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        System.setProperty("apple.laf.useScreenMenuBar", "true");

        FlatLightLaf.setup();

        ExceptionDialog.registerUncaughtExceptionHandler();

        SwingUtilities.invokeLater(() -> {
            Editor editor = new Editor();
            EditorWindow window = editor.getWindow();
            window.initialize(EditorWindow.TITLE);
            window.show();

            if (args.length > 0) {
                Path path = Path.of(args[0]);
                if (Files.exists(path)) {
                    editor.openFile(path);
                }
            }

            registerOpenFileHandler(editor);
        });
    }

    private static void registerOpenFileHandler(Editor editor) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.APP_OPEN_FILE)) {
                    desktop.setOpenFileHandler(e -> {
                        List<Path> paths = e.getFiles().stream().map(File::toPath).toList();
                        if (paths.size() > 1) {
                            LOGGER.warn("Loading multiple files is not supported!");
                        }

                        editor.openFile(paths.get(0));
                    });
                }
            }
        } catch (Throwable e) {
            LOGGER.error("Failed to register open file handler", e);
        }
    }
}
