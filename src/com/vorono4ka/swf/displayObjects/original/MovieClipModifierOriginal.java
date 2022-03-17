package com.vorono4ka.swf.displayObjects.original;

import com.vorono4ka.streams.ByteStream;
import com.vorono4ka.swf.SupercellSWF;

public class MovieClipModifierOriginal extends DisplayObjectOriginal {
    private int id;

    @Override
    public int load(SupercellSWF swf, int tag) {
        ByteStream stream = swf.getStream();

        this.id = stream.readInt16();

        return this.id;
    }

    public int getId() {
        return id;
    }
}
