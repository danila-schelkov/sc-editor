package com.vorono4ka.resources;

import com.vorono4ka.editor.renderer.shader.Attribute;
import com.vorono4ka.editor.renderer.shader.Shader;

public interface ShaderLoader {
    Shader load(String vertexFile, String fragmentFile, Attribute... attributes);
}
