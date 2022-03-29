package com.vorono4ka.swf.displayObjects;

import com.vorono4ka.editor.Main;
import com.vorono4ka.editor.renderer.Renderer;
import com.vorono4ka.swf.ColorTransform;
import com.vorono4ka.swf.Matrix2x3;

public class Shape extends DisplayObject {
    protected ShapeDrawBitmapCommand[] commands;

    @Override
    public void render(Matrix2x3 matrix, ColorTransform colorTransform, int a4, float a5) {
        System.out.println(matrix.getX());
        System.out.println(matrix.getY());

        Renderer renderer = Main.editor.getRenderer();
        for (ShapeDrawBitmapCommand command : this.commands) {
            command.render(renderer, matrix, colorTransform, this.getRenderConfigBits());
        }
    }

    @Override
    public void collisionRender(Matrix2x3 matrix, ColorTransform colorTransform) {

    }

    public ShapeDrawBitmapCommand[] getCommands() {
        return commands;
    }

    public void setCommands(ShapeDrawBitmapCommand[] commands) {
        this.commands = commands;
    }

    @Override
    public boolean isShape() {
        return true;
    }
}
