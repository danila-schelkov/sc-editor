package dev.donutquine.editor.renderer.impl.gl;

import dev.donutquine.editor.renderer.gl.GLRendererContext;
import dev.donutquine.editor.renderer.gl.GLShader;
import dev.donutquine.editor.renderer.shader.Attribute;
import dev.donutquine.editor.renderer.shader.Shader;
import dev.donutquine.resources.ShaderLoader;

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
