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
import com.vorono4ka.editor.renderer.impl.Stage;
import com.vorono4ka.editor.renderer.impl.texture.GLImage;
import com.vorono4ka.editor.renderer.texture.Texture;
import com.vorono4ka.exporter.ImageExporter;
import com.vorono4ka.sctx.FlatSctxTextureLoader;
import com.vorono4ka.sctx.SctxTexture;
import com.vorono4ka.swf.SupercellSWF;
import com.vorono4ka.renderer.impl.swf.objects.DisplayObject;
import com.vorono4ka.renderer.impl.swf.objects.MovieClip;
import com.vorono4ka.swf.exceptions.LoadingFaultException;
import com.vorono4ka.swf.exceptions.TextureFileNotFound;
import com.vorono4ka.swf.exceptions.UnableToFindObjectException;
import com.vorono4ka.swf.exceptions.UnsupportedCustomPropertyException;
import com.vorono4ka.swf.movieclips.MovieClipOriginal;
import com.vorono4ka.swf.textures.SWFTexture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Editor {
    private static final Logger LOGGER = LoggerFactory.getLogger(Editor.class);

    private final EditorWindow window = new EditorWindow();

    private final List<UsagesWindow> usagesWindows = new ArrayList<>();
    private final List<SpriteSheet> spriteSheets = new ArrayList<>();

    // For selection history
    private final List<DisplayObject> clonedObjects = new ArrayList<>();
    private final List<Integer> selectedIndices = new ArrayList<>();

    private final ImageExporter imageExporter = new ImageExporter(Stage.getInstance());

    private int selectedIndex = -1;

    private SupercellSWF swf;
    private SctxTexture sctxTexture;

    // Editor debug stuff
    private boolean shouldDisplayPolygons;

    public void openFile(String path) {
        if (path.endsWith(".sctx") && !loadSctx(path)) return;
        if (!loadSc(path)) return;

        FileMenu fileMenu = this.window.getMenubar().getFileMenu();
        fileMenu.checkCanSave();
    }

    private boolean loadSctx(String path) {
        try {
            String filename = path.substring(path.lastIndexOf("\\") + 1);
            this.window.setTitle(Main.TITLE + " - " + filename);

            sctxTexture = loadSctxTexture(Path.of(path));

            List<GLImage> images = uploadSwfTexturesToOpenGl();

            SwingUtilities.invokeLater(() -> this.updateTextureTable(images));
            return true;
        } catch (TextureFileNotFound e) {
            this.window.showErrorDialog(e.getMessage());
            return false;
        }
    }

    private boolean loadSc(String path) {
        try {
            String filename = path.substring(path.lastIndexOf("\\") + 1);
            this.window.setTitle(Main.TITLE + " - " + filename);

            this.swf = new SupercellSWF();
            this.swf.load(path, filename, false);

            List<GLImage> images = uploadSwfTexturesToOpenGl();

            SwingUtilities.invokeLater(() -> this.updateTextureTable(images));
        } catch (LoadingFaultException | UnableToFindObjectException |
                 UnsupportedCustomPropertyException exception) {
            LOGGER.error("An error occurred while loading the file: {}", path, exception);
            return false;
        } catch (TextureFileNotFound e) {
            this.window.showErrorDialog(e.getMessage());
            return false;
        }

        SwingUtilities.invokeLater(this::updateObjectTable);
        return true;
    }

    public void saveFile(String path) {
        if (this.swf == null) return;

        this.swf.save(path, null);
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

        if (this.swf == null) return;

        Stage stage = Stage.getInstance();

        int textureCount = this.swf.getTextureCount();
        if (textureCount > 0) {
            int[] textureIds = new int[textureCount];
            for (int i = 0; i < textureCount; i++) {
                Texture image = stage.getTextureByIndex(this.swf.getTexture(i).getIndex());
                if (image == null) continue;

                textureIds[i] = image.getId();
            }

            stage.doInRenderThread(() -> stage.getGl().glDeleteTextures(textureIds.length, textureIds, 0));
        }

        stage.clearBatches();
        stage.removeAllChildren();

        this.swf = null;
    }

    public DisplayObject getSelectedObject() {
        if (this.selectedIndex == -1) return null;
        return this.clonedObjects.get(this.selectedIndices.get(this.selectedIndex));
    }

    /**
     * Adds a display object to the Stage and to the object history and updates the info panel.
     *
     * @param displayObject selected display object
     */
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

        int[] ids = this.swf.getMovieClipIds();
        try {
            for (int i = 0; i < this.swf.getMovieClipCount(); i++) {
                int movieClipId = ids[i];

                MovieClipOriginal movieClipOriginal = this.swf.getOriginalMovieClip(movieClipId & 0xFFFF, null);
                if (movieClipOriginal.getChildren().stream().anyMatch(movieClipChild -> movieClipChild.id() == displayObjectId)) {
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

    public ImageExporter getImageExporter() {
        return imageExporter;
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

        try (TaskProgressTracker taskTracker = statusBar.createTaskTracker("Loading objects table...", 0, rowDataList.size())) {
            Table objectsTable = this.window.getObjectsTable();
            for (int i = 0; i < rowDataList.size(); i++) {
                Object[] objects = rowDataList.get(i);
                objectsTable.addRow(objects);

                taskTracker.setValue(i);
            }
        }
    }

    private List<GLImage> uploadSctxTextureToOpenGl() throws TextureFileNotFound {
        List<GLImage> images = new ArrayList<>();

        Stage stage = Stage.getInstance();

        for (int i = 0; i < this.swf.getTextureCount(); i++) {
//            SWFTexture texture = .getTexture(i);
//            GLImage image = stage.createGLImage(texture, this.swf.getPath().getParent());
//            images.add(image);
        }

        return images;
    }

    private List<GLImage> uploadSwfTexturesToOpenGl() throws TextureFileNotFound {
        List<GLImage> images = new ArrayList<>();

        Stage stage = Stage.getInstance();
        for (int i = 0; i < this.swf.getTextureCount(); i++) {
            SWFTexture texture = this.swf.getTexture(i);
            GLImage image = stage.createGLImage(texture, this.swf.getPath().getParent());
            images.add(image);
        }

        return images;
    }

    private void updateTextureTable(List<GLImage> images) {
        Table texturesTable = this.window.getTexturesTable();
        StatusBar statusBar = this.window.getStatusBar();

        try (TaskProgressTracker taskTracker = statusBar.createTaskTracker("Loading textures table...", 0, images.size())) {
            for (int i = 0; i < images.size(); i++) {
                GLImage image = images.get(i);
                texturesTable.addRow(i, image.getWidth(), image.getHeight(), image.getPixelFormat());

                SpriteSheet spriteSheet = new SpriteSheet(image, swf.getDrawBitmapsOfTexture(i));
                this.spriteSheets.add(spriteSheet);

                taskTracker.setValue(i);
            }
        }
    }

    private List<Object[]> collectObjectTableRows() {
        List<Object[]> rowDataList = new ArrayList<>();

        StatusBar statusBar = this.window.getStatusBar();

        int[] movieClipsIds = this.swf.getMovieClipIds();
        try (TaskProgressTracker taskTracker = statusBar.createTaskTracker("Collecting MovieClip info...", 0, movieClipsIds.length)) {
            for (int i = 0; i < movieClipsIds.length; i++) {
                int movieClipId = movieClipsIds[i];

                try {
                    MovieClipOriginal movieClipOriginal = this.swf.getOriginalMovieClip(movieClipId & 0xFFFF, null);
                    rowDataList.add(new Object[]{movieClipId, movieClipOriginal.getExportName(), "MovieClip"});
                } catch (UnableToFindObjectException e) {
                    LOGGER.error(e.getMessage(), e);
                }

                taskTracker.setValue(i);
            }
        }

        int[] shapesIds = this.swf.getShapeIds();
        try (TaskProgressTracker taskTracker = statusBar.createTaskTracker("Collecting Shape info...", 0, shapesIds.length)) {
            for (int i = 0; i < shapesIds.length; i++) {
                int shapesId = shapesIds[i];
                rowDataList.add(new Object[]{shapesId, null, "Shape"});
                taskTracker.setValue(i);
            }
        }

        int[] textFieldsIds = this.swf.getTextFieldIds();
        try (TaskProgressTracker taskTracker = statusBar.createTaskTracker("Collecting TextField info...", 0, textFieldsIds.length)) {
            for (int i = 0; i < textFieldsIds.length; i++) {
                int textFieldId = textFieldsIds[i];
                rowDataList.add(new Object[]{textFieldId, null, "TextField"});
                taskTracker.setValue(i);
            }
        }

        return rowDataList;
    }

    private static SctxTexture loadSctxTexture(Path path) {
        try (FileInputStream inputStream = new FileInputStream(path.toFile())) {
            return FlatSctxTextureLoader.getInstance().load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
