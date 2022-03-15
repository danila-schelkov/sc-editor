package com.vorono4ka.editor;

import com.vorono4ka.editor.layout.Window;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static Editor editor;

    public static void main(String[] args) {
        Main.editor = new Editor();

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                UIManager.put("MenuItem.margin", new Insets(0, -15, 0, 0));
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }

            Window window = Main.editor.getWindow();
            window.create();
            window.show();
        });
    }
}
