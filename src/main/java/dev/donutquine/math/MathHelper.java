package dev.donutquine.math;

public class MathHelper {
    public static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    public static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }

    public static float round(float value, int digits) {
        if (digits == 0) return Math.round(value);

        double pow = Math.pow(10, digits);
        return (float) (Math.round(value * pow) / pow);
    }
}
