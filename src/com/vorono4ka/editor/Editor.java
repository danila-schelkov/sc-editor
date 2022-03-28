package com.vorono4ka.editor;

import com.vorono4ka.editor.layout.Window;
import com.vorono4ka.editor.layout.components.Table;
import com.vorono4ka.editor.layout.components.blocks.EditorInfoPanel;
import com.vorono4ka.editor.renderer.Renderer;
import com.vorono4ka.swf.SupercellSWF;
import com.vorono4ka.swf.displayObjects.DisplayObject;
import com.vorono4ka.swf.displayObjects.original.MovieClipOriginal;
import com.vorono4ka.swf.exceptions.LoadingFaultException;
import com.vorono4ka.swf.exceptions.UnableToFindObjectException;

public class Editor {
    private final Renderer renderer;
    private final Window window;

    private SupercellSWF swf;
    private DisplayObject selectedObject;

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

    public void closeFile() {
        this.window.getObjectsTable().clear();

        EditorInfoPanel infoBlock = this.window.getInfoBlock();
        infoBlock.setPanel(null);

        this.updateCanvas();

        this.selectedObject = null;
        this.swf = null;
    }

    public void updateCanvas() {
        this.window.getCanvas().display();
    }

    public DisplayObject getSelectedObject() {
        return selectedObject;
    }

    public void setSelectedObject(DisplayObject selectedObject) {
        this.selectedObject = selectedObject;
    }

    public SupercellSWF getSwf() {
        return swf;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public Window getWindow() {
        return window;
    }
}
