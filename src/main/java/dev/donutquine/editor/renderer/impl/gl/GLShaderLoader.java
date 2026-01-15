package dev.donutquine.editor.renderer.impl.gl;

import dev.donutquine.editor.renderer.gl.GLContext;
import dev.donutquine.editor.renderer.gl.GLShader;
import dev.donutquine.editor.renderer.shader.Attribute;
import dev.donutquine.editor.renderer.shader.Shader;
import dev.donutquine.resources.ShaderLoader;

public class GLShaderLoader implements ShaderLoader {
    private final GLContext gl;

    public GLShaderLoader(GLContext gl) {
        this.gl = gl;
    }

    @Override
    public Shader load(String vertexFile, String fragmentFile, Attribute... attributes) {
        return new GLShader(gl, vertexFile, fragmentFile, attributes);
    }
}
