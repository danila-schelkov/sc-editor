package com.vorono4ka.utilities;

import com.vorono4ka.swf.ColorTransform;

public final class RenderConfig {
    public static int getUnknownRenderModification(ColorTransform colorTransform, int a4) {
        int redMultiplier = colorTransform.getRedMultiplier();
        int greenMultiplier = colorTransform.getGreenMultiplier();
        int blueMultiplier = colorTransform.getBlueMultiplier();
        int alpha = colorTransform.getAlpha();

        int redAddition = colorTransform.getRedAddition();
        int greenAddition = colorTransform.getGreenAddition();
        int blueAddition = colorTransform.getBlueAddition();

        int v45 = a4;
        if (redMultiplier + greenMultiplier + blueMultiplier + alpha != 1020)
            v45 = a4 | 1;
        if (redAddition + greenAddition + blueAddition > 0)
            v45 = a4 | 3;
        return v45;
    }
}
