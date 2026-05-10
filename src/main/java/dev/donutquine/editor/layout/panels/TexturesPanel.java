package dev.donutquine.editor.layout.panels;

import java.awt.BorderLayout;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import dev.donutquine.editor.displayObjects.SpriteSheet;
import dev.donutquine.editor.layout.TextureLayoutController;
import dev.donutquine.editor.layout.components.tables.Table;
import dev.donutquine.editor.layout.components.listeners.TextureListMouseListener;
import dev.donutquine.editor.layout.contextmenus.TextureTableContextMenu;
import dev.donutquine.editor.renderer.gl.texture.GLTexture;

public class TexturesPanel extends JPanel {
    private static final Object[] COLUMN_NAMES = {"Index", "Width", "Height", "Type"};
    private static final Class<?>[] COLUMN_CLASSES = {Integer.class, Integer.class, Integer.class, Integer.class};

    private final JTable table;

    public TexturesPanel(TextureLayoutController<?> layoutController) {
        List<SpriteSheet> spriteSheets = layoutController.getAssetFile().getSpriteSheets();
        Object[][] textureRows = new Object[spriteSheets.size()][];

        for (int i = 0; i < spriteSheets.size(); i++) {
            GLTexture texture = (GLTexture) spriteSheets.get(i).getTexture();
            textureRows[i] = new Object[] {i, texture.getWidth(), texture.getHeight(), texture.getFormat()};
        }

        this.table = new Table(textureRows, COLUMN_NAMES, COLUMN_CLASSES);

        new TextureTableContextMenu(this.table, layoutController);
        this.table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        this.table.addMouseListener(new TextureListMouseListener(this.table, layoutController));

        setLayout(new BorderLayout());
        this.add(new JScrollPane(this.table), BorderLayout.CENTER);
    }
}
