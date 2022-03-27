package com.vorono4ka.editor;

import com.vorono4ka.editor.layout.Window;
import com.vorono4ka.editor.renderer.Renderer;
import com.vorono4ka.swf.SupercellSWF;
import com.vorono4ka.swf.exceptions.LoadingFaultException;
import com.vorono4ka.swf.exceptions.UnableToFindObjectException;

public class Editor {
    private final Renderer renderer;
    private final Window window;

    private SupercellSWF swf;

    public Editor() {
        this.renderer = new Renderer();
        this.window = new Window("SC Editor");
    }

    public void openFile(String path) {
        try {
            this.swf = new SupercellSWF();
            this.swf.load(path, path.substring(path.lastIndexOf("\\") + 1));
        } catch (LoadingFaultException | UnableToFindObjectException e) {
            e.printStackTrace();
        }
    }

    public void updateCanvas() {
        this.window.getCanvas().display();
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public Window getWindow() {
        return window;
    }
}
