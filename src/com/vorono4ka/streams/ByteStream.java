package com.vorono4ka.streams;

import java.io.EOFException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class ByteStream {
    public static final int DEFAULT_BUFFER_LENGTH = 512;

    private byte[] buffer;
    private int offset;

    public ByteStream() {
        this(new byte[ByteStream.DEFAULT_BUFFER_LENGTH]);
    }

    public ByteStream(byte[] data) {
        this.setData(data);
    }

    public boolean isAtAnd() {
        return this.offset >= this.buffer.length;
    }

    public void ensureCapacity(int count) {
        if (this.buffer.length < this.buffer.length + count) {
            this.buffer = ByteBuffer.allocate(this.buffer.length + count).put(this.buffer).array();
        }
    }

    public void write(byte[] data) {
        this.buffer = ByteBuffer.allocate(this.buffer.length + data.length).put(this.buffer).put(data, this.offset, data.length).array();
        this.offset += data.length;
    }

    public byte[] read(int length) {
        byte[] data = new byte[length];
        if (length <= this.buffer.length - this.offset) {
            System.arraycopy(this.buffer, this.offset, data, 0, length);
            this.offset += length;
        }

        return data;
    }

    public void skip(int length) {
        this.offset += length;
    }


    private void write(byte value) {
        this.buffer[this.offset] = value;
        this.offset++;
    }

    public void writeInt8(byte value) {
        this.ensureCapacity(1);

        this.write(value);
    }

    public void writeInt16(int value) {
        this.ensureCapacity(2);

        this.write((byte) ((value & 0xFF00) >> 8));
        this.write((byte) ((value & 0x00FF)));
    }

    public void writeInt32(int value) {
        this.ensureCapacity(4);

        this.write((byte) ((value & 0xFF000000) >> 24));
        this.write((byte) ((value & 0x00FF0000) >> 16));
        this.write((byte) ((value & 0x0000FF00) >> 8));
        this.write((byte) ((value & 0x000000FF)));
    }

    public void writeInt64(long value) {
        this.ensureCapacity(8);

        this.writeInt32((int) ((value & 0xFFFFFFFF00000000L) >> 32));
        this.writeInt32((int) ((value & 0x00000000FFFFFFFFL)));
    }

    public void writeBoolean(boolean value) {
        this.writeInt8((byte) (value ? 1 : 0));
    }

    public void writeString(String value) {
        if (value == null) {
            this.writeInt32(-1);
            return;
        }

        byte[] encodedString = value.getBytes(StandardCharsets.UTF_8);
        this.writeInt32(encodedString.length);
        this.write(encodedString);
    }


    public byte readInt8() {
        return this.buffer[this.offset++];
    }

    public int readInt16() {
        return (this.readInt8() & 0xFF) << 8 |
               (this.readInt8() & 0xFF);
    }

    public int readInt32() {
        return (this.readInt8() & 0xFF) << 24 |
               (this.readInt8() & 0xFF) << 16 |
               (this.readInt8() & 0xFF) << 8 |
               (this.readInt8() & 0xFF);
    }

    public boolean readBoolean() {
        return this.readInt8() == 1;
    }


    public byte[] getBuffer() {
        return ByteBuffer.allocate(this.offset).put(this.buffer, 0, this.offset).array();
    }

    public void setData(byte[] a2) {
        this.buffer = a2;
        this.offset = 0;
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}