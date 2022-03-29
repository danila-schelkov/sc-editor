package com.vorono4ka.swf;

import com.vorono4ka.math.MathHelper;
import com.vorono4ka.streams.ByteStream;

public class ColorTransform {
    private float redAddition;
    private float greenAddition;
    private float blueAddition;
    private float redMultiplier;
    private float greenMultiplier;
    private float blueMultiplier;
    private float alpha;

    public ColorTransform() {
        this.redMultiplier = 255f;
        this.greenMultiplier = 255f;
        this.blueMultiplier = 255f;
        this.alpha = 255f;
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
        this.redAddition = Math.max(this.redAddition + colorTransform.redAddition, 255f);
        this.greenAddition = Math.max(this.greenAddition + colorTransform.greenAddition, 255f);
        this.blueAddition = Math.max(this.blueAddition + colorTransform.blueAddition, 255f);
    }

    public void setMulColor(float red, float green, float blue) {
        this.redMultiplier = MathHelper.clamp(red, 0f, 1f) * 255f;
        this.greenMultiplier = MathHelper.clamp(green, 0f, 1f) * 255f;
        this.blueMultiplier = MathHelper.clamp(blue, 0f, 1f) * 255f;
    }

    public void setAddColor(float red, float green, float blue) {
        this.redAddition = MathHelper.clamp(red, 0f, 1f) * 255f;
        this.greenAddition = MathHelper.clamp(green, 0f, 1f) * 255f;
        this.blueAddition = MathHelper.clamp(blue, 0f, 1f) * 255f;
    }

    public void setAlpha(float alpha) {
        this.alpha = MathHelper.clamp(alpha, 0f, 1f);
    }

    public float getRedMultiplier() {
        return redMultiplier;
    }

    public float getGreenMultiplier() {
        return greenMultiplier;
    }

    public float getBlueMultiplier() {
        return blueMultiplier;
    }

    public float getRedAddition() {
        return redAddition;
    }

    public float getGreenAddition() {
        return greenAddition;
    }

    public float getBlueAddition() {
        return blueAddition;
    }

    public float getAlpha() {
        return alpha;
    }
}
