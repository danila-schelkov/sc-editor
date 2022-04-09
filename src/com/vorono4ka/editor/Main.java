package com.vorono4ka.editor;

import com.formdev.flatlaf.FlatLightLaf;
import com.vorono4ka.editor.layout.Window;

import javax.swing.*;

public class Main {
    public static Editor editor;

    public static void main(String[] args) {
        Main.editor = new Editor();

        SwingUtilities.invokeLater(() -> {
            FlatLightLaf.setup();

            Window window = Main.editor.getWindow();
            window.create();

            window.show();
        });
    }
}
