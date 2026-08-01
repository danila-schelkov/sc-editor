package dev.donutquine.editor.renderer;

import java.nio.IntBuffer;

public interface Triangulator {
    void initIndices(IntBuffer buffer, int startPosition, int startPoint, int triangleCount);

    Triangulator TRIANGLE_FAN = (buffer, startPosition, startPoint, triangleCount) -> {
        int endPosition = startPosition + triangleCount * 3;

        int j = startPoint;
        for (int i = startPosition; i < endPosition; i += 3, j++) {
            buffer.put(i + 0, startPoint + 0);
            buffer.put(i + 1, j + 1);
            buffer.put(i + 2, j + 2);
        }

        assert j - startPoint == triangleCount;
    };

    Triangulator TRIANGLE_STRIP = (buffer, startPosition, startPoint, triangleCount) -> {
        int endPosition = startPosition + triangleCount * 3;

        int j = startPoint;
        for (int i = startPosition; i < endPosition; i += 3, j++) {
            buffer.put(i + 0, j + 0);
            buffer.put(i + 1, j + 1);
            buffer.put(i + 2, j + 2);
        }

        assert j - startPoint == triangleCount;
    };
}
