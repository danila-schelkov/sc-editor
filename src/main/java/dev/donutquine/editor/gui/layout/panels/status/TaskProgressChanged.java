package dev.donutquine.editor.gui.layout.panels.status;

@FunctionalInterface
public interface TaskProgressChanged {
    void onProgressChanged(TaskProgressTracker tracker);
}
