package com.vorono4ka.swf.originalObjects;

import com.vorono4ka.flatloader.SerializeType;
import com.vorono4ka.flatloader.annotations.FlatType;
import com.vorono4ka.flatloader.annotations.Offset;
import com.vorono4ka.flatloader.annotations.StructureSize;
import com.vorono4ka.math.ShortRect;
import com.vorono4ka.streams.ByteStream;
import com.vorono4ka.swf.constants.Tag;

import java.util.List;
import java.util.function.Function;

@StructureSize(40)
public class TextFieldOriginal extends DisplayObjectOriginal {
    private transient Tag tag;

    @Offset(0)
    @FlatType(value = SerializeType.INT16, isUnsigned = true)
    private int id;

    @Offset(4)
    private int fontNameReferenceId;
    private transient String fontName;

    @Offset(8)
    private ShortRect bounds;

    @Offset(16)
    private int color;
    @Offset(20)
    private int outlineColor;

    @Offset(24)
    private int textReferenceId;
    private transient String defaultText;
    @Offset(28)
    private int anotherTextReferenceId;
    private transient String anotherText;

    @Offset(32)
    private byte styles;

    // TODO: add ability to read shifted values and apply it here
    private transient boolean useDeviceFont;  // styles | 1
    private transient boolean isOutlineEnabled;  // styles | 2
    private transient boolean isBold;  // styles | 4
    private transient boolean isItalic;  // styles | 8
    private transient boolean isMultiline;  // styles | 16
    private transient boolean unkBoolean;  // styles | 32
    private transient boolean autoAdjustFontSize;  // styles | 64

    @Offset(33)
    private byte align;
    @Offset(34)
    private byte fontSize;

    @Offset(36)
    private int unk32;
    @Offset(38)
    private short bendAngle;

    public int load(ByteStream stream, Tag tag, Function<ByteStream, String> fontNameReader) {
        this.tag = tag;

        this.id = stream.readShort();
        this.fontName = fontNameReader.apply(stream);
        this.color = stream.readInt();

        this.isBold = stream.readBoolean();
        this.isItalic = stream.readBoolean();
        this.isMultiline = stream.readBoolean();  // unused since BS v58

        stream.readBoolean();  // unused

        this.align = (byte) stream.readUnsignedChar();
        this.fontSize = (byte) stream.readUnsignedChar();

        this.bounds = new ShortRect(
            (short) stream.readShort(),
            (short) stream.readShort(),
            (short) stream.readShort(),
            (short) stream.readShort()
        );

        this.isOutlineEnabled = stream.readBoolean();

        this.defaultText = stream.readAscii();

        if (tag == Tag.TEXT_FIELD) {
            return this.id;
        }

        this.useDeviceFont = stream.readBoolean();

        switch (tag) {
            case TEXT_FIELD_2 -> {
                return this.id;
            }
            case TEXT_FIELD_3 -> {
                this.unkBoolean = true;
                return this.id;
            }
            case TEXT_FIELD_4 -> {
                this.unkBoolean = true;
                this.outlineColor = stream.readInt();
                return this.id;
            }
            case TEXT_FIELD_5 -> {
                this.outlineColor = stream.readInt();
                return this.id;
            }
            case TEXT_FIELD_6, TEXT_FIELD_7, TEXT_FIELD_8, TEXT_FIELD_9 -> {
                this.outlineColor = stream.readInt();
                this.unk32 = stream.readShort();
                stream.readShort();  // unused

                this.unkBoolean = true;

                if (tag == Tag.TEXT_FIELD_6) {
                    return this.id;
                }

                this.bendAngle = (short) stream.readShort();

                if (tag == Tag.TEXT_FIELD_7) {
                    return this.id;
                }

                this.autoAdjustFontSize = stream.readBoolean();

                if (tag == Tag.TEXT_FIELD_8) {
                    return this.id;
                }

                this.anotherText = stream.readAscii();

                return this.id;
            }
        }

        return this.id;
    }

    @Override
    public void save(ByteStream stream) {
        stream.writeShort(this.id);
        stream.writeAscii(this.fontName);
        stream.writeInt(this.color);

        stream.writeBoolean(this.isBold);
        stream.writeBoolean(this.isItalic);
        stream.writeBoolean(this.isMultiline);

        stream.writeBoolean(false);  // unused

        stream.writeUnsignedChar(this.align);
        stream.writeUnsignedChar(this.fontSize);

        stream.writeShort(this.bounds.left());
        stream.writeShort(this.bounds.top());
        stream.writeShort(this.bounds.right());
        stream.writeShort(this.bounds.bottom());

        stream.writeBoolean(this.isOutlineEnabled);

        stream.writeAscii(this.defaultText);

        if (this.tag == Tag.TEXT_FIELD) return;

        stream.writeBoolean(this.useDeviceFont);

        if (this.tag == Tag.TEXT_FIELD_2 || this.tag == Tag.TEXT_FIELD_3) return;

        stream.writeInt(this.outlineColor);

        if (this.tag == Tag.TEXT_FIELD_4 || this.tag == Tag.TEXT_FIELD_5) return;

        stream.writeShort(this.unk32);
        stream.writeShort(0);  // unused

        if (this.tag == Tag.TEXT_FIELD_6) return;

        stream.writeShort(this.bendAngle);

        if (this.tag == Tag.TEXT_FIELD_7) return;

        stream.writeBoolean(this.autoAdjustFontSize);

        if (this.tag == Tag.TEXT_FIELD_8) return;

        stream.writeAscii(this.anotherText);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Tag getTag() {
        return tag;
    }

    public float getBendAngle() {
        return (float) bendAngle / Short.MAX_VALUE * 360f;
    }

    public void getBendAngle(float bendAngle) {
        this.bendAngle = (short) (bendAngle * Short.MAX_VALUE / 360f);
    }

    public void resolveStrings(List<String> strings) {
        this.fontName = strings.get(this.fontNameReferenceId);
        this.defaultText = strings.get(this.textReferenceId);
        this.anotherText = strings.get(this.anotherTextReferenceId);
    }
}
