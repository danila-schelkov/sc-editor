package com.vorono4ka.editor.layout.contextmenus;

import com.vorono4ka.editor.Main;
import com.vorono4ka.editor.layout.components.Table;
import com.vorono4ka.editor.layout.components.TablePopupMenuListener;
import com.vorono4ka.editor.renderer.impl.Stage;
import com.vorono4ka.exporter.FfmpegVideoExporter;
import com.vorono4ka.exporter.ImageExporter;
import com.vorono4ka.exporter.VideoExporter;
import com.vorono4ka.math.ReadonlyRect;
import com.vorono4ka.math.Rect;
import com.vorono4ka.streams.ByteStream;
import com.vorono4ka.swf.DisplayObjectOriginal;
import com.vorono4ka.swf.SupercellSWF;
import com.vorono4ka.renderer.impl.swf.objects.DisplayObject;
import com.vorono4ka.renderer.impl.swf.objects.DisplayObjectFactory;
import com.vorono4ka.renderer.impl.swf.objects.MovieClip;
import com.vorono4ka.swf.exceptions.UnableToFindObjectException;
import com.vorono4ka.swf.movieclips.MovieClipOriginal;
import com.vorono4ka.swf.movieclips.MovieClipState;
import com.vorono4ka.swf.textfields.TextFieldOriginal;
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
import java.util.Objects;

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

        // Note: aka quick export, which uses user settings (but not impl yet)
        JMenuItem exportButton = this.add("Export", KeyEvent.VK_X);
        exportButton.addActionListener(this::export);

        exportAsMenu = this.addMenu("Export as...", KeyEvent.VK_A);

        JMenuItem exportAsImageButton = this.add(exportAsMenu, "Export as image", KeyEvent.VK_I);
        exportAsImageButton.addActionListener(this::exportAsImageCallback);

        exportAsVideoButton = this.add(exportAsMenu, "Export as video", KeyEvent.VK_V);
        exportAsVideoButton.addActionListener(this::exportAsVideoCallback);

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

        if (rowIndex != -1) {
            setMainComponentsEnabled(true);
        } else {
            setMainComponentsEnabled(false);
            return;
        }

        int[] selectedRows = table.getSelectedRows();
        if (selectedRows.length == 1) {
            int displayObjectId = getDisplayObjectId(rowIndex);
            try {
                DisplayObjectOriginal displayObject = swf.getOriginalDisplayObject(displayObjectId, null);
                exportAsMenu.setEnabled(!(displayObject instanceof TextFieldOriginal));
                if (displayObject instanceof MovieClipOriginal movieClipOriginal) {
                    boolean hasMoreThanOneFrame = movieClipOriginal.getFrames().size() > 1;
                    exportAsVideoButton.setEnabled(hasMoreThanOneFrame);
                } else {
                    exportAsVideoButton.setEnabled(false);
                }
            } catch (UnableToFindObjectException e) {
                throw new RuntimeException(e);
            }
        } else {
            exportAsMenu.setEnabled(false);
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

    private void export(ActionEvent actionEvent) {
        Stage stage = Stage.getInstance();
        ReadonlyRect viewport = stage.getCamera().getViewport();

        for (int row : this.table.getSelectedRows()) {
            int displayObjectId = getDisplayObjectId(row);

            DisplayObject renderableObject = getRenderableObject(displayObjectId);
            if (renderableObject == null || renderableObject.isTextField()) continue;

            if (canExportAsVideo(renderableObject)) {
                exportAsVideo((MovieClip) renderableObject);
            } else {
                exportAsImage(renderableObject);
            }
        }

        stage.doInRenderThread(()->{
            stage.unbindRender();
            stage.init(0, 0, (int) viewport.getWidth(), (int) viewport.getHeight());

            stage.getCamera().reset();
            stage.updatePMVMatrix();
        });
    }

    private static boolean canExportAsVideo(DisplayObject renderableObject) {
        return renderableObject.isMovieClip() && ((MovieClip) renderableObject).getFrameCountRecursive() > 1;
    }

    private void exportAsImageCallback(ActionEvent actionEvent) {
        if (this.table.getSelectedRowCount() == 0) return;

        int displayObjectId = getDisplayObjectId(this.table.getSelectedRow());

        Stage stage = Stage.getInstance();
        ReadonlyRect viewport = stage.getCamera().getViewport();

        exportAsImage(getRenderableObject(displayObjectId));

        stage.doInRenderThread(()->{
            stage.unbindRender();
            stage.init(0, 0, (int) viewport.getWidth(), (int) viewport.getHeight());

            stage.getCamera().reset();
            stage.updatePMVMatrix();
        });
    }

    private void exportAsVideoCallback(ActionEvent actionEvent) {
        if (this.table.getSelectedRowCount() == 0) return;

        int displayObjectId = getDisplayObjectId(this.table.getSelectedRow());

        Stage stage = Stage.getInstance();
        ReadonlyRect viewport = stage.getCamera().getViewport();

        exportAsVideo((MovieClip) getRenderableObject(displayObjectId));

        stage.doInRenderThread(()->{
            stage.unbindRender();
            stage.init(0, 0, (int) viewport.getWidth(), (int) viewport.getHeight());

            stage.getCamera().reset();
            stage.updatePMVMatrix();
        });
    }

    private static DisplayObject getRenderableObject(int displayObjectId) {
        SupercellSWF swf = Main.editor.getSwf();

        DisplayObject selectedObject = Main.editor.getSelectedObject();
        if (selectedObject != null && selectedObject.getId() == displayObjectId) {
            return selectedObject;
        }

        try {
            DisplayObjectOriginal displayObjectOriginal = swf.getOriginalDisplayObject(displayObjectId, null);
            return DisplayObjectFactory.createFromOriginal(displayObjectOriginal, swf, null);
        } catch (UnableToFindObjectException e) {
            throw new RuntimeException(e);
        }
    }

    private static void exportAsImage(DisplayObject displayObject) {
        Stage stage = Stage.getInstance();

        Rect bounds = stage.calculateBoundsForAllFrames(displayObject);

        ImageExporter imageExporter = Main.editor.getImageExporter();

        stage.doInRenderThread(() -> {
            stage.getCamera().moveToFit(bounds);
            Main.editor.selectObject(displayObject);

            stage.unbindRender();
            stage.init(0, 0, (int) Math.ceil(bounds.getWidth()), (int) Math.ceil(bounds.getHeight()));

//            stage.updatePMVMatrix();

            stage.render(0);

            BufferedImage screenshot = imageExporter.takeScreenshot(displayObject);
            imageExporter.saveScreenshot(displayObject, screenshot);
        });
    }

    private static void exportAsVideo(MovieClip movieClip) {
        Stage stage = Stage.getInstance();

        Rect bounds = stage.calculateBoundsForAllFrames(movieClip);

        int scaleFactor = 1;

        stage.doInRenderThread(() -> {
            stage.getCamera().moveToFit(bounds);
            bounds.scale(scaleFactor);

            Main.editor.selectObject(movieClip);

            stage.unbindRender();
            stage.init(0, 0, (int) Math.ceil(bounds.getWidth()), (int) Math.ceil(bounds.getHeight()));

            stage.getCamera().getZoom().setPointSize(scaleFactor);
            stage.updatePMVMatrix();

            String filename = movieClip.getExportName();
            if (filename == null) {
                filename = String.valueOf(movieClip.getId());
            }

            MovieClipState state = movieClip.getState();
            int loopFrame = movieClip.getLoopFrame();
            int startFrame = movieClip.getCurrentFrame();

            String frameLabel = null;
            if (state == MovieClipState.STOPPED) {
                frameLabel = Objects.requireNonNullElse(movieClip.getFrameLabel(startFrame), String.valueOf(startFrame));
            } else if (loopFrame != -1) {
                frameLabel = Objects.requireNonNullElse(movieClip.getFrameLabel(loopFrame), String.valueOf(loopFrame));
            }

            if (frameLabel != null) {
                filename += "_" + frameLabel;
            }

            Path workingDirectory = Path.of("screenshots").toAbsolutePath();

            // TODO: ask where to save the video file
            try (VideoExporter videoExporter = new FfmpegVideoExporter(workingDirectory, filename, "webm", "libvpx-vp9", movieClip.getFps())) {
                MovieClipHelper.doForAllFrames(movieClip, (frameIndex) -> {
                    // Note: it's necessary to set frame index using this method,
                    // because absolute time frame setting may skip frames.
                    movieClip.gotoAbsoluteTimeRecursive(frameIndex * movieClip.getMsPerFrame());
                    if (loopFrame != -1) {
                        movieClip.setFrame(loopFrame);
                    } else if (state == MovieClipState.STOPPED) {
                        movieClip.setFrame(startFrame);
                    }

                    stage.render(0);

                    ImageData imageData = Main.editor.getImageExporter().getCroppedFramebufferData(bounds, false);
                    BufferedImage image = ImageUtils.createBufferedImageFromPixels(imageData.width(), imageData.height(), imageData.pixels(), false);
                    videoExporter.encodeFrame(image, frameIndex);
                });
            }
        });
    }
}
