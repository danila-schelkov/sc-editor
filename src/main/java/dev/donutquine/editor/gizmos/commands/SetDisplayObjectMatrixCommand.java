package dev.donutquine.editor.gizmos.commands;

import dev.donutquine.editor.commands.UndoableCommand;
import dev.donutquine.renderer.impl.swf.objects.DisplayObject;
import dev.donutquine.swf.Matrix2x3;
import org.jetbrains.annotations.NotNull;

public final class SetDisplayObjectMatrixCommand implements UndoableCommand {
    private final @NotNull DisplayObject object;
    private final @NotNull Matrix2x3 initialMatrix;
    private final @NotNull Matrix2x3 newMatrix;

    public SetDisplayObjectMatrixCommand(@NotNull DisplayObject object, @NotNull Matrix2x3 newMatrix) {
        this.object = object;
        this.newMatrix = newMatrix;

        // Note: this command is not a record to make this statement possible.
        // I don't want to enable the developer to pass the wrong initialMatrix,
        // and records just doesn't allow you to have an extra field.
        this.initialMatrix = this.object.getMatrix();
    }

    @Override
    public void execute() {
        object.setMatrix(newMatrix);
    }

    @Override
    public void undo() {
        object.setMatrix(initialMatrix);
    }

    @Override
    public String toString() {
        return "UpdateDisplayObjectMatrixCommand[" +
            "object=" + object + ", " +
            "newMatrix=" + newMatrix + ']';
    }
}
