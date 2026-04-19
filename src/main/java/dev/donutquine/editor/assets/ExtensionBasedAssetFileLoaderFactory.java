package dev.donutquine.editor.assets;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import dev.donutquine.editor.assets.exceptions.AssetFileLoaderAlreadyRegisteredException;
import dev.donutquine.editor.assets.exceptions.AssetFileLoaderNotFoundException;

public class ExtensionBasedAssetFileLoaderFactory implements AssetFileLoaderFactory {
    private final Map<String, Function<Path, AssetFileLoader<?>>> loaderResolvers = new HashMap<>();

    @Override
    public AssetFileLoader<?> create(Path path) throws AssetFileLoaderNotFoundException {
        String extension = getExtension(path);
        Function<Path, AssetFileLoader<?>> function = this.loaderResolvers.get(extension);
        if (function == null) {
            throw new AssetFileLoaderNotFoundException(
                    "Couldn't find a loader resolver function for extension: " + extension);
        }

        return function.apply(path);
    }

    public void register(Function<Path, AssetFileLoader<?>> loaderResolver, String... extensions) {
        for (String extension : extensions) {
            register(extension, loaderResolver);
        }
    }

    public void register(String extension, Function<Path, AssetFileLoader<?>> loaderResolver) {
        if (this.loaderResolvers.containsKey(extension)) {
            throw new AssetFileLoaderAlreadyRegisteredException(
                    "Loader already registered for extension: " + extension);
        }

        this.loaderResolvers.put(extension, loaderResolver);
    }

    public void unregister(String extension) {
        this.loaderResolvers.remove(extension);
    }

    private static String getExtension(Path path) {
        String fileName = path.getFileName().toString();
        int dotIndex = fileName.lastIndexOf('.');

        if (dotIndex == -1 || dotIndex == fileName.length() - 1) {
            return null;
        } else {
            return fileName.substring(dotIndex + 1);
        }
    }
}
