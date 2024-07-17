package com.vorono4ka.editor.renderer;

import com.jogamp.opengl.GL3;
import com.vorono4ka.resources.ResourceManager;


public class Shader {
    private final GL3 gl;
    private final int programId;
    private final Attribute[] attributes;

    public Shader(GL3 gl, String vertexFile, String fragmentFile, Attribute... attributes) {
        this.gl = gl;
        this.attributes = attributes;

        int vertexShader = gl.glCreateShader(GL3.GL_VERTEX_SHADER);
        gl.glShaderSource(vertexShader, 1, new String[]{ResourceManager.loadString(vertexFile)}, null);
        gl.glCompileShader(vertexShader);

        int fragmentShader = gl.glCreateShader(GL3.GL_FRAGMENT_SHADER);
        gl.glShaderSource(fragmentShader, 1, new String[]{ResourceManager.loadString(fragmentFile)}, null);
        gl.glCompileShader(fragmentShader);

        this.programId = gl.glCreateProgram();
        gl.glAttachShader(this.programId, vertexShader);
        gl.glAttachShader(this.programId, fragmentShader);
        gl.glLinkProgram(this.programId);

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
}
