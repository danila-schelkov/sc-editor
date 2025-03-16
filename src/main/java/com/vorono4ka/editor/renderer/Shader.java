package com.vorono4ka.editor.renderer;

import com.jogamp.opengl.GL3;
import com.vorono4ka.editor.renderer.exceptions.ShaderCompilationException;
import com.vorono4ka.editor.renderer.exceptions.ShaderLinkingException;
import com.vorono4ka.resources.ResourceManager;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;


public class Shader {
    private static final int LOG_BUFFER_LENGTH = 1024;

    private final GL3 gl;
    private final int programId;
    private final Attribute[] attributes;

    public Shader(GL3 gl, String vertexFile, String fragmentFile, Attribute... attributes) {
        this.gl = gl;
        this.attributes = attributes;

        int vertexShader = gl.glCreateShader(GL3.GL_VERTEX_SHADER);
        gl.glShaderSource(vertexShader, 1, new String[]{ResourceManager.loadString(vertexFile)}, null);
        gl.glCompileShader(vertexShader);
        checkShaderCompiled(gl, vertexShader, "VERTEX");

        int fragmentShader = gl.glCreateShader(GL3.GL_FRAGMENT_SHADER);
        gl.glShaderSource(fragmentShader, 1, new String[]{ResourceManager.loadString(fragmentFile)}, null);
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

    public int getUniformLocation(String uniform) {
        return gl.glGetUniformLocation(this.programId, uniform);
    }

    public void setTexture(int id, String uniform, int slot) {
        gl.glActiveTexture(GL3.GL_TEXTURE0 + slot);
        gl.glBindTexture(GL3.GL_TEXTURE_2D, id);

        int uniformLocation = this.getUniformLocation(uniform);
        if (uniformLocation != -1) {
            gl.glUniform1i(uniformLocation, slot);
        }
    }

    public void bind() {
        gl.glUseProgram(this.programId);
    }

    public void unbind() {
        gl.glUseProgram(0);
    }

    public void delete() {
        gl.glDeleteProgram(this.programId);
    }

    public Attribute[] getAttributes() {
        return attributes;
    }

    private static void checkShaderCompiled(GL3 gl, int shader, String type) {
        IntBuffer hasCompiled = IntBuffer.allocate(1);
        gl.glGetShaderiv(shader, GL3.GL_COMPILE_STATUS, hasCompiled);
        if (hasCompiled.get() == GL3.GL_TRUE) return;

        ByteBuffer logBuffer = ByteBuffer.allocate(LOG_BUFFER_LENGTH);
        gl.glGetShaderInfoLog(shader, LOG_BUFFER_LENGTH, null, logBuffer);
        throw new ShaderCompilationException("Shader (%s) compilation failed:\n%s", type, new String(logBuffer.array()).trim());
    }

    private static void checkProgramLinked(GL3 gl, int shader) {
        IntBuffer hasCompiled = IntBuffer.allocate(1);
        gl.glGetProgramiv(shader, GL3.GL_LINK_STATUS, hasCompiled);
        if (hasCompiled.get() == GL3.GL_TRUE) return;

        ByteBuffer logBuffer = ByteBuffer.allocate(LOG_BUFFER_LENGTH);
        gl.glGetShaderInfoLog(shader, LOG_BUFFER_LENGTH, null, logBuffer);
        throw new ShaderLinkingException("Program linking failed:\n%s", new String(logBuffer.array()).trim());
    }
}
