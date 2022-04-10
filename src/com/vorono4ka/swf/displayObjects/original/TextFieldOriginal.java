package com.vorono4ka.swf.displayObjects.original;

import com.vorono4ka.math.Rect;
import com.vorono4ka.swf.SupercellSWF;
import com.vorono4ka.swf.constants.Tag;
import com.vorono4ka.swf.displayObjects.DisplayObject;
import com.vorono4ka.swf.displayObjects.TextField;
import com.vorono4ka.swf.exceptions.UnableToFindObjectException;

public class TextFieldOriginal extends DisplayObjectOriginal {
    public int load(SupercellSWF swf, Tag tag) {
        this.id = swf.readShort();

        return id;
    }

    @Override
    public DisplayObject clone(SupercellSWF swf, Rect scalingGrid) throws UnableToFindObjectException {
        TextField textField = new TextField();
        textField.setId(this.id);

        return textField;
    }
}
