package dev.donutquine.editor.cli;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.spi.SubCommand;
import org.kohsuke.args4j.spi.SubCommandHandler;
import org.kohsuke.args4j.spi.SubCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLDrawableFactory;
import com.jogamp.opengl.GLOffscreenAutoDrawable;
import com.jogamp.opengl.GLProfile;

public class Cli {
    private static final Logger LOGGER = LoggerFactory.getLogger(Cli.class);

    @Argument(handler = SubCommandHandler.class)
    @SubCommands({
        @SubCommand(name = "inspect", impl = InspectCommand.class),
        @SubCommand(name = "render", impl = RenderCommand.class),
    })
    private CliCommand command;

    Cli() {}

    public int run() {
        GLProfile profile = GLProfile.get(GLProfile.GL3);
        GLCapabilities caps = new GLCapabilities(profile);
        caps.setOnscreen(false);
        caps.setFBO(true);

        GLDrawableFactory factory = GLDrawableFactory.getFactory(profile);
        GLOffscreenAutoDrawable drawable = factory.createOffscreenAutoDrawable(null, caps, null, 256, 256);
        drawable.addGLEventListener(new CliGLEventListener());

        // First display() initializes the GL context (EditorStage.init runs).
        drawable.display();

        try {
            this.command.setDrawable(drawable);
            return this.command.execute();
        } finally {
            try {
                drawable.destroy();
            } catch (Throwable t) {
                LOGGER.debug("Ignored error while destroying offscreen drawable", t);
            }
        }
    }
}
