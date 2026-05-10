package dev.donutquine.editor.layout.contextmenus;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.nio.IntBuffer;
import java.nio.file.Path;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import dev.donutquine.editor.layout.TextureLayoutController;
import dev.donutquine.editor.layout.components.tables.JTablePopupMenuListener;
import dev.donutquine.editor.renderer.gl.GLConstants;
import dev.donutquine.editor.renderer.gl.texture.GLTexture;
import dev.donutquine.editor.renderer.impl.EditorStage;
import dev.donutquine.editor.renderer.texture.RenderableTexture;
import dev.donutquine.utilities.BufferUtils;
import dev.donutquine.utilities.ImageUtils;

public class TextureTableContextMenu extends ContextMenu {
    private final JTable table;
    private final TextureLayoutController<?> swfLayoutController;

    public TextureTableContextMenu(JTable table, TextureLayoutController<?> layoutController) {
        super(table, null);

        this.table = table;
        this.swfLayoutController = layoutController;

        JMenuItem exportButton = this.add("Export", KeyEvent.VK_E);
        exportButton.addActionListener(this::export);

        this.popupMenu.addPopupMenuListener(new JTablePopupMenuListener(this.popupMenu, table, this::onRowSelected));
    }

    private void export(ActionEvent actionEvent) {
        int[] selectedRows = table.getSelectedRows();

        // TODO: add setting for the saving path pattern (e.g. "{folder}/{filename}/texture_{index}.png", "{filepath}/{index}.png", "{filepath}/{basename}_{index}.png")
        // TODO: ask folder to save files in
        Path path = Path.of("screenshots").toAbsolutePath().resolve(swfLayoutController.getAssetFile().getName());
        path.toFile().mkdirs();

        EditorStage stage = EditorStage.getInstance();
        stage.doInRenderThread(() -> {
            for (int selectedRow : selectedRows) {
                int textureIndex = (int) this.table.getValueAt(selectedRow, 0);

                RenderableTexture texture = this.swfLayoutController.getAssetFile().getSpriteSheet(textureIndex).getTexture();
                texture.bind();
                IntBuffer pixels = texture.getPixels(0);
                texture.unbind();

                boolean isLuminanceAlpha = false;
                if (texture instanceof GLTexture glTexture) {
                    isLuminanceAlpha = glTexture.getFormat() == GLConstants.GL_LUMINANCE_ALPHA || glTexture.getFormat() == GLConstants.GL_RG;
                }

                BufferedImage bufferedImage = ImageUtils.createBufferedImageFromPixels(texture.getWidth(), texture.getHeight(), BufferUtils.toArray(pixels), isLuminanceAlpha);
                ImageUtils.saveImage(path.resolve("texture_" + textureIndex + ".png"), bufferedImage);
            }
        });
    }

    private void onRowSelected(int rowIndex) {
        setMainComponentsEnabled(rowIndex != -1);
    }
}
