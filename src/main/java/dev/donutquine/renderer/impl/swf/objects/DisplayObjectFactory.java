package dev.donutquine.renderer.impl.swf.objects;

import dev.donutquine.editor.assets.TextureAsset;
import dev.donutquine.math.ReadonlyRect;
import dev.donutquine.swf.DisplayObjectOriginal;
import dev.donutquine.swf.SupercellSWF;
import dev.donutquine.swf.exceptions.UnableToFindObjectException;
import dev.donutquine.swf.movieclips.MovieClipModifierOriginal;
import dev.donutquine.swf.movieclips.MovieClipOriginal;
import dev.donutquine.swf.shapes.ShapeOriginal;
import dev.donutquine.swf.textfields.TextFieldOriginal;

public class DisplayObjectFactory {
    public static DisplayObject createFromOriginal(DisplayObjectOriginal original, SupercellSWF swf, ReadonlyRect scalingGrid, TextureAsset textureAsset) throws UnableToFindObjectException {
        if (original instanceof MovieClipModifierOriginal movieClipModifierOriginal) {
            return MovieClipModifier.createModifier(movieClipModifierOriginal);
        }

        if (original instanceof ShapeOriginal shapeOriginal) {
            if (scalingGrid != null) {
                Shape9Slice shape = Shape9Slice.createShape(shapeOriginal, textureAsset, scalingGrid);
                shape.setTriangleFunction(swf.getContainerVersion() >= 5);
                return shape;
            }

            Shape shape = Shape.createShape(shapeOriginal, textureAsset);
            shape.setTriangleFunction(swf.getContainerVersion() >= 5);
            return shape;
        }

        if (original instanceof MovieClipOriginal movieClipOriginal) {
            return MovieClip.createMovieClip(movieClipOriginal, swf, textureAsset);
        }

        if (original instanceof TextFieldOriginal textFieldOriginal) {
            return TextField.createTextField(textFieldOriginal);
        }

        throw new IllegalStateException("Unexpected original object: " + original);
    }
}
