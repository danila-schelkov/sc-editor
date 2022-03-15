package com.vorono4ka.editor.layout;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.vorono4ka.editor.Main;
import com.vorono4ka.editor.graphics.EventListener;
import com.vorono4ka.editor.graphics.objects.Triangle;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class Window {
    private final JFrame frame;
    private GLCanvas canvas;

    public Window(String name) {
        this.frame = new JFrame(name);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void create() {
        JMenuBar menuBar = createJMenuBar();
        this.frame.setJMenuBar(menuBar);

        this.canvas = createCanvas();

        this.frame.getContentPane().add(this.canvas);
        this.frame.setSize(frame.getContentPane().getPreferredSize());
    }

    public void show() {
        frame.setVisible(true);
    }

    public int getWidth() {
        return this.frame.getWidth();
    }

    public int getHeight() {
        return this.frame.getHeight();
    }

    public float getAspectRatio() {
        return getWidth() / (float) getHeight();
    }


    private GLCanvas createCanvas() {
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        GLCanvas glCanvas = new GLCanvas(capabilities);
        glCanvas.addGLEventListener(new EventListener());
        glCanvas.setSize(1200, 800);

        return glCanvas;
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
            System.out.println("Open.");
            Main.editor.getRenderer().setDisplayObject(new Triangle());
            this.canvas.display();
        });

        fileMenu.add(open);

        JMenuItem close = new JMenuItem("Close", KeyEvent.VK_C);
        close.addActionListener((e) -> {
            Main.editor.getRenderer().setDisplayObject(null);
            this.canvas.display();
        });

        fileMenu.add(close);

        fileMenu.addSeparator();

        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener((e) -> System.exit(0));
        fileMenu.add(exit);
        return fileMenu;
    }
}
