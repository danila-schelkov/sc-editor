package dev.donutquine.editor.layout.panels.status;

import java.util.ArrayList;
import java.util.List;

public class TaskProgressTracker implements AutoCloseable {
    private final List<TaskProgressChanged> changeListeners = new ArrayList<>();
    private final List<TaskFinished> finishListeners = new ArrayList<>();
    private final String description;
    private final int min, max;

    private int value;

    public TaskProgressTracker(String description, int min, int max) {
        this.description = description;
        this.min = min;
        this.max = max;
    }

    public void setValue(int value) {
        if (this.value == value) return;

        this.value = value;

        changeListeners.forEach(progress -> progress.onProgressChanged(this));
    }

    public void addListener(TaskProgressChanged listener) {
        changeListeners.add(listener);
    }

    public void addListener(TaskFinished listener) {
        finishListeners.add(listener);
    }

    @Override
    public void close() {
        finishListeners.forEach(progress -> progress.onTaskFinished(this));
    }

    public String getDescription() {
        return description;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public int getValue() {
        return value;
    }
}
