package dev.donutquine.editor.layout.components.tables;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dev.donutquine.swf.movieclips.MovieClipFrame;
import dev.donutquine.swf.movieclips.MovieClipFrameElement;

public class MovieClipFrameElementsTableModel extends AbstractTableModel implements RowReorderableTableModel {
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieClipFrameElementsTableModel.class);

    private static final String[] COLUMN_NAMES = {"#", "Child #", "Matrix", "Color Transform"};
    private static final Class<?>[] COLUMN_CLASSES = {Integer.class, Integer.class, Integer.class, Integer.class};

    private static final int COLUMN_INDEX = 0;
    private static final int COLUMN_CHILD_INDEX = 1;
    private static final int COLUMN_MATRIX_INDEX = 2;
    private static final int COLUMN_COLOR_TRANSFORM_INDEX = 3;

    private final ChildCountGetter childCountGetter;
    private final MatrixCountGetter matrixCountGetter;
    private final ColorTransformCountGetter colorTransformCountGetter;
    // Note: was final before, but I prefer modifying existing object rather than allocating a new one every sneeze
    private MovieClipFrame frame;
    private List<MovieClipFrameElement> frameElements;

    public interface ChildCountGetter {
        int get();
    }

    public interface MatrixCountGetter {
        int get();
    }

    public interface ColorTransformCountGetter {
        int get();
    }

    public MovieClipFrameElementsTableModel(MovieClipFrame frame, ChildCountGetter childCountGetter, MatrixCountGetter matrixCountGetter, ColorTransformCountGetter colorTransformCountGetter) {
        super();

        this.setFrame(frame);
		this.childCountGetter = childCountGetter;
		this.matrixCountGetter = matrixCountGetter;
		this.colorTransformCountGetter = colorTransformCountGetter;
	}

	public void setFrame(MovieClipFrame frame) {
        this.frame = frame;
        // Note: Copying as returned value is unmodifiable
        this.frameElements = new ArrayList<>(frame.getElements());
        this.fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return this.frameElements.size();
    }

    @Override
    public int getColumnCount() {
        assert COLUMN_NAMES.length == COLUMN_CLASSES.length;
        return COLUMN_NAMES.length;
    }

    @Override
    public String getColumnName(int column) {
        assert column >= 0 && column <= COLUMN_NAMES.length;
        return COLUMN_NAMES[column];
    }

    @Override
    public Class<?> getColumnClass(int column) {
        assert column >= 0 && column <= COLUMN_NAMES.length;
        return COLUMN_CLASSES[column];
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return switch (column) {
            case COLUMN_INDEX -> false;
            case COLUMN_CHILD_INDEX -> true;
            case COLUMN_MATRIX_INDEX -> true;
            case COLUMN_COLOR_TRANSFORM_INDEX -> true;
            default -> throw new IllegalArgumentException("Unknown column: " + column);
        };
    }

    @Override
    public Object getValueAt(int row, int column) {
        MovieClipFrameElement frameElement = this.frameElements.get(row);

        return switch (column) {
            case COLUMN_INDEX -> row;
            case COLUMN_CHILD_INDEX -> frameElement.childIndex();
            case COLUMN_MATRIX_INDEX -> frameElement.matrixIndex();
            case COLUMN_COLOR_TRANSFORM_INDEX -> frameElement.colorTransformIndex();
            default -> throw new IllegalArgumentException("Unknown column: " + column);
        };
    }

    // FIXME: all changes are visible only after frame changing back and forth
    //  and that leads to a problem: you can't see any changes if movie clip has only 1 frame
    @Override
    public void setValueAt(Object value, int row, int column) {
        MovieClipFrameElement frameElement = this.frameElements.get(row);

        int childIndex = frameElement.childIndex();
        int matrixIndex = frameElement.matrixIndex();
        int colorTransformIndex = frameElement.colorTransformIndex();

        try {
            switch (column) {
                case COLUMN_CHILD_INDEX -> {
                    if (value == null) {
                        throw new IllegalArgumentException("Child index cannot be null");
                    }

                    int newChildIndex = (int) value;
                    if (newChildIndex == childIndex) return;
                    if (newChildIndex < 0 || newChildIndex >= childCountGetter.get()) {
                        throw new IndexOutOfBoundsException("Child index is out of bounds");
                    }

                    childIndex = newChildIndex;
                }
                case COLUMN_MATRIX_INDEX -> {
                    int newMatrixIndex = value == null || (int) value == -1 ? 0xFFFF : (int) value;
                    if (newMatrixIndex == matrixIndex) return;
                    if (newMatrixIndex != 0xFFFF && (newMatrixIndex < 0 || newMatrixIndex >= matrixCountGetter.get())) {
                        throw new IndexOutOfBoundsException("Matrix index is out of bounds");
                    }

                    matrixIndex = newMatrixIndex;
                }
                case COLUMN_COLOR_TRANSFORM_INDEX -> {
                    int newColorTransformIndex = value == null || (int) value == -1 ? 0xFFFF : (int) value;
                    if (newColorTransformIndex == colorTransformIndex) return;
                    if (newColorTransformIndex != 0xFFFF && (newColorTransformIndex < 0 || newColorTransformIndex >= colorTransformCountGetter.get())) {
                        throw new IndexOutOfBoundsException("Color transform index is out of bounds");
                    }

                    colorTransformIndex = newColorTransformIndex;
                }
                default -> throw new IllegalArgumentException("Unknown column: " + column);
            }

            // TODO: make a command and add it to global UndoRedoManager
            this.frameElements.set(row, new MovieClipFrameElement(childIndex, matrixIndex, colorTransformIndex));

            this.updateFrameElements();

            fireTableCellUpdated(row, column);
        } catch (Exception e) {
            // TODO: highlight cell with red border
            LOGGER.warn("New value rejected: {}", e.getLocalizedMessage());
        }
    }

    @Override
    public void reorderRows(int firstRow, int rowCount, int targetRow) {
        List<MovieClipFrameElement> rowRange = this.frameElements.subList(firstRow, firstRow + rowCount);
        List<MovieClipFrameElement> movedElements = new ArrayList<>(rowRange);

        rowRange.clear();

        if (targetRow > firstRow) {
            targetRow -= rowCount;
        }

        // TODO: make a command and add it to global UndoRedoManager
        this.frameElements.addAll(targetRow, movedElements);

        this.updateFrameElements();
    }

    public void delete(int firstRow, int rowCount) {
        // TODO: make a command and add it to global UndoRedoManager
        this.frameElements.subList(firstRow, firstRow + rowCount).clear();
        this.fireTableRowsDeleted(firstRow, firstRow + rowCount);

        this.updateFrameElements();
    }

    private void updateFrameElements() {
        // Note: there is no need in notifying renderable MovieClip object as it always tries to get current frame elements from frame
        this.frame.setElements(this.frameElements);
    }
}
