package com.vorono4ka.editor.layout.panels.status;

@FunctionalInterface
public interface TaskFinished {
    void onTaskFinished(TaskProgressTracker tracker);
}
