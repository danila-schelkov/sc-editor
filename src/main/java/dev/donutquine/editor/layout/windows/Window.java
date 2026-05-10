package dev.donutquine.editor.layout.windows;

import javax.swing.*;
import java.awt.*;

public class Window {
    public static final Dimension MINIMUM_SIZE = new Dimension(680, 640);

    protected JFrame frame;

    public void show() {
        this.frame.pack();
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
    }

    public void close() {
        this.frame.dispose();
    }

    public void setTitle(String title) {
        this.frame.setTitle(title);
    }

    public JFrame getFrame() {
        return frame;
    }

    public void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(
            this.getFrame(),
            message,
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
}
