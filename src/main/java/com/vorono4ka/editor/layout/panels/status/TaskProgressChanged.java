package com.vorono4ka.editor.layout.panels.status;

@FunctionalInterface
public interface TaskProgressChanged {
    void onProgressChanged(TaskProgressTracker tracker);
}
