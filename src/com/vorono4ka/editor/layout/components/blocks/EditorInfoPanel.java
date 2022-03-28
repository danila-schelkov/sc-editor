package com.vorono4ka.editor.layout.components.blocks;

import com.vorono4ka.editor.layout.components.LinkLabel;

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

            this.add(new LinkLabel("Github", "https://github.com/vorono4ka"));
        } else {
            this.add(panel);
        }

        this.revalidate();
        this.updateUI();
    }
}
