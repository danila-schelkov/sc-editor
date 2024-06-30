package com.vorono4ka.utilities;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.nio.ByteBuffer;

public final class Utilities {
    public static ByteBuffer getPixelBuffer(BufferedImage image) {
        return ByteBuffer.wrap(((DataBufferByte) image.getRaster().getDataBuffer()).getData());
    }
}
