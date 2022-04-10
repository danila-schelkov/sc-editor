package com.vorono4ka.editor;

import com.jogamp.opengl.GLAnimatorControl;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import com.vorono4ka.editor.layout.Window;
import com.vorono4ka.editor.layout.components.Table;
import com.vorono4ka.editor.layout.menubar.menus.EditMenu;
import com.vorono4ka.editor.layout.panels.info.EditorInfoPanel;
import com.vorono4ka.editor.layout.panels.info.MovieClipInfoPanel;
import com.vorono4ka.swf.MovieClipFrame;
import com.vorono4ka.swf.SupercellSWF;
import com.vorono4ka.swf.displayObjects.DisplayObject;
import com.vorono4ka.swf.displayObjects.MovieClip;
import com.vorono4ka.swf.displayObjects.original.MovieClipOriginal;
import com.vorono4ka.swf.exceptions.LoadingFaultException;
import com.vorono4ka.swf.exceptions.UnableToFindObjectException;

import java.util.ArrayList;
import java.util.List;

public class Editor {
    private final Window window;

    private SupercellSWF swf;

    private final List<DisplayObject> clonedObjects;
    private final List<Integer> clonedIds;
    private int selectedIndex;

    public Editor() {
        this.window = new Window();

        this.clonedObjects = new ArrayList<>();
        this.clonedIds = new ArrayList<>();
        this.selectedIndex = -1;
    }

    public void openFile(String path) {
        try {
            this.swf = new SupercellSWF();
            this.swf.load(path, path.substring(path.lastIndexOf("\\") + 1));
        } catch (LoadingFaultException | UnableToFindObjectException e) {
            e.printStackTrace();
            return;
        }

        this.window.setTitle(Main.TITLE + " - " + this.swf.getFilename());

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
        this.window.setTitle(Main.TITLE);

        EditorInfoPanel infoBlock = this.window.getInfoPanel();
        infoBlock.setPanel(null);

        this.clonedObjects.clear();
        this.selectedIndex = -1;

        this.swf = null;

        this.updateCanvas();
    }

    public void updateCanvas() {
        GLCanvas canvas = this.window.getCanvas();

        GLAnimatorControl animator = canvas.getAnimator();
        if (animator != null && animator.isAnimating()) return;

        canvas.display();
    }

    public DisplayObject getSelectedObject() {
        if (this.selectedIndex == -1) return null;
        return this.clonedObjects.get(this.selectedIndex);
    }

    public void selectObject(DisplayObject displayObject) {
//        int index = this.clonedIds.indexOf(displayObject.getId());
//        if (index != -1) {
//            this.selectObject(index);
//            return;
//        }

        if (!this.clonedObjects.contains(displayObject)) {
            this.clonedIds.add(displayObject.getId());
            this.clonedObjects.add(displayObject);
        }

        this.selectObject(this.clonedObjects.indexOf(displayObject));
    }

    private void selectObject(int objectIndex) {
        if (objectIndex < 0) return;
        if (objectIndex >= this.clonedObjects.size()) return;

        this.selectedIndex = objectIndex;

        EditorInfoPanel infoBlock = this.window.getInfoPanel();
        infoBlock.setPanel(null);

        DisplayObject displayObject = this.clonedObjects.get(objectIndex);
        if (displayObject.isMovieClip()) {
            MovieClip movieClip = (MovieClip) displayObject;

            MovieClipInfoPanel movieClipInfoPanel = new MovieClipInfoPanel();

            DisplayObject[] timelineChildren = movieClip.getTimelineChildren();
            String[] timelineChildrenNames = movieClip.getTimelineChildrenNames();
            for (int i = 0; i < timelineChildren.length; i++) {
                movieClipInfoPanel.addTimelineChild(i, timelineChildren[i].getId(), timelineChildrenNames[i]);
            }

            MovieClipFrame[] frames = movieClip.getFrames();
            for (int i = 0; i < frames.length; i++) {
                MovieClipFrame frame = frames[i];
                movieClipInfoPanel.addFrame(i, frame.getName());
            }

            infoBlock.setPanel(movieClipInfoPanel);

            movieClipInfoPanel.getFramesTable().select(movieClip.getCurrentFrame());
        }

        EditMenu editMenu = this.window.getMenubar().getEditMenu();
        editMenu.checkPreviousAvailable();
        editMenu.checkNextAvailable();

        this.updateCanvas();
    }

    public void selectPrevious() {
        this.selectObject(this.selectedIndex - 1);
    }

    public void selectNext() {
        this.selectObject(this.selectedIndex + 1);
    }

    public int getClonedObjectCount() {
        return clonedObjects.size();
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public SupercellSWF getSwf() {
        return swf;
    }

    public Window getWindow() {
        return window;
    }

    public FPSAnimator getAnimator() {
        return (FPSAnimator) this.window.getCanvas().getAnimator();
    }
}
