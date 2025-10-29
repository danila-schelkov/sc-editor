package dev.donutquine.resources;

import dev.donutquine.editor.renderer.shader.Attribute;
import dev.donutquine.editor.renderer.shader.Shader;

public interface ShaderLoader {
    Shader load(String vertexFile, String fragmentFile, Attribute... attributes);
}
