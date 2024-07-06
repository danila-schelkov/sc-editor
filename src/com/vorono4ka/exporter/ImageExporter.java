package com.vorono4ka.exporter;

import com.vorono4ka.editor.renderer.Camera;
import com.vorono4ka.editor.renderer.Framebuffer;
import com.vorono4ka.editor.renderer.Stage;
import com.vorono4ka.math.MathHelper;
import com.vorono4ka.math.Rect;
import com.vorono4ka.swf.displayObjects.DisplayObject;
import com.vorono4ka.swf.displayObjects.MovieClip;
import com.vorono4ka.utilities.ImageData;
import com.vorono4ka.utilities.ImageUtils;

import java.awt.image.BufferedImage;
import java.nio.file.Path;

public class ImageExporter {
    private static final Path SCREENSHOT_FOLDER = Path.of("screenshots");

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
     * @param displayObject screenshot target, used for choosing a name for file
     * @param bufferedImage image data
     */
    public void saveScreenshot(DisplayObject displayObject, BufferedImage bufferedImage) {
        ImageUtils.saveImage(SCREENSHOT_FOLDER.resolve(getScreenshotFilename(displayObject)), bufferedImage);
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

    private Path getScreenshotFilename(DisplayObject child) {
        if (child.isMovieClip()) {
            MovieClip movieClip = (MovieClip) child;

            if (movieClip.getFrames().length > 1) {
                int currentFrame = movieClip.getCurrentFrame();
                String frameLabel = movieClip.getFrameLabel(currentFrame);
                String frameName = String.valueOf(currentFrame);
                if (frameLabel != null) {
                    frameName = String.join("-", frameName, frameLabel);
                }

                return Path.of(String.valueOf(child.getId()), frameName + ".png");
            }
        }

        return Path.of(child.getId() + ".png");
    }
}
