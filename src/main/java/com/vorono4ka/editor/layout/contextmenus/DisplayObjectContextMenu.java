package com.vorono4ka.editor.layout.contextmenus;

import com.vorono4ka.editor.Editor;
import com.vorono4ka.editor.layout.components.Table;
import com.vorono4ka.editor.layout.components.TablePopupMenuListener;
import com.vorono4ka.editor.renderer.Framebuffer;
import com.vorono4ka.editor.renderer.impl.RendererHelper;
import com.vorono4ka.editor.renderer.impl.EditorStage;
import com.vorono4ka.exporter.FfmpegVideoExporter;
import com.vorono4ka.exporter.VideoExporter;
import com.vorono4ka.math.ReadonlyRect;
import com.vorono4ka.math.Rect;
import com.vorono4ka.renderer.impl.swf.objects.DisplayObject;
import com.vorono4ka.renderer.impl.swf.objects.DisplayObjectFactory;
import com.vorono4ka.renderer.impl.swf.objects.MovieClip;
import com.vorono4ka.streams.ByteStream;
import com.vorono4ka.swf.ColorTransform;
import com.vorono4ka.swf.DisplayObjectOriginal;
import com.vorono4ka.swf.Matrix2x3;
import com.vorono4ka.swf.SupercellSWF;
import com.vorono4ka.swf.exceptions.UnableToFindObjectException;
import com.vorono4ka.swf.movieclips.MovieClipOriginal;
import com.vorono4ka.swf.movieclips.MovieClipState;
import com.vorono4ka.swf.textfields.TextFieldOriginal;
import com.vorono4ka.utilities.ByteArrayFlavor;
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
import java.text.DecimalFormat;
import java.util.Objects;

public class DisplayObjectContextMenu extends ContextMenu {
    private static final Clipboard SYSTEM_CLIPBOARD = Toolkit.getDefaultToolkit().getSystemClipboard();

    // TODO: load from settings
    private static final Path SCREENSHOT_FOLDER = Path.of("screenshots").toAbsolutePath();

    private final Table table;
    private final Editor editor;

    private final JMenuItem exportAsVideoButton;
    private final JMenu exportAsMenu;

    public DisplayObjectContextMenu(Table table, Editor editor) {
        super(table, null);

        this.table = table;
        this.editor = editor;

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
        SupercellSWF swf = editor.getSwf();
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

        editor.findUsages(displayObjectId, displayObjectName);
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

        SupercellSWF swf = editor.getSwf();
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
        EditorStage stage = EditorStage.getInstance();
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

        RendererHelper.rollbackRenderer(stage, viewport);
    }

    private void exportAsImageCallback(ActionEvent actionEvent) {
        if (this.table.getSelectedRowCount() == 0) return;

        int displayObjectId = getDisplayObjectId(this.table.getSelectedRow());

        EditorStage stage = EditorStage.getInstance();
        ReadonlyRect viewport = stage.getCamera().getViewport();

        exportAsImage(getRenderableObject(displayObjectId));

        RendererHelper.rollbackRenderer(stage, viewport);
    }

    private void exportAsVideoCallback(ActionEvent actionEvent) {
        if (this.table.getSelectedRowCount() == 0) return;

        int displayObjectId = getDisplayObjectId(this.table.getSelectedRow());

        EditorStage stage = EditorStage.getInstance();
        ReadonlyRect viewport = stage.getCamera().getViewport();

        exportAsVideo((MovieClip) getRenderableObject(displayObjectId));

        RendererHelper.rollbackRenderer(stage, viewport);
    }

