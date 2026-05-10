package dev.donutquine.editor.gizmos;

import org.jetbrains.annotations.NotNull;
import dev.donutquine.editor.commands.CommandManager;
import dev.donutquine.editor.gizmos.commands.SetDisplayObjectMatrixCommand;
import dev.donutquine.renderer.impl.swf.objects.DisplayObject;
import dev.donutquine.swf.Matrix2x3;

public class EditClipChildPositionGizmoAction implements GizmoAction {
    private final @NotNull CommandManager commandManager;
    private final @NotNull DisplayObject displayObject;
    private final @NotNull Matrix2x3 initialMatrix;
    // private final @NotNull Rect initialBounds;

    private float startX, startY;
    private Matrix2x3 newMatrix;

    public EditClipChildPositionGizmoAction(@NotNull CommandManager commandManager, @NotNull DisplayObject displayObject) {
        this.commandManager = commandManager;
        this.displayObject = displayObject;
        this.initialMatrix = displayObject.getMatrix();
        // this.initialBounds = new Rect(touchedObjectBounds);
    }

    @Override
    public void begin(float mouseX, float mouseY) {
        this.startX = mouseX;
        this.startY = mouseY;
    }

    @Override
    public void update(float mouseX, float mouseY) {
        // TODO: make it persistent — save it to the original object.
        float x = mouseX - startX;
        float y = mouseY - startY;
        // TODO: Maybe form command here already and then just push it on dragging end?
        // I could create a Matrix2x3 and store it, so I will be able to modify it and
        // it will be immediately updated in the Command instance

        // TODO: maybe display only bounds movement and leave an object as is.
        // You could also duplicate an object and render it separately
        // Perhaps should apply same optimization as below
        this.newMatrix = new Matrix2x3(this.initialMatrix);
        this.newMatrix.move(x, y);

        this.displayObject.setMatrix(newMatrix);

        // // TODO: benchmark this piece of code
        // float lastDx = this.touchedObjectBounds.getLeft() - this.initialBounds.getLeft();
        // float lastDy = this.touchedObjectBounds.getTop() - this.initialBounds.getTop();
        // this.touchedObjectBounds.movePosition(x - lastDx, y - lastDy);
        //
        // // Copying 4 floats instead of math
        // // this.touchedObjectBounds.copyFrom(this.initialBounds);
        // // this.touchedObjectBounds.movePosition(x, y);
        //
        // // Or even copying Rect class (initial idea)
        // // this.touchedObjectBounds = new Rect(initialBounds)
        // // this.touchedObjectBounds.movePosition(x, y);
    }

    @Override
    public void end() {
        if (this.newMatrix == null) return;  // maybe assert instead?
        
        // TODO: somehow undo touchedObject preview movement
        //  so I could pass it without resetting the matrix to initial
        this.displayObject.setMatrix(this.initialMatrix);
        this.commandManager.execute(new SetDisplayObjectMatrixCommand(this.displayObject, newMatrix));
    }
}
