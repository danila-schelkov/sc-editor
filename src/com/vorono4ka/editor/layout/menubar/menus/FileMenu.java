package com.vorono4ka.editor.layout.menubar.menus;

import com.vorono4ka.editor.Main;
import com.vorono4ka.resources.ResourceManager;
import com.vorono4ka.swf.SupercellSWF;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class FileMenu extends JMenu {
    private final JFrame frame;
    private final JMenuItem saveButton;
    private final JMenuItem saveAsButton;

    public FileMenu(JFrame frame) {
        super("File");

        this.frame = frame;

        setMnemonic(KeyEvent.VK_F);

        JMenuItem open = new JMenuItem("Open", KeyEvent.VK_O);
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));

        this.saveButton = new JMenuItem("Save", KeyEvent.VK_O);
        this.saveButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));

        this.saveAsButton = new JMenuItem("Save as...", KeyEvent.VK_O);
        this.saveAsButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
        JMenuItem close = new JMenuItem("Close", KeyEvent.VK_C);
        JMenuItem exit = new JMenuItem("Exit");

        open.addActionListener(this::open);
        this.saveButton.addActionListener(this::save);
        close.addActionListener(FileMenu::close);
        exit.addActionListener(FileMenu::exit);

        this.add(open);
        this.add(this.saveButton);
        this.add(this.saveAsButton);
        this.add(close);

        this.addSeparator();
        this.add(exit);

        this.checkCanSave();
    }

    public void checkCanSave() {
        boolean canSave = Main.editor.getSwf() != null;

        this.saveButton.setEnabled(canSave);
        this.saveAsButton.setEnabled(canSave);
    }

    private void open(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Supercell SWF (*.sc)", "sc"));

        int result = fileChooser.showOpenDialog(this.frame);
        if (result != JFileChooser.APPROVE_OPTION) return;

        String path = fileChooser.getSelectedFile().getPath();
        if (!ResourceManager.doesFileExist(path.substring(0, path.length() - 3) + SupercellSWF.TEXTURE_EXTENSION)) {
            Object[] options = {"Yes", "Cancel"};
            int warningResult = JOptionPane.showOptionDialog(
                this.frame,
                "There is no texture file (but it may have a different suffix specified in the file).\n" +
                        "Do you want to open file anyway?",
                "Answer the question",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                options,
                options[0]
            );

            if (warningResult != 0) return;
        }

        close(null);

        Main.editor.openFile(path);
    }

    private void save(ActionEvent actionEvent) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Supercell SWF (*.sc)", "sc"));

        int result = fileChooser.showSaveDialog(this.frame);
        if (result != JFileChooser.APPROVE_OPTION) return;

        String path = fileChooser.getSelectedFile().getPath();
        if (!path.endsWith(".sc")) {
            path += ".sc";
        }

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

            if (warningResult != 0) return;
        }

        Main.editor.saveFile(path);
    }

    private static void close(ActionEvent e) {
        Main.editor.closeFile();
    }

    private static void exit(ActionEvent e) {
        System.exit(0);
    }
}
