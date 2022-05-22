package com.vorono4ka.resources;

import com.jogamp.opengl.GL3;
import com.vorono4ka.editor.renderer.Shader;

import java.util.HashMap;
import java.util.Map;

public class Assets {
    private static final Map<String, Shader> shaders = new HashMap<>();

    public static Shader getShader(GL3 gl, String vertexFile, String fragmentFile) {
        String key = vertexFile + fragmentFile;
        if (shaders.containsKey(key)) {
            return shaders.get(key);
        }

        Shader shader = new Shader(gl, vertexFile, fragmentFile);
        shaders.put(key, shader);
        return shader;
    }
}
