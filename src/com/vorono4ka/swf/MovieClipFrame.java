package com.vorono4ka.swf;

public class MovieClipFrame {
    private String label;  // "label"
    private MovieClipFrameElement[] elements;

    public int load(SupercellSWF swf) {
        int elementsCount = swf.readShort();
        this.label = swf.readAscii();

        return elementsCount;
    }

    public String getLabel() {
        return label;
    }

    public MovieClipFrameElement[] getElements() {
        return elements;
    }

    public void setElements(MovieClipFrameElement[] elements) {
        this.elements = elements;
    }
}
