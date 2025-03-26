package com.vorono4ka.editor.renderer.gl;

import com.vorono4ka.editor.renderer.gl.exceptions.ShaderCompilationException;
import com.vorono4ka.editor.renderer.gl.exceptions.ShaderLinkingException;
import com.vorono4ka.editor.renderer.shader.Attribute;
import com.vorono4ka.editor.renderer.shader.Shader;
import com.vorono4ka.resources.ResourceManager;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class GLShader extends Shader {
    private final GLRendererContext gl;
    private final int programId;
    // Note: maybe should replace it with int[] shaderIds
    private final int vertexShaderId, fragmentShaderId;

    public GLShader(GLRendererContext gl, String vertexFile, String fragmentFile, Attribute... attributes) {
        super(attributes);
        this.gl = gl;

        this.vertexShaderId = loadShader(gl, GLConstants.GL_VERTEX_SHADER, vertexFile, "VERTEX");
        this.fragmentShaderId = loadShader(gl, GLConstants.GL_FRAGMENT_SHADER, fragmentFile, "FRAGMENT");

        this.programId = createProgram(gl, this.vertexShaderId, this.fragmentShaderId);
    }

    @Override
    public void bind() {
        gl.glUseProgram(this.programId);
    }

    @Override
    public void unbind() {
        gl.glUseProgram(0);
    }

    @Override
    public void delete() {
        // Note: it is possible to delete shaders right after linking the program
        gl.glDeleteShader(this.vertexShaderId);
        gl.glDeleteShader(this.fragmentShaderId);

        gl.glDeleteProgram(this.programId);
    }

    @Override
    public void setUniformMatrix4f(String uniformName, FloatBuffer matrixBuffer) {
        this.gl.glUniformMatrix4fv(this.getUniformLocation("pmv"), 1, false, matrixBuffer);
    }

    private int getUniformLocation(String name) {
        return gl.glGetUniformLocation(this.programId, name);
    }

    private static int loadShader(GLRendererContext gl, int shaderType, String shaderFilename, String shaderTypeName) {
        int shader = gl.glCreateShader(shaderType);
        gl.glShaderSource(shader, ResourceManager.loadString(shaderFilename));
        gl.glCompileShader(shader);
        checkShaderCompiled(gl, shader, shaderTypeName);
        return shader;
    }

    private static int createProgram(GLRendererContext gl, int vertexShaderId, int fragmentShaderId) {
        int programId = gl.glCreateProgram();
        gl.glAttachShader(programId, vertexShaderId);
        gl.glAttachShader(programId, fragmentShaderId);
        gl.glLinkProgram(programId);
        checkProgramLinked(gl, programId);
        return programId;
    }

    private static void checkShaderCompiled(GLRendererContext gl, int shader, String type) {
        IntBuffer hasCompiled = IntBuffer.allocate(1);
        gl.glGetShaderiv(shader, GLConstants.GL_COMPILE_STATUS, hasCompiled);
        if (hasCompiled.get() == GLConstants.GL_TRUE) return;

        IntBuffer infoLogLength = IntBuffer.allocate(1);
        gl.glGetShaderiv(shader, GLConstants.GL_INFO_LOG_LENGTH, infoLogLength);

        ByteBuffer logBuffer = ByteBuffer.allocateDirect(infoLogLength.get());
        gl.glGetShaderInfoLog(shader, logBuffer.capacity(), null, logBuffer);
        gl.glDeleteShader(shader);

        throw new ShaderCompilationException("Shader (%s) compilation failed:\n%s", type, new String(logBuffer.array()).trim());
    }

    private static void checkProgramLinked(GLRendererContext gl, int program) {
        IntBuffer hasCompiled = IntBuffer.allocate(1);
        gl.glGetProgramiv(program, GLConstants.GL_LINK_STATUS, hasCompiled);
        if (hasCompiled.get() == GLConstants.GL_TRUE) return;

        IntBuffer infoLogLength = IntBuffer.allocate(1);
        gl.glGetProgramiv(program, GLConstants.GL_INFO_LOG_LENGTH, infoLogLength);

        ByteBuffer logBuffer = ByteBuffer.allocateDirect(infoLogLength.get());
        gl.glGetProgramInfoLog(program, logBuffer.capacity(), null, logBuffer);
        gl.glDeleteProgram(program);

        throw new ShaderLinkingException("Program linking failed:\n%s", new String(logBuffer.array()).trim());
    }
}
