package com.vorono4ka.editor;

import com.vorono4ka.editor.displayObjects.SpriteSheet;
import com.vorono4ka.editor.layout.components.Table;
import com.vorono4ka.editor.layout.menubar.menus.EditMenu;
import com.vorono4ka.editor.layout.menubar.menus.FileMenu;
import com.vorono4ka.editor.layout.panels.StatusBar;
import com.vorono4ka.editor.layout.panels.info.EditorInfoPanel;
import com.vorono4ka.editor.layout.panels.status.TaskProgressTracker;
import com.vorono4ka.editor.layout.windows.EditorWindow;
import com.vorono4ka.editor.layout.windows.UsagesWindow;
import com.vorono4ka.editor.renderer.Stage;
import com.vorono4ka.swf.SupercellSWF;
import com.vorono4ka.swf.displayObjects.DisplayObject;
import com.vorono4ka.swf.displayObjects.MovieClip;
import com.vorono4ka.swf.exceptions.LoadingFaultException;
import com.vorono4ka.swf.exceptions.TextureFileNotFound;
import com.vorono4ka.swf.exceptions.UnableToFindObjectException;
import com.vorono4ka.swf.exceptions.UnsupportedCustomPropertyException;
import com.vorono4ka.swf.originalObjects.MovieClipOriginal;
import com.vorono4ka.swf.originalObjects.SWFTexture;
import com.vorono4ka.utilities.ArrayUtilities;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Editor {
    private final EditorWindow window = new EditorWindow();

    private final List<UsagesWindow> usagesWindows = new ArrayList<>();
    private final List<SpriteSheet> spriteSheets = new ArrayList<>();

    // For selection history
    private final List<DisplayObject> clonedObjects = new ArrayList<>();
    private final List<Integer> selectedIndices = new ArrayList<>();

    private int selectedIndex = -1;

    private SupercellSWF swf;

    // Editor debug stuff
    private boolean shouldDisplayPolygons;

    public void openFile(String path) {
        try {
            this.swf = new SupercellSWF();
            this.swf.load(path, path.substring(path.lastIndexOf("\\") + 1));
        } catch (LoadingFaultException | UnableToFindObjectException |
                 UnsupportedCustomPropertyException e) {
            e.printStackTrace();
            return;
        } catch (TextureFileNotFound e) {
            this.window.showErrorDialog(e.getMessage());
            return;
        }

        this.window.setTitle(Main.TITLE + " - " + this.swf.getFilename());

        SwingUtilities.invokeLater(this::updateObjectTable);

        Table texturesTable = this.window.getTexturesTable();
        for (int i = 0; i < this.swf.getTexturesCount(); i++) {
            SWFTexture texture = this.swf.getTexture(i);
            texturesTable.addRow(i, texture.getWidth(), texture.getHeight(), texture.getPixelFormat());

            SpriteSheet spriteSheet = new SpriteSheet(texture, swf.getDrawBitmapsOfTexture(i));
            this.spriteSheets.add(spriteSheet);
        }

        FileMenu fileMenu = this.window.getMenubar().getFileMenu();
        fileMenu.checkCanSave();
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

        for (UsagesWindow usagesWindow : this.usagesWindows) {
            usagesWindow.close();
        }

        this.usagesWindows.clear();

        this.spriteSheets.clear();

        FileMenu fileMenu = this.window.getMenubar().getFileMenu();
        fileMenu.checkCanSave();

        EditMenu editMenu = this.window.getMenubar().getEditMenu();
        editMenu.checkPreviousAvailable();
        editMenu.checkNextAvailable();

        if (this.swf != null) {
            int[] textureIds = new int[this.swf.getTexturesCount()];
            for (int i = 0; i < this.swf.getTexturesCount(); i++) {
                textureIds[i] = this.swf.getTexture(i).getTextureId();
            }

            Stage stage = Stage.getInstance();
            stage.doInRenderThread(() -> stage.getGl().glDeleteTextures(textureIds.length, textureIds, 0));
            stage.clearBatches();
            stage.removeAllChildren();

            this.swf = null;
        }
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

        DisplayObject displayObject = this.clonedObjects.get(this.selectedIndices.get(objectIndex));

        if (displayObject.isMovieClip()) {
            this.window.setTargetFps(((MovieClip) displayObject).getFps());
        }

        this.window.updateInfoPanel(displayObject);

        EditMenu editMenu = this.window.getMenubar().getEditMenu();
        editMenu.checkPreviousAvailable();
        editMenu.checkNextAvailable();

        Stage stage = Stage.getInstance();
        stage.clearBatches();
        stage.removeAllChildren();
        stage.addChild(displayObject);
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
        if (name != null && !name.isEmpty()) {
            title += " - " + name;
        }

        UsagesWindow usagesWindow = new UsagesWindow();
        usagesWindow.initialize(title);
        this.usagesWindows.add(usagesWindow);

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

    public SpriteSheet getSpriteSheet(int index) {
        return this.spriteSheets.get(index);
    }

    public boolean shouldDisplayPolygons() {
        return this.shouldDisplayPolygons;
    }

    public void setShouldDisplayPolygons(boolean shouldDisplayPolygons) {
        this.shouldDisplayPolygons = shouldDisplayPolygons;
    }

    private void updateObjectTable() {
        List<Object[]> rowDataList = collectObjectTableRows();

        StatusBar statusBar = this.window.getStatusBar();

        try (TaskProgressTracker taskProgressTracker = statusBar.createTaskTracker("Loading objects table...", 0, rowDataList.size())) {
            Table objectsTable = this.window.getObjectsTable();
            for (int i = 0; i < rowDataList.size(); i++) {
                Object[] objects = rowDataList.get(i);
                objectsTable.addRow(objects);

                taskProgressTracker.setValue(i);
            }
        }
    }

    private List<Object[]> collectObjectTableRows() {
        List<Object[]> rowDataList = new ArrayList<>();

        StatusBar statusBar = this.window.getStatusBar();

        int[] movieClipsIds = this.swf.getMovieClipsIds();
        try (TaskProgressTracker taskProgressTracker = statusBar.createTaskTracker("Collecting MovieClip info...", 0, movieClipsIds.length)) {
            for (int i = 0; i < movieClipsIds.length; i++) {
                int movieClipId = movieClipsIds[i];

                try {
                    MovieClipOriginal movieClipOriginal = this.swf.getOriginalMovieClip(movieClipId, null);
                    rowDataList.add(new Object[]{movieClipId, movieClipOriginal.getExportName(), "MovieClip"});
                } catch (UnableToFindObjectException e) {
                    e.printStackTrace();
                }

                taskProgressTracker.setValue(i);
            }
        }

        int[] shapesIds = this.swf.getShapesIds();
        try (TaskProgressTracker taskProgressTracker = statusBar.createTaskTracker("Collecting Shape info...", 0, shapesIds.length)) {
            for (int i = 0; i < shapesIds.length; i++) {
                int shapesId = shapesIds[i];
                rowDataList.add(new Object[]{shapesId, null, "Shape"});
                taskProgressTracker.setValue(i);
            }
        }

        int[] textFieldsIds = this.swf.getTextFieldsIds();
        try (TaskProgressTracker taskProgressTracker = statusBar.createTaskTracker("Collecting TextField info...", 0, textFieldsIds.length)) {
            for (int i = 0; i < textFieldsIds.length; i++) {
                int textFieldId = textFieldsIds[i];
                rowDataList.add(new Object[]{textFieldId, null, "TextField"});
                taskProgressTracker.setValue(i);
            }
        }

        return rowDataList;
    }
}
