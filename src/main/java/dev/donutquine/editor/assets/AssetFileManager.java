package dev.donutquine.editor.assets;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import dev.donutquine.editor.assets.events.AssetFileClosedEvent;
import dev.donutquine.editor.assets.events.AssetFileOpenedEvent;
import dev.donutquine.editor.assets.events.AssetFileSelectedEvent;
import dev.donutquine.editor.assets.exceptions.AssetLoadingException;
import dev.donutquine.editor.events.EventBus;
import dev.donutquine.editor.events.EventListener;

public class AssetFileManager {
    private final List<AssetFile<?>> files = new ArrayList<>();
    private final AssetFileLoaderFactory loaderFactory;

    private final EventBus<AssetFileClosedEvent> fileClosedEventBus = new EventBus<>();
    private final EventBus<AssetFileOpenedEvent> fileOpenedEventBus = new EventBus<>();
    private final EventBus<AssetFileSelectedEvent> fileSelectedEventBus = new EventBus<>();

    private AssetFile<?> activeFile;

    public AssetFileManager(AssetFileLoaderFactory loaderFactory) {
        this.loaderFactory = loaderFactory;
    }

    public void openFile(Path path) throws AssetLoadingException {
        AssetFileLoader<?> loader = this.loaderFactory.create(path);
        AssetFile<?> file = loader.load();

        files.add(file);
        fileOpenedEventBus.fire(new AssetFileOpenedEvent(file, files.size() - 1));
        setActiveFile(file);
    }

    public void closeFile(AssetFile<?> file) {
        int index = files.indexOf(file);
        files.remove(file);
        file.close();
        fileClosedEventBus.fire(new AssetFileClosedEvent(index));

        if (file == activeFile) {
            if (!files.isEmpty()) {
                setActiveFile(files.get(Math.max(0, index - 1)));
            } else {
                setActiveFile(null);
            }
        }
    }

    public void setActiveFile(AssetFile<?> file) {
        if (file == activeFile) return;

        activeFile = file;
        fileSelectedEventBus.fire(new AssetFileSelectedEvent(file));
    }

    public void registerSelectedEventListener(EventListener<AssetFileSelectedEvent> listener) {
        fileSelectedEventBus.register(listener);
    }

    public void registerOpenedEventListener(EventListener<AssetFileOpenedEvent> listener) {
        fileOpenedEventBus.register(listener);
    }

    public void registerClosedEventListener(EventListener<AssetFileClosedEvent> listener) {
        fileClosedEventBus.register(listener);
    }

    public AssetFile<?> getActiveFile() {
        return activeFile;
    }

    public List<AssetFile<?>> getFiles() {
        return Collections.unmodifiableList(files);
    }
}
