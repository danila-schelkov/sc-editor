package dev.donutquine.utilities;

import dev.donutquine.swf.ColorTransform;

public final class RenderConfig {
    public static int getUnknownRenderModification(ColorTransform colorTransform) {
        int redAddition = colorTransform.getRedAddition() & 0xFF;
        int greenAddition = colorTransform.getGreenAddition() & 0xFF;
        int blueAddition = colorTransform.getBlueAddition() & 0xFF;

        if (redAddition + greenAddition + blueAddition > 0) {
            return 3;
        }

        int redMultiplier = colorTransform.getRedMultiplier() & 0xFF;
        int greenMultiplier = colorTransform.getGreenMultiplier() & 0xFF;
        int blueMultiplier = colorTransform.getBlueMultiplier() & 0xFF;
        int alpha = colorTransform.getAlpha() & 0xFF;

        if (redMultiplier + greenMultiplier + blueMultiplier + alpha != 1020) {
            return 1;
        }

        return 0;
    }
}
