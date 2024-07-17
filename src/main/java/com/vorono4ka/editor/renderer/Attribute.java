package com.vorono4ka.editor.renderer;

public record Attribute(int layout, int size, int bytePerElement, int glType) {
    public int sizeInBytes() {
        return size * bytePerElement;
    }
}
