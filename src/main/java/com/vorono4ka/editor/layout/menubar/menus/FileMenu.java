package com.vorono4ka.editor.layout.menubar.menus;

import com.vorono4ka.editor.Editor;
import com.vorono4ka.editor.layout.filechooser.BetterFileChooser;
import com.vorono4ka.editor.layout.shortcut.KeyboardUtils;
import com.vorono4ka.resources.ResourceManager;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Path;
import java.util.prefs.Preferences;

public class FileMenu extends JMenu {
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
        String lastDirectory = preferences.get("lastDirectory", null);

        BetterFileChooser fileChooser = lastDirectory != null ? new BetterFileChooser(lastDirectory) : new BetterFileChooser();
        fileChooser.setFileSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Supercell Texture (*.sctx)", "sctx"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Supercell SWF (*.sc, *.sc2)", "sc", "sc2"));

        int result = fileChooser.showOpenDialog(this.frame);
        if (result != JFileChooser.APPROVE_OPTION) return;

        Path path = fileChooser.getSelectedFile().toPath();

        preferences.put("lastDirectory", path.toAbsolutePath().getParent().toString());

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
        BetterFileChooser fileChooser = new BetterFileChooser();
        fileChooser.setFileSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Supercell SWF (*.sc)", "sc"));

        int result = fileChooser.showSaveDialog(this.frame);
        if (result != JFileChooser.APPROVE_OPTION) return;

        String path = getPathWithExtension(fileChooser);

        if (ResourceManager.doesFileExist(path)) {
            Object[] options = {"Yes", "Cancel"};
            int warningResult = JOptionPane.showOptionDialog(
                this.frame,
                "There is already a file with that name.\n" +
                    "Do you want to replace it?",
                "Answer the question",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                options,
                options[0]
            );

            if (warningResult != JOptionPane.OK_OPTION) return;
        }

        editor.saveFile(path);
    }

    private static String getPathWithExtension(BetterFileChooser fileChooser) {
        String path = fileChooser.getSelectedFile().getPath();
        if (fileChooser.getFileFilter() instanceof FileNameExtensionFilter extensionFilter) {
            String extension = extensionFilter.getExtensions()[0];
            if (!path.endsWith("." + extension)) {
                path += "." + extension;
            }
        } else {
            // TODO: choose extension depending on the file format
            if (!path.endsWith(".sc")) {
                path += ".sc";
            }
        }
        return path;
    }
}
