package com.vorono4ka.swf.originalObjects;

import com.vorono4ka.streams.ByteStream;
import com.vorono4ka.swf.constants.Tag;

public interface Savable {
    void save(ByteStream stream);

    Tag getTag();
}
