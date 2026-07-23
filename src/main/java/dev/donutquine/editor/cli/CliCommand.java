package dev.donutquine.editor.cli;

import com.jogamp.opengl.GLOffscreenAutoDrawable;

public interface CliCommand {
    int execute();

    void setDrawable(GLOffscreenAutoDrawable drawable);
}
