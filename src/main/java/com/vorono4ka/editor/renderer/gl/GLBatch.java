package com.vorono4ka.editor.renderer.gl;

import com.vorono4ka.editor.renderer.Batch;
import com.vorono4ka.editor.renderer.RenderStencilState;
import com.vorono4ka.editor.renderer.gl.buffers.Ebo;
import com.vorono4ka.editor.renderer.gl.buffers.FloatBufferObject;
import com.vorono4ka.editor.renderer.gl.buffers.IntBufferObject;
import com.vorono4ka.editor.renderer.gl.buffers.Vbo;
import com.vorono4ka.editor.renderer.shader.Attribute;
import com.vorono4ka.editor.renderer.shader.Shader;
import com.vorono4ka.editor.renderer.texture.RenderableTexture;

public class GLBatch extends Batch {
    private final GLRendererContext gl;

    private Vao vao;
    private FloatBufferObject vbo;
    private IntBufferObject ebo;

    public GLBatch(Shader shader, RenderableTexture texture, RenderStencilState stencilRenderingState, GLRendererContext gl) {
        super(shader, texture, stencilRenderingState);

        this.gl = gl;
    }

    public void init() {
        this.vao = new Vao(gl);
        this.vao.bind();

        this.vbo = new Vbo(gl, this.vertices.capacity(), GLConstants.GL_DYNAMIC_DRAW);
        this.ebo = new Ebo(gl, this.indices.capacity(), GLConstants.GL_DYNAMIC_DRAW);
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
        this.gl.glDrawElements(GLConstants.GL_TRIANGLES, this.triangleCount * 3, GLConstants.GL_UNSIGNED_INT, 0);
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
