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

    public void read(ByteStream stream) {
        this.redAddition = stream.readInt8();
        this.greenAddition = stream.readInt8();
        this.blueAddition = stream.readInt8();
        this.alpha = stream.readInt8();
        this.redMultiplier = stream.readInt8();
        this.greenMultiplier = stream.readInt8();
        this.blueMultiplier = stream.readInt8();
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
}
