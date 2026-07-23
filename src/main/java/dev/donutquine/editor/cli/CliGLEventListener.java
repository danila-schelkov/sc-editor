package dev.donutquine.editor.cli;

import com.jogamp.opengl.GLAutoDrawable;
import dev.donutquine.editor.renderer.impl.listeners.EventListener;
import dev.donutquine.editor.renderer.impl.texture.khronos.ExtensionKhronosTextureLoader;
import dev.donutquine.editor.renderer.impl.texture.khronos.KhronosTextureLoaders;

/**
 * OpenGL event listener for the headless CLI mode. Mirrors {@link EventListener}
 * but performs no Swing animator cleanup in {@link #dispose(GLAutoDrawable)} and no reshaping in {@link #reshape(GLAutoDrawable, int, int, int, int)}.
 */
public class CliGLEventListener extends EventListener {
    private ExtensionKhronosTextureLoader extensionKhronosTextureLoader;

    @Override
    public void dispose(GLAutoDrawable canvas) {
        KhronosTextureLoaders.unregisterLoader(extensionKhronosTextureLoader);
    }

    @Override
    public void reshape(GLAutoDrawable canvas, int x, int y, int width, int height) {
        // No-op for the headless mode.
    }
}
