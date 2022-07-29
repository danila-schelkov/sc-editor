package com.vorono4ka.swf.originalObjects;

import com.vorono4ka.streams.ByteStream;
import com.vorono4ka.swf.constants.Tag;

public interface SavableObject {
    void save(ByteStream stream);

    Tag getTag();
}
