package com.vorono4ka.swf.displayObjects.original;

import com.vorono4ka.swf.SupercellSWF;

public class MovieClipOriginal extends DisplayObjectOriginal {
    private String name;

    @Override
    public int load(SupercellSWF swf, int tag) {
        return 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
