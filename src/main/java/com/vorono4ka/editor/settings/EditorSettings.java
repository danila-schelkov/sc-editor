package com.vorono4ka.editor.settings;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class EditorSettings {
    private final Path path;

    private boolean shouldDisplayPolygons;
    private float pixelSize = 1.0f;

    private EditorSettings(Path path) {
        this.path = path;
    }

    public static EditorSettings load(Path path) throws IOException {
        EditorSettings settings = new EditorSettings(path);

        Properties props = new Properties();
        if (!path.toFile().exists()) {
            return settings;
        }

        props.load(Files.newBufferedReader(path));

        settings.shouldDisplayPolygons = Boolean.parseBoolean(props.getProperty("shouldDisplayPolygons", "false"));
        settings.pixelSize = Float.parseFloat(props.getProperty("pixelSize", "1.0"));

        return settings;
    }

    public void save() {
        Properties props = new Properties();
        props.setProperty("pixelSize", String.valueOf(pixelSize));
        props.setProperty("shouldDisplayPolygons", String.valueOf(shouldDisplayPolygons));

        try {
            props.store(Files.newBufferedWriter(path), null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isShouldDisplayPolygons() {
        return shouldDisplayPolygons;
    }

    public void setShouldDisplayPolygons(boolean shouldDisplayPolygons) {
        this.shouldDisplayPolygons = shouldDisplayPolygons;
    }

    public float getPixelSize() {
        return pixelSize;
    }

    public void setPixelSize(float pixelSize) {
        if (pixelSize < 0) throw new IllegalArgumentException("pixelSize must be >= 0.0f");
        this.pixelSize = pixelSize;
    }
}
