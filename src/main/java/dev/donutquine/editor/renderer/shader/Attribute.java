package dev.donutquine.editor.renderer.shader;

public record Attribute(int layout, int size, int bytePerElement, int glType) {
    public int sizeInBytes() {
        return size * bytePerElement;
    }
}
