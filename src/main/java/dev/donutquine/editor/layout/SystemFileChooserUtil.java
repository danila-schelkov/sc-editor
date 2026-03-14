package dev.donutquine.editor.layout;

import java.nio.file.Path;
import com.formdev.flatlaf.util.SystemFileChooser;
import com.formdev.flatlaf.util.SystemFileChooser.FileFilter;
import com.formdev.flatlaf.util.SystemFileChooser.FileNameExtensionFilter;
import dev.donutquine.utilities.PathUtils;

public abstract class SystemFileChooserUtil {
    private static final String EXTSEP = ".";

    public static Path getPathWithExtension(SystemFileChooser fileChooser, String defaultExtension) {
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
