package com.vorono4ka.resources;

import java.nio.file.Files;
import java.nio.file.Path;

public class ResourceManager {
    public static boolean doesFileExist(String path) {
        return Files.exists(Path.of(path));
    }
}
