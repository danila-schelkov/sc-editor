package dev.donutquine.utilities;

import dev.donutquine.math.MathHelper;

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
        for (int y = 0; y < height / 2; y++) {
            int topOffset = y * width;
            int bottomOffset = (height - 1 - y) * width;

            for (int x = 0; x < width; x++) {
                int topIndex = topOffset + x;
                int bottomIndex = bottomOffset + x;

                int pixel = pixelArray[topIndex];
                pixelArray[topIndex] = pixelArray[bottomIndex];
                pixelArray[bottomIndex] = pixel;
            }
        }
    }

    public static ByteBuffer getPixelBuffer(BufferedImage image) {
        return BufferUtils.wrapDirect(((DataBufferByte) image.getRaster().getDataBuffer()).getData());
    }

    public static byte[] intArrayToByteArray(int[] argb) {
        byte[] rgba = new byte[argb.length * 4];

        for (int i = 0; i < argb.length; i++) {
            rgba[4 * i + 0] = (byte) ((argb[i] >>  0) & 0xff); // R
            rgba[4 * i + 1] = (byte) ((argb[i] >>  8) & 0xff); // G
            rgba[4 * i + 2] = (byte) ((argb[i] >> 16) & 0xff); // B
            rgba[4 * i + 3] = (byte) ((argb[i] >> 24) & 0xff); // A
        }

        return rgba;
    }

    public static byte[] intARGBtoByteRGBAArray(int[] argb) {
        byte[] rgba = new byte[argb.length * 4];

        for (int i = 0; i < argb.length; i++) {
            rgba[4 * i + 0] = (byte) ((argb[i] >> 16) & 0xff); // R
            rgba[4 * i + 1] = (byte) ((argb[i] >>  8) & 0xff); // G
            rgba[4 * i + 2] = (byte) ((argb[i] >>  0) & 0xff); // B
            rgba[4 * i + 3] = (byte) ((argb[i] >> 24) & 0xff); // A
        }

        return rgba;
    }

    public static ByteBuffer intARGBtoByteRGBA(int[] argb) {
        ByteBuffer rgba = BufferUtils.allocateDirect(argb.length * 4);

        for (int i = 0; i < argb.length; i++) {
            rgba.put(4 * i, (byte) ((argb[i] >> 16) & 0xff)); // R
            rgba.put(4 * i + 1, (byte) ((argb[i] >> 8) & 0xff)); // G
            rgba.put(4 * i + 2, (byte) ((argb[i]) & 0xff)); // B
            rgba.put(4 * i + 3, (byte) ((argb[i] >> 24) & 0xff)); // A
        }

        return rgba;
    }
}
