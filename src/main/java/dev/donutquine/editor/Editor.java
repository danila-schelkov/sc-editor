package dev.donutquine.editor;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.donutquine.editor.displayObjects.SpriteSheet;
import dev.donutquine.editor.layout.components.Table;
import dev.donutquine.editor.layout.dialogs.ExceptionDialog;
import dev.donutquine.editor.layout.menubar.menus.EditMenu;
import dev.donutquine.editor.layout.menubar.menus.FileMenu;
import dev.donutquine.editor.layout.panels.StatusBar;
import dev.donutquine.editor.layout.panels.info.EditorInfoPanel;
import dev.donutquine.editor.layout.panels.status.TaskProgressTracker;
import dev.donutquine.editor.layout.windows.EditorWindow;
import dev.donutquine.editor.layout.windows.UsagesWindow;
import dev.donutquine.editor.renderer.gl.texture.GLTexture;
import dev.donutquine.editor.renderer.impl.EditorStage;
import dev.donutquine.editor.renderer.texture.Texture;
import dev.donutquine.editor.settings.EditorSettings;
import dev.donutquine.renderer.impl.swf.objects.DisplayObject;
import dev.donutquine.renderer.impl.swf.objects.MovieClip;
import dev.donutquine.sctx.FlatSctxTextureLoader;
import dev.donutquine.sctx.SctxTexture;
import dev.donutquine.swf.SupercellSWF;
import dev.donutquine.swf.exceptions.LoadingFaultException;
import dev.donutquine.swf.exceptions.TextureFileNotFound;
import dev.donutquine.swf.exceptions.UnableToFindObjectException;
import dev.donutquine.swf.exceptions.UnsupportedCustomPropertyException;
import dev.donutquine.swf.movieclips.MovieClipOriginal;
import dev.donutquine.swf.shapes.ShapeDrawBitmapCommand;
import dev.donutquine.swf.shapes.ShapeOriginal;
import dev.donutquine.swf.textures.SWFTexture;

public class Editor {
    private static final Logger LOGGER = LoggerFactory.getLogger(Editor.class);

    public static final String REPO_URL = "https://github.com/danila-schelkov/sc-editor";

    private final EditorWindow window = new EditorWindow(this);
    private final EditorSettings settings;

    private final List<UsagesWindow> usagesWindows = new ArrayList<>();
    private final List<SpriteSheet> spriteSheets = new ArrayList<>();

    // For selection history
    private final List<DisplayObject> clonedObjects = new ArrayList<>();
    private final List<Integer> selectedIndices = new ArrayList<>();

    private int selectedIndex = -1;

    private SupercellSWF swf;
    private SctxTexture sctxTexture;

    private String filename;

    public Editor(EditorSettings settings) {
        this.settings = settings;
    }

    public void openFile(Path path) {
        this.filename = path.getFileName().toString();
        this.window.setTitle(EditorWindow.TITLE + " - " + filename);

        if (path.toString().endsWith(".sc") || path.toString().endsWith(".sc2")) {
            if (!loadSc(path, filename)) {
                return;
            }
        } else if (path.toString().endsWith(".sctx")) {
            if (!loadSctx(path)) {
                return;
            }
        }

        FileMenu fileMenu = this.window.getMenubar().getFileMenu();
        fileMenu.checkCanSave();
    }

    private boolean loadSctx(Path path) {
        try {
            sctxTexture = loadSctxTexture(path);

            List<GLTexture> images = uploadSctxTextureToOpenGl(sctxTexture);

            SwingUtilities.invokeLater(() -> this.updateTextureTable(images));
            return true;
        } catch (TextureFileNotFound e) {
            this.window.showErrorDialog(e.getMessage());
            return false;
        } catch (Throwable e) {
            ExceptionDialog.showExceptionDialog(Thread.currentThread(), e);
            throw new RuntimeException(e);
        }
    }

