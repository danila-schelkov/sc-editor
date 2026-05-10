package dev.donutquine.editor.layout;

import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import dev.donutquine.editor.assets.TextureAssetFile;
import dev.donutquine.editor.layout.panels.TexturesPanel;
import dev.donutquine.editor.layout.windows.EditorWindow;
import dev.donutquine.editor.renderer.impl.EditorStage;

public class TextureAssetLayoutController implements TextureLayoutController<TextureAssetFile<?>> {
    public final EditorWindow window;
    public final TextureAssetFile<?> assetFile;

    // TODO: maybe get rid of this textures panel, but somehow leave export texture functionality and width and height info.
    //  I think it is a cool idea to display info in status bar line neovim does for line and column for buffers
    private final TexturesPanel texturesPanel;

    public TextureAssetLayoutController(EditorWindow window, TextureAssetFile<?> assetFile) {
        this.window = window;
        this.assetFile = assetFile;

        this.texturesPanel = new TexturesPanel(this);
    }

    @Override
    public void start() {
        JTabbedPane tabbedPane = this.window.getTabbedPane();
        tabbedPane.setVisible(true);
        tabbedPane.add("Textures", this.texturesPanel);

        // FIXME: wtf? I am forcing JSplitPane to revalidate its ui with this hack 
        //  because revalidate and repaint just don't work as intended.
        JSplitPane parent = (JSplitPane) tabbedPane.getParent();
        int dividerLocation = parent.getDividerLocation();
        parent.setLeftComponent(tabbedPane);
        if (dividerLocation != 0) {
            parent.setDividerLocation(dividerLocation);
        }
        // Container parent = tabbedPane.getParent();
        // parent.revalidate();
        // parent.repaint();

        EditorStage stage = EditorStage.getInstance();
        stage.reset();
        stage.addChild(this.assetFile.getSpriteSheet(0));
    }

    @Override
    public void finish() {
        JTabbedPane tabbedPane = this.window.getTabbedPane();
        tabbedPane.setVisible(false);
        tabbedPane.remove(this.texturesPanel);

        EditorStage stage = EditorStage.getInstance();
        stage.reset();
    }

    @Override
    public void openSpriteSheet(int textureIndex) {
        // Do nothing as we already added spritesheet to the stage
    }

    @Override
    public TextureAssetFile<?> getAssetFile() {
        return this.assetFile;
    }
}
