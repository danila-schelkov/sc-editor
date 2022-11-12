package com.vorono4ka.swf;

import com.vorono4ka.streams.ByteStream;

public class MovieClipFrame {
    private String label;
    private MovieClipFrameElement[] elements;

    public int load(SupercellSWF swf) {
        int elementsCount = swf.readShort();
        this.label = swf.readAscii();

        return elementsCount;
    }

    public void save(ByteStream stream) {
        stream.writeShort(this.elements.length);
        stream.writeAscii(this.label);
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
