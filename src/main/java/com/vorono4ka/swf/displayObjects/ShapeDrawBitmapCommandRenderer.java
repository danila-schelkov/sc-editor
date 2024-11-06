package com.vorono4ka.swf.displayObjects;

import com.vorono4ka.editor.renderer.Stage;
import com.vorono4ka.editor.renderer.texture.GLImage;
import com.vorono4ka.math.Rect;
import com.vorono4ka.swf.ColorTransform;
import com.vorono4ka.swf.Matrix2x3;
import com.vorono4ka.swf.originalObjects.ShapeDrawBitmapCommand;

public class ShapeDrawBitmapCommandRenderer {
    public static boolean render(ShapeDrawBitmapCommand command, Stage stage, Matrix2x3 matrix, ColorTransform colorTransform, int renderConfigBits) {
        Rect bounds = new Rect();

        float[] transformedPoints = new float[command.getVertexCount() * 2];
        for (int i = 0; i < command.getVertexCount(); i++) {
            float x = matrix.applyX(command.getX(i), command.getY(i));
            float y = matrix.applyY(command.getX(i), command.getY(i));

            transformedPoints[i * 2] = x;
            transformedPoints[i * 2 + 1] = y;

            if (i == 0) {
                bounds = new Rect(x, y, x, y);
                continue;
            }

            bounds.addPoint(x, y);
        }

        int[] indices = command.getIndices();

        GLImage image = stage.getImageByIndex(command.getTextureIndex());

        if (stage.startShape(bounds, image.getTexture(), renderConfigBits)) {
            stage.addTriangles(command.getTriangleCount(), indices);

            float redMultiplier = colorTransform.getRedMultiplier() / 255f;
            float greenMultiplier = colorTransform.getGreenMultiplier() / 255f;
            float blueMultiplier = colorTransform.getBlueMultiplier() / 255f;
            float redAddition = colorTransform.getRedAddition() / 255f;
            float greenAddition = colorTransform.getGreenAddition() / 255f;
            float blueAddition = colorTransform.getBlueAddition() / 255f;
            float alpha = colorTransform.getAlpha() / 255f;

            for (int i = 0; i < command.getVertexCount(); i++) {
                stage.addVertex(
                    transformedPoints[i * 2],
                    transformedPoints[i * 2 + 1],
                    command.getU(i),
                    command.getV(i),
                    redMultiplier,
                    greenMultiplier,
                    blueMultiplier,
                    alpha,
                    redAddition,
                    greenAddition,
                    blueAddition
                );
            }

            return true;
        }

        return false;
    }

    public static boolean render9Slice(ShapeDrawBitmapCommand command, Stage stage, Matrix2x3 matrix, ColorTransform colorTransform, int renderConfigBits, Rect safeArea, Rect shapeBounds, float width, float height) {
        Rect bounds = new Rect();

        float[] transformedPoints = new float[command.getVertexCount() * 2];
        for (int i = 0; i < command.getVertexCount(); i++) {
            float x = command.getX(i);
            if (x <= safeArea.getLeft()) {
                x = Math.min(safeArea.getMidX(), shapeBounds.getLeft() + (x - shapeBounds.getLeft()) * width);
            } else if (x >= safeArea.getRight()) {
                x = Math.max(safeArea.getMidX(), shapeBounds.getRight() + (x - shapeBounds.getRight()) * width);
            }

            float y = command.getY(i);
            if (y <= safeArea.getTop()) {
                y = Math.min(safeArea.getMidY(), shapeBounds.getTop() + (y - shapeBounds.getTop()) * height);
            } else if (y >= safeArea.getBottom()) {
                y = Math.max(safeArea.getMidY(), shapeBounds.getBottom() + (y - shapeBounds.getBottom()) * height);
            }

            float appliedX = matrix.applyX(x, y);
            float appliedY = matrix.applyY(x, y);

            transformedPoints[i * 2] = appliedX;
            transformedPoints[i * 2 + 1] = appliedY;

            if (i == 0) {
                bounds = new Rect(appliedX, appliedY, appliedX, appliedY);
                continue;
            }

            bounds.addPoint(appliedX, appliedY);
        }

        int[] indices = command.getIndices();

        GLImage image = stage.getImageByIndex(command.getTextureIndex());

        if (stage.startShape(bounds, image.getTexture(), renderConfigBits)) {
            stage.addTriangles(command.getTriangleCount(), indices);

            float redMultiplier = colorTransform.getRedMultiplier() / 255f;
            float greenMultiplier = colorTransform.getGreenMultiplier() / 255f;
            float blueMultiplier = colorTransform.getBlueMultiplier() / 255f;
            float redAddition = colorTransform.getRedAddition() / 255f;
            float greenAddition = colorTransform.getGreenAddition() / 255f;
            float blueAddition = colorTransform.getBlueAddition() / 255f;
            float alpha = colorTransform.getAlpha() / 255f;

            // TODO: optimize vertices and pass color transform via uniforms instead
            for (int i = 0; i < command.getVertexCount(); i++) {
                stage.addVertex(
                    transformedPoints[i * 2],
                    transformedPoints[i * 2 + 1],
                    command.getU(i),
                    command.getV(i),
                    redMultiplier,
                    greenMultiplier,
                    blueMultiplier,
                    alpha,
                    redAddition,
                    greenAddition,
                    blueAddition
                );
            }

            return true;
        }

        return false;
    }

    public static boolean collisionRender(ShapeDrawBitmapCommand command, Stage stage, Matrix2x3 matrix, ColorTransform colorTransform) {
        return render(command, stage, matrix, colorTransform, 0);
    }

    public static boolean renderUV(ShapeDrawBitmapCommand command, Stage stage, int renderConfigBits) {
        Rect bounds = new Rect();

        GLImage image = stage.getImageByIndex(command.getTextureIndex());

        float[] transformedPoints = new float[command.getVertexCount() * 2];
        for (int i = 0; i < command.getVertexCount(); i++) {
            float x = command.getU(i) * image.getWidth() - image.getWidth() / 2f;
            float y = command.getV(i) * image.getHeight() - image.getHeight() / 2f;

            transformedPoints[i * 2] = x;
            transformedPoints[i * 2 + 1] = y;

            if (i == 0) {
                bounds = new Rect(x, y, x, y);
                continue;
            }

            bounds.addPoint(x, y);
        }

        int[] indices = command.getIndices();

        if (stage.startShape(bounds, stage.getGradientTexture().getTexture(), renderConfigBits)) {
            stage.addTriangles(command.getTriangleCount(), indices);

            for (int i = 0; i < command.getVertexCount(); i++) {
                stage.addVertex(transformedPoints[i * 2], transformedPoints[i * 2 + 1], 1f, 0, 1, 0, 0, 0.5f, 0, 0, 0);
            }

            return true;
        }

        return false;
    }
}
