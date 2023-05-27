package com.vorono4ka.swf.originalObjects;

import com.vorono4ka.math.Rect;
import com.vorono4ka.streams.ByteStream;
import com.vorono4ka.swf.SupercellSWF;
import com.vorono4ka.swf.constants.Tag;
import com.vorono4ka.swf.displayObjects.DisplayObject;
import com.vorono4ka.swf.displayObjects.Shape;
import com.vorono4ka.swf.displayObjects.Shape9Slice;
import com.vorono4ka.swf.displayObjects.ShapeDrawBitmapCommand;
import com.vorono4ka.swf.exceptions.NegativeTagLengthException;
import com.vorono4ka.swf.exceptions.UnsupportedTagException;

public class ShapeOriginal extends DisplayObjectOriginal {
    private ShapeDrawBitmapCommand[] commands;

    public int load(SupercellSWF swf, Tag tag) throws NegativeTagLengthException {
        this.tag = tag;

        this.id = swf.readShort();
        int commandsCount = swf.readShort();

        this.commands = new ShapeDrawBitmapCommand[commandsCount];
        for (int i = 0; i < this.commands.length; i++) {
            this.commands[i] = new ShapeDrawBitmapCommand();
        }

        int pointsCount = 4 * this.commands.length;
        if (tag == Tag.SHAPE_2) {
            pointsCount = swf.readShort();
        }

        int loadedCommands = 0;

        while (true) {
            int commandTag = swf.readUnsignedChar();
            int length = swf.readInt();

            if (length < 0) {
                throw new NegativeTagLengthException(String.format("Negative tag length in Shape. Tag %d, %s", commandTag, swf.getFilename()));
            }

            Tag tagValue = Tag.values()[commandTag];
            switch (tagValue) {
                case EOF -> {
                    return this.id;
                }
                case SHAPE_DRAW_BITMAP_COMMAND, SHAPE_DRAW_BITMAP_COMMAND_2, SHAPE_DRAW_BITMAP_COMMAND_3 -> {
                    this.commands[loadedCommands++].load(swf, tagValue);
                }
                case SHAPE_DRAW_COLOR_FILL_COMMAND -> {
                    try {
                        throw new UnsupportedTagException(String.format("SupercellSWF::TAG_SHAPE_DRAW_COLOR_FILL_COMMAND not supported, %s", swf.getFilename()));
                    } catch (UnsupportedTagException exception) {
                        exception.printStackTrace();
                    }
                }
                default -> {
                    try {
                        throw new UnsupportedTagException(String.format("Unknown tag %d in Shape, %s", commandTag, swf.getFilename()));
                    } catch (UnsupportedTagException exception) {
                        exception.printStackTrace();
                    }

                    if (length > 0) {
                        swf.skip(length);
                    }
                }
            }
        }
    }

    @Override
    public void save(ByteStream stream) {
        stream.writeShort(this.id);
        stream.writeShort(this.commands.length);

        if (this.tag != Tag.SHAPE) {
            stream.writeShort(getPointsCount());
        }

        for (ShapeDrawBitmapCommand command : this.commands) {
            stream.writeBlock(command.getTag(), command::save);
        }

        stream.writeBlock(Tag.EOF, ignored -> {});
    }

    @Override
    public DisplayObject clone(SupercellSWF swf, Rect scalingGrid) {
        if (scalingGrid != null) {
            return Shape9Slice.createShape(this, scalingGrid);
        }

        return Shape.createShape(this);
    }

    public ShapeDrawBitmapCommand[] getCommands() {
        return this.commands;
    }

    private int getPointsCount() {
        int pointsCount = 0;
        for (ShapeDrawBitmapCommand command : this.commands) {
            pointsCount += command.getVertexCount();
        }

        return pointsCount;
    }
}
