package dev.donutquine.resources;

import java.io.IOException;
import java.io.InputStream;

public class ResourceManager {
    public static String loadString(String filename) {
        ClassLoader classLoader = ResourceManager.class.getClassLoader();
        try (InputStream resource = classLoader.getResourceAsStream(filename)) {
            if (resource == null) return null;

            return new String(resource.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] loadBytes(String filename) {
        ClassLoader classLoader = ResourceManager.class.getClassLoader();
        try (InputStream resource = classLoader.getResourceAsStream(filename)) {
            if (resource == null) return null;

            return resource.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static InputStream loadStream(String filename) {
        ClassLoader classLoader = ResourceManager.class.getClassLoader();
        return classLoader.getResourceAsStream(filename);
    }
}
