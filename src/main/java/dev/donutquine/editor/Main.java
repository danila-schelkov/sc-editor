package dev.donutquine.editor;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.icons.FlatTabbedPaneCloseIcon;
import com.formdev.flatlaf.util.SystemFileChooser;
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
import java.util.prefs.Preferences;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        System.setProperty("apple.laf.useScreenMenuBar", "true");

        FlatLightLaf.setup();
        
        UIManager.put( "TabbedPane.closeArc", 999 );
        UIManager.put( "TabbedPane.closeCrossFilledSize", 5.5f );
        UIManager.put( "TabbedPane.closeIcon", new FlatTabbedPaneCloseIcon() );

        SystemFileChooser.setStateStore(new SystemFileChooser.StateStore() {
            private static final String KEY_PREFIX = "fileChooser.";

            private final static Preferences state = Preferences.userRoot().node("sc-editor");

            @Override
            public String get( String key, String def ) {
                return state.get(KEY_PREFIX + key, def);
            }

            @Override
            public void put( String key, String value ) {
                if (value != null) state.put(KEY_PREFIX + key, value);
                else state.remove(KEY_PREFIX + key);
            }
        });

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
        EditorWindow window = new EditorWindow(editor);
        window.initialize();
        window.show();

        if (args.length > 0) {
            Path path = Path.of(args[0]);
            if (Files.exists(path)) {
                window.openFile(path);
            }
        }

        registerAboutHandler(window);
        registerOpenFileHandler(window);
    }

    private static void registerOpenFileHandler(EditorWindow window) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.APP_OPEN_FILE)) {
                    desktop.setOpenFileHandler(e -> {
                        List<Path> paths = e.getFiles().stream().map(File::toPath).toList();
                        if (paths.size() > 1) {
                            LOGGER.warn("Loading multiple files is not supported!");
                        }

                        window.openFile(paths.get(0));
                    });
                }
            }
        } catch (Throwable e) {
            LOGGER.error("Failed to register open file handler", e);
        }
    }

    private static void registerAboutHandler(EditorWindow window) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.APP_ABOUT)) {
                    desktop.setAboutHandler(e -> {
                        AboutDialog.showAboutDialog(window.getFrame());
                    });
                }
            }
        } catch (Throwable e) {
            LOGGER.error("Failed to register about handler", e);
        }
    }
}
