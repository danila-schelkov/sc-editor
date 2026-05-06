package dev.donutquine.editor.settings;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.prefs.Preferences;

public class EditorPreferences {
    private static final Preferences PREFERENCES = Preferences.userRoot().node("sc-editor");
    private static final String SHOULD_DISPLAY_POLYGONS = "shouldDisplayPolygons";
    private static final String PIXEL_SIZE = "pixelSize";

    private static final String PRESERVE_STAGE_CENTER_KEY = "preserve-stage-center";
    private static final boolean PRESERVE_STAGE_CENTER_BY_DEFAULT = true;

    private final Path path;

    private boolean shouldDisplayPolygons;
    private float pixelSize = 1.0f;

    private EditorPreferences(Path path) {
        this.path = path;
    }

    public static EditorPreferences load(Path path) throws IOException {
        EditorPreferences preferences = new EditorPreferences(path);

        Properties props = new Properties();
        if (!path.toFile().exists()) {
            return preferences;
        }

        props.load(Files.newBufferedReader(path));

        preferences.shouldDisplayPolygons = Boolean.parseBoolean(props.getProperty(SHOULD_DISPLAY_POLYGONS, "false"));
        preferences.pixelSize = Float.parseFloat(props.getProperty(PIXEL_SIZE, "1.0"));

        return preferences;
    }

    public void save() {
        Properties props = new Properties();
        props.setProperty(PIXEL_SIZE, String.valueOf(pixelSize));
        props.setProperty(SHOULD_DISPLAY_POLYGONS, String.valueOf(shouldDisplayPolygons));

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

    public boolean shouldPreserveStageCenter() {
        return PREFERENCES.getBoolean(PRESERVE_STAGE_CENTER_KEY, PRESERVE_STAGE_CENTER_BY_DEFAULT);
    }

    public void setShouldPreserveStageCenter(boolean state) {
        PREFERENCES.putBoolean(PRESERVE_STAGE_CENTER_KEY, state);
    }
}
