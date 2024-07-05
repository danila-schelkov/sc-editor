package com.vorono4ka.editor.layout.components;

import com.vorono4ka.editor.Main;
import com.vorono4ka.editor.renderer.Stage;
import com.vorono4ka.exporter.FfmpegVideoExporter;
import com.vorono4ka.exporter.ImageExporter;
import com.vorono4ka.exporter.VideoExporter;
import com.vorono4ka.math.Rect;
import com.vorono4ka.streams.ByteStream;
import com.vorono4ka.swf.SupercellSWF;
import com.vorono4ka.swf.displayObjects.DisplayObject;
import com.vorono4ka.swf.displayObjects.MovieClip;
import com.vorono4ka.swf.exceptions.UnableToFindObjectException;
import com.vorono4ka.swf.originalObjects.DisplayObjectOriginal;
import com.vorono4ka.swf.originalObjects.MovieClipOriginal;
import com.vorono4ka.swf.originalObjects.TextFieldOriginal;
import com.vorono4ka.utilities.ByteArrayFlavor;
import com.vorono4ka.utilities.ImageData;
import com.vorono4ka.utilities.ImageUtils;
import com.vorono4ka.utilities.MovieClipHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.nio.file.Path;

public class DisplayObjectContextMenu extends ContextMenu {
    public static final Clipboard SYSTEM_CLIPBOARD = Toolkit.getDefaultToolkit().getSystemClipboard();
    private final Table table;
    private final JMenuItem exportAsVideoButton;
    private final JMenu exportAsMenu;

    public DisplayObjectContextMenu(Table table) {
        super(table, null);

        this.table = table;

        JMenuItem copyExportNameButton = this.add("Copy Export Name", KeyEvent.VK_E);
        copyExportNameButton.addActionListener(this::copyExportName);

        JMenu copyAsMenu = this.addMenu("Copy as...", KeyEvent.VK_C);

        JMenuItem copyAsBytesButton = this.add(copyAsMenu, "Copy as bytes", KeyEvent.VK_B);
        copyAsBytesButton.addActionListener(this::copyAsBytes);

        exportAsMenu = this.addMenu("Export as...", KeyEvent.VK_E);

        JMenuItem exportAsImageButton = this.add(exportAsMenu, "Export as image", KeyEvent.VK_I);
        exportAsImageButton.addActionListener(this::exportAsImage);

        exportAsVideoButton = this.add(exportAsMenu, "Export as video", KeyEvent.VK_V);
        exportAsVideoButton.addActionListener(this::exportAsVideo);

        this.addSeparator();

        JMenuItem findUsagesButton = this.add("Find Usages", KeyEvent.VK_U);
        findUsagesButton.addActionListener(this::findUsages);

        this.addSeparator();

        this.add("Properties");

        this.popupMenu.addPopupMenuListener(new TablePopupMenuListener(this.popupMenu, table, this::onRowSelected));
    }

    private void onRowSelected(int rowIndex) {
        SupercellSWF swf = Main.editor.getSwf();
        if (swf == null) return;

        int displayObjectId = getDisplayObjectId(rowIndex);
        try {
            DisplayObjectOriginal displayObject = swf.getOriginalDisplayObject(displayObjectId, null);
            exportAsMenu.setEnabled(!(displayObject instanceof TextFieldOriginal));
            if (displayObject instanceof MovieClipOriginal movieClipOriginal) {
                boolean hasMoreThanOneFrame = movieClipOriginal.getFrames().length > 1;
                exportAsVideoButton.setEnabled(hasMoreThanOneFrame);
            } else {
                exportAsVideoButton.setEnabled(false);
            }
        } catch (UnableToFindObjectException e) {
            throw new RuntimeException(e);
        }
    }

    private String getDisplayObjectName(int selectedRow) {
        return (String) this.table.getValueAt(selectedRow, 1);
    }

    private int getDisplayObjectId(int selectedRow) {
        return (int) this.table.getValueAt(selectedRow, 0);
    }

    private void findUsages(ActionEvent event) {
        int selectedRow = this.table.getSelectedRow();
        if (selectedRow == -1) return;

        int displayObjectId = getDisplayObjectId(selectedRow);
        String displayObjectName = getDisplayObjectName(selectedRow);

        Main.editor.findUsages(displayObjectId, displayObjectName);
    }

