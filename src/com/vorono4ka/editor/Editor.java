package com.vorono4ka.editor;

import com.vorono4ka.editor.graphics.Renderer;
import com.vorono4ka.editor.layout.Window;
import com.vorono4ka.editor.world.Scene;

public class Editor {
    private final Renderer renderer;
    private final Window window;
    private final Scene scene;

    public Editor() {
        this.renderer = new Renderer();
        this.window = new Window("SC Editor");
        this.scene = new Scene();
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public Window getWindow() {
        return window;
    }

    public Scene getScene() {
        return scene;
    }
}
