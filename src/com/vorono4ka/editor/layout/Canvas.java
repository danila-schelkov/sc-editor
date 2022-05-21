package com.vorono4ka.editor.layout;

import com.jogamp.opengl.GLCapabilitiesImmutable;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.awt.GLCanvas;
import com.vorono4ka.editor.renderer.listeners.EventListener;
import com.vorono4ka.editor.renderer.listeners.MouseListener;
import com.vorono4ka.editor.renderer.listeners.MouseWheelListener;

public class Canvas extends GLCanvas {
    public Canvas(GLCapabilitiesImmutable glCapabilitiesImmutable) throws GLException {
        super(glCapabilitiesImmutable);

        MouseWheelListener mouseWheelListener = new MouseWheelListener();
        MouseListener mouseListener = new MouseListener();
        EventListener eventListener = new EventListener();

        this.addMouseWheelListener(mouseWheelListener);
        this.addMouseMotionListener(mouseListener);
        this.addMouseListener(mouseListener);
        this.addGLEventListener(eventListener);
    }
}
