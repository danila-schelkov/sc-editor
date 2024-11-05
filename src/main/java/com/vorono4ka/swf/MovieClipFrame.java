package com.vorono4ka.swf;

import com.vorono4ka.streams.ByteStream;
import com.vorono4ka.swf.constants.Tag;

import java.util.List;

public class MovieClipFrame {
    private int elementCount;
    private int labelStringReferenceId;
    private transient String label;

    private transient MovieClipFrameElement[] elements;

    public int load(ByteStream stream, Tag tag) {
        this.elementCount = stream.readShort();
        this.label = stream.readAscii();

        if (tag == Tag.MOVIE_CLIP_FRAME) {
            this.elements = new MovieClipFrameElement[this.elementCount];
            for (int i = 0; i < this.elementCount; i++) {
                int childIndex = stream.readShort() & 0xFFFF;
                int matrixIndex = stream.readShort() & 0xFFFF;
                int colorTransformIndex = stream.readShort() & 0xFFFF;
                this.elements[i] = new MovieClipFrameElement(childIndex, matrixIndex, colorTransformIndex);
            }
        }

        return this.elementCount;
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

    public void resolveStrings(List<String> strings) {
        this.label = strings.get(labelStringReferenceId);
    }

    public int resolveReferences(List<MovieClipFrameElement> frameElements, int frameElementOffset) {
        elements = new MovieClipFrameElement[elementCount];
        for (int i = 0; i < elementCount; i++) {
            elements[i] = frameElements.get(frameElementOffset + i);
        }

        return elementCount;
    }
}
