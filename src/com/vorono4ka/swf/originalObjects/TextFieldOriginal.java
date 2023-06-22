package com.vorono4ka.swf.originalObjects;

import com.vorono4ka.math.Rect;
import com.vorono4ka.streams.ByteStream;
import com.vorono4ka.swf.SupercellSWF;
import com.vorono4ka.swf.constants.Tag;
import com.vorono4ka.swf.displayObjects.DisplayObject;
import com.vorono4ka.swf.displayObjects.TextField;

public class TextFieldOriginal extends DisplayObjectOriginal {
    private String fontName;
    private int color;
    private boolean useDeviceFont;  // styles | 1
    private boolean isOutlineEnabled;  // styles | 2
    private boolean isBold;  // styles | 4
    private boolean isItalic;  // styles | 8
    private boolean isMultiline;  // styles | 16
    private boolean unkBoolean;  // styles | 32
    private boolean autoAdjustFontSize;  // styles | 64
    private int align;
    private int fontSize;

    private int left;
    private int top;
    private int right;
    private int bottom;

    private String defaultText;
    private int outlineColor;
    private int unk32;
    private float bendAngle;

    public int load(SupercellSWF swf, Tag tag) {
        this.tag = tag;

        this.id = swf.readShort();
        this.fontName = swf.readFontName();
        this.color = swf.readInt();

        this.isBold = swf.readBoolean();
        this.isItalic = swf.readBoolean();
        this.isMultiline = swf.readBoolean();

        swf.readBoolean();  // unused

        this.align = swf.readUnsignedChar();
        this.fontSize = swf.readUnsignedChar();

        this.left = swf.readShort();
        this.top = swf.readShort();
        this.right = swf.readShort();
        this.bottom = swf.readShort();

        this.isOutlineEnabled = swf.readBoolean();

        this.defaultText = swf.readAscii();

        if (tag == Tag.TEXT_FIELD) {
            return this.id;
        }

        this.useDeviceFont = swf.readBoolean();

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
                this.outlineColor = swf.readInt();
                return this.id;
            }
            case TEXT_FIELD_5 -> {
                this.outlineColor = swf.readInt();
                return this.id;
            }
            case TEXT_FIELD_6, TEXT_FIELD_7, TEXT_FIELD_8, TEXT_FIELD_9 -> {
                this.outlineColor = swf.readInt();
                this.unk32 = swf.readShort();
                swf.readShort();  // unused

                this.unkBoolean = true;

                if (tag == Tag.TEXT_FIELD_6) {
                    return this.id;
                }

                this.bendAngle = swf.readShort() * 91.019f;

                if (tag == Tag.TEXT_FIELD_7) {
                    return this.id;
                }

                this.autoAdjustFontSize = swf.readBoolean();

                if (tag == Tag.TEXT_FIELD_8) {
                    return this.id;
                }

                boolean unk = swf.readBoolean();

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

        stream.writeShort(this.left);
        stream.writeShort(this.top);
        stream.writeShort(this.right);
        stream.writeShort(this.bottom);

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

        stream.writeShort((int) (this.bendAngle / 91.019f));

        if (this.tag == Tag.TEXT_FIELD_7) return;

        stream.writeBoolean(this.autoAdjustFontSize);
    }

    @Override
    public DisplayObject clone(SupercellSWF swf, Rect scalingGrid) {
        TextField textField = new TextField();
        textField.setId(this.id);

        return textField;
    }
}
