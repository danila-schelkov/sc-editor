package dev.donutquine.editor.layout;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dev.donutquine.editor.assets.SupercellSWFAssetFile;
import dev.donutquine.editor.layout.panels.DisplayObjectListPanel;
import dev.donutquine.editor.layout.panels.TexturesPanel;
import dev.donutquine.editor.layout.panels.TimelinePanel;
import dev.donutquine.editor.layout.panels.info.DisplayObjectInfoPanel;
import dev.donutquine.editor.layout.panels.info.MovieClipPropertyPanel;
import dev.donutquine.editor.layout.panels.info.ShapeInfoPanel;
import dev.donutquine.editor.layout.windows.EditorWindow;
import dev.donutquine.editor.layout.windows.UsagesWindow;
import dev.donutquine.editor.navigation.NavigationEvent;
import dev.donutquine.editor.navigation.NavigationHistory;
import dev.donutquine.editor.renderer.BlendMode;
import dev.donutquine.editor.renderer.impl.EditorStage;
import dev.donutquine.renderer.impl.swf.objects.DisplayObject;
import dev.donutquine.renderer.impl.swf.objects.MovieClip;
import dev.donutquine.renderer.impl.swf.objects.Shape;
import dev.donutquine.swf.SupercellSWF;
import dev.donutquine.swf.exceptions.UnableToFindObjectException;
import dev.donutquine.swf.movieclips.MovieClipOriginal;

