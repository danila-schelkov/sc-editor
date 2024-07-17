package com.vorono4ka.resources;

import com.jogamp.opengl.GL3;
import com.vorono4ka.editor.renderer.Attribute;
import com.vorono4ka.editor.renderer.Shader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Assets {
    private static final Map<String, Shader> shaders = new HashMap<>();
    private static final Map<String, BufferedImage> images = new HashMap<>();

    public static Shader getShader(GL3 gl, String vertexFile, String fragmentFile, Attribute... attributes) {
        String key = vertexFile + fragmentFile;
        if (shaders.containsKey(key)) {
            return shaders.get(key);
        }

        Shader shader = new Shader(gl, vertexFile, fragmentFile, attributes);
        shaders.put(key, shader);
        return shader;
    }

    public static BufferedImage getImageBuffer(String imageFile) {
        if (images.containsKey(imageFile)) {
            return images.get(imageFile);
        }

        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(ResourceManager.loadStream(imageFile));
            if (bufferedImage == null) return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        images.put(imageFile, bufferedImage);

        return bufferedImage;
    }
}
