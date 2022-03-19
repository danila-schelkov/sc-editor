package com.vorono4ka.swf.displayObjects.original;

import com.vorono4ka.swf.SupercellSWF;

public class MovieClipModifierOriginal extends DisplayObjectOriginal {
    private int id;

    @Override
    public int load(SupercellSWF swf, int tag) {
        this.id = swf.readShort();

        return this.id;
    }

    public int getId() {
        return id;
    }
}
