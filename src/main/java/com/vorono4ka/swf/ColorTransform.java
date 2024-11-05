package com.vorono4ka.swf;

import com.vorono4ka.flatloader.SerializeType;
import com.vorono4ka.flatloader.annotations.FlatType;
import com.vorono4ka.math.MathHelper;
import com.vorono4ka.streams.ByteStream;
import com.vorono4ka.swf.constants.Tag;
import com.vorono4ka.swf.originalObjects.Savable;

public class ColorTransform implements Savable {
    @FlatType(value = SerializeType.INT8, isUnsigned = true)
    private int redMultiplier;
    @FlatType(value = SerializeType.INT8, isUnsigned = true)
    private int greenMultiplier;
    @FlatType(value = SerializeType.INT8, isUnsigned = true)
    private int blueMultiplier;
    @FlatType(value = SerializeType.INT8, isUnsigned = true)
    private int alpha;
    @FlatType(value = SerializeType.INT8, isUnsigned = true)
    private int redAddition;
    @FlatType(value = SerializeType.INT8, isUnsigned = true)
    private int greenAddition;
    @FlatType(value = SerializeType.INT8, isUnsigned = true)
    private int blueAddition;

    public ColorTransform() {
        this.redMultiplier = 0xFF;
        this.greenMultiplier = 0xFF;
        this.blueMultiplier = 0xFF;
        this.alpha = 0xFF;
    }

    public ColorTransform(byte redAddition, byte greenAddition, byte blueAddition, byte alpha, byte redMultiplier, byte greenMultiplier, byte blueMultiplier) {
        this.redAddition = redAddition;
        this.greenAddition = greenAddition;
        this.blueAddition = blueAddition;
        this.alpha = alpha;
        this.redMultiplier = redMultiplier;
        this.greenMultiplier = greenMultiplier;
        this.blueMultiplier = blueMultiplier;
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

    @Override
    public void save(ByteStream stream) {
        stream.writeUnsignedChar(this.redAddition);
        stream.writeUnsignedChar(this.greenAddition);
        stream.writeUnsignedChar(this.blueAddition);
        stream.writeUnsignedChar(this.alpha);
        stream.writeUnsignedChar(this.redMultiplier);
        stream.writeUnsignedChar(this.greenMultiplier);
        stream.writeUnsignedChar(this.blueMultiplier);
    }

    @Override
    public Tag getTag() {
        return Tag.COLOR_TRANSFORM;
    }

    public void multiply(ColorTransform colorTransform) {
        this.redMultiplier = (int) MathHelper.clamp(this.redMultiplier * colorTransform.redMultiplier / 255f, 0, 255);
        this.greenMultiplier = (int) MathHelper.clamp(this.greenMultiplier * colorTransform.greenMultiplier / 255f, 0, 255);
        this.blueMultiplier = (int) MathHelper.clamp(this.blueMultiplier * colorTransform.blueMultiplier / 255f, 0, 255);
        this.alpha = (int) MathHelper.clamp(this.alpha * colorTransform.alpha / 255f, 0, 255);
        this.redAddition = MathHelper.clamp(this.redAddition + colorTransform.redAddition, 0, 255);
        this.greenAddition = MathHelper.clamp(this.greenAddition + colorTransform.greenAddition, 0, 255);
        this.blueAddition = MathHelper.clamp(this.blueAddition + colorTransform.blueAddition, 0, 255);
    }

    public ColorTransform setMulColor(float red, float green, float blue) {
        this.redMultiplier = (int) (MathHelper.clamp(red, 0f, 1f) * 255);
        this.greenMultiplier = (int) (MathHelper.clamp(green, 0f, 1f) * 255);
        this.blueMultiplier = (int) (MathHelper.clamp(blue, 0f, 1f) * 255);
        return this;
    }

    public ColorTransform setAddColor(float red, float green, float blue) {
        this.redAddition = (int) (MathHelper.clamp(red, 0f, 1f) * 255);
        this.greenAddition = (int) (MathHelper.clamp(green, 0f, 1f) * 255);
        this.blueAddition = (int) (MathHelper.clamp(blue, 0f, 1f) * 255);
        return this;
    }

    public ColorTransform setAlpha(float alpha) {
        this.alpha = (int) (MathHelper.clamp(alpha, 0f, 1f) * 255);
        return this;
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
