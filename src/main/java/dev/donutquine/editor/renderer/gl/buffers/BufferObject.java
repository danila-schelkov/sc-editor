package dev.donutquine.editor.renderer.gl.buffers;

public interface BufferObject {
    void bind();

    void unbind();

    void delete();
}
