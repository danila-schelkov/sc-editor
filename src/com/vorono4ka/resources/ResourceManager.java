package com.vorono4ka.resources;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ResourceManager {
    public static boolean doesFileExist(String path) {
        return Files.exists(Path.of(path));
    }

    public static String load(String path) {
        try {
            return Files.readString(Path.of("resources/" + path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
