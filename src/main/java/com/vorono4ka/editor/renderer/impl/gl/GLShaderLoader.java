package com.vorono4ka.editor.renderer.impl.gl;

import com.vorono4ka.editor.renderer.gl.GLRendererContext;
import com.vorono4ka.editor.renderer.gl.GLShader;
import com.vorono4ka.editor.renderer.shader.Attribute;
import com.vorono4ka.editor.renderer.shader.Shader;
import com.vorono4ka.resources.ShaderLoader;

public class GLShaderLoader implements ShaderLoader {
    private final GLRendererContext gl;

    public GLShaderLoader(GLRendererContext gl) {
        this.gl = gl;
    }

    @Override
    public Shader load(String vertexFile, String fragmentFile, Attribute... attributes) {
        return new GLShader(gl, vertexFile, fragmentFile, attributes);
    }
}
