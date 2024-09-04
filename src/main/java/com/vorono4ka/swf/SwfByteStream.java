package com.vorono4ka.swf;

import com.vorono4ka.streams.ByteStream;

import java.nio.file.Path;

public class SwfByteStream extends ByteStream {
    private final String filename;
    private final Path path;

    public SwfByteStream(byte[] data, Path path, String filename) {
        super(data);

        this.path = path;
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public Path getPath() {
        return path;
    }
}
