package com.vorono4ka.editor.renderer.impl.gl;

import com.jogamp.opengl.GL3;
import com.vorono4ka.editor.renderer.gl.GLRendererContext;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class JoglRendererContext implements GLRendererContext {
    private final GL3 gl;

    public JoglRendererContext(GL3 gl) {
        this.gl = gl;
    }

    @Override
    public int glGenVertexArray() {
        int[] ids = new int[1];
        gl.glGenVertexArrays(1, ids, 0);
        return ids[0];
    }

    @Override
    public void glBindVertexArray(int id) {
        gl.glBindVertexArray(id);
    }

    @Override
    public void glDeleteVertexArray(int id) {
        gl.glDeleteVertexArrays(1, new int[]{id}, 0);
    }

    @Override
    public void glEnableVertexAttribArray(int index) {
        gl.glEnableVertexAttribArray(index);
    }

    @Override
    public void glDisableVertexAttribArray(int index) {
        gl.glDisableVertexAttribArray(index);
    }

    @Override
    public void glVertexAttribPointer(int layout, int size, int type, boolean normalized, int stride, int offset) {
        gl.glVertexAttribPointer(layout, size, type, normalized, stride, offset);
    }

    @Override
    public int glGenBuffer() {
        int[] ids = new int[1];
        gl.glGenBuffers(1, ids, 0);
        return ids[0];
    }

    @Override
    public void glBindBuffer(int bufferType, int id) {
        gl.glBindBuffer(bufferType, id);
    }

    @Override
    public void glDeleteBuffer(int id) {
        gl.glDeleteBuffers(1, new int[]{id}, 0);
    }

    @Override
    public void glBufferData(int bufferType, long byteLength, Buffer buffer, int usage) {
        gl.glBufferData(bufferType, byteLength, buffer, usage);
    }

    @Override
    public void glBufferSubData(int bufferType, int offset, long byteLength, Buffer buffer) {
        gl.glBufferSubData(bufferType, offset, byteLength, buffer);
    }

    @Override
    public void glDrawElements(int drawMode, int elementCount, int glUnsignedInt, int indices) {
        gl.glDrawElements(drawMode, elementCount, glUnsignedInt, indices);
    }

    @Override
    public int glGenTexture() {
        IntBuffer ids = IntBuffer.allocate(1);
        gl.glGenTextures(1, ids);
        return ids.get(0);
    }

    @Override
    public void glActiveTexture(int textureSlot) {
        gl.glActiveTexture(textureSlot);
    }

    @Override
    public void glBindTexture(int textureType, int id) {
        gl.glBindTexture(textureType, id);
    }

    @Override
    public void glDeleteTexture(int id) {
        gl.glDeleteTextures(1, new int[]{id}, 0);
    }

    @Override
    public void glDeleteTextures(int count, int[] ids, int offset) {
        gl.glDeleteTextures(count, ids, offset);
    }

    @Override
    public void glGenerateMipmap(int textureType) {
        gl.glGenerateMipmap(textureType);
    }

    @Override
    public void glGetTexImage(int textureType, int level, int internalFormat, int pixelType, IntBuffer pixels) {
        gl.glGetTexImage(textureType, level, internalFormat, pixelType, pixels);
    }

    @Override
    public void glGetTexLevelParameteriv(int textureType, int level, int type, IntBuffer result) {
        gl.glGetTexLevelParameteriv(textureType, level, type, result);
    }

    @Override
    public void glGetCompressedTexImage(int textureType, int level, IntBuffer data) {
        gl.glGetCompressedTexImage(textureType, level, data);
    }

    @Override
    public void glTexParameteri(int textureType, int type, int value) {
        gl.glTexParameteri(textureType, type, value);
    }

    @Override
    public void glTexParameteriv(int textureType, int type, IntBuffer value) {
        gl.glTexParameteriv(textureType, type, value);
    }

    @Override
    public void glTexImage2D(int textureType, int level, int internalFormat, int width, int height, int border, int format, int pixelType, Buffer data) {
        gl.glTexImage2D(textureType, level, internalFormat, width, height, border, format, pixelType, data);
    }

    @Override
    public void glCompressedTexImage2D(int textureType, int level, int internalFormat, int width, int height, int border, int byteLength, ByteBuffer data) {
        gl.glCompressedTexImage2D(textureType, level, internalFormat, width, height, border, byteLength, data);
    }

    @Override
    public void glTexSubImage2D(int target, int level, int xOffset, int yOffset, int width, int height, int format, int pixelType, Buffer pixels) {
        gl.glTexSubImage2D(target, level, xOffset, yOffset, width, height, format, pixelType, pixels);
    }

    @Override
    public int glGenFramebuffer() {
        IntBuffer ids = IntBuffer.allocate(1);
        gl.glGenFramebuffers(1, ids);
        return ids.get(0);
    }

    @Override
    public void glBindFramebuffer(int target, int id) {
        gl.glBindFramebuffer(target, id);
    }

    @Override
    public void glDeleteFramebuffer(int id) {
        gl.glDeleteFramebuffers(1, new int[]{id}, 0);
    }

    @Override
    public void glFramebufferRenderbuffer(int target, int attachmentType, int renderbufferTarget, int renderbuffer) {
        gl.glFramebufferRenderbuffer(target, attachmentType, renderbufferTarget, renderbuffer);
    }

    @Override
    public void glFramebufferTexture2D(int target, int attachmentType, int textureTarget, int texture, int level) {
        gl.glFramebufferTexture2D(target, attachmentType, textureTarget, texture, level);
    }

    @Override
    public int getBoundFramebuffer(int target) {
        return gl.getBoundFramebuffer(target);
    }

    @Override
    public int glCheckFramebufferStatus(int target) {
        return gl.glCheckFramebufferStatus(target);
    }

    @Override
    public int glGenRenderbuffer() {
        IntBuffer ids = IntBuffer.allocate(1);
        gl.glGenRenderbuffers(1, ids);
        return ids.get(0);
    }

    @Override
    public void glBindRenderbuffer(int bufferType, int id) {
        gl.glBindRenderbuffer(bufferType, id);
    }

    @Override
    public void glDeleteRenderbuffer(int id) {
        gl.glDeleteRenderbuffers(1, new int[]{id}, 0);
    }

    @Override
    public void glRenderbufferStorage(int target, int internalFormat, int width, int height) {
        gl.glRenderbufferStorage(target, internalFormat, width, height);
    }

    @Override
    public void glViewport(int x, int y, int width, int height) {
        gl.glViewport(x, y, width, height);
    }

    @Override
    public void glEnable(int capability) {
        gl.glEnable(capability);
    }

    @Override
    public void glDisable(int capability) {
        gl.glDisable(capability);
    }

    @Override
    public void glBlendEquation(int mode) {
        gl.glBlendEquation(mode);
    }

    @Override
    public void glBlendFunc(int sFactor, int dFactor) {
        gl.glBlendFunc(sFactor, dFactor);
    }

    @Override
    public void glClearColor(float r, float g, float b, float a) {
        gl.glClearColor(r, g, b, a);
    }

    @Override
    public void glClear(int buffersMask) {
        gl.glClear(buffersMask);
    }

    @Override
    public void glStencilMask(int mask) {
        gl.glStencilMask(mask);
    }

    @Override
    public void glClearStencil(int index) {
        gl.glClearStencil(index);
    }

    @Override
    public void glStencilFunc(int func, int ref, int mask) {
        gl.glStencilFunc(func, ref, mask);
    }

    @Override
    public void glStencilOp(int sFail, int dpFail, int dpPass) {
        gl.glStencilOp(sFail, dpFail, dpPass);
    }

    @Override
    public void glColorMask(boolean r, boolean g, boolean b, boolean a) {
        gl.glColorMask(r, g, b, a);
    }

    @Override
    public void glDepthMask(boolean enabled) {
        gl.glDepthMask(enabled);
    }

    @Override
    public int glCreateShader(int shaderType) {
        return gl.glCreateShader(shaderType);
    }

    @Override
    public void glDeleteShader(int shader) {
        gl.glDeleteShader(shader);
    }

    @Override
    public void glShaderSource(int shader, String source) {
        gl.glShaderSource(shader, 1, new String[]{source}, null);
    }

    @Override
    public void glCompileShader(int shader) {
        gl.glCompileShader(shader);
    }

    @Override
    public void glGetShaderInfoLog(int shader, int logBufferLength, IntBuffer length, ByteBuffer logBuffer) {
        gl.glGetShaderInfoLog(shader, logBufferLength, length, logBuffer);
    }

    @Override
    public void glGetShaderiv(int shader, int parameter, IntBuffer result) {
        gl.glGetShaderiv(shader, parameter, result);
    }

    @Override
    public int glCreateProgram() {
        return gl.glCreateProgram();
    }

    @Override
    public void glUseProgram(int program) {
        gl.glUseProgram(program);
    }

    @Override
    public void glDeleteProgram(int program) {
        gl.glDeleteProgram(program);
    }

    @Override
    public void glAttachShader(int program, int shader) {
        gl.glAttachShader(program, shader);
    }

    @Override
    public void glLinkProgram(int program) {
        gl.glLinkProgram(program);
    }

    @Override
    public void glGetProgramiv(int program, int parameter, IntBuffer result) {
        gl.glGetProgramiv(program, parameter, result);
    }

    @Override
    public void glGetProgramInfoLog(int program, int logBufferLength, IntBuffer length, ByteBuffer logBuffer) {
        gl.glGetProgramInfoLog(program, logBufferLength, length, logBuffer);
    }

    @Override
    public int glGetUniformLocation(int program, String uniformName) {
        return gl.glGetUniformLocation(program, uniformName);
    }

    @Override
    public void glUniformMatrix4fv(int uniformLocation, int count, boolean transpose, FloatBuffer matrixBuffer) {
        gl.glUniformMatrix4fv(uniformLocation, count, transpose, matrixBuffer);
    }

    @Override
    public void glPixelStorei(int parameter, int value) {
        gl.glPixelStorei(parameter, value);
    }

    @Override
    public boolean isExtensionAvailable(String extension) {
        return gl.isExtensionAvailable(extension);
    }

    @Override
    public int glGetError() {
        return gl.glGetError();
    }

    @Override
    public String glGetString(int target) {
        return gl.glGetString(target);
    }
}
