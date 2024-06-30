package com.vorono4ka.editor.layout.menubar.menus;

import com.vorono4ka.editor.Main;
import com.vorono4ka.editor.layout.panels.StatusBar;
import com.vorono4ka.editor.renderer.Stage;
import com.vorono4ka.exporter.FfmpegVideoExporter;
import com.vorono4ka.exporter.VideoExporter;
import com.vorono4ka.math.Rect;
import com.vorono4ka.resources.ResourceManager;
import com.vorono4ka.swf.constants.MovieClipState;
import com.vorono4ka.swf.displayObjects.DisplayObject;
import com.vorono4ka.swf.displayObjects.MovieClip;
import com.vorono4ka.utilities.ImageData;
import com.vorono4ka.utilities.ImageUtils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

public class FileMenu extends JMenu {
    private final JFrame frame;
    private final JMenuItem saveButton;
    private final JMenuItem saveAsButton;
    private final JMenu exportMenu;

    public FileMenu(JFrame frame) {
        super("File");

        this.frame = frame;

        setMnemonic(KeyEvent.VK_F);

        JMenuItem open = new JMenuItem("Open", KeyEvent.VK_O);
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));

        this.saveButton = new JMenuItem("Save", KeyEvent.VK_O);
        this.saveButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));

        this.exportMenu = new JMenu("Export");

        JMenuItem takeScreenshotButton = new JMenuItem("Take screenshot", KeyEvent.VK_T);
        takeScreenshotButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, InputEvent.CTRL_DOWN_MASK));
        this.exportMenu.add(takeScreenshotButton);

        JMenuItem recordAllFramesButton = new JMenuItem("Record all frames", KeyEvent.VK_T);
        recordAllFramesButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, InputEvent.CTRL_DOWN_MASK));
        this.exportMenu.add(recordAllFramesButton);

        JMenuItem openScreenshotsFolderButton = new JMenuItem("Open screenshots folder");

        this.saveAsButton = new JMenuItem("Save as...", KeyEvent.VK_O);
        this.saveAsButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
        JMenuItem close = new JMenuItem("Close", KeyEvent.VK_C);
        JMenuItem exit = new JMenuItem("Exit");

        open.addActionListener(this::open);
        this.saveButton.addActionListener(this::save);
        takeScreenshotButton.addActionListener(this::exportAsImage);
        recordAllFramesButton.addActionListener(this::exportAllFrames);
        openScreenshotsFolderButton.addActionListener(e -> {
            try {
                Path screenshots = Path.of("screenshots");
                screenshots.toFile().mkdirs();

                Desktop.getDesktop().open(screenshots.toFile());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        close.addActionListener(FileMenu::close);
        exit.addActionListener(FileMenu::exit);

        this.add(open);
        this.add(this.saveButton);
        this.add(this.saveAsButton);
        this.add(close);

        this.addSeparator();
        this.add(this.exportMenu);
        this.add(openScreenshotsFolderButton);

        this.addSeparator();
        this.add(exit);

        this.checkCanSave();
    }

    private static void close(ActionEvent e) {
        Main.editor.closeFile();
    }

    private static void exit(ActionEvent e) {
        System.exit(0);
    }

    private void exportAsImage(ActionEvent actionEvent) {
        Stage instance = Stage.getInstance();
        instance.doInRenderThread(instance::takeScreenshot);
        Main.editor.updateCanvas();
    }

    private void exportAllFrames(ActionEvent actionEvent) {
        Stage instance = Stage.getInstance();
        instance.doInRenderThread(() -> {
            DisplayObject child = instance.getStageSprite().getChild(0);
            if (!child.isMovieClip()) return;

            MovieClip movieClip = ((MovieClip) child);
            int maxFrames = movieClip.getFrames().length;

            int currentFrame = movieClip.getCurrentFrame();

            StatusBar statusBar = Main.editor.getWindow().getStatusBar();

            Rect movieClipBounds = new Rect();
            for (int i = 0; i < maxFrames; i++) {
                movieClip.gotoAbsoluteTimeRecursive(i * movieClip.getMSPerFrame());
                movieClipBounds.mergeBounds(instance.getDisplayObjectBounds(movieClip));
            }

            Rect clipArea = new Rect(instance.getCamera().getClipArea());
            if (!clipArea.containsPoint(movieClipBounds.getLeft(), movieClipBounds.getTop())
                || !clipArea.containsPoint(movieClipBounds.getRight(), movieClipBounds.getBottom())) {
                movieClipBounds.clamp(clipArea);
            }

            Rect scaledBounds = new Rect(movieClipBounds);
            scaledBounds.scale(instance.getCamera().getPointSize());

            String filename = child.getId() + movieClip.getExportName();
            Path workingDirectory = Path.of("screenshots");
            // TODO: ask where to save the video file
            try (VideoExporter videoExporter = new FfmpegVideoExporter(workingDirectory, filename, "webm", "libvpx-vp9", movieClip.getFps())) {
                for (int i = 0; i < maxFrames; i++) {
                    movieClip.gotoAbsoluteTimeRecursive(i * movieClip.getMSPerFrame());
                    // Note: it's necessary to set frame index using this method,
                    // because absolute time frame setting may skip frames.
                    movieClip.gotoAndStopFrameIndex(i);
                    instance.render(0);

                    ImageData imageData = instance.getCroppedFramebufferData(movieClipBounds);
                    BufferedImage image = ImageUtils.createBufferedImageFromPixels(imageData.width(), imageData.height(), imageData.pixels());
                    videoExporter.encodeFrame(image, i);

                    statusBar.createProgressBarTask(i, 0, maxFrames);
                }
            }

            statusBar.removeProgressBarTask();

            movieClip.gotoAndPlayFrameIndex(currentFrame, -1, MovieClipState.PLAYING);
        });

        Main.editor.updateCanvas();
    }

    public void checkCanSave() {
        boolean canSave = Main.editor.getSwf() != null;

        this.saveButton.setEnabled(canSave);
        this.saveAsButton.setEnabled(canSave);
        this.exportMenu.setEnabled(canSave);
    }

    private void open(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Supercell SWF (*.sc)", "sc"));

        int result = fileChooser.showOpenDialog(this.frame);
        if (result != JFileChooser.APPROVE_OPTION) return;

        String path = fileChooser.getSelectedFile().getPath();

        close(null);

        StatusBar statusBar = Main.editor.getWindow().getStatusBar();
        statusBar.createProgressBarTask(0, 0, 100);

        SwingWorker<Integer, Integer> worker = new SwingWorker<>() {
            @Override
            protected Integer doInBackground() {
                Main.editor.openFile(path);
                return 0;
            }

            @Override
            protected void done() {
                super.done();
                statusBar.removeProgressBarTask();
            }
        };

        worker.execute();
    }

    private void save(ActionEvent actionEvent) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Supercell SWF (*.sc)", "sc"));

        int result = fileChooser.showSaveDialog(this.frame);
        if (result != JFileChooser.APPROVE_OPTION) return;

        String path = fileChooser.getSelectedFile().getPath();
        if (!path.endsWith(".sc")) {
            path += ".sc";
        }

        if (ResourceManager.doesFileExist(path)) {
            Object[] options = {"Yes", "Cancel"};
            int warningResult = JOptionPane.showOptionDialog(
                this.frame,
                "There is already a file with that name.\n" +
                    "Do you want to replace it?",
                "Answer the question",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                options,
                options[0]
            );

            if (warningResult != 0) return;
        }

        Main.editor.saveFile(path);
    }
}
