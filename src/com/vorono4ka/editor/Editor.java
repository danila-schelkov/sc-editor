package com.vorono4ka.editor;

import com.vorono4ka.editor.graphics.Renderer;
import com.vorono4ka.editor.layout.Window;
import com.vorono4ka.editor.world.Scene;
import com.vorono4ka.editor.world.objects.Square;
import com.vorono4ka.editor.world.objects.Triangle;
import com.vorono4ka.swf.SupercellSWF;
import com.vorono4ka.swf.exceptions.LoadingFaultException;
import com.vorono4ka.swf.exceptions.NegativeTagLengthException;
import com.vorono4ka.swf.exceptions.TooManyObjectsException;

import java.util.Random;

public class Editor {
    private final Renderer renderer;
    private final Window window;
    private final Scene scene;

    private SupercellSWF swf;

    public Editor() {
        this.renderer = new Renderer();
        this.window = new Window("SC Editor");
        this.scene = new Scene();
    }

    public void openFile(String path) {
        try {
            this.swf = new SupercellSWF();
            this.swf.load(path);
        } catch (TooManyObjectsException | NegativeTagLengthException | LoadingFaultException e) {
            e.printStackTrace();
            return;
        }

        int rand = new Random().nextInt(2);
        Main.editor.getScene().add(rand == 1 ? new Triangle() : new Square());
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
