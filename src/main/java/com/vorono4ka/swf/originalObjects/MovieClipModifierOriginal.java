package com.vorono4ka.swf.originalObjects;

import com.vorono4ka.streams.ByteStream;
import com.vorono4ka.swf.constants.Tag;

public class MovieClipModifierOriginal extends DisplayObjectOriginal {
    public int load(ByteStream stream, Tag tag) {
        this.tag = tag;
        this.id = stream.readShort();
        return this.id;
    }

    public void save(ByteStream stream) {
        stream.writeShort(this.id);
    }
}
