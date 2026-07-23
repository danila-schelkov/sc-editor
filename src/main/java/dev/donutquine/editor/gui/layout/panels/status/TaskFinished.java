package dev.donutquine.editor.gui.layout.panels.status;

@FunctionalInterface
public interface TaskFinished {
    void onTaskFinished(TaskProgressTracker tracker);
}
