package com.vorono4ka.editor.layout.panels;

import com.vorono4ka.editor.layout.panels.status.TaskFinished;
import com.vorono4ka.editor.layout.panels.status.TaskProgressChanged;
import com.vorono4ka.editor.layout.panels.status.TaskProgressTracker;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StatusBar extends JPanel {
    private final List<TaskProgressTracker> taskTrackers = new ArrayList<>();

    private final JLabel progressBarLabel;
    private final JProgressBar progressBar;

    private TaskProgressTracker currentTracker;

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

        setStatus(null);
        progressBar.setVisible(false);
    }

    public void setStatus(String status) {
        if (status == null) {
            status = "Idling...";
        } else {
            System.out.println(status);
        }

        progressBarLabel.setText("Status: " + status);
    }

    public TaskProgressTracker createTaskTracker(String description, int min, int max) {
        TaskProgressTracker tracker = new TaskProgressTracker(description, min, max);
        tracker.addListener((TaskFinished) this::taskFinished);
        tracker.addListener((TaskProgressChanged) this::taskProgressChanged);
        changeTask(tracker);
        return tracker;
    }

    private void taskProgressChanged(TaskProgressTracker tracker) {
        if (currentTracker != tracker) return;

        progressBar.setValue(tracker.getValue());
    }

    private void taskFinished(TaskProgressTracker tracker) {
        taskTrackers.remove(tracker);

        if (taskTrackers.isEmpty()) {
            if (progressBar.isVisible()) {
                progressBar.setVisible(false);
            }

            changeTask(null);
        } else {
            changeTask(taskTrackers.get(taskTrackers.size() - 1));
        }
    }

    private void changeTask(TaskProgressTracker tracker) {
        if (this.currentTracker == tracker) return;

        this.currentTracker = tracker;
        progressBar.setVisible(tracker != null);

        if (tracker == null) {
            setStatus(null);
            return;
        }

        setStatus(tracker.getDescription());
        progressBar.setValue(tracker.getValue());
        progressBar.setMinimum(tracker.getMin());
        progressBar.setMaximum(tracker.getMax());
    }
}
