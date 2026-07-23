package dev.donutquine.editor.cli;

import java.nio.file.Path;
import org.kohsuke.args4j.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dev.donutquine.editor.assets.SupercellSWFAssetFile;
import dev.donutquine.editor.assets.SupercellSWFAssetFileLoader;
import dev.donutquine.editor.assets.exceptions.AssetLoadingException;

public abstract class SwfCliCommand implements CliCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(SwfCliCommand.class);

    @Option(name = "--input", aliases = {"-i"}, required = true)
    protected Path inputPath;

    protected SupercellSWFAssetFile assetFile;

	@Override
	public int execute() {
        try {
            this.assetFile = (SupercellSWFAssetFile) new SupercellSWFAssetFileLoader(inputPath).load();
        } catch (AssetLoadingException e) {
            LOGGER.error("Failed to load file: {}", inputPath, e);
            return 1;
        }

        return 0;
    }
}
