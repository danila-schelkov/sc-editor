package dev.donutquine.editor.assets;

import java.nio.file.Path;
import dev.donutquine.editor.assets.exceptions.AssetLoadingException;
import dev.donutquine.swf.SupercellSWF;
import dev.donutquine.swf.exceptions.LoadingFaultException;
import dev.donutquine.swf.exceptions.TextureFileNotFound;
import dev.donutquine.swf.exceptions.UnableToFindObjectException;
import dev.donutquine.swf.exceptions.UnsupportedCustomPropertyException;

public class SupercellSWFAssetFileLoader implements AssetFileLoader<SupercellSWF> {
    private final Path path;

    public SupercellSWFAssetFileLoader(Path path) {
        this.path = path;
    }

    @Override
    public AssetFile<SupercellSWF> load() throws AssetLoadingException {
        return new SupercellSWFAssetFile(loadInternal(this.path));
    }

    public static SupercellSWF loadInternal(Path path) throws AssetLoadingException {
        SupercellSWF swf = new SupercellSWF();

        try {
            swf.load(path.toString(), path.getFileName().toString(), false);
        } catch (LoadingFaultException | UnableToFindObjectException | UnsupportedCustomPropertyException | TextureFileNotFound e) {
            throw new AssetLoadingException(e);
        }
        return swf;
    }
}
