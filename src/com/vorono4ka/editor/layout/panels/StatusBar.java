package com.vorono4ka.editor.layout.panels;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StatusBar extends JPanel {
    private final JLabel progressBarLabel;
    private final JProgressBar progressBar;

    public StatusBar() {
        super(new FlowLayout(FlowLayout.LEFT));

        setBorder(new EmptyBorder(0, 0, 0, 0));

        progressBarLabel = new JLabel();
        progressBar = new JProgressBar();

        initComponents();
    }

    private void initComponents() {
        add(progressBarLabel);
        add(progressBar);

        setStatus("Test");
        progressBar.setVisible(false);
    }

    public void setStatus(String status) {
        progressBarLabel.setText("Status: " + status);
    }

    public void createProgressBarTask(int value, int min, int max) {
        if (!progressBar.isVisible()) {
            progressBar.setVisible(true);
        }

        progressBar.setMinimum(min);
        progressBar.setMaximum(max);
        progressBar.setValue(value);
    }

    public void removeProgressBarTask() {
        if (progressBar.isVisible()) {
            progressBar.setVisible(false);
        }
    }
}
