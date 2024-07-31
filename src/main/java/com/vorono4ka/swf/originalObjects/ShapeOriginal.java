package com.vorono4ka.swf.originalObjects;

import com.vorono4ka.math.Rect;
import com.vorono4ka.streams.ByteStream;
import com.vorono4ka.swf.GLImage;
import com.vorono4ka.swf.SupercellSWF;
import com.vorono4ka.swf.SwfByteStream;
import com.vorono4ka.swf.constants.Tag;
import com.vorono4ka.swf.displayObjects.DisplayObject;
import com.vorono4ka.swf.displayObjects.Shape;
import com.vorono4ka.swf.displayObjects.Shape9Slice;
import com.vorono4ka.swf.displayObjects.ShapeDrawBitmapCommand;
import com.vorono4ka.swf.exceptions.NegativeTagLengthException;
import com.vorono4ka.swf.exceptions.UnsupportedTagException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

public class ShapeOriginal extends DisplayObjectOriginal {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShapeOriginal.class);

    private ShapeDrawBitmapCommand[] commands;

    public int load(ByteStream stream, Tag tag, Function<Integer, GLImage> imageFunction, String filename) throws NegativeTagLengthException {
        this.tag = tag;

        this.id = stream.readShort();
        int commandCount = stream.readShort();

        this.commands = new ShapeDrawBitmapCommand[commandCount];
        for (int i = 0; i < this.commands.length; i++) {
            this.commands[i] = new ShapeDrawBitmapCommand();
        }

        // Used for allocating memory for points
        int pointCount = 4 * this.commands.length;
        if (tag == Tag.SHAPE_2) {
            pointCount = stream.readShort();
        }

        int loadedCommands = 0;

        while (true) {
            int commandTag = stream.readUnsignedChar();
            int length = stream.readInt();

            if (length < 0) {
                throw new NegativeTagLengthException(String.format("Negative tag length in Shape. Tag %d, %s", commandTag, filename));
            }

            Tag tagValue = Tag.values()[commandTag];
            switch (tagValue) {
                case EOF -> {
                    return this.id;
                }
                case SHAPE_DRAW_BITMAP_COMMAND, SHAPE_DRAW_BITMAP_COMMAND_2, SHAPE_DRAW_BITMAP_COMMAND_3 ->
                    this.commands[loadedCommands++].load(stream, tagValue, imageFunction);
                case SHAPE_DRAW_COLOR_FILL_COMMAND -> {
                    try {
                        throw new UnsupportedTagException(String.format("SupercellSWF::TAG_SHAPE_DRAW_COLOR_FILL_COMMAND not supported, %s", filename));
                    } catch (UnsupportedTagException exception) {
                        LOGGER.error(exception.getMessage(), exception);
                    }
                }
                default -> {
                    try {
                        throw new UnsupportedTagException(String.format("Unknown tag %d in Shape, %s", commandTag, filename));
                    } catch (UnsupportedTagException exception) {
                        LOGGER.error(exception.getMessage(), exception);
                    }

                    if (length > 0) {
                        stream.skip(length);
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
            stream.writeShort(calculatePointCount());
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

    private int calculatePointCount() {
        int pointCount = 0;
        for (ShapeDrawBitmapCommand command : this.commands) {
            pointCount += command.getVertexCount();
        }

        return pointCount;
    }
}
