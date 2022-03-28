package com.vorono4ka.editor.layout;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.vorono4ka.editor.Main;
import com.vorono4ka.editor.layout.listeners.TableSelectionListener;
import com.vorono4ka.editor.renderer.listeners.EventListener;
import com.vorono4ka.editor.renderer.listeners.MouseListener;
import com.vorono4ka.editor.renderer.listeners.MouseMotionListener;
import com.vorono4ka.editor.renderer.listeners.MouseWheelListener;
import com.vorono4ka.resources.ResourceManager;
import com.vorono4ka.swf.SupercellSWF;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Window {
    private final JFrame frame;
    private GLCanvas canvas;
    private Table objectsTable;
    private JPanel infoBlock;

    public Window(String name) {
        this.frame = new JFrame(name);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void create() {
        JMenuBar menuBar = createJMenuBar();
        this.frame.setJMenuBar(menuBar);

        this.objectsTable = createObjectsTable();

        JScrollPane tableScrollPane = new JScrollPane(this.objectsTable);
        tableScrollPane.setPreferredSize(new Dimension(300, 0));

        this.canvas = createCanvas();

        this.infoBlock = createInfoBlock();
        this.infoBlock.setPreferredSize(new Dimension(300, 0));

        this.frame.getContentPane().add(tableScrollPane, BorderLayout.WEST);
        this.frame.getContentPane().add(this.canvas);
        this.frame.getContentPane().add(this.infoBlock, BorderLayout.EAST);
        this.frame.setMinimumSize(new Dimension(1000, 640));
        this.frame.setSize(this.frame.getContentPane().getPreferredSize());
    }

    public void show() {
        this.frame.setVisible(true);
    }

    public Table getObjectsTable() {
        return objectsTable;
    }

    public JPanel getInfoBlock() {
        return infoBlock;
    }

    public GLCanvas getCanvas() {
        return this.canvas;
    }


    private Table createObjectsTable() {
        Table table = new Table("id", "name", "type");

        table.addSelectionListener(new TableSelectionListener(table));

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);

        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(2, SortOrder.DESCENDING));
        sorter.setSortKeys(sortKeys);

        return table;
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

        return glCanvas;
    }

    private JPanel createInfoBlock() {
        JPanel panel = new JPanel();

//        panel.add(new JLabel("Children"));
//
//        Table timelineChildrenTable = new Table("#", "id", "name");
//        JScrollPane tableScrollPane = new JScrollPane(timelineChildrenTable);
//        tableScrollPane.setPreferredSize(new Dimension(300, infoBlock.getHeight()));
//        panel.add(tableScrollPane);
//
//        panel.add(new JLabel("Frames"));
//
//        Table framesTable = new Table("#", "name");
//        JScrollPane framesTableScrollPane = new JScrollPane(timelineChildrenTable);
//        framesTableScrollPane.setPreferredSize(new Dimension(300, infoBlock.getHeight()));
//        panel.add(framesTableScrollPane);

        return panel;
    }

    private JMenuBar createJMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = createFileMenu();

        menuBar.add(fileMenu);

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

            Main.editor.closeFile();

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

    public void clearTable() {
        DefaultTableModel model = (DefaultTableModel) this.objectsTable.getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
    }
}
