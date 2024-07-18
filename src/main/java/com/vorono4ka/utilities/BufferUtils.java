package com.vorono4ka.utilities;

import java.nio.*;

public final class BufferUtils {
    public static final ByteOrder NATIVE_ORDER = ByteOrder.nativeOrder();

    public static int[] toArray(IntBuffer buffer) {
        int[] array = new int[buffer.capacity()];
        buffer.rewind();
        buffer.get(array);
        return array;
    }

    public static ByteBuffer wrapDirect(byte... bytes) {
        ByteBuffer byteBuffer = allocateDirect(bytes.length * Byte.BYTES);
        byteBuffer.put(bytes);
        byteBuffer.position(0);
        return byteBuffer;
    }

    public static ShortBuffer wrapDirect(short... shorts) {
        ByteBuffer byteBuffer = allocateDirect(shorts.length * Short.BYTES);
        ShortBuffer shortBuffer = byteBuffer.asShortBuffer();
        shortBuffer.put(0, shorts);
        return shortBuffer;
    }

    public static IntBuffer wrapDirect(int... ints) {
        ByteBuffer byteBuffer = allocateDirect(ints.length * Integer.BYTES);
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        intBuffer.put(0, ints);
        return intBuffer;
    }

    public static FloatBuffer wrapDirect(float... floats) {
        ByteBuffer byteBuffer = allocateDirect(floats.length * Float.BYTES);
        FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
        floatBuffer.put(0, floats);
        return floatBuffer;
    }

    public static ByteBuffer allocateDirect(int size) {
        return ByteBuffer.allocateDirect(size).order(NATIVE_ORDER);
    }
}
