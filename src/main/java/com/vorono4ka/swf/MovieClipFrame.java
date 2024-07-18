package com.vorono4ka.swf;

import com.vorono4ka.streams.ByteStream;
import com.vorono4ka.swf.constants.Tag;

public class MovieClipFrame {
    private String label;
    private MovieClipFrameElement[] elements;

    public int load(SupercellSWF swf, Tag tag) {
        int elementsCount = swf.readShort();
        this.label = swf.readAscii();

        if (tag == Tag.MOVIE_CLIP_FRAME) {
            this.elements = new MovieClipFrameElement[elementsCount];
            for (int i = 0; i < elementsCount; i++) {
                int childIndex = swf.readShort() & 0xFFFF;
                int matrixIndex = swf.readShort() & 0xFFFF;
                int colorTransformIndex = swf.readShort() & 0xFFFF;
                this.elements[i] = new MovieClipFrameElement(childIndex, matrixIndex, colorTransformIndex);
            }
        }

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
