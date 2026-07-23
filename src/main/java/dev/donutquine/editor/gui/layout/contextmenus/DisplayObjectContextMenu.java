package dev.donutquine.editor.gui.layout.contextmenus;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.Objects;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import com.formdev.flatlaf.util.SystemFileChooser;
import com.formdev.flatlaf.util.SystemFileChooser.FileNameExtensionFilter;
import dev.donutquine.editor.gui.layout.SupercellSWFLayoutController;
import dev.donutquine.editor.gui.layout.SystemFileChooserUtil;
import dev.donutquine.editor.gui.layout.components.tables.JTablePopupMenuListener;
import dev.donutquine.editor.gui.settings.EditorPreferences;
import dev.donutquine.editor.renderer.impl.EditorStage;
import dev.donutquine.editor.renderer.impl.RendererHelper;
import dev.donutquine.exporter.FfmpegVideoExporter;
import dev.donutquine.exporter.GifExporter;
import dev.donutquine.exporter.VideoFormat;
import dev.donutquine.exporter.VideoFormats;
import dev.donutquine.math.ReadonlyRect;
import dev.donutquine.renderer.impl.swf.objects.DisplayObject;
import dev.donutquine.renderer.impl.swf.objects.MovieClip;
import dev.donutquine.streams.ByteStream;
import dev.donutquine.swf.DisplayObjectOriginal;
import dev.donutquine.swf.SupercellSWF;
import dev.donutquine.swf.exceptions.UnableToFindObjectException;
import dev.donutquine.swf.movieclips.MovieClipOriginal;
import dev.donutquine.swf.movieclips.MovieClipState;
import dev.donutquine.swf.textfields.TextFieldOriginal;
import dev.donutquine.utilities.ByteArrayFlavor;
import dev.donutquine.utilities.MovieClipHelper;
import dev.donutquine.utilities.PathUtils;

public class DisplayObjectContextMenu extends ContextMenu {
    private static final Clipboard SYSTEM_CLIPBOARD = Toolkit.getDefaultToolkit().getSystemClipboard();

    private final JTable table;
    private final SupercellSWFLayoutController swfLayoutController;

    private final JMenuItem exportAsVideoButton;
    private final JMenuItem exportAsGifButton;
    private final JMenu exportAsMenu;

    private EditorPreferences preferences;

    public DisplayObjectContextMenu(JTable table, SupercellSWFLayoutController swfLayoutController) {
        super(table, null);

        this.table = table;
        this.swfLayoutController = swfLayoutController;
        this.preferences = swfLayoutController.window.getEditor().getPreferences();

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

        exportAsGifButton = this.add(exportAsMenu, "Export as GIF", KeyEvent.VK_G);
        exportAsGifButton.addActionListener(this::exportAsGifCallback);

        this.addSeparator();

        JMenuItem findUsagesButton = this.add("Find Usages", KeyEvent.VK_U);
        findUsagesButton.addActionListener(this::findUsages);

        this.addSeparator();

        this.add("Properties");

        this.popupMenu.addPopupMenuListener(new JTablePopupMenuListener(this.popupMenu, table, this::onRowSelected));
    }