    private boolean loadSc(Path path, String filename) {
        try {
            this.swf = new SupercellSWF();
            this.swf.load(path.toString(), filename, false);

            List<GLTexture> images = uploadSwfTexturesToOpenGl();

            SwingUtilities.invokeLater(() -> this.updateTextureTable(images));
        } catch (LoadingFaultException | UnableToFindObjectException |
                 UnsupportedCustomPropertyException exception) {
            LOGGER.error("An error occurred while loading the file: {}", path, exception);
            ExceptionDialog.showExceptionDialog(Thread.currentThread(), exception);
            return false;
        } catch (TextureFileNotFound e) {
            this.window.showErrorDialog(e.getMessage());
            return false;
        } catch (Throwable e) {
            ExceptionDialog.showExceptionDialog(Thread.currentThread(), e);
            throw new RuntimeException(e);
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
        this.window.setTitle(EditorWindow.TITLE);
        this.filename = null;

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

        EditorStage stage = EditorStage.getInstance();

        int textureCount = stage.getTextureCount();
        if (textureCount > 0) {
            int[] textureIds = new int[textureCount];
            for (int i = 0; i < textureCount; i++) {
                Texture texture = stage.getTextureByIndex(i);
                if (texture == null) continue;

                textureIds[i] = texture.getId();
            }

            stage.doInRenderThread(() -> stage.getGlContext().glDeleteTextures(textureIds.length, textureIds, 0));
        }

        stage.reset();

        this.swf = null;
        this.sctxTexture = null;
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
        window.getTimelinePanel().setSelectedObject(displayObject);

        if (displayObject.isMovieClip()) {
            this.window.setTargetFps(((MovieClip) displayObject).getFps());
        }

        this.window.updateInfoPanel(displayObject);

        EditMenu editMenu = this.window.getMenubar().getEditMenu();
        editMenu.checkPreviousAvailable();
        editMenu.checkNextAvailable();

        EditorStage stage = EditorStage.getInstance();
        stage.reset();
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

        UsagesWindow usagesWindow = new UsagesWindow(this);
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

    public String getFilename() {
        return filename;
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

    public void setShouldDisplayPolygons(boolean shouldDisplayPolygons) {
        this.settings.setShouldDisplayPolygons(shouldDisplayPolygons);

        for (SpriteSheet spriteSheet : spriteSheets) {
            spriteSheet.setShouldDisplayPolygons(shouldDisplayPolygons);
        }
    }

    public float getPixelSize() {
        return settings.getPixelSize();
    }

    public void setPixelSize(float pixelSize) {
        settings.setPixelSize(pixelSize);
    }

    public void setWireframeEnabled(boolean wireframeEnabled) {
        EditorStage.getInstance().setWireframeEnabled(wireframeEnabled);
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

    private List<GLTexture> uploadSctxTextureToOpenGl(SctxTexture texture) throws TextureFileNotFound {
        return List.of(EditorStage.getInstance().createGLTexture(texture, 0));
    }

    private List<GLTexture> uploadSwfTexturesToOpenGl() throws TextureFileNotFound {
        List<GLTexture> images = new ArrayList<>();

        EditorStage stage = EditorStage.getInstance();
        for (int i = 0; i < this.swf.getTextureCount(); i++) {
            SWFTexture texture = this.swf.getTexture(i);
            GLTexture image = stage.createGLTexture(texture, this.swf.getPath().getParent());
            images.add(image);
        }

        return images;
    }

    private void updateTextureTable(List<GLTexture> images) {
        Table texturesTable = this.window.getTexturesTable();
        StatusBar statusBar = this.window.getStatusBar();

        try (TaskProgressTracker taskTracker = statusBar.createTaskTracker("Loading textures table...", 0, images.size())) {
            for (int i = 0; i < images.size(); i++) {
                GLTexture texture = images.get(i);
                texturesTable.addRow(i, texture.getWidth(), texture.getHeight(), texture.getFormat());

                SpriteSheet spriteSheet = new SpriteSheet(texture, getDrawBitmapsOfTexture(i));
                spriteSheet.setTriangleFunction(swf.getContainerVersion() >= 5);
                this.spriteSheets.add(spriteSheet);

                taskTracker.setValue(i);
            }
        }
    }

    private List<ShapeDrawBitmapCommand> getDrawBitmapsOfTexture(int textureIndex) {
        if (swf == null) {
            return Collections.emptyList();
        }

        List<ShapeDrawBitmapCommand> bitmapCommands = new ArrayList<>();

        for (ShapeOriginal shape : swf.getShapes()) {
            for (ShapeDrawBitmapCommand command : shape.getCommands()) {
                if (command.getTextureIndex() == textureIndex) {
                    bitmapCommands.add(command);
                }
            }
        }

        return bitmapCommands;
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
