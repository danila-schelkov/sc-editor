package com.vorono4ka.utilities;

import java.nio.file.Path;

public class PathUtils {
    public static Path replaceExtension(Path path, String newExtension) {
        String filename = path.getFileName().toString();
        int i = filename.lastIndexOf('.');
        String newFilename = String.join(".", filename.substring(0, i), newExtension);

        Path parent = path.getParent();
        if (parent != null) {
            return parent.resolve(newFilename);
        }

        return Path.of(newFilename);
    }
}
