package com.vorono4ka.swf.originalObjects;

import com.vorono4ka.flatloader.SerializeType;
import com.vorono4ka.flatloader.annotations.FlatType;
import com.vorono4ka.streams.ByteStream;
import com.vorono4ka.swf.constants.Tag;

public class MovieClipModifierOriginal extends DisplayObjectOriginal {
    @FlatType(value = SerializeType.INT16, isUnsigned = true)
    private int id;
    @FlatType(value = SerializeType.INT16, isUnsigned = true)
    private int type;

    public int load(ByteStream stream, Tag tag) {
        this.id = stream.readShort();
        this.type = tag.ordinal();
        return this.id;
    }

    public void save(ByteStream stream) {
        stream.writeShort(this.id);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Tag getTag() {
        return Tag.values()[type];
    }
}
