package com.vorono4ka.editor.renderer.gl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public interface GLRendererContext {
    // Vertex Array Object
    int glGenVertexArray();

    void glBindVertexArray(int id);

    void glDeleteVertexArray(int id);

    void glEnableVertexAttribArray(int index);

    void glDisableVertexAttribArray(int index);

    void glVertexAttribPointer(int layout, int size, int type, boolean normalized, int stride, int offset);

    // Buffers
    int glGenBuffer();

    void glBindBuffer(int bufferType, int id);

    void glDeleteBuffer(int id);

    void glBufferData(int bufferType, long byteLength, Buffer buffer, int usage);

    void glBufferSubData(int bufferType, int offset, long byteLength, Buffer buffer);

    void glDrawElements(int drawMode, int triangleCount, int glUnsignedInt, int i1);

    // Textures
    int glGenTexture();

    void glBindTexture(int textureType, int id);

    void glDeleteTexture(int id);

    void glDeleteTextures(int count, int[] ids, int offset);

    void glGenerateMipmap(int textureType);

    void glGetTexImage(int textureType, int level, int internalFormat, int pixelType, IntBuffer pixels);

    void glGetTexLevelParameteriv(int textureType, int level, int type, IntBuffer result);

    void glGetCompressedTexImage(int textureType, int level, IntBuffer data);

    void glTexParameteri(int textureType, int type, int value);

    void glTexParameteriv(int textureType, int type, IntBuffer value);

    void glTexImage2D(int textureType, int level, int internalFormat, int width, int height, int border, int format, int pixelType, Buffer data);

    void glCompressedTexImage2D(int textureType, int level, int internalFormat, int width, int height, int border, int byteLength, ByteBuffer data);

    void glTexSubImage2D(int target, int level, int xOffset, int yOffset, int width, int height, int format, int pixelType, Buffer pixels);

    // Framebuffer
    int glGenFramebuffer();

    void glBindFramebuffer(int target, int id);

    void glDeleteFramebuffer(int id);

    void glFramebufferRenderbuffer(int target, int attachmentType, int renderbufferTarget, int renderbuffer);

    void glFramebufferTexture2D(int target, int attachmentType, int textureTarget, int texture, int level);

    int getBoundFramebuffer(int target);

    int glCheckFramebufferStatus(int target);

    // Renderbuffer
    int glGenRenderbuffer();

    void glBindRenderbuffer(int bufferType, int id);

    void glDeleteRenderbuffer(int id);

    void glRenderbufferStorage(int target, int internalFormat, int width, int height);

    void glEnable(int capability);

    void glDisable(int capability);

    void glBlendEquation(int mode);

    void glBlendFunc(int sFactor, int dFactor);

    void glClear(int buffersMask);

    void glClearColor(float r, float g, float b, float a);

    void glViewport(int x, int y, int width, int height);

    void glStencilMask(int mask);

    void glClearStencil(int index);

    void glStencilFunc(int func, int ref, int mask);

    void glStencilOp(int sFail, int dpFail, int dpPass);

    void glColorMask(boolean r, boolean g, boolean b, boolean a);

    void glDepthMask(boolean enabled);

    // Shader
    int glCreateShader(int shaderType);

    void glDeleteShader(int shader);

    void glShaderSource(int shader, String source);

    void glCompileShader(int shader);

    void glGetShaderInfoLog(int shader, int logBufferLength, IntBuffer length, ByteBuffer logBuffer);

    void glGetShaderiv(int shader, int parameter, IntBuffer result);

    // Shader Program
    int glCreateProgram();

    void glUseProgram(int program);

    void glDeleteProgram(int program);

    void glAttachShader(int program, int shader);

    void glLinkProgram(int program);

    void glGetProgramiv(int program, int parameter, IntBuffer result);

    void glGetProgramInfoLog(int program, int logBufferLength, IntBuffer length, ByteBuffer logBuffer);

    // Program Uniforms
    int glGetUniformLocation(int program, String uniformName);

    void glUniformMatrix4fv(int uniformLocation, int count, boolean transpose, FloatBuffer matrixBuffer);

    void glPixelStorei(int parameter, int value);

    boolean isExtensionAvailable(String extension);

    // Some getters
    String glGetString(int i);

    int glGetError();
}
