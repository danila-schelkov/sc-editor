package com.vorono4ka.editor.layout.contextmenus;

import com.vorono4ka.editor.Editor;
import com.vorono4ka.editor.layout.components.Table;
import com.vorono4ka.editor.layout.components.TablePopupMenuListener;
import com.vorono4ka.editor.renderer.gl.GLConstants;
import com.vorono4ka.editor.renderer.gl.texture.GLTexture;
import com.vorono4ka.editor.renderer.impl.EditorStage;
import com.vorono4ka.utilities.BufferUtils;
import com.vorono4ka.utilities.ImageUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.nio.IntBuffer;
import java.nio.file.Path;

public class TextureTableContextMenu extends ContextMenu {
    private final Table table;
    private final Editor editor;

    public TextureTableContextMenu(Table table, Editor editor) {
        super(table, null);

        this.table = table;
        this.editor = editor;

        JMenuItem exportButton = this.add("Export", KeyEvent.VK_E);
        exportButton.addActionListener(this::export);

        this.popupMenu.addPopupMenuListener(new TablePopupMenuListener(this.popupMenu, table, this::onRowSelected));
    }

    private void export(ActionEvent actionEvent) {
        int[] selectedRows = table.getSelectedRows();

        EditorStage stage = EditorStage.getInstance();
        // TODO: add setting for the saving path pattern (e.g. "{folder}/{filename}/texture_{index}.png", "{filepath}/{index}.png", "{filepath}/{basename}_{index}.png")
        // TODO: ask folder to save files in
        Path path = Path.of("screenshots").toAbsolutePath().resolve(editor.getFilename());
        path.toFile().mkdirs();

        stage.doInRenderThread(() -> {
            for (int selectedRow : selectedRows) {
                int textureIndex = (int) this.table.getValueAt(selectedRow, 0);

                GLTexture texture = stage.getTextureByIndex(textureIndex);
                texture.bind();
                IntBuffer pixels = texture.getPixels(0);
                texture.unbind();

                boolean isLuminanceAlpha = texture.getFormat() == GLConstants.GL_LUMINANCE_ALPHA || texture.getFormat() == GLConstants.GL_RG;
                BufferedImage bufferedImage = ImageUtils.createBufferedImageFromPixels(texture.getWidth(), texture.getHeight(), BufferUtils.toArray(pixels), isLuminanceAlpha);
                ImageUtils.saveImage(path.resolve("texture_" + textureIndex + ".png"), bufferedImage);
            }
        });
    }

    private void onRowSelected(int rowIndex) {
        setMainComponentsEnabled(rowIndex != -1);
    }
}
