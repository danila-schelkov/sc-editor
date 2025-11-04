package dev.donutquine.editor.commands;

/// Certainly not every command may be undoable, so I left an ability to add non-undoable commands to the {@link CommandManager}
public interface UndoableCommand extends Command {
    void undo();
}
