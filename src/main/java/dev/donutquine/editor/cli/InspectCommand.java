package dev.donutquine.editor.cli;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.kohsuke.args4j.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jogamp.opengl.GLOffscreenAutoDrawable;
import dev.donutquine.editor.assets.exceptions.AssetLoadingException;
import dev.donutquine.swf.DisplayObjectOriginal;
import dev.donutquine.swf.Export;
import dev.donutquine.swf.SupercellSWF;
import dev.donutquine.swf.exceptions.LoadingFaultException;
import dev.donutquine.swf.exceptions.TextureFileNotFound;
import dev.donutquine.swf.exceptions.UnableToFindObjectException;
import dev.donutquine.swf.exceptions.UnsupportedCustomPropertyException;
import dev.donutquine.swf.movieclips.MovieClipChild;
import dev.donutquine.swf.movieclips.MovieClipFrame;
import dev.donutquine.swf.movieclips.MovieClipOriginal;
import dev.donutquine.swf.shapes.ShapeOriginal;
import dev.donutquine.swf.textfields.TextFieldOriginal;

public class InspectCommand implements CliCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(InspectCommand.class);

    @Option(name = "--input", aliases = {"-i"}, required = true)
    private List<Path> inputPaths;

    @Option(name = "--print-exports", depends = {"--input"})
    private boolean shouldPrintExports;

    @Option(name = "--id", depends = {"--input"})
    private List<Integer> ids = new ArrayList<>();

    @Option(name = "--json", forbids = {"--print-exports"})
    private boolean useJson;

    @Override
    public int execute() {
        try {
            for (Path inputPath : inputPaths) {
                SupercellSWF swf = loadInternal(inputPath);
                inspectSingle(swf);
            }
        } catch (AssetLoadingException e) {
            return 1;
        }

        return 0;
    }

    public int inspectSingle(SupercellSWF swf) {
        if (useJson) {
            // Used for building arrays and dictionaries
            boolean first;

            StringBuilder jsonBuilder = new StringBuilder();
            jsonBuilder.append('{');
            jsonBuilder.append("\"exports\":{"); 
            first = true;
            for (Export export : swf.getExports()) {
                if (!first) jsonBuilder.append(',');
                jsonBuilder.append('"').append(export.name()).append('"').append(':').append(export.id());
                first = false;
            }
            jsonBuilder.append('}');

            jsonBuilder.append(',').append("\"clips\":["); 
            first = true;
            for (MovieClipOriginal movieClip : swf.getMovieClips()) {
                if (!first) jsonBuilder.append(',');
                jsonBuilder.append('{');
                jsonBuilder.append('"').append("id").append('"').append(':').append(movieClip.getId()).append(',');
                // jsonBuilder.append('"').append("fps").append('"').append(':').append(movieClip.getFps()).append(',');

                List<MovieClipChild> children = movieClip.getChildren();
                jsonBuilder.append('"').append("childCount").append('"').append(':').append(children.size());

                int namedChildCount = 0;
                for (MovieClipChild child : children) {
                    if (child.name() != null) {
                        namedChildCount++;
                    }
                }
                if (namedChildCount > 0) {
                    jsonBuilder.append(',').append('"').append("namedChildren").append('"').append(':').append('{');
                    first = true;
                    for (int i = 0; i < children.size(); i++) {
                        MovieClipChild child = children.get(i);
                        if (child.name() == null) continue;
                        if (!first) jsonBuilder.append(',');
                        jsonBuilder.append('"').append(child.name()).append('"').append(':').append(child.id());
                        first = false;
                    }
                    jsonBuilder.append('}');
                }
                // jsonBuilder.append('"').append("children").append('"').append(':').append('[');
                // first = true;
                // for (MovieClipChild child : movieClip.getChildren()) {
                //     if (!first) jsonBuilder.append(',');
                //     jsonBuilder.append('{');
                //     jsonBuilder.append('"').append("id").append('"').append(':').append(child.id());
                //     if (child.name() != null) {
                //         jsonBuilder.append(',').append('"').append("name").append('"').append(':').append('"').append(child.name()).append('"');
                //     }
                //     jsonBuilder.append('}');
                //     first = false;
                // }
                // jsonBuilder.append(']');

                List<MovieClipFrame> frames = movieClip.getFrames();

                jsonBuilder.append(',').append('"').append("frameCount").append('"').append(':').append(frames.size());

                int labeledFrameCount = 0;
                for (MovieClipFrame frame : frames) {
                    if (frame.getLabel() != null) {
                        labeledFrameCount++;
                    }
                }

                if (labeledFrameCount > 0) {
                    jsonBuilder.append(',').append('"').append("labeledFrames").append('"').append(':').append('{');
                    first = true;
                    for (int i = 0; i < frames.size(); i++) {
                        MovieClipFrame frame = frames.get(i);
                        if (frame.getLabel() == null) continue;
                        if (!first) jsonBuilder.append(',');
                        jsonBuilder.append('"').append(frame.getLabel()).append('"').append(':').append(i);
                        first = false;
                    }
                    jsonBuilder.append('}');
                }
                jsonBuilder.append('}');
                first = false;
            }
            jsonBuilder.append(']');
            jsonBuilder.append('}');

            System.out.println(jsonBuilder);
            return 0;
        }

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

                        int labeledFrameCount = 0;
                        for (int i = 0; i < frames.size(); i++) {
                            MovieClipFrame frame = frames.get(i);
                            if (frame.getLabel() != null) {
                                labeledFrameCount++;
                            }
                        }

                        if (labeledFrameCount == 0) {
                            System.out.println("No labeled frames");
                        } else {
                            System.out.printf("Labeled frames (%d):\n", labeledFrameCount);
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

    private static SupercellSWF loadInternal(Path path) throws AssetLoadingException {
        SupercellSWF swf = new SupercellSWF();

        try {
            swf.load(path.toString(), path.getFileName().toString(), false);
        } catch (LoadingFaultException | UnableToFindObjectException | UnsupportedCustomPropertyException e) {
            throw new AssetLoadingException(e);
        } catch (TextureFileNotFound e) {
            LOGGER.warn("Texture not found", e);
        }

        return swf;
    }

}
