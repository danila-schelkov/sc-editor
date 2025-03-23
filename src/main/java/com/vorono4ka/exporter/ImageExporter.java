package com.vorono4ka.exporter;

import com.vorono4ka.editor.renderer.Camera;
import com.vorono4ka.editor.renderer.Framebuffer;
import com.vorono4ka.editor.renderer.impl.Stage;
import com.vorono4ka.math.MathHelper;
import com.vorono4ka.math.Rect;
import com.vorono4ka.renderer.impl.swf.objects.DisplayObject;
import com.vorono4ka.utilities.ImageData;
import com.vorono4ka.utilities.ImageUtils;

import java.awt.image.BufferedImage;
import java.nio.file.Path;

public class ImageExporter {
    // TODO: load from settings
    private static final Path SCREENSHOT_FOLDER = Path.of("screenshots").toAbsolutePath();

    private final Stage stage;
    private final Camera camera;

    public ImageExporter(Stage stage) {
        this.stage = stage;
        this.camera = stage.getCamera();
    }

    public BufferedImage takeScreenshot(DisplayObject displayObject) {
        return takeScreenshot(displayObject, null);
    }

    /**
     * Takes a screenshot of the selected {@link DisplayObject} with provided bounds. <br>
     * <br>
     * Please, consider calling this method in the render thread, otherwise you will get an empty image
     *
     * @param bounds bounds cropping the framebuffer image
     * @return instance object of the {@link BufferedImage}, containing screenshot data
     */
    public BufferedImage takeScreenshot(DisplayObject displayObject, Rect bounds) {
        if (bounds == null) {
            bounds = stage.calculateBoundsForAllFrames(displayObject);
        }

        ImageData imageData = getCroppedFramebufferData(bounds, false);

        return ImageUtils.createBufferedImageFromPixels(imageData.width(), imageData.height(), imageData.pixels(), false);
    }

    /**
     * Saves an image into the file in the screenshot folder.
     *
     * @param bufferedImage image data
     * @param filename screenshot relative filepath
     */
    public void saveScreenshot(BufferedImage bufferedImage, Path filename) {
        ImageUtils.saveImage(SCREENSHOT_FOLDER.resolve(filename), bufferedImage);
    }

    public Rect toFramebufferBounds(Rect bounds, boolean shouldBeDividableByTwo) {
        float pointSize = camera.getZoom().getPointSize();

        int width = (int) Math.ceil(bounds.getWidth() * pointSize);
        int height = (int) Math.ceil(bounds.getHeight() * pointSize);

        Framebuffer framebuffer = stage.getFramebuffer();
        width = MathHelper.clamp(width, 1, framebuffer.getWidth());
        height = MathHelper.clamp(height, 1, framebuffer.getHeight());

        if (shouldBeDividableByTwo) {
            width += width % 2;
            height += height % 2;
        }

        Rect framebufferBounds = new Rect(width, height);
        framebufferBounds.movePosition(
            (int) ((bounds.getLeft() - camera.getOffsetX()) * pointSize),
            (int) ((bounds.getTop() - camera.getOffsetY()) * pointSize)
        );

        return framebufferBounds;
    }

    public ImageData getCroppedFramebufferData(Rect bounds, boolean shouldBeDividableByTwo) {
        Rect framebufferBounds = toFramebufferBounds(bounds, shouldBeDividableByTwo);

        Framebuffer framebuffer = stage.getFramebuffer();
        int[] croppedPixelArray = ImageUtils.cropPixelArray(
            framebuffer.getPixelArray(true),
            framebuffer.getWidth(),
            framebuffer.getHeight(),
            (int) framebufferBounds.getWidth(),
            (int) framebufferBounds.getHeight(),
            (int) framebufferBounds.getLeft(),
            (int) framebufferBounds.getTop()
        );

        return new ImageData((int) framebufferBounds.getWidth(), (int) framebufferBounds.getHeight(), croppedPixelArray);
    }
}
