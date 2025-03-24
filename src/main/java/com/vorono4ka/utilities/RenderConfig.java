package com.vorono4ka.utilities;

import com.vorono4ka.swf.ColorTransform;

public final class RenderConfig {
    public static int getUnknownRenderModification(ColorTransform colorTransform, int a4) {
        int redMultiplier = colorTransform.getRedMultiplier() & 0xFF;
        int greenMultiplier = colorTransform.getGreenMultiplier() & 0xFF;
        int blueMultiplier = colorTransform.getBlueMultiplier() & 0xFF;
        int alpha = colorTransform.getAlpha() & 0xFF;

        int redAddition = colorTransform.getRedAddition() & 0xFF;
        int greenAddition = colorTransform.getGreenAddition() & 0xFF;
        int blueAddition = colorTransform.getBlueAddition() & 0xFF;

        int v45 = a4;
        if (redMultiplier + greenMultiplier + blueMultiplier + alpha != 1020)
            v45 = a4 | 1;
        if (redAddition + greenAddition + blueAddition > 0)
            v45 = a4 | 3;
        return v45;
    }
}
