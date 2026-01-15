package dev.donutquine.editor.gizmos.commands;

import dev.donutquine.editor.commands.UndoableCommand;
import dev.donutquine.renderer.impl.swf.objects.DisplayObject;
import org.jetbrains.annotations.NotNull;

@Deprecated(since="Not idempotent, not sure if it is a good way doing it like this for Undo/Redo commands.")
// Note: implemented as delta description, but maybe I have to reimplement it as idempotent command.
public record MoveDisplayObjectCommand(@NotNull DisplayObject object, float dx, float dy) implements UndoableCommand {
    @Override
    public void execute() {
        object.getMatrix().move(dx, dy);
    }

    @Override
    public void undo() {
        object.getMatrix().move(-dx, -dy);
    }
}
