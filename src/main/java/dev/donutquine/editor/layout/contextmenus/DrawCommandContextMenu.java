package dev.donutquine.editor.layout.contextmenus;

import dev.donutquine.editor.Editor;
import dev.donutquine.editor.displayObjects.SpriteSheet;
import dev.donutquine.editor.layout.components.Table;
import dev.donutquine.editor.layout.components.TablePopupMenuListener;
import dev.donutquine.editor.renderer.Framebuffer;
import dev.donutquine.editor.renderer.impl.EditorStage;
import dev.donutquine.editor.renderer.impl.RendererHelper;
import dev.donutquine.math.ReadonlyRect;
import dev.donutquine.math.Rect;
import dev.donutquine.renderer.impl.swf.objects.DisplayObject;
import dev.donutquine.renderer.impl.swf.objects.Shape;
import dev.donutquine.renderer.impl.swf.objects.ShapeDrawBitmapCommandRenderer;
import dev.donutquine.swf.ColorTransform;
import dev.donutquine.swf.shapes.ShapeDrawBitmapCommand;
import dev.donutquine.utilities.ImageUtils;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.function.Function;

public class DrawCommandContextMenu extends ContextMenu {
    private static final ColorTransform EMPTY_COLOR_TRANSFORM = new ColorTransform();
    private static final Path SCREENSHOT_FOLDER = Path.of("screenshots").toAbsolutePath();

    private final Table table;
    private final Editor editor;

    public DrawCommandContextMenu(Table table, Editor editor) {
        super(table, null);

        this.table = table;
        this.editor = editor;

        JMenuItem showOnViewportButton = this.add("Show on viewport", KeyEvent.VK_V);
        showOnViewportButton.addActionListener(this::showOnViewport);

        JMenuItem showOnAtlasButton = this.add("Show on atlas", KeyEvent.VK_A);
        showOnAtlasButton.addActionListener(this::showOnAtlas);

        JMenuItem exportFromAtlasButton = this.add("Export from atlas", KeyEvent.VK_E);
        exportFromAtlasButton.addActionListener(this::exportFromAtlas);

        this.addSeparator();

        this.add("Toggle visibility", event -> this.changeVisibility(visible -> !visible));
        this.add("Enable", event -> this.changeVisibility(visible -> true));
        this.add("Disable", event -> this.changeVisibility(visible -> false));

        this.popupMenu.addPopupMenuListener(new TablePopupMenuListener(this.popupMenu, table, this::onRowSelected));
    }

    public void changeVisibility(Function<Boolean, Boolean> visibilityFunction) {
        Shape shape = this.getShape();
        if (shape == null) return;

        int[] selectedRows = this.table.getSelectedRows();
        for (int commandIndex : selectedRows) {
            boolean visible = (boolean) this.table.getValueAt(commandIndex, 3);
            boolean newVisibility = visibilityFunction.apply(visible);

            shape.setCommandVisibility(commandIndex, newVisibility);

            this.table.setValueAt(newVisibility, commandIndex, 3);
        }
    }

    private void showOnViewport(ActionEvent actionEvent) {
        Shape shape = getShape();
        if (shape == null) return;

        int commandIndex = getCommandIndex(this.table.getSelectedRow());
        ShapeDrawBitmapCommand command = shape.getCommand(commandIndex);

        Rect bounds = getDrawCommandBounds(command);

        EditorStage stage = EditorStage.getInstance();
        stage.getCamera().zoomToFit(bounds);

        stage.doInRenderThread(stage::updatePMVMatrix);
    }

    private @Nullable Shape getShape() {
        DisplayObject selectedObject = editor.getSelectedObject();
        if (selectedObject == null) return null;

        return (Shape) selectedObject;
    }

    private void showOnAtlas(ActionEvent actionEvent) {
        DisplayObject selectedObject = editor.getSelectedObject();
        if (selectedObject == null) return;

        int selectedRow = this.table.getSelectedRow();

        int commandIndex = getCommandIndex(selectedRow);
        int textureIndex = getTextureIndex(selectedRow);

        SpriteSheet spriteSheet = editor.getSpriteSheet(textureIndex);

        Shape shape = (Shape) selectedObject;
        ShapeDrawBitmapCommand command = shape.getCommand(commandIndex);

        Rect bounds = getDrawCommandTextureBounds(command, spriteSheet);

        EditorStage stage = EditorStage.getInstance();
        stage.getCamera().zoomToFit(bounds);

        stage.doInRenderThread(() -> {
            editor.selectObject(spriteSheet);
            stage.updatePMVMatrix();
        });
    }

    private void exportFromAtlas(ActionEvent actionEvent) {
        DisplayObject selectedObject = editor.getSelectedObject();
        if (selectedObject == null) return;

        int selectedRow = this.table.getSelectedRow();

        int commandIndex = getCommandIndex(selectedRow);
        int textureIndex = getTextureIndex(selectedRow);

        SpriteSheet spriteSheet = editor.getSpriteSheet(textureIndex);

        Shape shape = (Shape) selectedObject;
        ShapeDrawBitmapCommand command = shape.getCommand(commandIndex);

        Rect bounds = getDrawCommandTextureBounds(command, spriteSheet);

        EditorStage stage = EditorStage.getInstance();
        ReadonlyRect viewport = stage.getCamera().getViewport();

        stage.doInRenderThread(() -> {
            Framebuffer framebuffer = RendererHelper.prepareStageForRendering(stage, bounds);

            ShapeDrawBitmapCommandRenderer.renderUV(command, stage, EMPTY_COLOR_TRANSFORM, shape.getRenderConfigBits());
            stage.renderToFramebuffer(framebuffer);

            BufferedImage screenshot = ImageUtils.createBufferedImageFromPixels(framebuffer.getWidth(), framebuffer.getHeight(), framebuffer.getPixelArray(true), false);
            ImageUtils.saveImage(SCREENSHOT_FOLDER.resolve(shape.getId() + "_" + commandIndex + ".png"), screenshot);

            framebuffer.delete();
        });

        RendererHelper.rollbackRenderer(stage, viewport);
    }

    private static Rect getDrawCommandBounds(ShapeDrawBitmapCommand command) {
        Rect bounds = new Rect(100000, 100000, -100000, -100000);
        for (int i = 0; i < command.getVertexCount(); i++) {
            bounds.addPoint(command.getX(i), command.getY(i));
        }

        return bounds;
    }

    private static Rect getDrawCommandTextureBounds(ShapeDrawBitmapCommand command, SpriteSheet spriteSheet) {
        Rect bounds = new Rect(100000, 100000, -100000, -100000);
        for (int i = 0; i < command.getVertexCount(); i++) {
            bounds.addPoint((command.getU(i) - 0.5f) * spriteSheet.getWidth(), (command.getV(i) - 0.5f) * spriteSheet.getHeight());
        }

        return bounds;
    }

    private void onRowSelected(int rowIndex) {
        setMainComponentsEnabled(rowIndex != -1);
    }

    private int getCommandIndex(int selectedRow) {
        return (int) this.table.getValueAt(selectedRow, 0);
    }

    private int getTextureIndex(int selectedRow) {
        return (int) this.table.getValueAt(selectedRow, 1);
    }
}
