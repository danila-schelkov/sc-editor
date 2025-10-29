package dev.donutquine.editor.layout.menubar.menus;

import dev.donutquine.editor.Editor;
import dev.donutquine.editor.layout.filechooser.BetterFileChooser;
import dev.donutquine.editor.layout.shortcut.KeyboardUtils;
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
import java.util.prefs.Preferences;

public class FileMenu extends JMenu {
    private static final String LAST_DIRECTORY_KEY = "lastDirectory";
    private static final String SAVE_DIRECTORY_KEY = "saveDirectory";

    private final Editor editor;

    private final JFrame frame;
    private final JMenuItem saveButton;
    private final JMenuItem saveAsButton;

    public FileMenu(JFrame frame, Editor editor) {
        super("File");

        this.frame = frame;
        this.editor = editor;

        setMnemonic(KeyEvent.VK_F);

        JMenuItem open = new JMenuItem("Open", KeyEvent.VK_O);
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyboardUtils.ctrlButton()));

        this.saveButton = new JMenuItem("Save", KeyEvent.VK_O);
        this.saveButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyboardUtils.ctrlButton()));

        JMenuItem openScreenshotsFolderButton = new JMenuItem("Open screenshots folder");

        this.saveAsButton = new JMenuItem("Save as...", KeyEvent.VK_O);
        this.saveAsButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyboardUtils.ctrlButton() | InputEvent.SHIFT_DOWN_MASK));
        JMenuItem close = new JMenuItem("Close", KeyEvent.VK_C);
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
        exit.addActionListener(FileMenu::exit);

        this.add(open);
        this.add(this.saveButton);
        this.add(this.saveAsButton);
        this.add(close);

        this.addSeparator();
        this.add(openScreenshotsFolderButton);

        this.addSeparator();
        this.add(exit);

        this.checkCanSave();
    }

    private void close(ActionEvent e) {
        editor.closeFile();
    }

    private static void exit(ActionEvent e) {
        System.exit(0);
    }

    public void checkCanSave() {
        boolean canSave = editor.getSwf() != null;

        this.saveButton.setEnabled(canSave);
        this.saveAsButton.setEnabled(canSave);
    }

    private void open(ActionEvent e) {
        Preferences preferences = Preferences.userRoot().node("sc-editor");

        BetterFileChooser fileChooser = createFileChooser(preferences, LAST_DIRECTORY_KEY);
        fileChooser.setFileSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Supercell SWF (*.sc, *.sc2)", "sc", "sc2"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Supercell Texture (*.sctx)", "sctx"));

        int result = fileChooser.showOpenDialog(this.frame);
        if (result != JFileChooser.APPROVE_OPTION) return;

        Path path = fileChooser.getPathWithExtension(null);
        if (!Files.exists(path)) {
            editor.getWindow().showErrorDialog("File %s does not exist".formatted(path));
            return;
        }

        preferences.put(LAST_DIRECTORY_KEY, path.toAbsolutePath().getParent().toString());

        close(null);

        SwingWorker<Integer, Integer> worker = new SwingWorker<>() {
            @Override
            protected Integer doInBackground() {
                editor.openFile(path);
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
        Preferences preferences = Preferences.userRoot().node("sc-editor");
        BetterFileChooser fileChooser = createFileChooser(preferences, SAVE_DIRECTORY_KEY);
        fileChooser.setFileSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Supercell SWF (*.sc)", "sc"));

        Path path = BetterFileChooser.showSaveDialog(fileChooser, this.frame, null);
        if (path == null) return;

        preferences.put(SAVE_DIRECTORY_KEY, path.toAbsolutePath().getParent().toString());

        editor.saveFile(path.toString());
    }

    private static @NotNull BetterFileChooser createFileChooser(Preferences preferences, String saveDirectoryKey) {
        String lastDirectoryString = preferences.get(saveDirectoryKey, null);
        Path lastDirectory = lastDirectoryString != null ? Path.of(lastDirectoryString) : null;

        return new BetterFileChooser(lastDirectory);
    }
}
