package dev.donutquine.utilities;

import java.util.function.Function;

public class PolygonUtils {
    private PolygonUtils() {}

    public static boolean isInside(
        float x, float y, int n,
        Function<Integer, Float> getX,
        Function<Integer, Float> getY
    ) {
        boolean inside = false;

        for (int i = 0, j = n - 1; i < n; j = i++) {
            float xi = getX.apply(i), yi = getY.apply(i);
            float xj = getX.apply(j), yj = getY.apply(j);

            boolean intersect = ((yi > y) != (yj > y)) && 
                                (x < (xj - xi) * (y - yi) / (double) (yj - yi) + xi);
            if (intersect) {
                inside = !inside;
            }
        }

        return inside;
    }

    public static int getStripPointIndex(int index, int count) {
        if (2 * index < count) {
            return 2 * index;
        } else {
            int i = index - count / 2;
            return count - 1 - 2 * i;
        }
    }
}
