package com.vorono4ka.swf.displayObjects.original;

import com.vorono4ka.math.Rect;
import com.vorono4ka.swf.MovieClipFrame;
import com.vorono4ka.swf.MovieClipFrameElement;
import com.vorono4ka.swf.SupercellSWF;
import com.vorono4ka.swf.constants.Tag;
import com.vorono4ka.swf.displayObjects.DisplayObject;
import com.vorono4ka.swf.displayObjects.MovieClip;
import com.vorono4ka.swf.exceptions.LoadingFaultException;
import com.vorono4ka.swf.exceptions.NegativeTagLengthException;
import com.vorono4ka.swf.exceptions.UnableToFindObjectException;
import com.vorono4ka.swf.exceptions.UnsupportedTagException;

import java.util.Arrays;

public class MovieClipOriginal extends DisplayObjectOriginal {
    private int fps;
    private int framesCount;
    private short[] framesElements;
    private MovieClipFrame[] frames;

    private int childrenCount;
    private short[] childrenIds;
    private byte[] childrenBlends;
    private String[] childrenNames;

    private Rect scalingGrid;
    private int matrixBankIndex;

    private String exportName;
    private DisplayObjectOriginal[] children;

    public int load(SupercellSWF swf, Tag tag) throws LoadingFaultException {
        this.id = swf.readShort();
        this.fps = swf.readUnsignedChar();

        this.framesCount = swf.readShort();
        this.frames = new MovieClipFrame[this.framesCount];
        for (int i = 0; i < this.framesCount; i++) {
            this.frames[i] = new MovieClipFrame();
        }

        switch (tag) {
            case MOVIE_CLIP_4 -> {
                try {
                    throw new UnsupportedTagException("TAG_MOVIE_CLIP_4 no longer supported\n");
                } catch (UnsupportedTagException e) {
                    e.printStackTrace();
                }
            }
            case MOVIE_CLIP -> {
                try {
                    throw new UnsupportedTagException("TAG_MOVIE_CLIP no longer supported\n");
                } catch (UnsupportedTagException e) {
                    e.printStackTrace();
                }
            }
            default -> {
                int elementsCount = swf.readInt();
                this.framesElements = swf.readShortArray(elementsCount * 3);
            }
        }

        this.childrenCount = swf.readShort();
        this.childrenIds = swf.readShortArray(this.childrenCount);

        if (tag == Tag.MOVIE_CLIP_3 || tag == Tag.MOVIE_CLIP_35) {
            this.childrenBlends = swf.readByteArray(this.childrenCount);
        } else {
            this.childrenBlends = new byte[this.childrenCount];
            Arrays.fill(this.childrenBlends, (byte) 0);
        }

        this.childrenNames = new String[this.childrenCount];
        for (int i = 0; i < this.childrenCount; i++) {
            this.childrenNames[i] = swf.readAscii();
        }

        int loadedCommands = 0;
        int usedElements = 0;

        while (true) {
            int commandTag = swf.readUnsignedChar();
            int length = swf.readInt();

            if (length < 0) {
                throw new NegativeTagLengthException(String.format("Negative tag length in MovieClip. Tag %d, %s", commandTag, swf.getFilename()));
            }

            Tag tagValue = Tag.values()[commandTag];
            switch (tagValue) {
                case EOF -> {
                    return this.id;
                }
                case MOVIE_CLIP_FRAME -> {
                    try {
                        throw new UnsupportedTagException("TAG_MOVIE_CLIP_FRAME no longer supported");
                    } catch (UnsupportedTagException exception) {
                        exception.printStackTrace();
                    }
                }
                case MOVIE_CLIP_FRAME_2 -> {
                    MovieClipFrame frame = this.frames[loadedCommands++];
                    int elementsCount = frame.load(swf);

                    MovieClipFrameElement[] elements = new MovieClipFrameElement[elementsCount];
                    for (int i = 0; i < elementsCount; i++) {
                        elements[i] = new MovieClipFrameElement(
                            this.framesElements[usedElements * 3] & 0xFFFF,
                            this.framesElements[usedElements * 3 + 1] & 0xFFFF,
                            this.framesElements[usedElements * 3 + 2] & 0xFFFF
                        );

                        usedElements++;
                    }
                    frame.setElements(elements);
                }
                case SCALING_GRID -> {
                    if (this.scalingGrid != null) {
                        throw new LoadingFaultException("multiple scaling grids");
                    }

                    float left = swf.readTwip();
                    float top = swf.readTwip();
                    float right = swf.readTwip() + left;
                    float bottom = swf.readTwip() + top;

                    this.scalingGrid = new Rect(left, top, right, bottom);
                }
                case MATRIX_BANK_INDEX -> this.matrixBankIndex = swf.readUnsignedChar();
                default -> {
                    try {
                        throw new UnsupportedTagException(String.format("Unknown tag %d in MovieClip, %s", commandTag, swf.getFilename()));
                    } catch (UnsupportedTagException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        }
    }

    public DisplayObject clone(SupercellSWF swf, Rect scalingGrid) throws UnableToFindObjectException {
        if (this.children == null) {
            this.children = new DisplayObjectOriginal[this.childrenCount];
            for (int i = 0; i < this.childrenCount; i++) {
                this.children[i] = swf.getOriginalDisplayObject(this.childrenIds[i], this.exportName);
            }
        }

        MovieClip movieClip = new MovieClip();
        movieClip.setId(this.id);
        movieClip.setMatrixBank(swf.getMatrixBank(this.matrixBankIndex));

        DisplayObject[] childrenArray = new DisplayObject[this.childrenCount];
        for (int i = 0; i < this.childrenCount; i++) {
            DisplayObjectOriginal child = this.children[i];
            DisplayObject displayObject = child.clone(swf, this.scalingGrid);

            displayObject.setVisibleRecursive((this.childrenBlends[i] & 64) == 0);
            displayObject.setInteractiveRecursive(true);

            childrenArray[i] = displayObject;
        }
        movieClip.setTimelineChildren(childrenArray);
        movieClip.setTimelineChildrenNames(this.childrenNames);
        movieClip.setFrames(this.frames);
        movieClip.setFPS(this.fps);
        movieClip.setExportName(this.exportName);
        movieClip.setFrame(0);

        return movieClip;
    }

    public String getExportName() {
        return exportName;
    }

    public void setExportName(String exportName) {
        this.exportName = exportName;
    }
}
