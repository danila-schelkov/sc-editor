package dev.donutquine.editor.gizmos;

import org.jetbrains.annotations.NotNull;
import dev.donutquine.editor.commands.CommandManager;
import dev.donutquine.editor.gizmos.commands.MoveDrawCommandPointCommand;
import dev.donutquine.math.Point;
import dev.donutquine.swf.Matrix2x3;
import dev.donutquine.swf.shapes.ShapeDrawBitmapCommand;

public class EditDrawCommandPointGizmoAction extends AbstractGizmoAction {
    private final @NotNull CommandManager commandManager;
    private final @NotNull ShapeDrawBitmapCommand drawCommand;
    private final int pointIndex;
    private final @NotNull Matrix2x3 inverseMatrix;
    private final @NotNull Point controlPoint;

    private float cpStartX, cpStartY;
    private float startX, startY;
    private float newX, newY;

    public EditDrawCommandPointGizmoAction(@NotNull CommandManager commandManager, @NotNull ShapeDrawBitmapCommand drawCommand, int pointIndex, @NotNull Matrix2x3 inverseMatrix, @NotNull Point controlPoint) {
        this.commandManager = commandManager;
        this.drawCommand = drawCommand;
        this.pointIndex = pointIndex;
        this.inverseMatrix = inverseMatrix;
        this.controlPoint = controlPoint;
    }

    @Override
    public void begin(float mouseX, float mouseY) {
        super.begin(mouseX, mouseY);

        this.cpStartX = this.controlPoint.getX();
        this.cpStartY = this.controlPoint.getY();

        float x = this.drawCommand.getX(this.pointIndex);
        float y = this.drawCommand.getY(this.pointIndex);
        this.startX = x;
        this.startY = y;
    }

    @Override
    public void update(float mouseX, float mouseY) {
        super.update(mouseX, mouseY);

        this.controlPoint.set(mouseX, mouseY);

        newX = this.inverseMatrix.applyX(mouseX, mouseY);
        newY = this.inverseMatrix.applyY(mouseX, mouseY);
        this.drawCommand.setXY(this.pointIndex, newX, newY);
    }

    @Override
    public void end() {
        this.commandManager.execute(new MoveDrawCommandPointCommand(
            drawCommand, pointIndex, 
            this.startX, this.startY, newX, newY, 
            // Note: I don't like that I put controlPoint in that command. It will become redundant since object selection will be changed
            controlPoint, 
            this.cpStartX, this.cpStartY, this.mouseX, this.mouseY
        ));
    }
}
