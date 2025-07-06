package dev.donutquine.editor.renderer.impl.texture.khronos;

import dev.donutquine.editor.renderer.gl.texture.GLTexture;
import dev.donutquine.editor.renderer.impl.texture.GLImage;
import dev.donutquine.utilities.BufferUtils;
import dev.donutquine.utilities.ImageUtils;
import dev.donutquine.utilities.PathUtils;
import dev.donutquine.utilities.SystemUtils;
import dev.donutquine.utilities.process.ChainedExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Path;

import static dev.donutquine.editor.renderer.gl.GLConstants.*;

/// Wrapper over <a href="https://github.com/KhronosGroup/KTX-Software/tree/main/tools">KTX CLI tools</a>.
public class KhronosToolTextureLoader implements KhronosTextureLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(KhronosToolTextureLoader.class);

    public static final String KTX2KTX = "ktx2ktx2";
    public static final String KTX = "ktx";

    private static void loadPngToGl(GLTexture texture, Path pngPath) {
        BufferedImage image = loadPngAsBufferedImage(pngPath);

        ByteBuffer pixelBuffer;
        int format = texture.getFormat();
        int pixelType = texture.getPixelType();

        int numColorComponents = image.getColorModel().getNumColorComponents();
        if (numColorComponents == 1 && format == GL_LUMINANCE) {
            pixelBuffer = ImageUtils.getPixelBuffer(image);
        } else if (numColorComponents == 2 && format == GL_LUMINANCE_ALPHA) {
            pixelBuffer = ImageUtils.getPixelBuffer(image);
        } else if (numColorComponents == 4 && format == GL_RGBA) {  // ARGB to RGBA
            byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
            for (int i = 0; i < data.length; i += 4) {
                // Swap 1st and 4th component
                byte b = data[i];
                data[i] = data[i + 3];
                data[i + 3] = b;

                // Swap 2nd and 3rd component
                b = data[i + 1];
                data[i + 1] = data[i + 2];
                data[i + 2] = b;
            }

            pixelBuffer = ImageUtils.getPixelBuffer(image);
        } else {
            int[] argb = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());

            pixelBuffer = intARGBtoByteRGBA(argb);
            format = GL_RGBA;
            pixelType = GL_UNSIGNED_BYTE;
        }

        // Note: here is a bug, if the texture is grayscale or kind of this,
        // you have to convert it by yourself, otherwise there won't be any images.

        GLImage.loadImage(texture, pixelBuffer, format, pixelType);
    }

    private static BufferedImage loadPngAsBufferedImage(Path pngPath) {
        BufferedImage bufferedImage;
        try {
            File pngPathFile = pngPath.toFile();
            bufferedImage = ImageIO.read(pngPathFile);
            pngPathFile.delete();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return bufferedImage;
    }

    @Override
    public boolean isAvailable() {
        return SystemUtils.canExecute(KTX2KTX, "-v") && SystemUtils.canExecute(KTX, "-v");
    }

    @Override
    public void load(GLTexture texture, ByteBuffer khronosTextureFileData) throws Exception {
        File ktx1File = File.createTempFile("texture", ".ktx1");
        try (FileOutputStream fileOutputStream = new FileOutputStream(ktx1File)) {
            fileOutputStream.getChannel().write(khronosTextureFileData);
        }

        Path ktx1Path = ktx1File.toPath();
        Path ktx2Path = PathUtils.replaceExtension(ktx1Path, "ktx2");
        Path pngPath = PathUtils.replaceExtension(ktx1Path, "png");

        ChainedExecutor<Process> executor = ChainedExecutor.<Process>create().add(
            () -> SystemUtils.runProcess(KTX2KTX, ktx1Path),
            (process) -> logProcessStarted(KTX2KTX),
            (process) -> {
                logProcessDone(KTX2KTX, process);
                ktx1File.delete();
            }
        ).add(
            () -> SystemUtils.runProcess(KTX, "extract", ktx2Path, pngPath),
            (process) -> logProcessStarted(KTX),
            (process) -> {
                logProcessDone(KTX, process);
                ktx2Path.toFile().delete();
                loadPngToGl(texture, pngPath);
            }
        );

        executor.run(SystemUtils::wait);
    }

    private static void logProcessStarted(String name) {
        LOGGER.info("Waiting for {} to do its work...", name);
    }

    private static void logProcessDone(String name, Process process) {
        LOGGER.info("{} done its work with code: {}", name, process.exitValue());
    }

    private static ByteBuffer intARGBtoByteRGBA(int[] argb) {
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
