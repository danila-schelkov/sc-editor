package dev.donutquine.utilities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;
import dev.donutquine.editor.displayObjects.SpriteSheet;
import dev.donutquine.math.Point;
import dev.donutquine.renderer.impl.swf.objects.DisplayObject;
import dev.donutquine.swf.shapes.ShapeDrawBitmapCommand;

public class SpriteSheetHelper {
    public static List<ShapeDrawBitmapCommand> getHoveroverCommands(SpriteSheet spriteSheet, float mouseX, float mouseY) {
        BiFunction<Integer, Integer, Integer> indexTransformer = getIndexTransformer(spriteSheet);

        List<ShapeDrawBitmapCommand> hoveredCommands = new ArrayList<>();
        
        float x = mouseX / spriteSheet.getWidth() + 0.5f;
        float y = mouseY / spriteSheet.getHeight() + 0.5f;

        List<ShapeDrawBitmapCommand> commands = spriteSheet.getDrawBitmapCommands();
        for (ShapeDrawBitmapCommand command : commands) {
            if (PolygonUtils.isInside(x, y, command.getVertexCount(), command::getU, command::getV, indexTransformer)) {
                hoveredCommands.add(command);
            }
        }

        return hoveredCommands;
    }

    public static Iterable<Point> getIterableCommandPoints(SpriteSheet spriteSheet, ShapeDrawBitmapCommand command) {
        return new Iterable<Point>() {
            @Override
            public Iterator<Point> iterator() {
                return new Iterator<Point>() {
                    int index = 0;
                    int count = command.getVertexCount();
                    BiFunction<Integer, Integer, Integer> indexTransformer = getIndexTransformer(spriteSheet);

                    @Override
                    public boolean hasNext() {
                        return index <= count;
                    }

                    @Override
                    public Point next() {
                        int index = indexTransformer.apply((this.index++) % count, count);
                        return new Point((command.getU(index) - 0.5f) * spriteSheet.getWidth(), (command.getV(index) - 0.5f) * spriteSheet.getHeight());
                    }
                };
            }
        };
    }

    private static BiFunction<Integer, Integer, Integer> getIndexTransformer(DisplayObject spriteSheet) {
        return spriteSheet.isStrip() ? PolygonUtils::getStripPointIndex : (index, count) -> index;
    }
}
