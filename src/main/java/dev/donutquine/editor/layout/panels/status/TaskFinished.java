package dev.donutquine.editor.layout.panels.status;

@FunctionalInterface
public interface TaskFinished {
    void onTaskFinished(TaskProgressTracker tracker);
}
