package dev.donutquine.editor.layout.panels.info;

import dev.donutquine.editor.layout.components.LinkLabel;

import javax.swing.*;

public class EditorInfoPanel extends JPanel {
    private JPanel panel;

    public EditorInfoPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setPanel(null);
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setPanel(JPanel panel) {
        this.setBorder(null);
        this.removeAll();

        this.panel = panel;

        if (panel == null) {
            this.setBorder(BorderFactory.createTitledBorder("SC Editor"));

            this.add(new JLabel("Author:"));
            this.add(new LinkLabel("https://github.com/danila-schelkov"));
        } else {
            this.add(panel);
        }

        this.update();
    }

    public void update() {
        this.revalidate();
        this.updateUI();
    }
}
