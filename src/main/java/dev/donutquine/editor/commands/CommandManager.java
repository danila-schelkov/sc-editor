package dev.donutquine.editor.commands;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements UndoRedoManager {
    private final List<UndoableCommand> commands;

    // Note: Will not be reset on reset() method call
    private final List<UndoableListener> undoableListeners;
    private final List<RedoableListener> redoableListeners;

    private int currentCommandIndex;

    public CommandManager() {
        this.commands = new ArrayList<>();
        this.undoableListeners = new ArrayList<>();
        this.redoableListeners = new ArrayList<>();
        this.reset();
    }

    public void execute(Command command) {
        command.execute();
        if (command instanceof UndoableCommand undoableCommand) {
            int nextIndex = currentCommandIndex + 1;
            assert nextIndex <= commands.size();
            if (nextIndex < commands.size()) {
                commands.subList(nextIndex, commands.size()).clear();
                redoableListeners.forEach(l -> l.setRedoAvailable(false));
            }

            commands.add(undoableCommand);
            assert nextIndex == commands.size() - 1 : "Something went wrong with current command index";
            currentCommandIndex++;

            // New undoable command just added
            undoableListeners.forEach(listener -> listener.setUndoAvailable(true));
        }
    }

    @Override
    public boolean undo() {
        assert currentCommandIndex >= 0 && currentCommandIndex < commands.size() : "Undo failed due to wrong command index";
        commands.get(currentCommandIndex).undo();
        currentCommandIndex--;

        boolean undoable = currentCommandIndex >= 0;
        this.undoableListeners.forEach(l -> l.setUndoAvailable(undoable));
        this.redoableListeners.forEach(l -> l.setRedoAvailable(true));

        return undoable;
    }

    @Override
    public boolean redo() {
        // MUST NOT be called if the state is not redoable (There is no commands and command index == -1)
        assert currentCommandIndex + 1 >= 0 && currentCommandIndex + 1 < commands.size() : "Redo failed due to wrong command index";
        currentCommandIndex++;
        commands.get(currentCommandIndex).execute();

        boolean redoable = currentCommandIndex < commands.size() - 1;
        this.redoableListeners.forEach(l -> l.setRedoAvailable(redoable));
        this.undoableListeners.forEach(l -> l.setUndoAvailable(true));

        return redoable;
    }

    public void reset() {
        commands.clear();
        currentCommandIndex = -1;

        undoableListeners.forEach(consumer -> consumer.setUndoAvailable(false));
        redoableListeners.forEach(consumer -> consumer.setRedoAvailable(false));
    }

    @Override
    public void addUndoableListener(UndoableListener listener) {
        this.undoableListeners.add(listener);
    }

    @Override
    public void addRedoableListener(RedoableListener listener) {
        this.redoableListeners.add(listener);
    }
}
