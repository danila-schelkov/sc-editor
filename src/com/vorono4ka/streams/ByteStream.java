package com.vorono4ka.streams;

import com.vorono4ka.swf.constants.Tag;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

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
        this.ensureCapacity(data.length);

        System.arraycopy(data, 0, this.buffer, this.offset, data.length);
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
        this.buffer[this.offset++] = value;
    }

    public void writeUnsignedChar(int value) {
        this.ensureCapacity(1);

        this.write((byte) value);
    }

    public void writeShort(int value) {
        this.ensureCapacity(2);

        this.write((byte) ((value & 0x00FF)));
        this.write((byte) ((value & 0xFF00) >> 8));
    }

    public void writeInt(int value) {
        this.ensureCapacity(4);

        this.write((byte) ((value & 0x000000FF)));
        this.write((byte) ((value & 0x0000FF00) >> 8));
        this.write((byte) ((value & 0x00FF0000) >> 16));
        this.write((byte) ((value & 0xFF000000) >> 24));
    }

    public void writeTwip(float value) {
        this.writeInt((int) (value * 20f));
    }

    public void writeBoolean(boolean value) {
        this.writeUnsignedChar(value ? 1 : 0);
    }

    public void writeAscii(String string) {
        if (string == null) {
            this.writeUnsignedChar(255);
            return;
        }

        byte[] stringBytes = string.getBytes(StandardCharsets.UTF_8);
        this.writeUnsignedChar(stringBytes.length);
        this.write(stringBytes);
    }

    public void writeBlock(Tag tag, Consumer<ByteStream> consumer) {
        ByteStream blockStream = new ByteStream();
        consumer.accept(blockStream);

        byte[] blockData = blockStream.getBuffer();

        this.writeUnsignedChar(tag.ordinal());
        this.writeInt(blockData.length);
        this.write(blockData);
    }

    public int readUnsignedChar() {
        return this.buffer[this.offset++] & 0xFF;
    }

    public int readShort() {
        return this.readUnsignedChar() |
               this.readUnsignedChar() << 8;
    }

    public int readInt() {
        return this.readUnsignedChar() |
               this.readUnsignedChar() << 8 |
               this.readUnsignedChar() << 16 |
               this.readUnsignedChar() << 24;
    }

    public boolean readBoolean() {
        return this.readUnsignedChar() == 1;
    }

    public byte[] readByteArray(int count) {
        byte[] array = new byte[count];
        for (int i = 0; i < array.length; i++) {
            array[i] = (byte) this.readUnsignedChar();
        }

        return array;
    }

    public short[] readShortArray(int count) {
        short[] array = new short[count];
        for (int i = 0; i < array.length; i++) {
            array[i] = (short) this.readShort();
        }

        return array;
    }

    public int[] readIntArray(int count) {
        int[] array = new int[count];
        for (int i = 0; i < array.length; i++) {
            array[i] = this.readInt();
        }

        return array;
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