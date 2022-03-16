package com.vorono4ka.editor;

import com.vorono4ka.compression.Decompressor;
import com.vorono4ka.compression.exceptions.UnknownFileMagicException;
import com.vorono4ka.compression.exceptions.UnknownFileVersionException;
import com.vorono4ka.editor.graphics.Renderer;
import com.vorono4ka.editor.layout.Window;
import com.vorono4ka.editor.world.Scene;
import com.vorono4ka.swf.SupercellSWF;

import java.io.*;

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

    public void openFile(File file) {
        byte[] data;

        try {
            data = new FileInputStream(file).readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        byte[] decompressedData;

        try {
            decompressedData = Decompressor.decompress(data);
        } catch (UnknownFileMagicException | UnknownFileVersionException | IOException e) {
            e.printStackTrace();
            return;
        }

        this.swf = new SupercellSWF();
        this.swf.load(decompressedData);
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
