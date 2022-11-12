package com.vorono4ka.utilities;

public class Utilities {
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
}
