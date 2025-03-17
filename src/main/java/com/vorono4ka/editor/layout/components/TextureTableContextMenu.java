package com.vorono4ka.editor.layout.components;

import com.jogamp.opengl.GL3;
import com.vorono4ka.editor.Main;
import com.vorono4ka.editor.layout.panels.StatusBar;
import com.vorono4ka.editor.layout.panels.status.TaskProgressTracker;
import com.vorono4ka.editor.renderer.impl.Stage;
import com.vorono4ka.editor.renderer.texture.GLTexture;
import com.vorono4ka.swf.SupercellSWF;
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

    public TextureTableContextMenu(Table table) {
        super(table, null);

        this.table = table;

        JMenuItem exportButton = this.add("Export", KeyEvent.VK_E);
        exportButton.addActionListener(this::export);

        this.popupMenu.addPopupMenuListener(new TablePopupMenuListener(this.popupMenu, table, this::onRowSelected));
    }

    private void export(ActionEvent actionEvent) {
        int[] selectedRows = table.getSelectedRows();

        Stage stage = Stage.getInstance();
        SupercellSWF swf = Main.editor.getSwf();
        StatusBar statusBar = Main.editor.getWindow().getStatusBar();

        // TODO: add setting for the saving path pattern (e.g. "{folder}/{filename}/texture_{index}.png", "{filepath}/{index}.png", "{filepath}/{basename}_{index}.png")
        // TODO: ask folder to save files in
        Path path = Path.of("screenshots").toAbsolutePath().resolve(swf.getFilename());
        path.toFile().mkdirs();

        stage.doInRenderThread(() -> {
            try (TaskProgressTracker taskTracker = statusBar.createTaskTracker("Exporting textures...", 0, selectedRows.length)) {
                int progress = 0;
                for (int selectedRow : selectedRows) {
                    int textureIndex = (int) this.table.getValueAt(selectedRow, 0);

                    GLTexture texture = stage.getTextureByIndex(swf.getTexture(textureIndex).getIndex());
                    texture.bind();
                    IntBuffer pixels = texture.getPixels(0);
                    texture.unbind();

                    boolean isLuminanceAlpha = texture.getFormat() == GL3.GL_LUMINANCE_ALPHA || texture.getFormat() == GL3.GL_RG;
                    BufferedImage bufferedImage = ImageUtils.createBufferedImageFromPixels(texture.getWidth(), texture.getHeight(), BufferUtils.toArray(pixels), isLuminanceAlpha);
                    ImageUtils.saveImage(path.resolve("texture_" + textureIndex + ".png"), bufferedImage);

                    // TODO: find a way to notify the main app thread about the changes, unfreeze it or transfer render thread to another thread
                    taskTracker.setValue(++progress);
                }
            }
        });
    }

    private void onRowSelected(int rowIndex) {
        setMainComponentsEnabled(rowIndex != -1);
    }
}
