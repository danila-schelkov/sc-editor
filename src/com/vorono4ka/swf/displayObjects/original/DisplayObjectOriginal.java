package com.vorono4ka.swf.displayObjects.original;

import com.vorono4ka.math.Rect;
import com.vorono4ka.swf.SupercellSWF;
import com.vorono4ka.swf.displayObjects.DisplayObject;
import com.vorono4ka.swf.exceptions.UnableToFindObjectException;

public abstract class DisplayObjectOriginal {
    protected int id;

    public abstract DisplayObject clone(SupercellSWF swf, Rect scalingGrid) throws UnableToFindObjectException;
}