    private void copyExportName(ActionEvent event) {
        int selectedRow = this.table.getSelectedRow();
        if (selectedRow == -1) return;

        String displayObjectName = getDisplayObjectName(selectedRow);

        SYSTEM_CLIPBOARD.setContents(
            new StringSelection(displayObjectName),
            null
        );
    }

    private void copyAsBytes(ActionEvent event) {
        int selectedRow = this.table.getSelectedRow();
        if (selectedRow == -1) return;

        SupercellSWF swf = Main.editor.getSwf();
        if (swf == null) return;

        int displayObjectId = getDisplayObjectId(selectedRow);
        try {
            DisplayObjectOriginal originalDisplayObject = swf.getOriginalDisplayObject(displayObjectId, null);
            ByteStream stream = new ByteStream();
            originalDisplayObject.save(stream);

            SYSTEM_CLIPBOARD.setContents(
                new ByteArrayFlavor(stream.getData()),
                null
            );
        } catch (UnableToFindObjectException e) {
            throw new RuntimeException(e);
            // TODO: add error window
        }
    }

    private void exportAsImage(ActionEvent actionEvent) {
        int selectedRow = this.table.getSelectedRow();
        if (selectedRow == -1) return;

        int displayObjectId = getDisplayObjectId(selectedRow);

        Stage stage = Stage.getInstance();
        SupercellSWF swf = Main.editor.getSwf();
        ImageExporter imageExporter = Main.editor.getImageExporter();

        try {
            DisplayObjectOriginal displayObjectOriginal = swf.getOriginalDisplayObject(displayObjectId, null);
            DisplayObject displayObject = displayObjectOriginal.clone(swf, null);

            Rect bounds = stage.calculateBoundsForAllFrames(displayObject);
            stage.getCamera().zoomToFit(bounds);

            stage.doInRenderThread(() -> {
                stage.clearBatches();
                stage.removeAllChildren();
                stage.addChild(displayObject);
                stage.updatePMVMatrix();

                stage.render(0);

                BufferedImage screenshot = imageExporter.takeScreenshot(displayObject);
                imageExporter.saveScreenshot(displayObject, screenshot);
            });
        } catch (UnableToFindObjectException e) {
            throw new RuntimeException(e);
        }
    }

    private void exportAsVideo(ActionEvent actionEvent) {
        int selectedRow = this.table.getSelectedRow();
        if (selectedRow == -1) return;

        int displayObjectId = getDisplayObjectId(selectedRow);

        Stage stage = Stage.getInstance();
        SupercellSWF swf = Main.editor.getSwf();

        try {
            DisplayObjectOriginal displayObjectOriginal = swf.getOriginalDisplayObject(displayObjectId, null);
            DisplayObject displayObject = displayObjectOriginal.clone(swf, null);
            if (!displayObject.isMovieClip()) return;

            MovieClip movieClip = ((MovieClip) displayObject);

            Rect bounds = stage.calculateBoundsForAllFrames(displayObject);
            stage.getCamera().zoomToFit(bounds);

            stage.doInRenderThread(() -> {
                stage.clearBatches();
                stage.removeAllChildren();
                stage.addChild(displayObject);
                stage.updatePMVMatrix();

                String filename = movieClip.getExportName();
                if (filename == null)
                    filename = String.valueOf(movieClip.getId());

                Path workingDirectory = Path.of("screenshots");
                // TODO: ask where to save the video file
                try (VideoExporter videoExporter = new FfmpegVideoExporter(workingDirectory, filename, "webm", "libvpx-vp9", movieClip.getFps())) {
                    MovieClipHelper.doForAllFrames(movieClip, (frameIndex) -> {
                        // Note: it's necessary to set frame index using this method,
                        // because absolute time frame setting may skip frames.
                        movieClip.gotoAndStopFrameIndex(frameIndex);
                        stage.render(0);

                        ImageData imageData = Main.editor.getImageExporter().getCroppedFramebufferData(bounds, false);
                        BufferedImage image = ImageUtils.createBufferedImageFromPixels(imageData.width(), imageData.height(), imageData.pixels());
                        videoExporter.encodeFrame(image, frameIndex);
                    });
                }
            });
        } catch (UnableToFindObjectException e) {
            throw new RuntimeException(e);
        }
    }
}
