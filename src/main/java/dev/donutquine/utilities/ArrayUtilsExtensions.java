package dev.donutquine.utilities;

public final class ArrayUtilsExtensions {
    @SafeVarargs
	public static Object[] concat(Object[]... arrays) {
        int resultSize = 0;
        for (Object[] array : arrays) {
            resultSize += array.length;
        }

        Object[] result = new Object[resultSize];

        int position = 0;
        for (Object[] array : arrays) {
            System.arraycopy(array, 0, result, position, array.length);
            position += array.length;
        }

        return result;
    }
}
