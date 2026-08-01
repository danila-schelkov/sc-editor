package dev.donutquine.renderer.impl.swf.objects;

import dev.donutquine.editor.assets.TextureAsset;
import dev.donutquine.editor.renderer.Stage;
import dev.donutquine.editor.renderer.Triangulator;
import dev.donutquine.editor.renderer.texture.RenderableTexture;
import dev.donutquine.editor.renderer.texture.Texture;
import dev.donutquine.math.ReadonlyRect;
import dev.donutquine.math.Rect;
import dev.donutquine.swf.ColorTransform;
import dev.donutquine.swf.Matrix2x3;
import dev.donutquine.swf.shapes.ShapeDrawBitmapCommand;

public final class ShapeDrawBitmapCommandRenderer {
    private static final float[] POINTS = new float[ShapeDrawBitmapCommand.MAX_VERTEX_COUNT * 2];
    private static final Rect BOUNDS = new Rect();

    private static final NineSliceTransformer NINE_SLICE_TRANSFORMER = new NineSliceTransformer();
    private static final UvTransformer UV_TRANSFORMER = new UvTransformer();
    private static final Matrix2x3Transformer MATRIX_TRANSFORMER = new Matrix2x3Transformer();

    private ShapeDrawBitmapCommandRenderer() {
    }

    public static boolean render(ShapeDrawBitmapCommand command, TextureAsset textureAsset, Stage stage, Matrix2x3 matrix, ColorTransform colorTransform, int renderConfigBits) {
        RenderableTexture texture = textureAsset.getSpriteSheet(command.getTextureIndex()).getTexture();
        return render0(stage, command, texture, MATRIX_TRANSFORMER.init(command, matrix), colorTransform, renderConfigBits);
    }

    public static boolean render9Slice(ShapeDrawBitmapCommand command, TextureAsset textureAsset, Stage stage, Matrix2x3 matrix, ColorTransform colorTransform, int renderConfigBits, ReadonlyRect safeArea, ReadonlyRect shapeBounds, float width, float height) {
        RenderableTexture texture = textureAsset.getSpriteSheet(command.getTextureIndex()).getTexture();
        NineSliceTransformer vertexTransformer = NINE_SLICE_TRANSFORMER.init(command, matrix, safeArea, shapeBounds, width, height);

        return render0(stage, command, texture, vertexTransformer, colorTransform, renderConfigBits);
    }

    public static boolean collisionRender(ShapeDrawBitmapCommand command, TextureAsset textureAsset, Stage stage, Matrix2x3 matrix, ColorTransform colorTransform) {
        return render(command, textureAsset, stage, matrix, colorTransform, 0);
    }

    public static boolean renderUV(ShapeDrawBitmapCommand command, TextureAsset textureAsset, Stage stage, ColorTransform colorTransform, int renderConfigBits) {
        RenderableTexture texture = textureAsset.getSpriteSheet(command.getTextureIndex()).getTexture();
        VertexTransformer vertexTransformer = UV_TRANSFORMER.init(command, texture);

        return render0(stage, command, texture, vertexTransformer, colorTransform, renderConfigBits);
    }

    private static boolean render0(Stage stage, ShapeDrawBitmapCommand command, RenderableTexture texture, VertexTransformer vertexTransformer, ColorTransform colorTransform, int renderConfigBits) {
        float[] transformedPoints = POINTS;
        int vertexCount = command.getVertexCount();
        for (int i = 0; i < vertexCount; i++) {
            vertexTransformer.transform(i);

            float x = vertexTransformer.getX();
            float y = vertexTransformer.getY();

            transformedPoints[i * 2] = x;
            transformedPoints[i * 2 + 1] = y;

            if (i == 0) {
                BOUNDS.set(x, y, x, y);
                continue;
            }

            BOUNDS.addPoint(x, y);
        }

        if (stage.startShape(BOUNDS, texture, renderConfigBits)) {
            Triangulator triangulator = (renderConfigBits & 0x8000) == 0 ? Triangulator.TRIANGLE_FAN : Triangulator.TRIANGLE_STRIP;
            stage.addTriangles(command.getTriangleCount(), triangulator);

            renderCommandVertices(stage, command, colorTransform, transformedPoints);

            return true;
        }

        return false;
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

    private interface VertexTransformer {
        void transform(int vertexIndex);

        float getX();

        float getY();
    }

    private static class UvTransformer implements VertexTransformer {
        private ShapeDrawBitmapCommand command;
        private Texture texture;

        private float x, y;

        private UvTransformer init(ShapeDrawBitmapCommand command, Texture texture) {
            this.command = command;
            this.texture = texture;
            return this;
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
        private ShapeDrawBitmapCommand command;
        private Matrix2x3 matrix;
        private ReadonlyRect safeArea, shapeBounds;
        private float width, height;

        private float x, y;

        public NineSliceTransformer init(ShapeDrawBitmapCommand command, Matrix2x3 matrix, ReadonlyRect safeArea, ReadonlyRect shapeBounds, float width, float height) {
            this.command = command;
            this.matrix = matrix;
            this.safeArea = safeArea;
            this.shapeBounds = shapeBounds;
            this.width = width;
            this.height = height;
            return this;
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
        private ShapeDrawBitmapCommand command;
        private Matrix2x3 matrix;

        private float x, y;

        public Matrix2x3Transformer init(ShapeDrawBitmapCommand command, Matrix2x3 matrix) {
            this.command = command;
            this.matrix = matrix;
            return this;
        }

        @Override
        public void transform(int vertexIndex) {
            float x = command.getX(vertexIndex);
            float y = command.getY(vertexIndex);

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
}
