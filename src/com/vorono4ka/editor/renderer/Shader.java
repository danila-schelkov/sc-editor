package com.vorono4ka.editor.renderer;

import com.jogamp.opengl.GL3;
import com.vorono4ka.resources.ResourceManager;

public class Shader {
    private final GL3 gl;

    private final int id;

    public Shader(GL3 gl, String vertexFile, String fragmentFile) {
        this.gl = gl;

        int vertexShader = this.gl.glCreateShader(GL3.GL_VERTEX_SHADER);
        this.gl.glShaderSource(vertexShader, 1, new String[] {ResourceManager.load(vertexFile)}, null);
        this.gl.glCompileShader(vertexShader);

        int fragmentShader = this.gl.glCreateShader(GL3.GL_FRAGMENT_SHADER);
        this.gl.glShaderSource(fragmentShader, 1, new String[] {ResourceManager.load(fragmentFile)}, null);
        this.gl.glCompileShader(fragmentShader);

        this.id = this.gl.glCreateProgram();
        this.gl.glAttachShader(this.id, vertexShader);
        this.gl.glAttachShader(this.id, fragmentShader);
        this.gl.glLinkProgram(this.id);

        this.gl.glDeleteShader(vertexShader);
        this.gl.glDeleteShader(fragmentShader);
    }

    public int getUniformLocation(String uniform) {
        return this.gl.glGetUniformLocation(this.id, uniform);
    }

    public void bind() {
        this.gl.glUseProgram(this.id);
    }

    public void unbind() {
        this.gl.glUseProgram(0);
    }

    public void delete() {
        this.gl.glDeleteProgram(this.id);
    }

    public int getId() {
        return id;
    }
}
