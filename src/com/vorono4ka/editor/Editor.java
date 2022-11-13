package com.vorono4ka.editor;

import com.jogamp.opengl.GLAnimatorControl;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import com.vorono4ka.editor.displayObjects.SpriteSheet;
import com.vorono4ka.editor.layout.EditorWindow;
import com.vorono4ka.editor.layout.UsagesWindow;
import com.vorono4ka.editor.layout.components.Table;
import com.vorono4ka.editor.layout.menubar.menus.EditMenu;
import com.vorono4ka.editor.layout.panels.info.EditorInfoPanel;
import com.vorono4ka.editor.layout.panels.info.MovieClipInfoPanel;
import com.vorono4ka.editor.layout.panels.info.ShapeInfoPanel;
import com.vorono4ka.editor.renderer.Stage;
import com.vorono4ka.swf.MovieClipFrame;
import com.vorono4ka.swf.SupercellSWF;
import com.vorono4ka.swf.displayObjects.DisplayObject;
import com.vorono4ka.swf.displayObjects.MovieClip;
import com.vorono4ka.swf.displayObjects.Shape;
import com.vorono4ka.swf.displayObjects.ShapeDrawBitmapCommand;
import com.vorono4ka.swf.originalObjects.MovieClipOriginal;
import com.vorono4ka.swf.originalObjects.SWFTexture;
import com.vorono4ka.swf.exceptions.LoadingFaultException;
import com.vorono4ka.swf.exceptions.UnableToFindObjectException;
import com.vorono4ka.utilities.ArrayUtilities;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class Editor {
    private final EditorWindow window;

    private final List<DisplayObject> clonedObjects;
    private final List<Integer> selectedIndices;

    private final List<SpriteSheet> spriteSheets;

    private SupercellSWF swf;

    private int selectedIndex;

    private boolean shouldDisplayPolygons;

    public Editor() {
        this.window = new EditorWindow();

        this.clonedObjects = new ArrayList<>();
        this.selectedIndices = new ArrayList<>();
        this.selectedIndex = -1;

        this.spriteSheets = new ArrayList<>();
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

        Table texturesTable = this.window.getTexturesTable();
        for (int i = 0; i < this.swf.getTexturesCount(); i++) {
            SWFTexture texture = this.swf.getTexture(i);
            texturesTable.addRow(i, texture.getWidth(), texture.getHeight(), texture.getPixelFormat());

            SpriteSheet spriteSheet = new SpriteSheet(texture);
            spriteSheet.setBitmaps(swf.getDrawBitmapsOfTexture(i));
            this.spriteSheets.add(spriteSheet);
        }
    }

    public void saveFile(String path) {
        if (this.swf == null) return;

        this.swf.save(path);
    }

    public void closeFile() {
        this.window.getTexturesTable().clear();
        this.window.getObjectsTable().clear();
        this.window.setTitle(Main.TITLE);

        EditorInfoPanel infoBlock = this.window.getInfoPanel();
        infoBlock.setPanel(null);

        this.clonedObjects.clear();
        this.selectedIndices.clear();
        this.selectedIndex = -1;

        this.spriteSheets.clear();

        EditMenu editMenu = this.window.getMenubar().getEditMenu();
        editMenu.checkPreviousAvailable();
        editMenu.checkNextAvailable();

        if (this.swf != null) {
            IntBuffer textureIds = IntBuffer.allocate(this.swf.getTexturesCount());
            for (int i = 0; i < this.swf.getTexturesCount(); i++) {
                SWFTexture texture = this.swf.getTexture(i);
                textureIds.put(texture.getTextureId());
            }

            Stage stage = Stage.getInstance();
            stage.doInRenderThread(() -> stage.getGl().glDeleteTextures(0, textureIds));
            stage.clearBatches();
            stage.removeAllChildren();

            this.swf = null;
        }

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
        return this.clonedObjects.get(this.selectedIndices.get(this.selectedIndex));
    }

    public void selectObject(DisplayObject displayObject) {
        if (!this.clonedObjects.contains(displayObject)) {
            this.clonedObjects.add(displayObject);
        }

        int index = this.clonedObjects.indexOf(displayObject);

        if (this.selectedIndex != -1 && this.selectedIndex + 1 < this.selectedIndices.size()) {
            this.selectedIndices.subList(this.selectedIndex + 1, this.selectedIndices.size()).clear();
        }

        this.selectedIndices.add(++this.selectedIndex, index);
        this.selectObject(this.selectedIndex);
    }

    private void selectObject(int objectIndex) {
        if (objectIndex < 0) return;
        if (objectIndex >= this.selectedIndices.size()) return;

        this.selectedIndex = objectIndex;

        EditorInfoPanel infoBlock = this.window.getInfoPanel();
        infoBlock.setPanel(null);

        DisplayObject displayObject = this.clonedObjects.get(this.selectedIndices.get(objectIndex));
        if (displayObject.isMovieClip()) {
            MovieClip movieClip = (MovieClip) displayObject;

            MovieClipInfoPanel movieClipInfoPanel = new MovieClipInfoPanel();

            DisplayObject[] timelineChildren = movieClip.getTimelineChildren();
            String[] timelineChildrenNames = movieClip.getTimelineChildrenNames();
            for (int i = 0; i < timelineChildren.length; i++) {
                movieClipInfoPanel.addTimelineChild(String.format("%d (%s)", i, timelineChildren[i].getClass().getSimpleName()), timelineChildren[i].getId(), timelineChildrenNames[i]);
            }

            MovieClipFrame[] frames = movieClip.getFrames();
            for (int i = 0; i < frames.length; i++) {
                movieClipInfoPanel.addFrame(i, movieClip.getFrameLabel(i));
            }

            movieClipInfoPanel.setTextInfo(
                "Export name: " + movieClip.getExportName(),
                "FPS: " + movieClip.getFps(),
                String.format("Duration: %.2fs", movieClip.getDuration())
            );

            infoBlock.setPanel(movieClipInfoPanel);

            movieClipInfoPanel.getFramesTable().select(movieClip.getCurrentFrame());
        } else if (displayObject.isShape()) {
            ShapeInfoPanel shapeInfoPanel = new ShapeInfoPanel();

            Shape shape = (Shape) displayObject;

            for (int i = 0; i < shape.getCommandCount(); i++) {
                ShapeDrawBitmapCommand command = shape.getCommand(i);
                shapeInfoPanel.addCommandInfo(i, command.getTexture().getIndex(), command.getTag());
            }

            infoBlock.setPanel(shapeInfoPanel);
        }

        EditMenu editMenu = this.window.getMenubar().getEditMenu();
        editMenu.checkPreviousAvailable();
        editMenu.checkNextAvailable();

        Stage stage = Stage.getInstance();
        stage.clearBatches();
        stage.removeAllChildren();
        stage.addChild(displayObject);
        this.updateCanvas();
    }

    public void selectPrevious() {
        this.selectObject(--this.selectedIndex);
        this.selectObjectInTable(this.getSelectedObject());
    }

    public void selectNext() {
        this.selectObject(++this.selectedIndex);
        this.selectObjectInTable(this.getSelectedObject());
    }

    public void selectObjectInTable(DisplayObject displayObject) {
        Table objectsTable = this.window.getObjectsTable();
        int row = objectsTable.indexOf(displayObject.getId(), 0);
        if (row == -1) {
            this.window.getDisplayObjectPanel().resetFilter();

            row = objectsTable.indexOf(displayObject.getId(), 0);
        }

        objectsTable.select(row);
    }

    public void findUsages(int displayObjectId, String name) {
        String title = "Usages";
        if (name != null && name.length() > 0) {
            title += " - " + name;
        }

        UsagesWindow usagesWindow = new UsagesWindow();
        usagesWindow.initialize(title);

        Table objectsTable = usagesWindow.getObjectsTable();

        int[] ids = this.swf.getMovieClipsIds();
        try {
            for (int i = 0; i < this.swf.getMovieClipsCount(); i++) {
                int movieClipId = ids[i];

                MovieClipOriginal movieClipOriginal = this.swf.getOriginalMovieClip(movieClipId, null);
                if (ArrayUtilities.contains(movieClipOriginal.getChildrenIds(), (short) displayObjectId)) {
                    objectsTable.addRow(movieClipId, movieClipOriginal.getExportName(), "MovieClip");
                }
            }
        } catch (UnableToFindObjectException e) {
            throw new RuntimeException(e);
        }

        usagesWindow.show();
    }

    public int getClonedObjectCount() {
        return this.selectedIndices.size();
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public SupercellSWF getSwf() {
        return swf;
    }

    public EditorWindow getWindow() {
        return window;
    }

    public FPSAnimator getAnimator() {
        return (FPSAnimator) this.window.getCanvas().getAnimator();
    }

    public SpriteSheet getSpriteSheet(int index) {
        return this.spriteSheets.get(index);
    }

    public boolean shouldDisplayPolygons() {
        return this.shouldDisplayPolygons;
    }

    public void setShouldDisplayPolygons(boolean shouldDisplayPolygons) {
        this.shouldDisplayPolygons = shouldDisplayPolygons;
    }
}
