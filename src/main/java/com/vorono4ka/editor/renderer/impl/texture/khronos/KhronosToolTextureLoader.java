package com.vorono4ka.editor.renderer.impl.texture.khronos;

import com.vorono4ka.editor.renderer.impl.texture.GLImage;
import com.vorono4ka.editor.renderer.texture.GLTexture;
import com.vorono4ka.utilities.ImageUtils;
import com.vorono4ka.utilities.PathUtils;
import com.vorono4ka.utilities.SystemUtils;
import com.vorono4ka.utilities.process.ChainedExecutor;
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

/// Wrapper over <a href="https://github.com/KhronosGroup/KTX-Software/tree/main/tools">KTX CLI tools</a>.
public class KhronosToolTextureLoader implements KhronosTextureLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(KhronosToolTextureLoader.class);

    public static final String KTX2KTX = "ktx2ktx2";
    public static final String KTX = "ktx";

    private static void loadPngToGl(GLTexture texture, Path pngPath) {
        BufferedImage bufferedImage = loadPngAsBufferedImage(pngPath);

        byte[] data = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
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

        // Note: here is a bug, if the texture is grayscale or kind of this,
        // you have to convert it by yourself, otherwise there won't be any images.

        ByteBuffer pixelBuffer = ImageUtils.getPixelBuffer(bufferedImage);
        GLImage.loadImage(texture, pixelBuffer, texture.getFormat(), texture.getPixelType());
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
}
