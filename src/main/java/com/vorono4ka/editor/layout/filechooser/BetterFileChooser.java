package com.vorono4ka.editor.layout.filechooser;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class BetterFileChooser extends JFileChooser {
    private static final String SEPARATOR = Path.of("").getFileSystem().getSeparator();
    private static final String QUOTE = "\"";

    static {
        // Disables left shortcut panel
        UIManager.put("FileChooser.noPlacesBar", Boolean.TRUE);
    }

    @Override
    public int showOpenDialog(Component parent) throws HeadlessException {
        FileFilter[] choosableFileFilters = getChoosableFileFilters();
        if (choosableFileFilters.length != 0) {
            setFileFilter(getAllExtensionsFilter(choosableFileFilters));
        } else {
            setAcceptAllFileFilterUsed(true);
        }

        return super.showOpenDialog(parent);
    }

    @Override
    public void setSelectedFile(File file) {
        if (file != null) {
            // Note: fix of annoying bug with quoted paths when copying paths using Ctrl+Shift+C on Windows
            // Note: this bug doesn't even exist in multi selection mode
            String path = file.getPath();
            String prefix = getCurrentDirectory() + SEPARATOR + QUOTE;
            if (path.startsWith(prefix) && path.endsWith(QUOTE)) {
                file = new File(path.substring(prefix.length(), path.length() - 1));
            }
        }

        super.setSelectedFile(file);
    }

    private static FileNameExtensionFilter getAllExtensionsFilter(FileFilter[] choosableFileFilters) {
        List<String> supportedExtensions = new ArrayList<>();
        for (FileFilter fileFilter : choosableFileFilters) {
            if (fileFilter instanceof FileNameExtensionFilter extensionFilter) {
                supportedExtensions.addAll(List.of(extensionFilter.getExtensions()));
            }
        }

        String description = "All supported files (" + String.join(", ", supportedExtensions.stream().map(extension -> "*." + extension).toList()) + ')';
        return new FileNameExtensionFilter(description, supportedExtensions.toArray(String[]::new));
    }
}
