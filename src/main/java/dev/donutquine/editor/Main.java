package dev.donutquine.editor;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.prefs.Preferences;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.icons.FlatTabbedPaneCloseIcon;
import com.formdev.flatlaf.util.SystemFileChooser;
import dev.donutquine.editor.layout.GestureUtilities;
import dev.donutquine.editor.layout.dialogs.AboutDialog;
import dev.donutquine.editor.layout.dialogs.ExceptionDialog;
import dev.donutquine.editor.layout.windows.EditorWindow;
import dev.donutquine.editor.settings.EditorPreferences;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private static final Set<String> jvmRequiredArgs = Set.of("--add-exports=java.base/java.lang=ALL-UNNAMED", "--add-exports=java.desktop/sun.awt=ALL-UNNAMED", "--add-exports=java.desktop/sun.java2d=ALL-UNNAMED");

    public static void main(String[] args) {
        try {
            Set<String> jvmArgs = new HashSet<>(ManagementFactory.getRuntimeMXBean().getInputArguments());
            if (!jvmArgs.containsAll(jvmRequiredArgs)) {
                restartWithRequiredJvmArgs(args);
                return;
            }
        } catch (Throwable t) {
            LOGGER.error("failed to get program arguments", t);
        }

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
        EditorPreferences settings;
        try {
            settings = EditorPreferences.load(Path.of(".", "editor.properties"));
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

    private static void restartWithRequiredJvmArgs(String[] args) {
        try {
            List<String> command = new ArrayList<>();

            String javaBin = System.getProperty("java.home")
                    + File.separator + "bin"
                    + File.separator + "java";

            command.add(javaBin);

            command.addAll(
                ManagementFactory.getRuntimeMXBean().getInputArguments()
            );

            command.addAll(jvmRequiredArgs);
            if (GestureUtilities.isDarwin()) {
                command.add(GestureUtilities.REQUIRED_JVM_OPT);
            }

            String classpath = System.getProperty("java.class.path");

            String commandProperty = System.getProperty("sun.java.command");

            if (commandProperty.endsWith(".jar")) {
                command.add("-jar");
                command.add(commandProperty.split(" ")[0]);

            } else {
                command.add("-cp");
                command.add(classpath);

                String mainClass = commandProperty.split(" ")[0];
                command.add(mainClass);
            }

            command.addAll(Arrays.asList(args));

            ProcessBuilder builder = new ProcessBuilder(command);

            builder.inheritIO();
            builder.start();

            System.exit(0);
        } catch (Exception e) {
            LOGGER.error("Failed to restart JVM with required arguments", e);
        }
    }
}
