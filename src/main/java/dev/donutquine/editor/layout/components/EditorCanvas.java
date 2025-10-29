package dev.donutquine.editor.layout.components;

import com.jogamp.opengl.GLCapabilitiesImmutable;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.awt.GLCanvas;
import dev.donutquine.editor.layout.components.listeners.ViewportMouseListener;
import dev.donutquine.editor.renderer.impl.listeners.CameraMouseListener;
import dev.donutquine.editor.renderer.impl.listeners.EventListener;
import dev.donutquine.editor.renderer.impl.listeners.MouseWheelListener;

public class EditorCanvas extends GLCanvas {
    public EditorCanvas(GLCapabilitiesImmutable glCapabilitiesImmutable) throws GLException {
        super(glCapabilitiesImmutable);

        MouseWheelListener mouseWheelListener = new MouseWheelListener();
        CameraMouseListener cameraMouseListener = new CameraMouseListener();
        EventListener eventListener = new EventListener();
        ViewportMouseListener viewportMouseListener = new ViewportMouseListener();

        this.addMouseWheelListener(mouseWheelListener);
        this.addMouseMotionListener(cameraMouseListener);
        this.addMouseListener(cameraMouseListener);
        this.addGLEventListener(eventListener);
        this.addMouseListener(viewportMouseListener);
        this.addMouseMotionListener(viewportMouseListener);
    }
}
