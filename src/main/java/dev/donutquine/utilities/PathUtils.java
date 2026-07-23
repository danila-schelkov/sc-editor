package dev.donutquine.utilities;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

public class PathUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(PathUtils.class);

    public static void deleteDirectory(Path directory) {
        assert directory.toFile().isDirectory();
        
        try {
            try (Stream<Path> walk = Files.walk(directory)) {
                walk.sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
            }
        } catch (IOException e) {
            LOGGER.error("Failed to clean frames directory", e);
        }
    }

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

    private PathUtils() {}
}
