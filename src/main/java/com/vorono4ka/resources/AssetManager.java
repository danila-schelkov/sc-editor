package com.vorono4ka.resources;

import com.vorono4ka.editor.renderer.shader.Attribute;
import com.vorono4ka.editor.renderer.shader.Shader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AssetManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(AssetManager.class);

    private final Map<String, Shader> shaders = new HashMap<>();
    private final Map<String, BufferedImage> images = new HashMap<>();

    private final ShaderLoader shaderLoader;

    public AssetManager(ShaderLoader shaderLoader) {
        this.shaderLoader = shaderLoader;
    }

    public Shader getShader(String vertexFile, String fragmentFile, Attribute... attributes) {
        String key = vertexFile + fragmentFile;
        if (shaders.containsKey(key)) {
            return shaders.get(key);
        }

        Shader shader = shaderLoader.load(vertexFile, fragmentFile, attributes);
        shaders.put(key, shader);
        return shader;
    }

    public BufferedImage getImageBuffer(String imageFile) {
        if (images.containsKey(imageFile)) {
            return images.get(imageFile);
        }

        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(ResourceManager.loadStream(imageFile));
            if (bufferedImage == null) return null;
        } catch (IOException exception) {
            LOGGER.error("Cannot load image {}", imageFile, exception);
            return null;
        }

        images.put(imageFile, bufferedImage);

        return bufferedImage;
    }
}
