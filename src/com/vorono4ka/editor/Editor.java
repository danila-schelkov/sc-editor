package com.vorono4ka.editor;

import com.vorono4ka.editor.layout.Window;
import com.vorono4ka.editor.layout.components.Table;
import com.vorono4ka.editor.layout.components.blocks.EditorInfoPanel;
import com.vorono4ka.swf.SupercellSWF;
import com.vorono4ka.swf.displayObjects.DisplayObject;
import com.vorono4ka.swf.displayObjects.original.MovieClipOriginal;
import com.vorono4ka.swf.exceptions.LoadingFaultException;
import com.vorono4ka.swf.exceptions.UnableToFindObjectException;

public class Editor {
    private final Window window;

    private SupercellSWF swf;
    private DisplayObject selectedObject;

    public Editor() {
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

        int[] movieClipsIds = this.swf.getMovieClipsIds();
        for (int movieClipId : movieClipsIds) {
            try {
                MovieClipOriginal movieClipOriginal = this.swf.getOriginalMovieClip(movieClipId, null);
                objectsTable.addRow(movieClipId, movieClipOriginal.getExportName(), "MovieClip");
            } catch (UnableToFindObjectException e) {
                e.printStackTrace();
            }
        }

        int[] shapesIds = this.swf.getShapesIds();
        for (int shapesId : shapesIds) {
            objectsTable.addRow(shapesId, null, "Shape");
        }

        int[] textFieldsIds = this.swf.getTextFieldsIds();
        for (int textFieldId : textFieldsIds) {
            objectsTable.addRow(textFieldId, null, "TextField");
        }
    }

    public void closeFile() {
        this.window.getObjectsTable().clear();

        EditorInfoPanel infoBlock = this.window.getInfoBlock();
        infoBlock.setPanel(null);

        this.selectedObject = null;
        this.swf = null;

        this.updateCanvas();
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

    public Window getWindow() {
        return window;
    }
}
