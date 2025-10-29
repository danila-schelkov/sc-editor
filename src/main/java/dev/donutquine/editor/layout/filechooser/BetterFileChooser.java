package dev.donutquine.editor.layout.filechooser;

import dev.donutquine.utilities.PathUtils;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class BetterFileChooser extends JFileChooser {
    private static final String SEPARATOR = Path.of("").getFileSystem().getSeparator();
    private static final String EXTSEP = ".";
    private static final String QUOTE = "\"";

    static {
        // Disables left shortcut panel
        UIManager.put("FileChooser.noPlacesBar", Boolean.TRUE);
    }

    public BetterFileChooser() {
        super();
    }

    public BetterFileChooser(@Nullable Path currentDirectoryPath) {
        super(currentDirectoryPath != null ? currentDirectoryPath.toString() : null);
    }

    /// Returns path if file chosen, {@code null} otherwise.
    public static @Nullable Path showSaveDialog(BetterFileChooser chooser, Component parent, String defaultExtension) {
        int result = chooser.showSaveDialog(parent);
        if (result != JFileChooser.APPROVE_OPTION) {
            return null;
        }

        Path path = chooser.getPathWithExtension(defaultExtension);
        if (path == null) {
            return null;
        }

        if (Files.exists(path)) {
            Object[] options = {"Yes", "Cancel"};
            int warningResult = JOptionPane.showOptionDialog(
                parent,
                "There is already a file with that name.\n" +
                    "Do you want to replace it?",
                "Answer the question",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                options,
                options[0]
            );

            if (warningResult != JOptionPane.OK_OPTION) {
                return null;
            }
        }

        return path;
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

    public Path getPathWithExtension(@Nullable String defaultExtension) {
        Path path = this.getSelectedFile().toPath();
        String fileExtension = PathUtils.getFileExtension(path.getFileName().toString());
        if (fileExtension != null) {
            for (FileFilter fileFilter : getChoosableFileFilters()) {
                if (fileFilter instanceof FileNameExtensionFilter extensionFilter) {
                    for (String extension : extensionFilter.getExtensions()) {
                        if (fileExtension.equals(extension)) {
                            return path;
                        }
                    }
                }
            }
        }

        if (this.getFileFilter() instanceof FileNameExtensionFilter extensionFilter) {
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
