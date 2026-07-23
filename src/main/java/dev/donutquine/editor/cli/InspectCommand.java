package dev.donutquine.editor.cli;

import org.kohsuke.args4j.Option;
import com.jogamp.opengl.GLOffscreenAutoDrawable;
import dev.donutquine.swf.Export;

public class InspectCommand extends SwfCliCommand {
    @Option(name = "--print-exports", depends = {"--input"})
    private boolean shouldPrintExports;

    @Override
    public int execute() {
        int exitCode = super.execute();
        if (exitCode != 0) {
            return exitCode;
        }

        if (shouldPrintExports) {
            for (Export export : assetFile.asset.getExports()) {
                System.out.printf("%s - %d\n", export.name(), export.id());
            }
        }

        return 0;
    }

	@Override
	public void setDrawable(GLOffscreenAutoDrawable drawable) { /* IGNORED */}
}
