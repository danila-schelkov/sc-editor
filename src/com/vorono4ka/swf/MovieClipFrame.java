package com.vorono4ka.swf;

public class MovieClipFrame {
    private String name;  // "label"
    private MovieClipFrameElement[] elements;

    public int load(SupercellSWF swf) {
        int elementsCount = swf.readShort();
        this.name = swf.readAscii();

        return elementsCount;
    }

    public String getName() {
        return name;
    }

    public MovieClipFrameElement[] getElements() {
        return elements;
    }

    public void setElements(MovieClipFrameElement[] elements) {
        this.elements = elements;
    }
}
