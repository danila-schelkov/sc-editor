package dev.donutquine.editor.layout.panels.status;

@FunctionalInterface
public interface TaskProgressChanged {
    void onProgressChanged(TaskProgressTracker tracker);
}
