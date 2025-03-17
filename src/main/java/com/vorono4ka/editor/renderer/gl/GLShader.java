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
    private static final int LOG_BUFFER_LENGTH = 1024;

    private final GLRendererContext gl;
    private final int programId;

    public GLShader(GLRendererContext gl, String vertexFile, String fragmentFile, Attribute... attributes) {
        super(attributes);
        this.gl = gl;

        int vertexShader = gl.glCreateShader(GLConstants.GL_VERTEX_SHADER);
        gl.glShaderSource(vertexShader, ResourceManager.loadString(vertexFile));
        gl.glCompileShader(vertexShader);
        checkShaderCompiled(gl, vertexShader, "VERTEX");

        int fragmentShader = gl.glCreateShader(GLConstants.GL_FRAGMENT_SHADER);
        gl.glShaderSource(fragmentShader, ResourceManager.loadString(fragmentFile));
        gl.glCompileShader(fragmentShader);
        checkShaderCompiled(gl, fragmentShader, "FRAGMENT");

        this.programId = gl.glCreateProgram();
        gl.glAttachShader(this.programId, vertexShader);
        gl.glAttachShader(this.programId, fragmentShader);
        gl.glLinkProgram(this.programId);
        checkProgramLinked(gl, this.programId);

        gl.glDeleteShader(vertexShader);
        gl.glDeleteShader(fragmentShader);
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
        gl.glDeleteProgram(this.programId);
    }

    @Override
    public void setUniformMatrix4f(String uniformName, FloatBuffer matrixBuffer) {
        this.gl.glUniformMatrix4fv(this.getUniformLocation("pmv"), 1, false, matrixBuffer);
    }

    private int getUniformLocation(String name) {
        return gl.glGetUniformLocation(this.programId, name);
    }

    private static void checkShaderCompiled(GLRendererContext gl, int shader, String type) {
        IntBuffer hasCompiled = IntBuffer.allocate(1);
        gl.glGetShaderiv(shader, GLConstants.GL_COMPILE_STATUS, hasCompiled);
        if (hasCompiled.get() == GLConstants.GL_TRUE) return;

        ByteBuffer logBuffer = ByteBuffer.allocateDirect(LOG_BUFFER_LENGTH);
        gl.glGetShaderInfoLog(shader, LOG_BUFFER_LENGTH, null, logBuffer);
        throw new ShaderCompilationException("Shader (%s) compilation failed:\n%s", type, new String(logBuffer.array()).trim());
    }

    private static void checkProgramLinked(GLRendererContext gl, int program) {
        IntBuffer hasCompiled = IntBuffer.allocate(1);
        gl.glGetProgramiv(program, GLConstants.GL_LINK_STATUS, hasCompiled);
        if (hasCompiled.get() == GLConstants.GL_TRUE) return;

        ByteBuffer logBuffer = ByteBuffer.allocateDirect(LOG_BUFFER_LENGTH);
        gl.glGetProgramInfoLog(program, LOG_BUFFER_LENGTH, null, logBuffer);
        throw new ShaderLinkingException("Program linking failed:\n%s", new String(logBuffer.array()).trim());
    }
}
