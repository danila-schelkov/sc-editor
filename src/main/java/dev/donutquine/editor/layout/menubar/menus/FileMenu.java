package dev.donutquine.editor.layout.menubar.menus;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingWorker;
import org.jetbrains.annotations.NotNull;
import com.formdev.flatlaf.util.SystemFileChooser;
import com.formdev.flatlaf.util.SystemFileChooser.FileFilter;
import com.formdev.flatlaf.util.SystemFileChooser.FileNameExtensionFilter;
import dev.donutquine.editor.assets.AssetFile;
import dev.donutquine.editor.assets.AssetFileManager;
import dev.donutquine.editor.assets.SavableAsset;
import dev.donutquine.editor.layout.filechooser.BetterFileChooser;
import dev.donutquine.editor.layout.shortcut.KeyboardUtils;
import dev.donutquine.editor.layout.windows.EditorWindow;
import dev.donutquine.utilities.PathUtils;

public class FileMenu extends JMenu {
    private static final String EXTSEP = ".";

    private final EditorWindow window;

    private final JMenuItem saveButton;
    private final JMenuItem saveAsButton;

    public FileMenu(EditorWindow window) {
        super("File");

        this.window = window;

        setMnemonic(KeyEvent.VK_F);

        JMenuItem open = new JMenuItem("Open", KeyEvent.VK_O);
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyboardUtils.ctrlButton()));

        this.saveButton = new JMenuItem("Save", KeyEvent.VK_O);
        this.saveButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyboardUtils.ctrlButton()));

        JMenuItem openScreenshotsFolderButton = new JMenuItem("Open screenshots folder");

        this.saveAsButton = new JMenuItem("Save as...", KeyEvent.VK_O);
        this.saveAsButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyboardUtils.ctrlButton() | InputEvent.SHIFT_DOWN_MASK));
        JMenuItem close = new JMenuItem("Close", KeyEvent.VK_C);
        close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyboardUtils.ctrlButton()));
        JMenuItem closeAll = new JMenuItem("Close All", KeyEvent.VK_C);
        closeAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyboardUtils.ctrlButton() | InputEvent.SHIFT_DOWN_MASK));
        JMenuItem exit = new JMenuItem("Exit");

        open.addActionListener(this::open);
        this.saveButton.addActionListener(this::save);
        openScreenshotsFolderButton.addActionListener(e -> {
            try {
                Path screenshots = Path.of("screenshots").toAbsolutePath();
                screenshots.toFile().mkdirs();

                Desktop.getDesktop().open(screenshots.toFile());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        close.addActionListener(this::close);
        closeAll.addActionListener(this::closeAll);
        exit.addActionListener(FileMenu::exit);

        this.add(open);
        this.add(this.saveButton);
        this.add(this.saveAsButton);
        this.add(close);
        this.add(closeAll);

        this.addSeparator();
        this.add(openScreenshotsFolderButton);

        this.addSeparator();
        this.add(exit);

        this.checkCanSave();
    }

    private void close(ActionEvent e) {
        AssetFileManager assetFileManager = this.window.getEditor().getAssetFileManager();
        AssetFile<?> activeFile = assetFileManager.getActiveFile();
        assert activeFile != null;

        assetFileManager.closeFile(activeFile);
    }

    private void closeAll(ActionEvent e) {
        AssetFileManager assetFileManager = this.window.getEditor().getAssetFileManager();
        List<AssetFile<?>> files = new ArrayList<>(assetFileManager.getFiles());
        for (AssetFile<?> file : files) {
            assetFileManager.closeFile(file);
        }
    }

    private static void exit(ActionEvent e) {
        System.exit(0);
    }

    public void checkCanSave() {
        boolean canSave = window.getEditor().getAssetFileManager().getActiveFile() instanceof SavableAsset;

        this.saveButton.setEnabled(canSave);
        this.saveAsButton.setEnabled(canSave);
    }

    private void open(ActionEvent e) {
        SystemFileChooser fileChooser = new SystemFileChooser();
        fileChooser.setStateStoreID("open");
        fileChooser.setFileSelectionMode(SystemFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.addChoosableFileFilter(new SystemFileChooser.FileNameExtensionFilter("Supercell SWF (*.sc, *.sc2)", "sc", "sc2"));
        fileChooser.addChoosableFileFilter(new SystemFileChooser.FileNameExtensionFilter("Supercell Texture (*.sctx)", "sctx"));

        int result = fileChooser.showOpenDialog(this.window.getFrame());
        if (result != SystemFileChooser.APPROVE_OPTION) return;

        Path path = getPathWithExtension(fileChooser, null);
        if (!Files.exists(path)) {
            this.window.showErrorDialog("File %s does not exist".formatted(path));
            return;
        }

        SwingWorker<Integer, Integer> worker = new SwingWorker<>() {
            @Override
            protected Integer doInBackground() {
                window.openFile(path);
                return 0;
            }

            @Override
            protected void done() {
                super.done();
            }
        };

        worker.execute();
    }

    private void save(ActionEvent actionEvent) {
        if (window.getEditor().getAssetFileManager().getActiveFile() instanceof SavableAsset savableAsset) {
            SystemFileChooser fileChooser = new SystemFileChooser();
            fileChooser.setStateStoreID("save");
            fileChooser.setFileSelectionMode(SystemFileChooser.FILES_ONLY);
            fileChooser.setMultiSelectionEnabled(false);
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Supercell SWF (*.sc)", "sc"));

            int result = fileChooser.showSaveDialog(this.window.getFrame());
            if (result != SystemFileChooser.APPROVE_OPTION) return;

            Path path = getPathWithExtension(fileChooser, null);
            if (path == null) return;

            savableAsset.save(path);
        }
    }

    private static @NotNull BetterFileChooser createFileChooser(Preferences preferences, String saveDirectoryKey) {
        String lastDirectoryString = preferences.get(saveDirectoryKey, null);
        Path lastDirectory = lastDirectoryString != null ? Path.of(lastDirectoryString) : null;

        return new BetterFileChooser(lastDirectory);
    }

    public Path getPathWithExtension(SystemFileChooser fileChooser, String defaultExtension) {
        Path path = fileChooser.getSelectedFile().toPath();
        String fileExtension = PathUtils.getFileExtension(path.getFileName().toString());
        if (fileExtension != null) {
            for (FileFilter fileFilter : fileChooser.getChoosableFileFilters()) {
                if (fileFilter instanceof FileNameExtensionFilter extensionFilter) {
                    for (String extension : extensionFilter.getExtensions()) {
                        if (fileExtension.equals(extension)) {
                            return path;
                        }
                    }
                }
            }
        }

        if (fileChooser.getFileFilter() instanceof FileNameExtensionFilter extensionFilter) {
            for (String extension : extensionFilter.getExtensions()) {
                if (path.endsWith(EXTSEP + extension)) {
                    return path;
                }
            }

            return path.resolveSibling( path.getFileName() + EXTSEP + extensionFilter.getExtensions()[0]);
        } else if (defaultExtension != null && !path.endsWith(EXTSEP + defaultExtension)) {
            return path.resolveSibling(path.getFileName() + EXTSEP + defaultExtension);
        }

        return path;
    }
}
