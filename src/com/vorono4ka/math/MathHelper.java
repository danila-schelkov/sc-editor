package com.vorono4ka.math;

public class MathHelper {
    public static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }

    public static float round(float value, int digits) {
        if (digits == 0) return Math.round(value);

        double pow = Math.pow(10, digits);
        return (float) (Math.round(value * pow) / pow);
    }
}