public class SupercellSWFLayoutController implements TextureLayoutController<SupercellSWFAssetFile>, SearchableLayoutController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SupercellSWFLayoutController.class);

    public final EditorWindow window;
    public final SupercellSWFAssetFile assetFile;

    private final DisplayObjectListPanel objectListPanel;
    private final DisplayObjectInfoPanel currentObjectInfoPanel;
    private final TexturesPanel texturesPanel;
    private final TimelinePanel timelinePanel;

    private boolean timelineShouldBeVisible;

    public SupercellSWFLayoutController(EditorWindow window, SupercellSWFAssetFile assetFile) {
        this.window = window;
        this.assetFile = assetFile;

        this.objectListPanel = new DisplayObjectListPanel(this, collectObjectTableRows(this.assetFile.asset).toArray(Object[][]::new));
        this.currentObjectInfoPanel = new DisplayObjectInfoPanel();
        this.texturesPanel = new TexturesPanel(this);
        this.timelinePanel = new TimelinePanel();
        this.timelinePanel.setVisible(this.timelineShouldBeVisible);
    }

    public void findUsages(int displayObjectId, String name) {
        String title = "Usages";
        if (name != null && !name.isEmpty()) {
            title += " - " + name;
        }

        List<Object[]> usagesRows = new ArrayList<>();

        try {
            for (int movieClipId : this.assetFile.asset.getMovieClipIds()) {
                MovieClipOriginal movieClipOriginal = this.assetFile.asset.getOriginalMovieClip(movieClipId & 0xFFFF, null);
                if (movieClipOriginal.getChildren().stream().anyMatch(movieClipChild -> movieClipChild.id() == displayObjectId)) {
                    usagesRows.add(new Object[] {movieClipId, movieClipOriginal.getExportName(), "MovieClip"});
                }
            }
        } catch (UnableToFindObjectException e) {
            throw new RuntimeException(e);
        }

        UsagesWindow usagesWindow = new UsagesWindow(title, usagesRows, this);
        usagesWindow.show();
    }

    /**
    * Adds a display object to the Stage and to the object history and updates the info panel.
    *
    * @param id display object id to be selected
    * @param name display object name
    */
    public void selectObject(int id, String name) {
        NavigationHistory<Integer> navigationHistory = this.assetFile.getNavigationHistory();

        // We don't want to add the same object to history multiple times 
        //  if it is the last object in the navigation history
        if (navigationHistory.hasPrevious() && navigationHistory.getCurrent() == id) return;

        try {
            // MovieClip modifiers cannot be displayed yet
            if (assetFile.getOrCreate(id, name).isMovieClipModifier())
                return;
        } catch (UnableToFindObjectException exception) {
            LOGGER.error(exception.getMessage(), exception);
            return;
        }

        navigationHistory.add(id);
    }

    public DisplayObject getSelectedObject() {
        NavigationHistory<Integer> navigationHistory = this.assetFile.getNavigationHistory();
        if (navigationHistory.hasCurrent()) {
            return this.assetFile.getById(navigationHistory.getCurrent());
        }

        return null;
    }

    @Override
    public void start() {
        JTabbedPane tabbedPane = this.window.getTabbedPane();
        tabbedPane.setVisible(true);
        tabbedPane.add("Objects", this.objectListPanel);
        tabbedPane.add("Info", this.currentObjectInfoPanel);
        tabbedPane.add("Textures", this.texturesPanel);

        // FIXME: wtf? I am forcing JSplitPane to revalidate its ui with this hack 
        //  because revalidate and repaint just don't work as intended.
        JSplitPane parent = (JSplitPane) tabbedPane.getParent();
        int dividerLocation = parent.getDividerLocation();
        parent.setLeftComponent(tabbedPane);
        if (dividerLocation != 0) {
            parent.setDividerLocation(parent.getLastDividerLocation());
        }
        // Container parent = tabbedPane.getParent();
        // parent.revalidate();
        // parent.repaint();

        this.window.getCanvasSplitPane().setBottomComponent(this.timelinePanel);

        NavigationHistory<?> navigationHistory = this.assetFile.getNavigationHistory();
        navigationHistory.registerNavigationListener(this::onNavigationEvent);
    }

    @Override
    public void finish() {
        JTabbedPane tabbedPane = this.window.getTabbedPane();
        tabbedPane.setVisible(false);
        tabbedPane.remove(this.objectListPanel);
        tabbedPane.remove(this.currentObjectInfoPanel);
        tabbedPane.remove(this.texturesPanel);

        this.window.getCanvasSplitPane().setBottomComponent(null);

        NavigationHistory<?> navigationHistory = this.assetFile.getNavigationHistory();
        navigationHistory.unregisterNavigationListener(this::onNavigationEvent);
    }

    @Override
    public SupercellSWFAssetFile getAssetFile() {
        return this.assetFile;
    }

    @Override
    public void openSpriteSheet(int textureIndex) {
        EditorStage stage = EditorStage.getInstance();
        stage.reset();
        stage.addChild(this.assetFile.getSpriteSheet(textureIndex));

        this.timelinePanel.setVisible(false);
    }

    @Override
    public void focusOnSearchField() {
        this.window.getTabbedPane().setSelectedComponent(this.objectListPanel);
        this.objectListPanel.setFocusOnTextField();
    }

    public void setTimelineVisible(boolean visible) {
        this.timelineShouldBeVisible = visible;

        this.timelinePanel.setVisible(visible);

        this.window.getCanvasSplitPane().setDividerLocation(0.7f);
    }

    private void onNavigationEvent(NavigationEvent event) {
        this.selectObject(this.getSelectedObject());
    }

    /**
    * Adds a display object to the Stage and to the object history and updates the info panel.
    *
    * @param displayObject display object to be selected
    */
    private void selectObject(DisplayObject displayObject) {
        this.objectListPanel.selectObjectById(displayObject.getId());

        if (displayObject.isMovieClip()) {
            MovieClip movieClip = (MovieClip) displayObject;
            this.timelinePanel.setVisible(this.timelineShouldBeVisible);
            this.timelinePanel.setMovieClip(movieClip);

            this.window.setTargetFps(((MovieClip) displayObject).getFps());
        } else {
            this.timelinePanel.setVisible(false);
        }

        this.currentObjectInfoPanel.setPanel(createPropertiesPanel(displayObject));

        EditorStage stage = EditorStage.getInstance();
        stage.reset();
        stage.addChild(displayObject);
    }

    private JPanel createPropertiesPanel(DisplayObject displayObject) {
        if (displayObject.isMovieClip()) {
            return new MovieClipPropertyPanel(this, (MovieClip) displayObject);
        } else if (displayObject.isShape()) {
            return new ShapeInfoPanel(this, (Shape) displayObject);
        }

        return null;
    }

    private static List<Object[]> collectObjectTableRows(SupercellSWF swf) {
        List<Object[]> rowDataList = new ArrayList<>();

        for (int movieClipId : swf.getMovieClipIds()) {
            try {
                MovieClipOriginal movieClipOriginal = swf.getOriginalMovieClip(movieClipId & 0xFFFF, null);
                rowDataList.add(new Object[] {movieClipId, movieClipOriginal.getExportName(), "MovieClip"});
            } catch (UnableToFindObjectException e) {
                LOGGER.error(e.getMessage(), e);
            }

        }

        for (int shapesId : swf.getShapeIds()) {
            rowDataList.add(new Object[] {shapesId, null, "Shape"});
        }

        for (int textFieldId : swf.getTextFieldIds()) {
            rowDataList.add(new Object[] {textFieldId, null, "TextField"});
        }

        return rowDataList;
    }

    public BlendMode getBlendMode(int childIndex) {
        MovieClip movieClip = getMovieClip();
        DisplayObject child = movieClip.getTimelineChildren()[childIndex];
        return child.getBlendMode();
    }

    public void setBlendMode(int childIndex, BlendMode blendMode) {
        MovieClip movieClip = getMovieClip();
        DisplayObject child = movieClip.getTimelineChildren()[childIndex];
        child.setBlendMode(blendMode);
    }

    public boolean[] changeVisibility(int[] childIndices, Function<DisplayObject, Boolean> visibilityFunction) {
        boolean[] results = new boolean[childIndices.length];

        MovieClip movieClip = getMovieClip();
        for (int i = 0; i < results.length; i++) {
            int childIndex = childIndices[i];
            DisplayObject child = movieClip.getTimelineChildren()[childIndex];
            child.setVisibleRecursive(visibilityFunction.apply(child));

            results[i] = child.isVisible();
        }

        return results;
    }

    private MovieClip getMovieClip() {
        DisplayObject selectedObject = this.getSelectedObject();
        assert selectedObject.isMovieClip();

        return (MovieClip) selectedObject;
    }
}