    private void onRowSelected(int rowIndex) {
        SupercellSWF swf = this.swfLayoutController.assetFile.asset;
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
                    boolean hasMoreThanOneFrame = MovieClipHelper.getFrameCountRecursive(swf, movieClipOriginal) > 1;
                    exportAsVideoButton.setEnabled(hasMoreThanOneFrame);
                    exportAsGifButton.setEnabled(hasMoreThanOneFrame);
                } else {
                    exportAsVideoButton.setEnabled(false);
                    exportAsGifButton.setEnabled(false);
                }
            } catch (UnableToFindObjectException e) {
                throw new RuntimeException(e);
            }
        } else {
            exportAsMenu.setEnabled(false);
            exportAsGifButton.setEnabled(false);
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

        this.swfLayoutController.findUsages(displayObjectId, displayObjectName);
    }

    private void copyExportName(ActionEvent event) {
        int selectedRow = this.table.getSelectedRow();
        if (selectedRow == -1) return;

        String displayObjectName = getDisplayObjectName(selectedRow);

        SYSTEM_CLIPBOARD.setContents(new StringSelection(displayObjectName), null);
    }

    private void copyAsBytes(ActionEvent event) {
        int selectedRow = this.table.getSelectedRow();
        if (selectedRow == -1) return;

        SupercellSWF swf = this.swfLayoutController.assetFile.asset;
        if (swf == null) return;

        int displayObjectId = getDisplayObjectId(selectedRow);
        try {
            DisplayObjectOriginal originalDisplayObject = swf.getOriginalDisplayObject(displayObjectId, null);
            ByteStream stream = new ByteStream();
            originalDisplayObject.save(stream);

            SYSTEM_CLIPBOARD.setContents(new ByteArrayFlavor(stream.getData()), null);
        } catch (UnableToFindObjectException e) {
            throw new RuntimeException(e);
        }
    }

    private void export(ActionEvent actionEvent) {
        EditorStage stage = EditorStage.getInstance();
        ReadonlyRect viewport = stage.getCamera().getViewport();

        float pixelSize = this.preferences.getPixelSize();

        Path outputDirectory = EditorPreferences.DEFAULT_SCREENSHOT_FOLDER;

        for (int row : this.table.getSelectedRows()) {
            int displayObjectId = getDisplayObjectId(row);

            DisplayObject renderableObject = getRenderableObject(displayObjectId);
            if (renderableObject == null || renderableObject.isTextField()) continue;

            if (canExportAsVideo(renderableObject)) {
                // TODO: get video format from user settings
                VideoFormat format = VideoFormats.WEBM;

                MovieClip movieClip = (MovieClip) renderableObject;

                String filename = getClipFilename(movieClip, movieClip.getState(), movieClip.getCurrentFrame(), movieClip.getLoopFrame(), pixelSize);

                Path filepath = outputDirectory.resolve(String.join(".", filename, format.name()));
                stage.doInRenderThread(() -> {
                    RendererHelper.exportAsVideo(movieClip, new FfmpegVideoExporter(format, filepath, movieClip.getFps()), format, this.preferences.getPixelSize(), this.preferences.shouldPreserveStageCenter());
                });
            } else {
                Path filepath = outputDirectory.resolve(getDisplayObjectFilename(renderableObject, pixelSize));
                stage.doInRenderThread(() -> {
                    RendererHelper.exportAsImage(renderableObject, filepath, this.preferences.getPixelSize(), this.preferences.shouldPreserveStageCenter());
                });
            }
        }

        stage.doInRenderThread(() -> {
            RendererHelper.rollbackRenderer(stage, viewport);
        });
    }

    private void exportAsImageCallback(ActionEvent actionEvent) {
        if (this.table.getSelectedRowCount() == 0) return;

        int displayObjectId = getDisplayObjectId(this.table.getSelectedRow());

        EditorStage stage = EditorStage.getInstance();
        ReadonlyRect viewport = stage.getCamera().getViewport();

        DisplayObject renderableObject = getRenderableObject(displayObjectId);

        Path filepath = EditorPreferences.DEFAULT_SCREENSHOT_FOLDER.resolve(getDisplayObjectFilename(renderableObject, this.preferences.getPixelSize()));
        stage.doInRenderThread(() -> {
            RendererHelper.exportAsImage(renderableObject, filepath, this.preferences.getPixelSize(), this.preferences.shouldPreserveStageCenter());
            RendererHelper.rollbackRenderer(stage, viewport);
        });
    }

    private void exportAsVideoCallback(ActionEvent actionEvent) {
        if (this.table.getSelectedRowCount() == 0) return;

        int displayObjectId = getDisplayObjectId(this.table.getSelectedRow());

        SystemFileChooser fileChooser = new SystemFileChooser();
        fileChooser.setStateStoreID("exportVideo");
        fileChooser.setFileSelectionMode(SystemFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("WEBM video", "webm"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("HEVC video", "hevc"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("AV1 video", "avi"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("MP4 video (no transparency)", "mp4"));

        MovieClip movieClip = (MovieClip) getRenderableObject(displayObjectId);
        String exportName = movieClip.getExportName();
        if (exportName != null) {
            java.io.File currentDir = fileChooser.getCurrentDirectory();
            fileChooser.setSelectedFile(new java.io.File(currentDir, exportName));
        }

        int result = fileChooser.showSaveDialog(swfLayoutController.window.getFrame());
        if (result != SystemFileChooser.APPROVE_OPTION) return;

        Path path = SystemFileChooserUtil.getPathWithExtension(fileChooser, null);
        if (path == null) return;

        String formatExtension = PathUtils.getFileExtension(path.getFileName().toString());
        VideoFormat format = VideoFormats.getVideoFormatByName(formatExtension);
        if (format == null) {
            this.swfLayoutController.window.showErrorDialog("Unknown format " + formatExtension);
            return;
        }

        EditorStage stage = EditorStage.getInstance();
        ReadonlyRect viewport = stage.getCamera().getViewport();

        stage.doInRenderThread(() -> {
            RendererHelper.exportAsVideo(movieClip, new FfmpegVideoExporter(format, path, movieClip.getFps()), format, this.preferences.getPixelSize(), this.preferences.shouldPreserveStageCenter());
            RendererHelper.rollbackRenderer(stage, viewport);
        });
    }

    private void exportAsGifCallback(ActionEvent actionEvent) {
        if (this.table.getSelectedRowCount() == 0) return;

        int displayObjectId = getDisplayObjectId(this.table.getSelectedRow());

        EditorStage stage = EditorStage.getInstance();
        ReadonlyRect viewport = stage.getCamera().getViewport();

        SystemFileChooser fileChooser = new SystemFileChooser();
        fileChooser.setStateStoreID("exportGif");
        fileChooser.setFileSelectionMode(SystemFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("GIF animation (transparent)", "gif"));

        MovieClip movieClip = (MovieClip) getRenderableObject(displayObjectId);
        String exportName = movieClip.getExportName();
        if (exportName != null) {
            java.io.File currentDir = fileChooser.getCurrentDirectory();
            fileChooser.setSelectedFile(new java.io.File(currentDir, exportName));
        }

        int result = fileChooser.showSaveDialog(swfLayoutController.window.getFrame());
        if (result != SystemFileChooser.APPROVE_OPTION) return;

        Path path = SystemFileChooserUtil.getPathWithExtension(fileChooser, "gif");
        if (path == null) return;

        // GIF frame delays are stored in centiseconds (1/100s).
        // To avoid rounding errors, pick the highest fps that evenly divides 100
        // and does not exceed the source fps.  e.g. 60fps src -> 50fps GIF (exact 2cs/frame).
        int srcFps = movieClip.getFps();
        int defaultFps = 1;
        for (int candidate : new int[]{100, 50, 25, 20, 10, 5, 4, 2, 1}) {
            if (candidate <= srcFps) {
                defaultFps = candidate;
                break;
            }
        }
        String input = javax.swing.JOptionPane.showInputDialog(
            swfLayoutController.window.getFrame(),
            "GIF frame rate (fps):\n"
                + "Original: " + srcFps + " fps\n"
                + "Recommended: " + defaultFps + " fps  (largest value that divides 100 evenly → no timing error)\n"
                + "Note: GIF delays are in 1/100s; non-divisors cause speed drift.",
            String.valueOf(defaultFps)
        );
        // Pre-fill by replacing the empty dialog return with defaultFps
        if (input == null) {
            RendererHelper.rollbackRenderer(stage, viewport);
            return; // cancelled
        }
        input = input.trim();
        int gifFps;
        try {
            gifFps = Integer.parseInt(input.isEmpty() ? String.valueOf(defaultFps) : input);
            if (gifFps <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            swfLayoutController.window.showErrorDialog("Invalid frame rate: \"" + input + "\"");
            RendererHelper.rollbackRenderer(stage, viewport);
            return;
        }

        stage.doInRenderThread(() -> {
            RendererHelper.exportAsVideo(movieClip, new GifExporter(path, movieClip.getFps(), gifFps), VideoFormats.WEBM, preferences.getPixelSize(), preferences.shouldPreserveStageCenter());
            RendererHelper.rollbackRenderer(stage, viewport);
        });
    }

    private DisplayObject getRenderableObject(int displayObjectId) {
        DisplayObject selectedObject = swfLayoutController.getSelectedObject();
        if (selectedObject != null && selectedObject.getId() == displayObjectId) {
            return selectedObject;
        }

        try {
            return swfLayoutController.assetFile.getOrCreate(displayObjectId, null);
        } catch (UnableToFindObjectException e) {
            throw new RuntimeException(e);
        }
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

                String exportName = movieClip.getExportName();
                String folderName = exportName != null ? exportName : String.valueOf(displayObject.getId());
                return Path.of(addPixelSizeToFilename(folderName, pixelSize), frameName + ".png");
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
