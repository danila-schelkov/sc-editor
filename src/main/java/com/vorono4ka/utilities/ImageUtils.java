package com.vorono4ka.utilities;

import com.vorono4ka.math.MathHelper;

import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Path;

public final class ImageUtils {
    public static final DirectColorModel RGBA_MODEL = new DirectColorModel(32,
        0xff,
        0xff00,
        0xff0000,
        0xff000000
    );

    public static final DirectColorModel LUMINANCE_ALPHA_MODEL = new DirectColorModel(32,
        0xff00,
        0xff00,
        0xff00,
        0xff
    );

    public static void saveImage(Path filepath, BufferedImage image) {
        try {
            File file = filepath.toFile();
            file.mkdirs();
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static BufferedImage createBufferedImageFromPixels(int width, int height, int[] pixelArray, boolean isLuminanceAlpha) {
        DirectColorModel colorModel = isLuminanceAlpha ? LUMINANCE_ALPHA_MODEL : RGBA_MODEL;

        SampleModel sampleModel = colorModel.createCompatibleSampleModel(width, height);
        DataBufferInt dataBufferInt = new DataBufferInt(pixelArray, pixelArray.length);
        WritableRaster writableRaster = Raster.createWritableRaster(sampleModel, dataBufferInt, null);
        return new BufferedImage(colorModel, writableRaster, false, null);
    }

    public static int[] cropPixelArray(int[] pixelArray, int originalWidth, int originalHeight, int width, int height, int offsetX, int offsetY) {
        int startX = MathHelper.clamp(originalWidth / 2 + offsetX, 0, originalWidth);
        int startY = MathHelper.clamp(originalHeight / 2 + offsetY, 0, originalHeight);
        int endX = MathHelper.clamp(startX + width, 0, originalWidth);
        int endY = MathHelper.clamp(startY + height, 0, originalHeight);

        int[] croppedPixelArray = new int[width * height];
        for (int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) {
                int pixelIndex = x + y * originalWidth;
                int croppedPixelIndex = (x - startX) + (y - startY) * width;

                croppedPixelArray[croppedPixelIndex] = pixelArray[pixelIndex];
            }
        }

        return croppedPixelArray;
    }

    public static BufferedImage convertToType(BufferedImage sourceImage, int targetType) {
        BufferedImage image;

        if (sourceImage.getType() == targetType)
            return sourceImage;

        image = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), targetType);
        image.getGraphics().drawImage(sourceImage, 0, 0, null);

        return image;
    }

    public static void flipY(int width, int height, int[] pixelArray) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height / 2; y++) {
                int pixelIndex = x + y * width;
                int flippedIndex = x + (height - y) * width;

                int oldPixel = pixelArray[pixelIndex];
                pixelArray[pixelIndex] = pixelArray[flippedIndex];
                pixelArray[flippedIndex] = oldPixel;
            }
        }
    }

    public static ByteBuffer getPixelBuffer(BufferedImage image) {
        return BufferUtils.wrapDirect(((DataBufferByte) image.getRaster().getDataBuffer()).getData());
    }
}
