package com.vorono4ka.editor.layout;

import com.formdev.flatlaf.FlatClientProperties;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import com.vorono4ka.editor.Main;
import com.vorono4ka.editor.layout.components.LinkLabel;
import com.vorono4ka.editor.layout.components.Table;
import com.vorono4ka.editor.layout.components.blocks.EditorInfoPanel;
import com.vorono4ka.editor.renderer.Stage;
import com.vorono4ka.editor.renderer.listeners.EventListener;
import com.vorono4ka.editor.renderer.listeners.MouseListener;
import com.vorono4ka.editor.renderer.listeners.MouseWheelListener;
import com.vorono4ka.resources.ResourceManager;
import com.vorono4ka.swf.SupercellSWF;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.time.Year;

public class Window {
    private JFrame frame;
    private GLCanvas canvas;
    private EditorInfoPanel infoBlock;
    private DisplayObjectListPanel displayObjectPanel;

    public void initialize(String title) {
        this.frame = new JFrame(title);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.displayObjectPanel = new DisplayObjectListPanel();
        this.displayObjectPanel.setPreferredSize(new Dimension(300, 0));
        this.canvas = createCanvas();
        this.infoBlock = createInfoBlock();
        this.infoBlock.setPreferredSize(new Dimension(300, 0));

        this.frame.setJMenuBar(createJMenuBar());

        this.frame.getContentPane().add(this.displayObjectPanel, BorderLayout.WEST);
        this.frame.getContentPane().add(this.canvas);
        this.frame.getContentPane().add(this.infoBlock, BorderLayout.EAST);
        this.frame.setMinimumSize(new Dimension(1000, 640));
        this.frame.setSize(this.frame.getContentPane().getPreferredSize());
    }

    public void show() {
        this.frame.pack();
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
    }

    public void setTitle(String title) {
        this.frame.setTitle(title);
    }

    public DisplayObjectListPanel getDisplayObjectPanel() {
        return displayObjectPanel;
    }

    public Table getObjectsTable() {
        return this.displayObjectPanel.getTable();
    }

    public EditorInfoPanel getInfoBlock() {
        return infoBlock;
    }

    public GLCanvas getCanvas() {
        return this.canvas;
    }


    private GLCanvas createCanvas() {
        final GLProfile profile = GLProfile.get(GLProfile.GL3);
        GLCapabilities capabilities = new GLCapabilities(profile);

        GLCanvas glCanvas = new GLCanvas(capabilities);
        glCanvas.addGLEventListener(new EventListener());
        MouseListener mouseListener = new MouseListener();
        glCanvas.addMouseListener(mouseListener);
        glCanvas.addMouseMotionListener(mouseListener);
        glCanvas.addMouseWheelListener(new MouseWheelListener());
        glCanvas.setSize(1200, 800);

        FPSAnimator animator = new FPSAnimator(glCanvas, 60);
        animator.start();

        return glCanvas;
    }

    private EditorInfoPanel createInfoBlock() {
        return new EditorInfoPanel();
    }

    private JMenuBar createJMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        menuBar.add(createFileMenu());
        menuBar.add(createEditMenu());
        menuBar.add(createViewMenu());
        menuBar.add(createOptionsMenu());
        menuBar.add(createHelpMenu());

        return menuBar;
    }

    private JMenu createFileMenu() {
        JMenu menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);

        JMenuItem open = new JMenuItem("Open", KeyEvent.VK_O);
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        open.addActionListener((e) -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            fileChooser.setFileFilter(new FileNameExtensionFilter("Supercell SWF (*.sc)", "sc"));

            int result = fileChooser.showOpenDialog(this.frame);
            if (result != JFileChooser.APPROVE_OPTION) return;

            String path = fileChooser.getSelectedFile().getPath();
            if (!ResourceManager.doesFileExist(path.substring(0, path.length() - 3) + SupercellSWF.TEXTURE_EXTENSION)) {
                Object[] options = {"Yes", "Cancel"};
                int warningResult = JOptionPane.showOptionDialog(
                    this.frame,
                    "There is no texture file (but it may have a different suffix specified in the file).\n" +
                            "Do you want to open file anyway?",
                    "Answer the question",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    options,
                    options[0]
                );

                if (warningResult != 0) return;
            }

            Main.editor.closeFile();

            Main.editor.openFile(path);
        });

        menu.add(open);

        JMenuItem close = new JMenuItem("Close", KeyEvent.VK_C);
        close.addActionListener((e) -> Main.editor.closeFile());

        menu.add(close);

        menu.addSeparator();

        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener((e) -> System.exit(0));
        menu.add(exit);
        return menu;
    }

    private JMenu createEditMenu() {
        JMenu menu = new JMenu("Edit");
        menu.setMnemonic(KeyEvent.VK_E);

        JMenuItem find = new JMenuItem("Find", KeyEvent.VK_F);
        find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));
        find.addActionListener((e) -> this.displayObjectPanel.setFocusOnTextField());

        menu.add(find);

        JMenuItem previous = new JMenuItem("Previous", KeyEvent.VK_P);
        previous.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, InputEvent.CTRL_DOWN_MASK | InputEvent.ALT_DOWN_MASK));
        previous.addActionListener((e) -> Main.editor.selectPrevious());

        menu.add(previous);

        JMenuItem next = new JMenuItem("Next", KeyEvent.VK_N);
        next.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, InputEvent.CTRL_DOWN_MASK | InputEvent.ALT_DOWN_MASK));
        next.addActionListener((e) -> Main.editor.selectNext());

        menu.add(next);

        return menu;
    }

    private JMenu createViewMenu() {
        JMenu menu = new JMenu("View");
        menu.setMnemonic(KeyEvent.VK_V);

        JMenuItem reset = new JMenuItem("Reset");
        reset.addActionListener(e -> {
            Stage stage = Stage.INSTANCE;

            stage.setScaleStep(39);
            stage.setScale(1);

            stage.setOffset(0, 0);

            stage.doInRenderThread(stage::updatePMVMatrix);
            Main.editor.updateCanvas();
        });

        menu.add(reset);

        return menu;
    }

    private JMenu createOptionsMenu() {
        return new JMenu("Options");
    }

    private JMenu createHelpMenu() {
        JMenu menu = new JMenu("Help");
        menu.setMnemonic(KeyEvent.VK_H);

        JMenuItem about = new JMenuItem("About", KeyEvent.VK_A);
        about.addActionListener((e) -> {
            JLabel titleLabel = new JLabel(Main.TITLE);
            titleLabel.putClientProperty(FlatClientProperties.STYLE_CLASS, "h1");

            JOptionPane.showMessageDialog(
                this.frame,
                new Object[] {
                    titleLabel,
                    new LinkLabel("https://github.com/vorono4ka/sc-editor"),
                    "Copyright 2022-" + Year.now() + " Vorono4ka"
                },
                "About",
                JOptionPane.PLAIN_MESSAGE
            );
        });

        menu.add(about);

        return menu;
    }
}
