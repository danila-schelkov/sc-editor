package com.vorono4ka.swf.displayObjects.original;

import com.vorono4ka.math.Rect;
import com.vorono4ka.swf.SupercellSWF;
import com.vorono4ka.swf.constants.Tag;
import com.vorono4ka.swf.displayObjects.DisplayObject;
import com.vorono4ka.swf.displayObjects.TextField;
import com.vorono4ka.swf.exceptions.UnableToFindObjectException;

public class TextFieldOriginal extends DisplayObjectOriginal {
    private String fontName;
    private int color;
    private int styles;
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
        this.id = swf.readShort();
        this.fontName = swf.readFontName();
        this.color = swf.readInt();

        boolean isBold = swf.readBoolean();
        if (isBold) {
            this.styles |= 4;
        }

        boolean isItalic = swf.readBoolean();
        if (isItalic) {
            this.styles |= 8;
        }

        boolean isMultiline = swf.readBoolean();
        if (isMultiline) {
            this.styles |= 16;
        }

        swf.readBoolean();  // unused

        this.align = swf.readUnsignedChar();
        this.fontSize = swf.readUnsignedChar();

        this.left = swf.readShort();
        this.top = swf.readShort();
        this.right = swf.readShort();
        this.bottom = swf.readShort();

        boolean isUppercase = swf.readBoolean();
        if (isUppercase) {
            this.styles |= 2;
        }

        this.defaultText = swf.readAscii();

        if (tag == Tag.TEXT_FIELD) {
            return this.id;
        }

        boolean useDeviceFont = swf.readBoolean();
        if (useDeviceFont) {
            this.styles |= 1;
        }

        switch (tag) {
            case TEXT_FIELD_2 -> {
                return this.id;
            }
            case TEXT_FIELD_3 -> {
                this.styles |= 32;
                return this.id;
            }
            case TEXT_FIELD_4 -> {
                this.styles |= 32;
                this.outlineColor = swf.readInt();
                return this.id;
            }
            case TEXT_FIELD_5 -> {
                this.outlineColor = swf.readInt();
                return this.id;
            }
            case TEXT_FIELD_6, TEXT_FIELD_7, TEXT_FIELD_8 -> {
                this.outlineColor = swf.readInt();
                this.unk32 = swf.readShort();
                swf.readShort();

                this.styles |= 32;

                if (tag == Tag.TEXT_FIELD_6) {
                    return this.id;
                }

                this.bendAngle = swf.readShort() * 91.019f;

                if (tag == Tag.TEXT_FIELD_7) {
                    return this.id;
                }

                boolean autoAdjustFontSize = swf.readBoolean();
                if (autoAdjustFontSize) {
                    this.styles |= 64;
                }
            }
        }

        return this.id;
    }

    @Override
    public DisplayObject clone(SupercellSWF swf, Rect scalingGrid) throws UnableToFindObjectException {
        TextField textField = new TextField();
        textField.setId(this.id);

        return textField;
    }
}
