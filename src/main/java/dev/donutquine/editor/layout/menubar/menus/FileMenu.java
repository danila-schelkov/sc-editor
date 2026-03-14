package dev.donutquine.editor.layout.menubar.menus;

import dev.donutquine.editor.Editor;
import dev.donutquine.editor.assets.AssetFile;
import dev.donutquine.editor.assets.AssetFileManager;
import dev.donutquine.editor.assets.SavableAsset;
import dev.donutquine.editor.layout.filechooser.BetterFileChooser;
import dev.donutquine.editor.layout.shortcut.KeyboardUtils;
import dev.donutquine.editor.layout.windows.EditorWindow;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

public class FileMenu extends JMenu {
    private static final String LAST_DIRECTORY_KEY = "lastDirectory";
    private static final String SAVE_DIRECTORY_KEY = "saveDirectory";

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
        Preferences preferences = Preferences.userRoot().node("sc-editor");

        BetterFileChooser fileChooser = createFileChooser(preferences, LAST_DIRECTORY_KEY);
        fileChooser.setFileSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fileChooser.setFileFilter(
                new FileNameExtensionFilter("Supercell SWF (*.sc, *.sc2)", "sc", "sc2"));
        fileChooser.addChoosableFileFilter(
                new FileNameExtensionFilter("Supercell Texture (*.sctx)", "sctx"));

        int result = fileChooser.showOpenDialog(this.window.getFrame());
        if (result != JFileChooser.APPROVE_OPTION)
            return;

        Path path = fileChooser.getPathWithExtension(null);
        if (!Files.exists(path)) {
            this.window.showErrorDialog("File %s does not exist".formatted(path));
            return;
        }

        preferences.put(LAST_DIRECTORY_KEY, path.toAbsolutePath().getParent().toString());

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
            Preferences preferences = Preferences.userRoot().node("sc-editor");
            BetterFileChooser fileChooser = createFileChooser(preferences, SAVE_DIRECTORY_KEY);
            fileChooser.setFileSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            fileChooser.setFileFilter(new FileNameExtensionFilter("Supercell SWF (*.sc)", "sc"));

            Path path = BetterFileChooser.showSaveDialog(fileChooser, this.window.getFrame(), null);
            if (path == null)
                return;

            preferences.put(SAVE_DIRECTORY_KEY, path.toAbsolutePath().getParent().toString());

            savableAsset.save(path);
        }
    }

    private static @NotNull BetterFileChooser createFileChooser(Preferences preferences, String saveDirectoryKey) {
        String lastDirectoryString = preferences.get(saveDirectoryKey, null);
        Path lastDirectory = lastDirectoryString != null ? Path.of(lastDirectoryString) : null;

        return new BetterFileChooser(lastDirectory);
    }
}
