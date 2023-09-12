package com.vorono4ka.swf.originalObjects;

import com.vorono4ka.math.Rect;
import com.vorono4ka.swf.SupercellSWF;
import com.vorono4ka.swf.constants.Tag;
import com.vorono4ka.swf.displayObjects.DisplayObject;
import com.vorono4ka.swf.exceptions.UnableToFindObjectException;

public abstract class DisplayObjectOriginal implements Savable {
    protected Tag tag;
    protected int id;

    public DisplayObjectOriginal() {}

    public DisplayObjectOriginal(Tag tag) {
        this.tag = tag;
    }

    public abstract DisplayObject clone(SupercellSWF swf, Rect scalingGrid) throws UnableToFindObjectException;

    @Override
    public Tag getTag() {
        return this.tag;
    }

    public int getId() {
        return id;
    }
}
