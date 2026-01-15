package dev.donutquine.editor.gizmos;

import dev.donutquine.editor.commands.CommandManager;
import dev.donutquine.editor.commands.UndoRedoManager;
import dev.donutquine.editor.gizmos.commands.MoveDrawCommandPointCommand;
import dev.donutquine.editor.gizmos.commands.SetDisplayObjectMatrixCommand;
import dev.donutquine.editor.layout.cursor.CursorStateListener;
import dev.donutquine.editor.layout.cursor.CursorType;
import dev.donutquine.editor.renderer.DrawApi;
import dev.donutquine.editor.renderer.Renderer;
import dev.donutquine.editor.renderer.Stage;
import dev.donutquine.math.Point;
import dev.donutquine.math.Rect;
import dev.donutquine.renderer.impl.swf.objects.DisplayObject;
import dev.donutquine.renderer.impl.swf.objects.Shape;
import dev.donutquine.renderer.impl.swf.objects.Sprite;
import dev.donutquine.renderer.impl.swf.objects.StageSprite;
import dev.donutquine.swf.Matrix2x3;
import dev.donutquine.swf.shapes.ShapeDrawBitmapCommand;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class Gizmos implements UndoRedoManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(Gizmos.class);

    private final Stage stage;
    private final StageSprite stageSprite;

    // TODO: pass command manager from within the editor,
    //  so all commands (action) history is shared between everything in the app
    private final CommandManager commandManager;

    private @Nullable CursorStateListener cursorStateListener;

    private Renderer renderer;
    private DrawApi drawApi;
    private float mouseX, mouseY;
    private boolean mousePressed;
    private ShapeDrawBitmapCommand targetShapeDrawCommand;
    private int pointIndex;

    // Note: cannot use mouse dx, dy due to inaccuracy
    private float startX, startY;
    private boolean dragging;
    private Rect initialBounds;
    private Matrix2x3 initialMatrix;

    // TODO: add a keyboard shortcut to switch between allowed modes
    private Mode mode;

    private @Nullable DisplayObject touchedObject;
    // TODO: may be moving, it would be better to stop animation since EDIT_POINTS turned on ig
    private Rect touchedObjectBounds;

    public Gizmos(Stage stage) {
        this.stage = stage;
        this.stageSprite = stage.getStageSprite();
        this.commandManager = new CommandManager();

        this.reset();
    }

    public void setRenderer(Renderer renderer, DrawApi drawApi) {
        this.renderer = renderer;
        this.drawApi = drawApi;
    }

    public void setCursorListener(CursorStateListener listener) {
        this.cursorStateListener = listener;
    }

    public void setMousePosition(float x, float y) {
        this.mouseX = x;
        this.mouseY = y;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
        CursorType cursor = CursorType.DEFAULT_CURSOR;
        if (this.touchedObject != null) {
            if (mousePressed && this.targetShapeDrawCommand != null && !this.dragging) {
                this.startX = this.targetShapeDrawCommand.getX(this.pointIndex);
                this.startY = this.targetShapeDrawCommand.getY(this.pointIndex);
                this.dragging = true;

                cursor = CursorType.MOVE_CURSOR;
            } else if (mousePressed && this.touchedObjectBounds.containsPoint(this.mouseX, this.mouseY) && !this.dragging) {
                this.initialMatrix = this.touchedObject.getMatrix();
                this.initialBounds = new Rect(this.touchedObjectBounds);

                this.startX = this.mouseX;
                this.startY = this.mouseY;
                this.dragging = true;

                cursor = CursorType.MOVE_CURSOR;
            } else if (!mousePressed) {
                if (this.dragging) {
                    // Note: It is forbidden to move object while you're editing points.
                    if (this.touchedObject.isShape() && this.mode == Mode.EDIT_POINTS) {
                        if (this.targetShapeDrawCommand != null) {
                            Matrix2x3 inverseMatrix = getObjectFinalMatrix(this.touchedObject);
                            inverseMatrix.inverse();
                            float x = mouseX;
                            float y = mouseY;
                            this.commandManager.execute(new MoveDrawCommandPointCommand(targetShapeDrawCommand,
                                pointIndex,
                                this.startX,
                                this.startY,
                                inverseMatrix.applyX(x, y),
                                inverseMatrix.applyY(x, y)));
                        }
                    } else {
                        float dx = mouseX - startX;
                        float dy = mouseY - startY;
                        Matrix2x3 newMatrix = new Matrix2x3(this.initialMatrix);
                        newMatrix.move(dx, dy);

                        // TODO: somehow undo touchedObject preview movement
                        //  so I could pass it without resetting the matrix to initial
                        this.touchedObject.setMatrix(this.initialMatrix);
                        this.commandManager.execute(new SetDisplayObjectMatrixCommand(touchedObject, newMatrix));
                    }
                }

                this.initialMatrix = null;
                this.initialBounds = null;
                this.dragging = false;
            }
        }

        if (this.cursorStateListener != null) {
            this.cursorStateListener.setCursor(cursor);
        }
    }

    public void mouseClicked(float worldX, float worldY, int clickCount) {
        DisplayObject rootObject = this.stageSprite;
        // TODO: improve it, so I could go to any depth like in Figma
        if (this.touchedObject != null && clickCount >= 2) {
            if (this.touchedObject.isShape()) {
                this.mode = Mode.EDIT_POINTS;
                return;
            // FIXME: There is a bug in animations, caused by non-static Stage instance.
            //  When you spam-click on animated object, which may be removed next frame from its parent,
            //  NPE will be thrown since its parent set to null.
            //  As a potential fix just stop all animations if an object selected.
            } else if (this.touchedObject.isSprite() && this.touchedObject.getStage() != null) {
                rootObject = this.touchedObject;
            } else if (this.touchedObjectBounds.containsPoint(worldX, worldY)) {
                return;
            }
        }

        DisplayObject touchedObject = null;
        Rect bounds = null;

        if (rootObject.isSprite()) {
            Sprite sprite = (Sprite) rootObject;

            int childrenCount = sprite.getChildrenCount();
            for (int i = childrenCount - 1; i >= 0; i--) {
                DisplayObject child = sprite.getChild(i);
                bounds = this.stage.getDisplayObjectBounds(child);

                if (bounds.containsPoint(worldX, worldY)) {
                    touchedObject = child;
                    break;
                }
            }
        // TODO: make it to edit TextField content when font renderer will be introduced
        } else {
            assert false : "Not implemented yet";
        }

        boolean targetChanged = this.touchedObject != touchedObject;

        this.touchedObject = touchedObject;
        this.touchedObjectBounds = bounds;

        if (targetChanged) {
            this.mode = Mode.DEFAULT;
            this.targetShapeDrawCommand = null;
            this.pointIndex = -1;
        }

        if (touchedObject != null) {
            LOGGER.info("{} at {}, {}", touchedObject, worldX, worldY);
        }
    }

    public void reset() {
        this.touchedObject = null;
        this.touchedObjectBounds = null;

        this.targetShapeDrawCommand = null;
        this.pointIndex = -1;

        this.commandManager.reset();

        this.mode = Mode.DEFAULT;

        if (this.cursorStateListener != null) {
            this.cursorStateListener.setCursor(CursorType.DEFAULT_CURSOR);
        }
    }

    public void render() {
        this.renderer.beginRendering();

        switch (this.mode) {
            case EDIT_POINTS -> {
                if (touchedObject != null && touchedObject.isShape()) {
                    this.drawShapeWireframe((Shape) touchedObject);
                }
            }
            default -> {
                if (touchedObject != null) {
                    this.drawApi.drawRectangleLines(touchedObjectBounds, Color.WHITE, 1);

                    if (this.dragging) {
                        // TODO: make it persistent — save it to the original object.
                        float x = mouseX - startX;
                        float y = mouseY - startY;
                        // TODO: Maybe form command here already and then just push it on dragging end?
                        //  I could create a Matrix2x3 and store it, so I will be able to modify it and
                        //  it will be immediately updated in the Command instance

                        // TODO: maybe display only bounds movement and leave an object as is.
                        //  You could also duplicate an object and render it separately
                        // Perhaps should apply same optimization as below
                        this.touchedObject.setMatrix(new Matrix2x3(this.initialMatrix));
                        this.touchedObject.getMatrix().move(x, y);

                        // TODO: benchmark this piece of code
                        float lastDx = this.touchedObjectBounds.getLeft() - this.initialBounds.getLeft();
                        float lastDy = this.touchedObjectBounds.getTop() - this.initialBounds.getTop();
                        this.touchedObjectBounds.movePosition(x - lastDx, y - lastDy);

                        // Copying 4 floats instead of math
                        // this.touchedObjectBounds.copyFrom(this.initialBounds);
                        // this.touchedObjectBounds.movePosition(x, y);

                        // Or even copying Rect class (initial idea)
                        // this.touchedObjectBounds = new Rect(initialBounds)
                        // this.touchedObjectBounds.movePosition(x, y);
                    }
                }
            }
        }

        this.renderer.endRendering();
    }

    public static void drawCommandWireframe(DrawApi drawApi, ShapeDrawBitmapCommand command, Matrix2x3 matrix, Color wireframeColor, float thickness, boolean useStrip) {
        if (command.getVertexCount() < 3) return;

        Point p1 = new Point(command.getX(0), command.getY(0));
        Point p2 = new Point(command.getX(1), command.getY(1));

        drawApi.drawLine(matrix.apply(p1), matrix.apply(p2), thickness, wireframeColor);

        for (int j = 2; j < command.getVertexCount(); j++) {
            p1 = new Point(command.getX(j), command.getY(j));
            p2 = new Point(command.getX(useStrip ? j - 2 : 0), command.getY(useStrip ? j - 2 : 0));

            drawApi.drawLine(matrix.apply(p1), matrix.apply(p2), thickness, wireframeColor);
            p2 = new Point(command.getX(j - 1), command.getY(j - 1));
            drawApi.drawLine(matrix.apply(p1), matrix.apply(p2), thickness, wireframeColor);
        }
    }

    private void drawShapeWireframe(Shape shape) {
        Color wireframeColor = Color.RED;

        float pixelSize = 1 / stage.getPixelSize();
        float thickness = 4 * pixelSize;

        // Note: Identity matrix for standalone Shape object, maybe shouldn't allow to edit points in MovieClips (?)
        Matrix2x3 matrix = getObjectFinalMatrix(shape);

        boolean useStrip = (shape.getRenderConfigBits() & 0x8000) != 0;

        for (int i = 0; i < shape.getCommandCount(); i++) {
            drawCommandWireframe(drawApi, shape.getCommand(i), matrix, wireframeColor, thickness, useStrip);
        }

        if (!dragging) {
            targetShapeDrawCommand = null;
            pointIndex = -1;
        }

        float size = 10 * pixelSize;
        for (int i = 0; i < shape.getCommandCount(); i++) {
            ShapeDrawBitmapCommand command = shape.getCommand(i);
            if (command.getVertexCount() < 3) continue;
            for (int j = 0; j < command.getVertexCount(); j++) {
                float x = matrix.applyX(command.getX(j), command.getY(j));
                float y = matrix.applyY(command.getX(j), command.getY(j));
                float left = x - size / 2;
                float top = y - size / 2;
                Rect bounds = new Rect(left - size * 0.2f, top - size * 0.2f, left + size * 1.2f, top + size * 1.2f);
                Rect innerRect = new Rect(left, top, left + size, top + size);

                Color color = Color.WHITE;
                if (bounds.containsPoint(mouseX, mouseY) && targetShapeDrawCommand == null && !dragging) {
                    color = Color.RED;
                    targetShapeDrawCommand = command;
                    pointIndex = j;
                }

                this.drawApi.drawRectangle(bounds, Color.BLACK);
                this.drawApi.drawRectangle(innerRect, color);
            }
        }

        CursorType cursor = CursorType.DEFAULT_CURSOR;
        if (pointIndex != -1) {
            cursor = CursorType.HAND_CURSOR;

            if (dragging) {
                cursor = CursorType.MOVE_CURSOR;

                // Not used after so no need to copy it
                //noinspection UnnecessaryLocalVariable
                Matrix2x3 inverseMatrix = matrix;
                inverseMatrix.inverse();
                this.targetShapeDrawCommand.setXY(pointIndex, inverseMatrix.applyX(mouseX, mouseY), inverseMatrix.applyY(mouseX, mouseY));
            }
        }

        if (cursorStateListener != null) {
            cursorStateListener.setCursor(cursor);
        }
    }

    // TODO: invalidate and recalculate bounds
    public boolean undo() {
        return this.commandManager.undo();
    }

    // TODO: invalidate and recalculate bounds
    public boolean redo() {
        return this.commandManager.redo();
    }

    @Override
    public void addUndoableListener(UndoableListener listener) {
        this.commandManager.addUndoableListener(listener);
    }

    @Override
    public void addRedoableListener(RedoableListener listener) {
        this.commandManager.addRedoableListener(listener);
    }

    public enum Mode {
        DEFAULT, EDIT_POINTS,
    }

    /// Returns a new matrix, composed of {@link DisplayObject} matrices.
    private static Matrix2x3 getObjectFinalMatrix(DisplayObject shape) {
        Matrix2x3 matrix = new Matrix2x3(shape.getMatrix());
        DisplayObject parent = shape.getParent();
        while (parent != null) {
            // Oh, so it's commutative?
            matrix.multiply(parent.getMatrix());
            parent = parent.getParent();
        }

        return matrix;
    }
}
