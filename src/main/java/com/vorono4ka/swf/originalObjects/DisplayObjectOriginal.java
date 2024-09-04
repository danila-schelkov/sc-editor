package com.vorono4ka.swf.originalObjects;

import com.vorono4ka.swf.constants.Tag;

public abstract class DisplayObjectOriginal implements Savable {
    protected Tag tag;
    protected int id;

    public DisplayObjectOriginal() {
    }

    public DisplayObjectOriginal(Tag tag) {
        this.tag = tag;
    }

    @Override
    public Tag getTag() {
        return this.tag;
    }

    public int getId() {
        return id;
    }
}
