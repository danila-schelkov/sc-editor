package dev.donutquine.editor.gizmos.commands;

import dev.donutquine.editor.commands.UndoableCommand;
import dev.donutquine.swf.shapes.ShapeDrawBitmapCommand;

public record MoveDrawCommandPointCommand(ShapeDrawBitmapCommand command,
                                          int pointIndex, float startX, float startY,
                                          float newX,
                                          float newY) implements UndoableCommand {
    @Override
    public void execute() {
        command.setXY(pointIndex, newX, newY);
    }

    @Override
    public void undo() {
        command.setXY(pointIndex, startX, startY);
    }
}
