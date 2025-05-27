package com.vorono4ka.renderer.impl.swf.objects;

import com.vorono4ka.editor.renderer.Stage;
import com.vorono4ka.editor.renderer.impl.Triangulator;
import com.vorono4ka.editor.renderer.texture.RenderableTexture;
import com.vorono4ka.editor.renderer.texture.Texture;
import com.vorono4ka.math.ReadonlyRect;
import com.vorono4ka.math.Rect;
import com.vorono4ka.swf.ColorTransform;
import com.vorono4ka.swf.Matrix2x3;
import com.vorono4ka.swf.shapes.ShapeDrawBitmapCommand;

public final class ShapeDrawBitmapCommandRenderer {
    private ShapeDrawBitmapCommandRenderer() {
    }

    public static boolean render(ShapeDrawBitmapCommand command, Stage stage, Matrix2x3 matrix, ColorTransform colorTransform, int renderConfigBits) {
        return render0(stage, command, new Matrix2x3Transformer(command, matrix), colorTransform, renderConfigBits);
    }

    public static boolean render9Slice(ShapeDrawBitmapCommand command, Stage stage, Matrix2x3 matrix, ColorTransform colorTransform, int renderConfigBits, Rect safeArea, Rect shapeBounds, float width, float height) {
        NineSliceTransformer vertexTransformer = new NineSliceTransformer(command, matrix, safeArea, shapeBounds, width, height);

        return render0(stage, command, vertexTransformer, colorTransform, renderConfigBits);
    }

    public static boolean collisionRender(ShapeDrawBitmapCommand command, Stage stage, Matrix2x3 matrix, ColorTransform colorTransform) {
        return render(command, stage, matrix, colorTransform, 0);
    }

    public static boolean renderUV(ShapeDrawBitmapCommand command, Stage stage, ColorTransform colorTransform, int renderConfigBits) {
        UvTransformer vertexTransformer = new UvTransformer(command, stage.getTextureByIndex(command.getTextureIndex()));

        return render0(stage, command, vertexTransformer, colorTransform, renderConfigBits);
    }

    private static boolean render0(Stage stage, ShapeDrawBitmapCommand command, VertexTransformer vertexTransformer, ColorTransform colorTransform, int renderConfigBits) {
        float[] transformedPoints = new float[command.getVertexCount() * 2];
        Rect bounds = transformPoints(vertexTransformer, transformedPoints);

        RenderableTexture texture = stage.getTextureByIndex(command.getTextureIndex());
        if (stage.startShape(bounds, texture, renderConfigBits)) {
            stage.addTriangles(command.getTriangleCount(), getIndices(command, renderConfigBits));

            renderCommandVertices(stage, command, colorTransform, transformedPoints);

            return true;
        }

        return false;
    }

    private static int[] getIndices(ShapeDrawBitmapCommand command, int renderConfigBits) {
        Triangulator triangulator = (renderConfigBits & 0x8000) != 0 ? Triangulator.TRIANGLE_FAN : Triangulator.TRIANGLE_STRIP;

        return triangulator.getIndices(command.getTriangleCount());
    }

    private static void renderCommandVertices(Stage stage, ShapeDrawBitmapCommand command, ColorTransform colorTransform, float[] transformedPoints) {
        float redMultiplier = (colorTransform.getRedMultiplier() & 0xFF) / 255f;
        float greenMultiplier = (colorTransform.getGreenMultiplier() & 0xFF) / 255f;
        float blueMultiplier = (colorTransform.getBlueMultiplier() & 0xFF) / 255f;
        float redAddition = (colorTransform.getRedAddition() & 0xFF) / 255f;
        float greenAddition = (colorTransform.getGreenAddition() & 0xFF) / 255f;
        float blueAddition = (colorTransform.getBlueAddition() & 0xFF) / 255f;
        float alpha = (colorTransform.getAlpha() & 0xFF) / 255f;

        for (int i = 0; i < command.getVertexCount(); i++) {
            stage.addVertex(transformedPoints[i * 2], transformedPoints[i * 2 + 1], command.getU(i), command.getV(i), redMultiplier, greenMultiplier, blueMultiplier, alpha, redAddition, greenAddition, blueAddition);
        }
    }

