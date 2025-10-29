package dev.donutquine.editor.layout.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class LinkLabel extends JLabel {
    public LinkLabel(String link) {
        this(link, link);
    }

    public LinkLabel(String text, String link) {
        super(String.format("<html><a href=\"#\">%s</a></html>", text));
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(link));
                } catch (URISyntaxException | IOException ignored) {

                }
            }
        });
    }
}
