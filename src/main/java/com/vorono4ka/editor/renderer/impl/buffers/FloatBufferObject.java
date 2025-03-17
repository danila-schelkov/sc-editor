package com.vorono4ka.editor.renderer.impl.buffers;

import java.nio.FloatBuffer;

public interface FloatBufferObject extends BufferObject {
    // Don't forget to prepare the buffer for reading by calling buffer.clear()
    void subData(int offset, FloatBuffer buffer);
}
