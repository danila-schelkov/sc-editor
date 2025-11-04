package dev.donutquine.editor.commands;

public interface UndoRedoManager {
    /// Returns boolean whether it is possible to undo further or not
    boolean undo();

    /// Returns boolean whether it is possible to redo further or not
    boolean redo();

    void addUndoableListener(UndoableListener listener);

    void addRedoableListener(RedoableListener listener);

    @FunctionalInterface
    interface UndoableListener {
        void setUndoAvailable(boolean undoable);
    }

    @FunctionalInterface
    interface RedoableListener {
        void setRedoAvailable(boolean redoable);
    }
}
