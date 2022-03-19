package com.vorono4ka.swf.displayObjects.original;

import com.vorono4ka.swf.SupercellSWF;
import com.vorono4ka.swf.constants.Tag;
import com.vorono4ka.swf.displayObjects.ShapeDrawBitmapCommand;
import com.vorono4ka.swf.exceptions.NegativeTagLengthException;
import com.vorono4ka.swf.exceptions.UnsupportedTagException;

public class ShapeOriginal extends DisplayObjectOriginal {
    private int commandsCount;
    private ShapeDrawBitmapCommand[] commands;

    @Override
    public int load(SupercellSWF swf, Tag tag) throws NegativeTagLengthException {
        int id = swf.readShort();
        this.commandsCount = swf.readShort();

        this.commands = new ShapeDrawBitmapCommand[this.commandsCount];
        for (int i = 0; i < this.commandsCount; i++) {
            this.commands[i] = new ShapeDrawBitmapCommand();
        }

        int pointsCount = 4 * this.commandsCount;
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
                    return id;
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
}
