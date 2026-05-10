package dev.donutquine.editor.gizmos;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dev.donutquine.editor.commands.CommandManager;
import dev.donutquine.editor.commands.UndoRedoManager;
import dev.donutquine.editor.displayObjects.SpriteSheet;
import dev.donutquine.editor.gizmos.drawables.WireframeGizmo;
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
import dev.donutquine.utilities.SpriteSheetHelper;

public class Gizmos implements UndoRedoManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(Gizmos.class);

    private static final int HANDLE_SIZE = 10;

    private final Stage stage;
    private final StageSprite stageSprite;

    // TODO: pass command manager from within the editor,
    //  so all commands (action) history is shared between everything in the app
    private final CommandManager commandManager;

    private final List<GizmoDrawable> drawables = new ArrayList<>();
    private final List<GizmoHandle> handles = new ArrayList<>();

    private @Nullable CursorStateListener cursorStateListener;

    private Renderer renderer;
    private DrawApi drawApi;
    // Mouse position in a world (on stage)
    private float mouseX, mouseY;
    private GizmoHandle hoveredHandle;

    private boolean dragging;

    private @Nullable DisplayObject touchedObject;
    // TODO: may be moving, it would be better to stop animation or not allow editing until stopped by user
    private Rect touchedObjectBounds;

    public Gizmos(Stage stage) {
        this.stage = stage;
        this.stageSprite = stage.getStageSprite();
        this.commandManager = new CommandManager();

        this.reset();
    }

    public void setRenderer(Renderer renderer, DrawApi drawApi) {
        this.renderer = renderer;
        this.drawApi = new GizmoDrawApi(drawApi, stage);
    }

    public void setCursorListener(CursorStateListener listener) {
        this.cursorStateListener = listener;
    }

    public void setMousePosition(float x, float y) {
        this.mouseX = x;
        this.mouseY = y;
    }

    public void setMousePressed(boolean mousePressed) {
        CursorType cursor = CursorType.DEFAULT_CURSOR;
        if (this.touchedObject != null) {
            if (mousePressed && this.hoveredHandle != null && !this.dragging) {
                this.dragging = true;

                this.hoveredHandle.action().begin(this.mouseX, this.mouseY);

                cursor = CursorType.MOVE_CURSOR;
            } else if (!mousePressed) {
                if (this.dragging) {
                    assert this.hoveredHandle != null; 
                    this.hoveredHandle.action().end();
                    this.hoveredHandle = null;
                }

                this.dragging = false;
            }
        }

        if (this.cursorStateListener != null) {
            this.cursorStateListener.setCursor(cursor);
        }
    }

    public void mouseClicked(float worldX, float worldY, int clickCount) {
        DisplayObject rootObject = this.stageSprite;
        // Note: it seems Figma actually not only allows to select between current object and its siblings, but rather all elements above it (with lesser depth). 
        //  So should I also track depth?

        DisplayObject touchedObject = null;
        Rect bounds = null;

        // FIXME: There is a bug in animations, caused by non-static Stage instance.
        //  When you spam-click on animated object, which may be removed next frame from
        //  its parent, NPE will be thrown since its parent set to null.
        //  As a potential fix just stop all animations if an object selected.
        if (this.touchedObject != null && this.touchedObject.getStage() != null && this.touchedObjectBounds.containsPoint(worldX, worldY)) {
            rootObject = this.touchedObject;
            touchedObject = rootObject;

            if (!rootObject.isSprite() && !rootObject.isShape()) {
                return;
            }
        }

        if (clickCount >= 2) {
            if (rootObject.isSprite()) {
                Sprite sprite = (Sprite) rootObject;

                int childrenCount = sprite.getChildrenCount();
                for (int i = childrenCount - 1; i >= 0; i--) {
                    DisplayObject child = sprite.getChild(i);

                    LOGGER.debug("{}", child);
                    // FIXME: Shape9Slice gives wrong bounds because parent's matrix is not included in calculation
                    bounds = this.stage.getDisplayObjectBounds(child);

                    LOGGER.debug("{} {} at {}, {}", child, bounds, worldX, worldY);
                    if (bounds.containsPoint(worldX, worldY)) {
                        touchedObject = child;
                        break;
                    }
                }
            // FIXME: Doesn't work properly for Shape9Slice because parent's matrix is not included in calculation
            } else if (this.touchedObject.isShape()) {
                this.drawables.clear();
                this.handles.clear();
                Shape shape = (Shape) this.touchedObject;

                // Note: Identity matrix for standalone Shape object, maybe shouldn't allow to edit points in MovieClips (?)
                Matrix2x3 matrix = getObjectFinalMatrix(shape);
                Matrix2x3 inverseMatrix = new Matrix2x3(matrix);
                inverseMatrix.inverse();

                boolean useStrip = shape.isStrip();

                for (int i = 0; i < shape.getCommandCount(); i++) {
                    ShapeDrawBitmapCommand command = shape.getCommand(i);
                    assert command.getVertexCount() >= 3 : "Obviously, incorrect draw command with less than 3 points (not even a triangle)";

                    List<Point> points = new ArrayList<>(command.getVertexCount());
                    for (int j = 0; j < command.getVertexCount(); j++) {
                        float x = command.getX(j);
                        float y = command.getY(j);
                        Point point = new Point(matrix.applyX(x, y), matrix.applyY(x, y));
                        points.add(point);
                        this.handles.add(new GizmoHandle(point, new EditDrawCommandPointGizmoAction(this.commandManager, command, j, inverseMatrix, point)));
                    }

                    this.drawables.add(new WireframeGizmo(points, useStrip));
                }
                return;
            // TODO: make it to edit TextField content when font renderer will be introduced
            } else {
                assert false : "Not implemented yet";
            }
        }

        boolean targetChanged = this.touchedObject != touchedObject;

        this.touchedObject = touchedObject;

        if (targetChanged) {
            this.touchedObjectBounds = bounds;

            this.drawables.clear();
            this.handles.clear();
        }

        LOGGER.debug("{} at {}, {}", touchedObject, worldX, worldY);
    }

    public void reset() {
        this.touchedObject = null;
        this.touchedObjectBounds = null;

        this.hoveredHandle = null;

        this.drawables.clear();
        this.handles.clear();
        this.commandManager.reset();

        if (this.cursorStateListener != null) {
            this.cursorStateListener.setCursor(CursorType.DEFAULT_CURSOR);
        }
    }

    public void render() {
        this.renderer.beginRendering();

        for (GizmoDrawable drawable : this.drawables) {
            drawable.draw(this.drawApi);
        }

        this.renderHandles();

        CursorType cursor = CursorType.DEFAULT_CURSOR;
        if (this.hoveredHandle != null) {
            cursor = CursorType.HAND_CURSOR;

            if (dragging) {
                cursor = CursorType.MOVE_CURSOR;

                this.hoveredHandle.action().update(this.mouseX, this.mouseY);
            }
        }

        if (cursorStateListener != null) {
            cursorStateListener.setCursor(cursor);
        }

        DisplayObject rootObject = this.touchedObject == null ? this.stageSprite : this.touchedObject;
        if (rootObject.isSprite()) {
            Sprite sprite = (Sprite) rootObject;

            int childrenCount = sprite.getChildrenCount();
            for (int i = childrenCount - 1; i >= 0; i--) {
                DisplayObject child = sprite.getChild(i);
                if (child.getStage() == null) continue;

                // FIXME: Shape9Slice gives wrong bounds because parent's matrix is not included in calculation
                Rect bounds = this.stage.getDisplayObjectBounds(child);

                if (bounds.containsPoint(this.mouseX, this.mouseY)) {
                    this.drawApi.drawRectangleLines(bounds, Color.WHITE, 1);
                    break;
                }
            }
        }

        // TODO: move somewhere else
        if (this.touchedObject != null) {
            this.drawApi.drawRectangleLines(touchedObjectBounds, Color.WHITE, 1);
        }

        // TODO: move somewhere else
        float thickness = 4;
        for (int i = 0; i < this.stageSprite.getChildrenCount(); i++) {
            DisplayObject child = this.stageSprite.getChild(i);
            if (child instanceof SpriteSheet spriteSheet) {
                List<ShapeDrawBitmapCommand> hoveroverCommands = SpriteSheetHelper.getHoveroverCommands(spriteSheet, this.mouseX, this.mouseY);
                if (hoveroverCommands.size() > 0) {
                    // Note: these commands are exact same, but used in a different Shapes, so should be deduplicated.
                    // assert hoveroverCommands.size() == 1 : "Oh no...";

                    ShapeDrawBitmapCommand hoveroverCommand = hoveroverCommands.get(0);
                    // for (ShapeDrawBitmapCommand hoveroverCommand : hoveroverCommands) {
                    Iterable<Point> points = SpriteSheetHelper.getIterableCommandPoints(spriteSheet, hoveroverCommand);

                    this.drawApi.drawDashedPath(points, thickness, 20, Color.WHITE);
                    // }

                    break;
                }
            }
        }

        this.renderer.endRendering();
    }

    private void renderHandles() {
        float size = HANDLE_SIZE / stage.getPixelSize();

        GizmoHandle hoveredHandle = null;
        if (this.dragging) {
            hoveredHandle = this.hoveredHandle;
        }

        for (GizmoHandle handle : this.handles) {
            float left = handle.position().getX() - size / 2;
            float top = handle.position().getY() - size / 2;
            Rect bounds = new Rect(left - size * 0.2f, top - size * 0.2f, left + size * 1.2f, top + size * 1.2f);
            Rect innerRect = new Rect(left, top, left + size, top + size);

            // Note: hit-test
            if (bounds.containsPoint(mouseX, mouseY) && hoveredHandle == null && !dragging) {
                hoveredHandle = handle;
            }

            Color color = Color.WHITE;
            if (hoveredHandle == handle) {
                color = Color.RED;
            }

            this.drawApi.drawRectangle(bounds, Color.BLACK);
            this.drawApi.drawRectangle(innerRect, color);
        }

        this.hoveredHandle = hoveredHandle;
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

    /// Returns a new matrix, composed of {@link DisplayObject} matrices.
    private static Matrix2x3 getObjectFinalMatrix(DisplayObject displayObject) {
        Matrix2x3 matrix = new Matrix2x3(displayObject.getMatrix());
        DisplayObject parent = displayObject.getParent();
        while (parent != null) {
            // Oh, so it's commutative?
            matrix.multiply(parent.getMatrix());
            parent = parent.getParent();
        }

        return matrix;
    }
}
