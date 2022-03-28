package com.vorono4ka.editor;

import com.vorono4ka.editor.layout.Table;
import com.vorono4ka.editor.layout.Window;
import com.vorono4ka.editor.renderer.Renderer;
import com.vorono4ka.swf.SupercellSWF;
import com.vorono4ka.swf.displayObjects.original.MovieClipOriginal;
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
            return;
        }

        Table objectsTable = this.window.getObjectsTable();

        int[] exportsIds = this.swf.getExportsIds();
        String[] exportsNames = this.swf.getExportsNames();
        for (int i = 0; i < this.swf.getExportsCount(); i++) {
            int exportId = exportsIds[i];
            String exportsName = exportsNames[i];

            try {
                MovieClipOriginal movieClipOriginal = this.swf.getOriginalMovieClip(exportId, exportsName);
                objectsTable.addRow(exportId, movieClipOriginal.getExportName(), "MovieClip");
            } catch (UnableToFindObjectException e) {
                e.printStackTrace();
            }
        }
    }

    public SupercellSWF getSwf() {
        return swf;
    }

    public void closeFile() {
        this.window.clearTable();
        this.updateCanvas();
        this.swf = null;
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
