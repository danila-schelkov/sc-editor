package com.vorono4ka.swf;

public class MovieClipFrame {
    private String name;  // "label"

    public int load(SupercellSWF swf) {
        int usedTransformsCount = swf.readShort();
        this.name = swf.readAscii();

        return usedTransformsCount;
    }

    public String getName() {
        return name;
    }
}
