package com.vorono4ka.editor.renderer.impl;

import com.jogamp.opengl.GL3;
import com.vorono4ka.editor.renderer.shader.Attribute;
import com.vorono4ka.editor.renderer.Batch;
import com.vorono4ka.editor.renderer.shader.Shader;
import com.vorono4ka.editor.renderer.impl.buffers.FloatBufferObject;
import com.vorono4ka.editor.renderer.impl.buffers.IntBufferObject;
import com.vorono4ka.editor.renderer.impl.buffers.JoglEbo;
import com.vorono4ka.editor.renderer.impl.buffers.JoglVbo;
import com.vorono4ka.editor.renderer.texture.RenderableTexture;

public class JoglBatch extends Batch {
    private final GL3 gl;

    private Vao vao;
    private FloatBufferObject vbo;
    private IntBufferObject ebo;

    public JoglBatch(Shader shader, RenderableTexture texture, int stencilRenderingState, GL3 gl) {
        super(shader, texture, stencilRenderingState);

        this.gl = gl;
    }

    public void init() {
        this.vao = new Vao(gl);
        this.vao.bind();

        this.vbo = new JoglVbo(gl, this.vertices.capacity(), GL3.GL_DYNAMIC_DRAW);
        this.ebo = new JoglEbo(gl, this.indices.capacity(), GL3.GL_DYNAMIC_DRAW);
        this.ebo.bind();

        linkAttributes();
        this.vao.bind();

        this.vao.unbind();
        this.ebo.unbind();
    }

    public void renderInternal() {
        this.vbo.subData(0, this.vertices);
        this.ebo.subData(0, this.indices);

        this.vao.bind();
        this.gl.glDrawElements(GL3.GL_TRIANGLES, this.triangleCount * 3, GL3.GL_UNSIGNED_INT, 0);
        this.vao.unbind();
    }

    public void delete() {
        this.vao.delete();
        this.vbo.delete();
        this.ebo.delete();
    }

    private void linkAttributes() {
        int offset = 0;
        for (Attribute attribute : attributes) {
            this.vao.linkAttrib(this.vbo, attribute.layout(), attribute.size(), attribute.glType(), vertexSizeInBytes, offset);
            offset += attribute.sizeInBytes();
        }
    }
}