    private DisplayObject getRenderableObject(int displayObjectId) {
        SupercellSWF swf = editor.getSwf();

        DisplayObject selectedObject = editor.getSelectedObject();
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

    private void exportAsImage(DisplayObject displayObject) {
        EditorStage stage = EditorStage.getInstance();

        Rect bounds = stage.calculateBoundsForAllFrames(displayObject);

        float pixelSize = editor.getPixelSize();
        bounds.scale(pixelSize);

        Matrix2x3 matrix = new Matrix2x3();
        matrix.scaleMultiply(pixelSize, pixelSize);

        stage.doInRenderThread(() -> {
            Framebuffer framebuffer = RendererHelper.prepareStageForRendering(stage, bounds);

            // Note: Passing own sprite as parent to provide Stage reference
            boolean parentSet = false;
            if (displayObject.getParent() == null) {
                displayObject.setParent(stage.getStageSprite());
                parentSet = true;
            }

            displayObject.render(matrix, new ColorTransform(), 0, 0);

            if (parentSet) {
                displayObject.setParent(null);
            }

            stage.renderToFramebuffer(framebuffer);

            BufferedImage screenshot = ImageUtils.createBufferedImageFromPixels(framebuffer.getWidth(), framebuffer.getHeight(), framebuffer.getPixelArray(true), false);
            ImageUtils.saveImage(SCREENSHOT_FOLDER.resolve(getDisplayObjectFilename(displayObject, pixelSize)), screenshot);

            framebuffer.delete();
        });
    }

    private void exportAsVideo(MovieClip movieClip) {
        EditorStage stage = EditorStage.getInstance();

        Rect bounds = stage.calculateBoundsForAllFrames(movieClip);

        float pixelSize = editor.getPixelSize();
        bounds.scale(pixelSize);

        Matrix2x3 matrix = new Matrix2x3();
        matrix.scaleMultiply(pixelSize, pixelSize);

        MovieClipState state = movieClip.getState();
        int loopFrame = movieClip.getLoopFrame();
        int startFrame = movieClip.getCurrentFrame();

        String filename = getClipFilename(movieClip, state, startFrame, loopFrame, pixelSize);

        stage.doInRenderThread(() -> {
            Framebuffer framebuffer = RendererHelper.prepareStageForRendering(stage, bounds);

            // Note: Passing own sprite as parent to provide Stage reference
            boolean parentSet = false;
            if (movieClip.getParent() == null) {
                movieClip.setParent(stage.getStageSprite());
                parentSet = true;
            }

            // TODO: ask where to save the video file
            try (VideoExporter videoExporter = new FfmpegVideoExporter(SCREENSHOT_FOLDER, filename, "webm", "libvpx-vp9", movieClip.getFps())) {
                MovieClipHelper.doForAllFrames(movieClip, (frameIndex) -> {
                    // Note: it's necessary to set frame index using this method,
                    // because absolute time frame setting may skip frames.
                    movieClip.gotoAbsoluteTimeRecursive(frameIndex * movieClip.getMsPerFrame());
                    if (loopFrame != -1) {
                        movieClip.setFrame(loopFrame);
                    } else if (state == MovieClipState.STOPPED) {
                        movieClip.setFrame(startFrame);
                    }

                    movieClip.render(matrix, new ColorTransform(), 0, 0);
                    stage.renderToFramebuffer(framebuffer);

                    BufferedImage image = ImageUtils.createBufferedImageFromPixels(framebuffer.getWidth(), framebuffer.getHeight(), framebuffer.getPixelArray(true), false);
                    videoExporter.encodeFrame(image, frameIndex);
                });
            }

            if (parentSet) {
                movieClip.setParent(null);
            }

            framebuffer.delete();
        });
    }

    private static String getClipFilename(MovieClip movieClip, MovieClipState state, int startFrame, int loopFrame, float pixelSize) {
        String filename = movieClip.getExportName();
        if (filename == null) {
            filename = String.valueOf(movieClip.getId());
        }

        String frameLabel = null;
        if (state == MovieClipState.STOPPED) {
            frameLabel = Objects.requireNonNullElse(movieClip.getFrameLabel(startFrame), String.valueOf(startFrame));
        } else if (loopFrame != -1) {
            frameLabel = Objects.requireNonNullElse(movieClip.getFrameLabel(loopFrame), String.valueOf(loopFrame));
        }

        if (frameLabel != null) {
            filename += "_" + frameLabel;
        }

        return addPixelSizeToFilename(filename, pixelSize);
    }

    private static Path getDisplayObjectFilename(DisplayObject displayObject, float pixelSize) {
        if (displayObject.isMovieClip()) {
            MovieClip movieClip = (MovieClip) displayObject;

            if (movieClip.getFrameCount() > 1) {
                int currentFrame = movieClip.getCurrentFrame();
                String frameLabel = movieClip.getFrameLabel(currentFrame);
                String frameName = String.valueOf(currentFrame);
                if (frameLabel != null) {
                    frameName = String.join("-", frameName, frameLabel);
                }

                return Path.of(addPixelSizeToFilename(String.valueOf(displayObject.getId()), pixelSize), frameName + ".png");
            }

            String exportName = movieClip.getExportName();
            if (exportName != null) {
                return Path.of(addPixelSizeToFilename(exportName, pixelSize) + ".png");
            }
        }

        return Path.of(addPixelSizeToFilename(String.valueOf(displayObject.getId()), pixelSize) + ".png");
    }

    private static boolean canExportAsVideo(DisplayObject renderableObject) {
        return renderableObject.isMovieClip() && ((MovieClip) renderableObject).getFrameCountRecursive() > 1;
    }

    private static String addPixelSizeToFilename(String filename, float pixelSize) {
        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(0);
        df.setMaximumFractionDigits(2);
        df.setGroupingUsed(false);

        // TODO: allow to disable this in settings
        if (pixelSize != 1.0f) {
            filename += "_" + df.format(pixelSize) + "x";
        }

        return filename;
    }
}
