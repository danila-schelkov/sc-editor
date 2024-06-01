package com.vorono4ka.utilities;

import java.nio.IntBuffer;

public final class BufferUtils {
    public static int[] toArray(IntBuffer buffer) {
        int[] array = new int[buffer.capacity()];
        buffer.rewind();
        buffer.get(array);
        return array;
    }
}
