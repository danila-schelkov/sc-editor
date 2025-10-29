package dev.donutquine.editor;

import com.formdev.flatlaf.FlatLightLaf;
import dev.donutquine.editor.layout.dialogs.AboutDialog;
import dev.donutquine.editor.layout.dialogs.ExceptionDialog;
import dev.donutquine.editor.layout.windows.EditorWindow;
import dev.donutquine.editor.settings.EditorSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        System.setProperty("apple.laf.useScreenMenuBar", "true");

        FlatLightLaf.setup();

        ExceptionDialog.registerUncaughtExceptionHandler();

        SwingUtilities.invokeLater(() -> initializeEditor(args));
    }

    private static void initializeEditor(String[] args) {
        EditorSettings settings;
        try {
            settings = EditorSettings.load(Path.of(".", "settings.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Editor editor = new Editor(settings);
        EditorWindow window = editor.getWindow();
        window.initialize(EditorWindow.TITLE);
        window.show();

        if (args.length > 0) {
            Path path = Path.of(args[0]);
            if (Files.exists(path)) {
                editor.openFile(path);
            }
        }

        registerAboutHandler(editor);
        registerOpenFileHandler(editor);
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

    private static void registerAboutHandler(Editor editor) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.APP_ABOUT)) {
                    desktop.setAboutHandler(e -> {
                        AboutDialog.showAboutDialog(editor.getWindow().getFrame());
                    });
                }
            }
        } catch (Throwable e) {
            LOGGER.error("Failed to register about handler", e);
        }
    }
}
