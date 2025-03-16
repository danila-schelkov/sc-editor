package com.vorono4ka.editor.renderer.listeners;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.vorono4ka.editor.Main;
import com.vorono4ka.editor.renderer.Stage;
import com.vorono4ka.editor.renderer.texture.GLImage;
import com.vorono4ka.editor.renderer.texture.khronos.ExtensionKhronosTextureLoader;
import com.vorono4ka.editor.renderer.texture.khronos.ExtensionSctxTextureLoader;
import com.vorono4ka.editor.renderer.texture.khronos.KhronosTextureLoaders;

import java.awt.*;

public class EventListener implements GLEventListener {
    private ExtensionKhronosTextureLoader extensionKhronosTextureLoader;

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL3 gl = glAutoDrawable.getGL().getGL3();

        GLCanvas canvas = Main.editor.getWindow().getCanvas();

        extensionKhronosTextureLoader = new ExtensionKhronosTextureLoader(gl);
        KhronosTextureLoaders.registerLoader(extensionKhronosTextureLoader);

        GLImage.khronosTextureLoader = KhronosTextureLoaders.getLoader();
        GLImage.sctxTextureLoader = new ExtensionSctxTextureLoader(gl);

        Stage.getInstance().init(gl, 0, 0, canvas.getWidth(), canvas.getHeight());
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {
        glAutoDrawable.getAnimator().stop();

        KhronosTextureLoaders.unregisterLoader(extensionKhronosTextureLoader);
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        Stage.getInstance().update();
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {
        GL3 gl = glAutoDrawable.getGL().getGL3();

        // Note: fix for https://github.com/NASAWorldWind/WorldWindJava/issues/195 and https://forum.jogamp.org/JOGL-broken-with-JRE-gt-8-and-Windows-window-scaling-td4039122.html
        // caused by Windows window scaling, but it is inoperable because it zooms out the window
        // System.setProperty("sun.java2d.uiScale.enabled", "false");
        // System.setProperty("sun.java2d.dpiaware", "false");

        // Note: Got from here: https://github.com/nicolas-van/WorldWindJava/commit/0360d5f1c6117f1b989dc68d2b52085e2cbb7a09#diff-0998fa6b85b93125b092a5a0fc9246247ad761615ddec52c15616096593b5a98R498
        // This is apparently necessary to enable the canvas to resize correctly with JSplitPane.
        float dpiScalingFactor = Toolkit.getDefaultToolkit().getScreenResolution() / 96.0f;
        width = (int) (width * dpiScalingFactor);
        height = (int) (height * dpiScalingFactor);

        Stage.getInstance().unbindRender();
        Stage.getInstance().init(gl, 0, 0, width, height);
    }
}
