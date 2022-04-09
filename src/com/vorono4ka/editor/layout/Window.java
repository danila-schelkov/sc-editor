package com.vorono4ka.editor.layout;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import com.vorono4ka.editor.Main;
import com.vorono4ka.editor.layout.components.Table;
import com.vorono4ka.editor.layout.components.blocks.EditorInfoPanel;
import com.vorono4ka.editor.renderer.listeners.EventListener;
import com.vorono4ka.editor.renderer.listeners.MouseListener;
import com.vorono4ka.editor.renderer.listeners.MouseMotionListener;
import com.vorono4ka.editor.renderer.listeners.MouseWheelListener;
import com.vorono4ka.resources.ResourceManager;
import com.vorono4ka.swf.SupercellSWF;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class Window {
    private final JFrame frame;
    private GLCanvas canvas;
    private EditorInfoPanel infoBlock;
    private DisplayObjectListPanel displayObjectPanel;

    public Window(String name) {
        this.frame = new JFrame(name);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void create() {
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
        this.frame.setVisible(true);
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
        glCanvas.addMouseListener(new MouseListener());
        glCanvas.addMouseWheelListener(new MouseWheelListener());
        glCanvas.addMouseMotionListener(new MouseMotionListener());
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

        return menuBar;
    }

    private JMenu createFileMenu() {
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

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

        fileMenu.add(open);

        JMenuItem close = new JMenuItem("Close", KeyEvent.VK_C);
        close.addActionListener((e) -> Main.editor.closeFile());

        fileMenu.add(close);

        fileMenu.addSeparator();

        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener((e) -> System.exit(0));
        fileMenu.add(exit);
        return fileMenu;
    }

    private JMenu createEditMenu() {
        JMenu fileMenu = new JMenu("Edit");
        fileMenu.setMnemonic(KeyEvent.VK_E);

        JMenuItem find = new JMenuItem("Find", KeyEvent.VK_F);
        find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));
        find.addActionListener((e) -> this.displayObjectPanel.setFocusOnTextField());

        fileMenu.add(find);

        return fileMenu;
    }
}
