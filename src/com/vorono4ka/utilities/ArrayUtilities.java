package com.vorono4ka.utilities;

public class ArrayUtilities {
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
}
