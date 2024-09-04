package com.vorono4ka.utilities;

import java.util.function.Supplier;

public final class ArrayUtils {
    public static int indexOf(byte[] array, byte[] bytesToFind) {
        for (int i = 0; i < array.length; i++) {
            boolean found = true;
            for (int j = 0; j < bytesToFind.length; j++) {
                if (array[i+j] != bytesToFind[j]) {
                    found = false;
                    break;
                }
            }

            if (found) {
                return i;
            }
        }

        return -1;
    }

    public static int indexOf(byte[] array, byte value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                return i;
            }
        }

        return -1;
    }

    public static int indexOf(short[] array, short value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                return i;
            }
        }

        return -1;
    }

    public static int indexOf(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                return i;
            }
        }

        return -1;
    }

    public static boolean contains(byte[] array, byte value) {
        return indexOf(array, value) >= 0;
    }

    public static boolean contains(short[] array, short value) {
        return indexOf(array, value) >= 0;
    }

    public static boolean contains(int[] array, int value) {
        return indexOf(array, value) >= 0;
    }

    public static Byte[] toObject(byte[] primitives) {
        Byte[] result = new Byte[primitives.length];
        for (int i = 0; i < primitives.length; i++) {
            result[i] = primitives[i];
        }

        return result;
    }

    public static byte[] toPrimitive(Byte[] objects) {
        byte[] result = new byte[objects.length];
        for (int i = 0; i < objects.length; i++) {
            result[i] = objects[i];
        }

        return result;
    }

    public static Short[] toObject(short[] primitives) {
        Short[] result = new Short[primitives.length];
        for (int i = 0; i < primitives.length; i++) {
            result[i] = primitives[i];
        }

        return result;
    }

    public static short[] toPrimitive(Short[] objects) {
        short[] result = new short[objects.length];
        for (int i = 0; i < objects.length; i++) {
            result[i] = objects[i];
        }

        return result;
    }

    public static Integer[] toObject(int[] primitives) {
        Integer[] result = new Integer[primitives.length];
        for (int i = 0; i < primitives.length; i++) {
            result[i] = primitives[i];
        }

        return result;
    }

    public static int[] toPrimitive(Integer[] objects) {
        int[] result = new int[objects.length];
        for (int i = 0; i < objects.length; i++) {
            result[i] = objects[i];
        }

        return result;
    }

    public static <T> void fill(T[] array, Supplier<T> objectSupplier) {
        for (int i = 0; i < array.length; i++) {
            array[i] = objectSupplier.get();
        }
    }
}
