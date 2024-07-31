package com.vorono4ka.streams;

import com.vorono4ka.swf.constants.Tag;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.function.Consumer;

public class ByteStream {
    public static final int DEFAULT_BUFFER_LENGTH = 16;

    private byte[] data;
    private int position;

    public ByteStream() {
        this(new byte[ByteStream.DEFAULT_BUFFER_LENGTH]);
    }

    public ByteStream(byte[] data) {
        this.setData(data);
    }

    public boolean isAtAnd() {
        return this.position >= this.data.length;
    }

    public void ensureCapacity(int count) {
        int capacity = this.position + count;
        if (this.data.length < capacity) {
            int newSize = (int) (this.data.length * 1.5f);
            if (newSize < capacity) {
                newSize = capacity;
            }

            this.data = Arrays.copyOf(this.data, newSize);
        }
    }

    public void write(byte[] data) {
        this.ensureCapacity(data.length);

        System.arraycopy(data, 0, this.data, this.position, data.length);
        this.position += data.length;
    }

    public byte[] read(int length) {
        byte[] data = new byte[length];
        if (length <= this.data.length - this.position) {
            System.arraycopy(this.data, this.position, data, 0, length);
            this.position += length;
        }

        return data;
    }

    public void skip(int length) {
        this.position += length;
    }

    public void writeUnsignedChar(int value) {
        this.ensureCapacity(1);

        this.write((byte) value);
    }

    public void writeShort(int value) {
        this.ensureCapacity(2);

        this.write((byte) (value & 0xFF));
        this.write((byte) ((value >> 8) & 0xFF));
    }

    public void writeInt(int value) {
        this.ensureCapacity(4);

        this.write((byte) (value & 0xFF));
        this.write((byte) ((value >> 8) & 0xFF));
        this.write((byte) ((value >> 16) & 0xFF));
        this.write((byte) ((value >> 24) & 0xFF));
    }

    public void writeBoolean(boolean value) {
        this.writeUnsignedChar(value ? 1 : 0);
    }

    public void writeTwip(float value) {
        this.writeInt((int) (value * 20f));
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

        byte[] blockData = blockStream.getData();

        this.writeUnsignedChar(tag.ordinal());
        this.writeInt(blockData.length);
        this.write(blockData);
    }

    public int readUnsignedChar() {
        return this.data[this.position++] & 0xFF;
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

    public float readTwip() {
        return this.readInt() / 20f;
    }

    public String readAscii() {
        int length = this.readUnsignedChar() & 0xff;
        if (length == 255) return null;

        return new String(this.read(length), StandardCharsets.UTF_8);
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


    public byte[] getData() {
        return ByteBuffer.allocate(this.position).put(this.data, 0, this.position).array();
    }

    public void setData(byte[] data) {
        this.data = data;
        this.position = 0;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private void write(byte value) {
        this.data[this.position++] = value;
    }
}