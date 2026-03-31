package dev.donutquine.editor.gizmos.commands;

import dev.donutquine.editor.commands.UndoableCommand;
import dev.donutquine.math.Point;
import dev.donutquine.swf.shapes.ShapeDrawBitmapCommand;

public record MoveDrawCommandPointCommand(
    ShapeDrawBitmapCommand command, int pointIndex, 
    float startX, float startY,
    float newX, float newY,
    Point controlPoint, 
    float cpStartX, float cpStartY,
    float cpNewX, float cpNewY
) implements UndoableCommand {
    @Override
    public void execute() {
        command.setXY(pointIndex, newX, newY);
        controlPoint.set(cpNewX, cpNewY);
    }

    @Override
    public void undo() {
        command.setXY(pointIndex, startX, startY);
        controlPoint.set(cpStartX, cpStartY);
    }
}
