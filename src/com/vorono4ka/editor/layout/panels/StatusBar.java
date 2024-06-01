package com.vorono4ka.editor.layout.panels;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StatusBar extends JPanel {
    private JLabel progressBarLabel;
    private JProgressBar progressBar;

    public StatusBar() {
        super(new FlowLayout(FlowLayout.LEFT));

        setBorder(new EmptyBorder(0, 0, 0, 0));

        initComponents();
    }

    private void initComponents() {
        progressBarLabel = new JLabel("Status: Test");
        add(progressBarLabel);

        progressBar = new JProgressBar();
        progressBar.setVisible(false);
        add(progressBar);
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
