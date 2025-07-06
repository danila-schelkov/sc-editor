package dev.donutquine.editor.renderer.impl;

public interface Triangulator {
    int[] getIndices(int triangleCount);

    Triangulator TRIANGLE_FAN = (triangleCount) -> {
        int[] indices = new int[triangleCount * 3];
        for (int i = 0; i < triangleCount; i++) {
            indices[i * 3] = 0;
            indices[i * 3 + 1] = i + 1;
            indices[i * 3 + 2] = i + 2;
        }
        return indices;
    };

    Triangulator TRIANGLE_STRIP = (triangleCount) -> {
        int[] indices = new int[triangleCount * 3];
        for (int i = 0; i < triangleCount; i++) {
            indices[i * 3] = i;
            indices[i * 3 + 1] = i + 1;
            indices[i * 3 + 2] = i + 2;
        }
        return indices;
    };
}
