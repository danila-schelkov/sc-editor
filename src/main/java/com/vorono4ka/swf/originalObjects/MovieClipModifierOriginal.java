package com.vorono4ka.swf.originalObjects;

import com.vorono4ka.math.Rect;
import com.vorono4ka.streams.ByteStream;
import com.vorono4ka.swf.SupercellSWF;
import com.vorono4ka.swf.constants.Tag;
import com.vorono4ka.swf.displayObjects.DisplayObject;
import com.vorono4ka.swf.displayObjects.MovieClipModifier;

public class MovieClipModifierOriginal extends DisplayObjectOriginal {
    public int load(ByteStream stream, Tag tag) {
        this.tag = tag;
        this.id = stream.readShort();
        return this.id;
    }

    public void save(ByteStream stream) {
        stream.writeShort(this.id);
    }

    @Override
    public DisplayObject clone(SupercellSWF swf, Rect scalingGrid) {
        MovieClipModifier modifier = new MovieClipModifier(this.tag);
        modifier.setId(this.id);
        return modifier;
    }
}