    private static Rect transformPoints(VertexTransformer vertexTransformer, float[] transformedPoints) {
        Rect bounds = null;
        for (int i = 0; i < transformedPoints.length / 2; i++) {
            vertexTransformer.transform(i);

            float x = vertexTransformer.getX();
            float y = vertexTransformer.getY();

            transformedPoints[i * 2] = x;
            transformedPoints[i * 2 + 1] = y;

            if (i == 0) {
                bounds = new Rect(x, y, x, y);
                continue;
            }

            bounds.addPoint(x, y);
        }

        return bounds;
    }

    private interface VertexTransformer {
        void transform(int vertexIndex);

        float getX();

        float getY();
    }

    private static class UvTransformer implements VertexTransformer {
        private final ShapeDrawBitmapCommand command;
        private final Texture texture;

        private float x, y;

        private UvTransformer(ShapeDrawBitmapCommand command, Texture texture) {
            this.command = command;
            this.texture = texture;
        }

        @Override
        public void transform(int vertexIndex) {
            this.x = (command.getU(vertexIndex) - 0.5f) * texture.getWidth();
            this.y = (command.getV(vertexIndex) - 0.5f) * texture.getHeight();
        }

        @Override
        public float getX() {
            return this.x;
        }

        @Override
        public float getY() {
            return this.y;
        }
    }

    private static class NineSliceTransformer implements VertexTransformer {
        private final ShapeDrawBitmapCommand command;
        private final Matrix2x3 matrix;
        private final ReadonlyRect safeArea, shapeBounds;
        private final float width, height;

        private float x, y;

        public NineSliceTransformer(ShapeDrawBitmapCommand command, Matrix2x3 matrix, ReadonlyRect safeArea, ReadonlyRect shapeBounds, float width, float height) {
            this.command = command;
            this.matrix = matrix;
            this.safeArea = safeArea;
            this.shapeBounds = shapeBounds;
            this.width = width;
            this.height = height;
        }

        @Override
        public void transform(int vertexIndex) {
            float x = command.getX(vertexIndex);
            if (x <= safeArea.getLeft()) {
                x = Math.min(safeArea.getMidX(), shapeBounds.getLeft() + (x - shapeBounds.getLeft()) * width);
            } else if (x >= safeArea.getRight()) {
                x = Math.max(safeArea.getMidX(), shapeBounds.getRight() + (x - shapeBounds.getRight()) * width);
            }

            float y = command.getY(vertexIndex);
            if (y <= safeArea.getTop()) {
                y = Math.min(safeArea.getMidY(), shapeBounds.getTop() + (y - shapeBounds.getTop()) * height);
            } else if (y >= safeArea.getBottom()) {
                y = Math.max(safeArea.getMidY(), shapeBounds.getBottom() + (y - shapeBounds.getBottom()) * height);
            }

            this.x = matrix.applyX(x, y);
            this.y = matrix.applyY(x, y);
        }

        @Override
        public float getX() {
            return this.x;
        }

        @Override
        public float getY() {
            return this.y;
        }
    }

    private static class Matrix2x3Transformer implements VertexTransformer {
        private final ShapeDrawBitmapCommand command;
        private final Matrix2x3 matrix;

        private float x, y;

        public Matrix2x3Transformer(ShapeDrawBitmapCommand command, Matrix2x3 matrix) {
            this.command = command;
            this.matrix = matrix;
        }

        @Override
        public void transform(int vertexIndex) {
            this.x = matrix.applyX(command.getX(vertexIndex), command.getY(vertexIndex));
            this.y = matrix.applyY(command.getX(vertexIndex), command.getY(vertexIndex));
        }

        @Override
        public float getX() {
            return this.x;
        }

        @Override
        public float getY() {
            return this.y;
        }
    }
}
