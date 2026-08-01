package dev.donutquine.editor.renderer.impl;

public interface Triangulator {
    /**
     * NOTE: use only first {@code triangleCount * 3} values
     */
    default int[] getIndices(int triangleCount) {
        int[] indices = new int[triangleCount * 3];
        initIndices(indices, 0, 0, triangleCount);
        return indices;
    }

    void initIndices(int[] array, int startPosition, int startTriangle, int triangleCount);

    Triangulator TRIANGLE_FAN = new CachingTriangulator((array, startPosition, startTriangle, triangleCount) -> {
        int endPosition = startPosition + triangleCount * 3;

        int j = startTriangle;
        for (int i = startPosition; i < endPosition; i += 3, j++) {
            array[i + 0] = 0 + 0;
            array[i + 1] = j + 1;
            array[i + 2] = j + 2;
        }

        assert j - startTriangle == triangleCount;
    });

    Triangulator TRIANGLE_STRIP = new CachingTriangulator((array, startPosition, startTriangle, triangleCount) -> {
        int endPosition = startPosition + triangleCount * 3;

        int j = startTriangle;
        for (int i = startPosition; i < endPosition; i += 3, j++) {
            array[i + 0] = j + 0;
            array[i + 1] = j + 1;
            array[i + 2] = j + 2;
        }

        assert j - startTriangle == triangleCount;
    });

    public class CachingTriangulator implements Triangulator {
        private static final int INITIAL_CAPACITY = 100; // in triangle count

        private final Triangulator innerTriangulator;
		private int[] cachedIndices;

        public CachingTriangulator(Triangulator innerTriangulator) {
			this.innerTriangulator = innerTriangulator;
            this.cachedIndices = innerTriangulator.getIndices(INITIAL_CAPACITY);
		}

		@Override
		public int[] getIndices(int triangleCount) {
            int oldLengthInTriangles = cachedIndices.length / 3;
            if (oldLengthInTriangles < triangleCount) {
                int newLengthInTriangles = (int) (oldLengthInTriangles * 1.5f);
                if (newLengthInTriangles - oldLengthInTriangles < triangleCount) {
                    newLengthInTriangles = (int) (oldLengthInTriangles + triangleCount * 1.5f);
                }

                int[] indices = new int[newLengthInTriangles * 3];
                System.arraycopy(this.cachedIndices, 0, indices, 0, this.cachedIndices.length);
                initIndices(indices, this.cachedIndices.length, oldLengthInTriangles, newLengthInTriangles - oldLengthInTriangles);
                this.cachedIndices = indices;
            }

            return this.cachedIndices;
		}

		@Override
		public void initIndices(int[] array, int startPosition, int startTriangle, int triangleCount) {
            this.innerTriangulator.initIndices(array, startPosition, startTriangle, triangleCount);
		}
    }
}
