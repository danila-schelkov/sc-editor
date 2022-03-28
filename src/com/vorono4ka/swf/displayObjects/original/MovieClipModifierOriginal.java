package com.vorono4ka.swf.displayObjects.original;

import com.vorono4ka.math.Rect;
import com.vorono4ka.swf.SupercellSWF;
import com.vorono4ka.swf.constants.Tag;
import com.vorono4ka.swf.displayObjects.DisplayObject;
import com.vorono4ka.swf.displayObjects.MovieClipModifier;
import com.vorono4ka.swf.exceptions.UnableToFindObjectException;

public class MovieClipModifierOriginal extends DisplayObjectOriginal {
    private Tag tag;
    private int id;

    public int load(SupercellSWF swf, Tag tag) {
        this.tag = tag;
        this.id = swf.readShort();

        return this.id;
    }

    @Override
    public DisplayObject clone(SupercellSWF swf, Rect scalingGrid) throws UnableToFindObjectException {
        return new MovieClipModifier(this.tag);
    }

    public int getId() {
        return id;
    }
}
