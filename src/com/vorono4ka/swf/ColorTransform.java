package com.vorono4ka.swf;

import com.vorono4ka.math.MathHelper;
import com.vorono4ka.streams.ByteStream;

public class ColorTransform {
    private int redAddition;
    private int greenAddition;
    private int blueAddition;
    private int redMultiplier;
    private int greenMultiplier;
    private int blueMultiplier;
    private int alpha;

    public ColorTransform() {
        this.redMultiplier = 255;
        this.greenMultiplier = 255;
        this.blueMultiplier = 255;
        this.alpha = 255;
    }

    public ColorTransform(ColorTransform colorTransform) {
        this.redAddition = colorTransform.redAddition;
        this.greenAddition = colorTransform.greenAddition;
        this.blueAddition = colorTransform.blueAddition;
        this.alpha = colorTransform.alpha;
        this.redMultiplier = colorTransform.redMultiplier;
        this.greenMultiplier = colorTransform.greenMultiplier;
        this.blueMultiplier = colorTransform.blueMultiplier;
    }

    public void read(ByteStream stream) {
        this.redAddition = stream.readUnsignedChar();
        this.greenAddition = stream.readUnsignedChar();
        this.blueAddition = stream.readUnsignedChar();
        this.alpha = stream.readUnsignedChar();
        this.redMultiplier = stream.readUnsignedChar();
        this.greenMultiplier = stream.readUnsignedChar();
        this.blueMultiplier = stream.readUnsignedChar();
    }

    public void apply(ColorTransform colorTransform) {
        this.redMultiplier *= colorTransform.redMultiplier / 255f;
        this.greenMultiplier *= colorTransform.greenMultiplier / 255f;
        this.blueMultiplier *= colorTransform.blueMultiplier / 255f;
        this.alpha *= colorTransform.alpha / 255f;
        this.redAddition = Math.max(this.redAddition + colorTransform.redAddition, 255);
        this.greenAddition = Math.max(this.greenAddition + colorTransform.greenAddition, 255);
        this.blueAddition = Math.max(this.blueAddition + colorTransform.blueAddition, 255);
    }

    public void setMulColor(float red, float green, float blue) {
        this.redMultiplier = (int) (MathHelper.clamp(red, 0f, 1f) * 255);
        this.greenMultiplier = (int) (MathHelper.clamp(green, 0f, 1f) * 255);
        this.blueMultiplier = (int) (MathHelper.clamp(blue, 0f, 1f) * 255);
    }

    public void setAddColor(float red, float green, float blue) {
        this.redAddition = (int) (MathHelper.clamp(red, 0f, 1f) * 255);
        this.greenAddition = (int) (MathHelper.clamp(green, 0f, 1f) * 255);
        this.blueAddition = (int) (MathHelper.clamp(blue, 0f, 1f) * 255);
    }

    public void setAlpha(float alpha) {
        this.alpha = (int) (MathHelper.clamp(alpha, 0f, 1f) * 255);
    }

    public int getRedMultiplier() {
        return redMultiplier;
    }

    public int getGreenMultiplier() {
        return greenMultiplier;
    }

    public int getBlueMultiplier() {
        return blueMultiplier;
    }

    public int getRedAddition() {
        return redAddition;
    }

    public int getGreenAddition() {
        return greenAddition;
    }

    public int getBlueAddition() {
        return blueAddition;
    }

    public int getAlpha() {
        return alpha;
    }
}
