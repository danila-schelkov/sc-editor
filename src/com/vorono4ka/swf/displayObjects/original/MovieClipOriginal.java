package com.vorono4ka.swf.displayObjects.original;

import com.vorono4ka.math.Rect;
import com.vorono4ka.swf.MovieClipFrame;
import com.vorono4ka.swf.SupercellSWF;
import com.vorono4ka.swf.constants.Tag;
import com.vorono4ka.swf.exceptions.LoadingFaultException;
import com.vorono4ka.swf.exceptions.NegativeTagLengthException;
import com.vorono4ka.swf.exceptions.UnsupportedTagException;

import java.util.Arrays;

public class MovieClipOriginal extends DisplayObjectOriginal {
    private int fps;
    private int framesCount;
    private int[] transforms;
    private MovieClipFrame[] frames;

    private int bindsCount;
    private int[] bindsIds;
    private int[] bindsBlends;
    private String[] bindsNames;

    private Rect scalingGrid;
    private int matrixBankIndex;

    private String name;

    @Override
    public int load(SupercellSWF swf, Tag tag) throws LoadingFaultException {
        int id = swf.readShort();
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
                int transformsCount = swf.readInt();
                this.transforms = swf.readShortArray(transformsCount * 3);
            }
        }

        this.bindsCount = swf.readShort();
        this.bindsIds = swf.readShortArray(this.bindsCount);

        if (tag == Tag.MOVIE_CLIP_3 || tag == Tag.MOVIE_CLIP_35) {
            this.bindsBlends = swf.readByteArray(this.bindsCount);
        } else {
            this.bindsBlends = new int[this.bindsCount];
            Arrays.fill(this.bindsBlends, 0);
        }

        this.bindsNames = new String[this.bindsCount];
        for (int i = 0; i < this.bindsCount; i++) {
            this.bindsNames[i] = swf.readAscii();
        }

        int loadedCommands = 0;

        while (true) {
            int commandTag = swf.readUnsignedChar();
            int length = swf.readInt();

            if (length < 0) {
                throw new NegativeTagLengthException(String.format("Negative tag length in MovieClip. Tag %d, %s", commandTag, swf.getFilename()));
            }

            Tag tagValue = Tag.values()[commandTag];
            switch (tagValue) {
                case EOF -> {
                    return id;
                }
                case MOVIE_CLIP_FRAME -> {
                    try {
                        throw new UnsupportedTagException("TAG_MOVIE_CLIP_FRAME no longer supported");
                    } catch (UnsupportedTagException exception) {
                        exception.printStackTrace();
                    }
                }
                case MOVIE_CLIP_FRAME_2 -> this.frames[loadedCommands++].load(swf);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
