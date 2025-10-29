package dev.donutquine.utilities;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;

public class PathUtils {
    public static @NotNull Path replaceExtension(@NotNull Path path, @NotNull String newExtension) {
        String filename = path.getFileName().toString();
        int i = filename.lastIndexOf('.');
        String newFilename = String.join(".", filename.substring(0, i), newExtension);

        Path parent = path.getParent();
        if (parent != null) {
            return parent.resolve(newFilename);
        }

        return Path.of(newFilename);
    }

    public static @Nullable String getFileExtension(@Nullable String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return null;
        }

        int dotIndex = filePath.lastIndexOf('.');

        // If no dot is found, or the dot is the last character (e.g., "filename.")
        if (dotIndex == -1 || dotIndex == filePath.length() - 1) {
            return null; // No extension
        }

        // Extract the substring after the last dot
        return filePath.substring(dotIndex + 1);
    }
}
