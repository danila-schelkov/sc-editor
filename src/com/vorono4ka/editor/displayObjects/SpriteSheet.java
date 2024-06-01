package com.vorono4ka.editor.displayObjects;

import com.vorono4ka.editor.Main;
import com.vorono4ka.editor.renderer.Stage;
import com.vorono4ka.math.Rect;
import com.vorono4ka.swf.ColorTransform;
import com.vorono4ka.swf.GLImage;
import com.vorono4ka.swf.Matrix2x3;
import com.vorono4ka.swf.displayObjects.DisplayObject;
import com.vorono4ka.swf.displayObjects.ShapeDrawBitmapCommand;

import java.util.List;

public class SpriteSheet extends DisplayObject {
    private final GLImage image;
    private final Rect bounds;
    private List<ShapeDrawBitmapCommand> drawBitmapCommands;

    public SpriteSheet(GLImage image) {
        this.image = image;

        float halfWidth = this.image.getWidth() / 2f;
        float halfHeight = this.image.getHeight() / 2f;

        this.bounds = new Rect(-halfWidth, -halfHeight, halfWidth, halfHeight);
    }

    @Override
    public boolean render(Matrix2x3 matrix, ColorTransform colorTransform, int a4, float deltaTime) {
        Stage stage = this.getStage();
        if (stage.renderRect(this.bounds, this.image.getTexture())) {
            if (Main.editor.shouldDisplayPolygons()) {
                for (ShapeDrawBitmapCommand drawBitmapCommand : this.drawBitmapCommands) {
                    drawBitmapCommand.renderUV(stage, 0);
                }
            }

            return true;
        }

        return false;
    }

    @Override
    public boolean collisionRender(Matrix2x3 matrix) {
        return false;
    }

    public void setBitmaps(List<ShapeDrawBitmapCommand> commands) {
        this.drawBitmapCommands = commands;
    }
}
