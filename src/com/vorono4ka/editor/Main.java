package com.vorono4ka.editor;

import com.formdev.flatlaf.FlatLightLaf;
import com.vorono4ka.editor.layout.Window;
import com.vorono4ka.resources.ResourceManager;

import javax.swing.*;
import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;

public class Main {
    public static final String TITLE = "SC Editor";
    public static Editor editor;

    public static void main(String[] args) {
        Main.editor = new Editor();

        SwingUtilities.invokeLater(() -> {
            FlatLightLaf.setup();

            Window window = Main.editor.getWindow();
            window.initialize(TITLE);
            window.show();
        });

        if (args.length > 0) {
            String path = args[0];
            if (ResourceManager.doesFileExist(path)) {
                Main.editor.openFile(path);
            }
        }
    }
}
