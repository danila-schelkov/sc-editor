package dev.donutquine.editor.cli;

import java.util.ArrayList;
import java.util.List;
import org.kohsuke.args4j.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jogamp.opengl.GLOffscreenAutoDrawable;
import dev.donutquine.swf.DisplayObjectOriginal;
import dev.donutquine.swf.Export;
import dev.donutquine.swf.SupercellSWF;
import dev.donutquine.swf.exceptions.UnableToFindObjectException;
import dev.donutquine.swf.movieclips.MovieClipChild;
import dev.donutquine.swf.movieclips.MovieClipFrame;
import dev.donutquine.swf.movieclips.MovieClipOriginal;
import dev.donutquine.swf.shapes.ShapeOriginal;
import dev.donutquine.swf.textfields.TextFieldOriginal;

public class InspectCommand extends SwfCliCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(InspectCommand.class);

    @Option(name = "--print-exports", depends = {"--input"})
    private boolean shouldPrintExports;

    @Option(name = "--id", depends = {"--input"})
    private List<Integer> ids = new ArrayList<>();

    @Override
    public int execute() {
        int exitCode = super.execute();
        if (exitCode != 0) {
            return exitCode;
        }

        SupercellSWF swf = assetFile.asset;
        if (shouldPrintExports) {
            for (Export export : swf.getExports()) {
                System.out.printf("%s - %d\n", export.name(), export.id());
            }
        }

        System.out.printf("Total object count: %d\n", (swf.getMovieClipCount() + swf.getShapes().size() + swf.getTextFields().size()));
        System.out.println();

        for (int id : ids) {
            try {
				DisplayObjectOriginal original = swf.getOriginalDisplayObject(id, null);

                System.out.println("*".repeat(30));
                System.out.println();

                System.out.printf("%d — ", id);
                if (original instanceof MovieClipOriginal movieClip) {
                    System.out.printf("MovieClip");
                    String exportName = movieClip.getExportName();
                    if (exportName != null) {
                        System.out.printf(" — %s", exportName);
                    }
                    System.out.println("\n");

                    List<MovieClipChild> children = movieClip.getChildren();
                    if (children.isEmpty()) {
                        System.out.println("No children\n");
                    } else {
                        System.out.println("Children:");
                        for (MovieClipChild child : children) {
                            System.out.printf("%d", child.id());
                            if (child.name() != null) {
                                System.out.printf(" — %s", child.name());
                            }
                            System.out.println();
                        }
                    }
                    System.out.println();

                    List<MovieClipFrame> frames = movieClip.getFrames();
                    if (frames.isEmpty()) {
                        System.out.println("No frames\n");
                    } else {
                        System.out.printf("Frame count: %d\n", frames.size());
                        System.out.println();

                        int namedFrameCount = 0;
                        for (int i = 0; i < frames.size(); i++) {
                            MovieClipFrame frame = frames.get(i);
                            if (frame.getLabel() != null) {
                                namedFrameCount++;
                            }
                        }

                        if (namedFrameCount == 0) {
                            System.out.println("No named frames");
                        } else {
                            System.out.printf("Named frames (%d):\n", namedFrameCount);
                            for (int i = 0; i < frames.size(); i++) {
                                MovieClipFrame frame = frames.get(i);
                                if (frame.getLabel() != null) {
                                    System.out.printf("%s — %d\n", frame.getLabel(), i);
                                }
                            }
                        }
                    }

                    System.out.println();
                } else if (original instanceof TextFieldOriginal textField) {
                    System.out.printf("TextField");
                    System.out.println();

                    System.out.println();
                } else if (original instanceof ShapeOriginal shape) {
                    System.out.printf("Shape");
                    System.out.println();

                    System.out.println();
                }
			} catch (UnableToFindObjectException e) {
                LOGGER.error("Could not get display object by id {}", id);
			}
        }

        return 0;
    }

	@Override
	public void setDrawable(GLOffscreenAutoDrawable drawable) { /* IGNORED */}
}
