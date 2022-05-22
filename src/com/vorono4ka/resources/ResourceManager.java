package com.vorono4ka.resources;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class ResourceManager {
    public static boolean doesFileExist(String path) {
        return Files.exists(Path.of(path));
    }

    public static String load(String filename) {
        ClassLoader classLoader = ResourceManager.class.getClassLoader();
        try (InputStream resource = classLoader.getResourceAsStream(filename)) {
            if (resource == null) return null;

            return new String(resource.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
