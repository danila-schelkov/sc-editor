package com.vorono4ka.editor.renderer.impl.buffers;

import java.nio.IntBuffer;

public interface IntBufferObject extends BufferObject {
    // Don't forget to prepare the buffer for reading by calling buffer.clear()
    void subData(int offset, IntBuffer buffer);
}
