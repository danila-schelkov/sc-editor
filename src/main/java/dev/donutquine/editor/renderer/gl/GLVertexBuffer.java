package dev.donutquine.editor.renderer.gl;

import dev.donutquine.editor.renderer.DrawMode;
import dev.donutquine.editor.renderer.VertexBuffer;
import dev.donutquine.editor.renderer.gl.buffers.*;
import dev.donutquine.editor.renderer.shader.Attribute;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class GLVertexBuffer extends VertexBuffer {
    private final GLRendererContext gl;
    private final int usage;
    private final int glDrawMode;

    private Vao vao;
    private FloatBufferObject vbo;
    private IntBufferObject ebo;

    public GLVertexBuffer(GLRendererContext gl, int usage, Attribute... attributes) {
        super(attributes);

        this.gl = gl;
        this.usage = usage;

        this.glDrawMode = getGLDrawMode(this.drawMode);
    }

    @Override
    public void init(int vertexCapacity, int indexCapacity) {
        this.vao = new Vao(gl);
        this.vao.bind();

        this.vbo = new Vbo(this.gl, vertexCapacity, this.usage);
        this.ebo = new Ebo(this.gl, indexCapacity, this.usage);
        this.ebo.bind();

        linkAttributes();

        this.vao.unbind();
        this.ebo.unbind();
    }

    @Override
    public void render(int offset, int count) {
        prepareRender();
        renderPart(offset, count);
        finishRender();
    }

    @Override
    public void prepareRender() {
        this.bind();

//        linkAttributes();
    }

    @Override
    public void renderPart(int offset, int count) {
        this.gl.glDrawElements(this.glDrawMode, count, GLConstants.GL_UNSIGNED_INT, offset * Integer.BYTES);
    }

    @Override
    public void finishRender() {
//        for (Attribute attribute : attributes) {
//            this.gl.glDisableVertexAttribArray(attribute.layout());
//        }

        this.unbind();
    }

    public void bind() {
        this.vao.bind();
//        this.vbo.bind();
//        this.ebo.bind();
    }

    public void unbind() {
        this.vao.unbind();
//        this.vbo.unbind();
//        this.ebo.unbind();
    }

    @Override
    public void delete() {
        this.vao.delete();
        this.vbo.delete();
        this.ebo.delete();
    }

    @Override
    public void updateVertices(int offset, FloatBuffer vertices) {
        this.vbo.subData(offset, vertices);
    }

    @Override
    public void updateIndices(int offset, IntBuffer indices) {
        this.ebo.subData(offset, indices);
    }

    private void linkAttributes() {
        this.vbo.bind();
        int offset = 0;
        for (Attribute attribute : attributes) {
            linkAttrib(attribute.layout(), attribute.size(), attribute.glType(), vertexSizeInBytes, offset);
            offset += attribute.sizeInBytes();
        }
        this.vbo.unbind();
    }

    private void linkAttrib(int layout, int size, int type, int stride, int offset) {
        gl.glEnableVertexAttribArray(layout);
        gl.glVertexAttribPointer(layout, size, type, false, stride, offset);
    }

    private static int getGLDrawMode(DrawMode drawMode) {
        return switch (drawMode) {
            case TRIANGLES -> GLConstants.GL_TRIANGLES;
            case TRIANGLE_STRIP -> GLConstants.GL_TRIANGLE_STRIP;
            case TRIANGLE_FAN -> GLConstants.GL_TRIANGLE_FAN;
            case LINES -> GLConstants.GL_LINES;
            case POINTS -> GLConstants.GL_POINTS;
        };
    }
}
