package com.vorono4ka.swf;

import com.vorono4ka.streams.ByteStream;
import com.vorono4ka.swf.constants.Tag;

public class MovieClipFrame {
    private String label;
    private MovieClipFrameElement[] elements;

    public int load(ByteStream stream, Tag tag) {
        int elementCount = stream.readShort();
        this.label = stream.readAscii();

        if (tag == Tag.MOVIE_CLIP_FRAME) {
            this.elements = new MovieClipFrameElement[elementCount];
            for (int i = 0; i < elementCount; i++) {
                int childIndex = stream.readShort() & 0xFFFF;
                int matrixIndex = stream.readShort() & 0xFFFF;
                int colorTransformIndex = stream.readShort() & 0xFFFF;
                this.elements[i] = new MovieClipFrameElement(childIndex, matrixIndex, colorTransformIndex);
            }
        }

        return elementCount;
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
