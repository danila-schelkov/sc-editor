package dev.donutquine.editor.gui.layout.components.tables;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dev.donutquine.swf.movieclips.MovieClipFrame;
import dev.donutquine.swf.movieclips.MovieClipFrameElement;

public class MovieClipFrameElementsTableModel extends AbstractTableModel implements RowReorderableTableModel, RowAppendableTableModel, PendingRowTableModel {
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieClipFrameElementsTableModel.class);

    private static final String[] COLUMN_NAMES = {"#", "Child #", "Matrix", "Color Transform"};
    private static final Class<?>[] COLUMN_CLASSES = {Integer.class, Integer.class, Integer.class, Integer.class};

    public static final int COLUMN_INDEX = 0;
    public static final int COLUMN_CHILD_INDEX = 1;
    public static final int COLUMN_MATRIX_INDEX = 2;
    public static final int COLUMN_COLOR_TRANSFORM_INDEX = 3;

	private final OnPendingRowInsertedListener onPendingRowInserted;

    private final Runnable forceFrameUpdate;
    private final ChildCountGetter childCountGetter;
    private final MatrixCountGetter matrixCountGetter;
    private final ColorTransformCountGetter colorTransformCountGetter;
    // Note: was final before, but I prefer modifying existing object rather than allocating a new one every sneeze
    private MovieClipFrame frame;
    private List<MovieClipFrameElement> frameElements;

    private int pendingRow = -1;

    public interface ChildCountGetter {
        int get();
    }

    public interface MatrixCountGetter {
        int get();
    }

    public interface ColorTransformCountGetter {
        int get();
    }

    public MovieClipFrameElementsTableModel(MovieClipFrame frame, Runnable forceFrameUpdate, ChildCountGetter childCountGetter, MatrixCountGetter matrixCountGetter, ColorTransformCountGetter colorTransformCountGetter, OnPendingRowInsertedListener onPendingRowInserted) {
        super();

        this.onPendingRowInserted = onPendingRowInserted;

        this.setFrame(frame);
        this.forceFrameUpdate = forceFrameUpdate;
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
        // NOTE: empty "append" row
        return this.frameElements.size() + getPendingRowCount() + 1;
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
        if (isAppendRow(row) || isPendingRow(row)) {
            return switch (column) {
                case COLUMN_INDEX -> false;
                case COLUMN_CHILD_INDEX -> true;
                case COLUMN_MATRIX_INDEX -> false;
                case COLUMN_COLOR_TRANSFORM_INDEX -> false;
                default -> throw new IllegalArgumentException("Unknown column: " + column);
            };
        }

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
        if (isAppendRow(row) || isPendingRow(row)) {
            return null;
        }

        row = offsetNonPendingRow(row);

        MovieClipFrameElement frameElement = this.frameElements.get(row);

        return switch (column) {
            case COLUMN_INDEX -> row;
            case COLUMN_CHILD_INDEX -> frameElement.childIndex();
            case COLUMN_MATRIX_INDEX -> frameElement.matrixIndex();
            case COLUMN_COLOR_TRANSFORM_INDEX -> frameElement.colorTransformIndex();
            default -> throw new IllegalArgumentException("Unknown column: " + column);
        };
    }

	@Override
    public void setValueAt(Object value, int row, int column) {
        try {
            if (isAppendRow(row) || isPendingRow(row)) {
                validateChildIndex((Integer) value, -1);
                if (isPendingRow(row)) {
                    removePendingRow(row);
                }

                insert(row, new MovieClipFrameElement((int) value, 0xFFFF, 0xFFFF));
                return;
            }

            MovieClipFrameElement frameElement = this.frameElements.get(row);

            int childIndex = frameElement.childIndex();
            int matrixIndex = frameElement.matrixIndex();
            int colorTransformIndex = frameElement.colorTransformIndex();

            switch (column) {
                case COLUMN_CHILD_INDEX -> {
                    validateChildIndex((Integer) value, row);

                    int newChildIndex = (int) value;
                    if (newChildIndex == childIndex) return;

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

        // TODO: make a command and add it to global UndoRedoManager
        rowRange.clear();
        if (targetRow > firstRow) {
            targetRow -= rowCount;
        }

        this.fireTableRowsDeleted(firstRow, firstRow + rowCount);
        this.frameElements.addAll(targetRow, movedElements);
        this.fireTableRowsInserted(targetRow, targetRow + rowCount);

        this.updateFrameElements();
    }

	@Override
	public boolean isAppendRow(int row) {
        return row == this.frameElements.size() + getPendingRowCount();
	}
    
    @Override
    public int getPendingRowCount() {
        return this.pendingRow != -1 ? 1 : 0;
    }

	@Override
	public boolean isPendingRow(int row) {
        return row == this.pendingRow;
	}

    @Override
    public void insertPendingRow(int row) {
        assert !hasPendingRow() : "This implementation supports only one pending row";

        this.pendingRow = row;
        this.fireTableRowsInserted(row, row);
        this.onPendingRowInserted.handlePendingRowInserted(row);
    }

    @Override
    public void removePendingRow(int row) {
        assert this.pendingRow == row;
        this.pendingRow = -1;
        this.fireTableRowsDeleted(row, row);
    }

    @Override
    public void clearPendingRows() {
        removePendingRow(this.pendingRow);
    }

    @Override
    public int offsetNonPendingRow(int nonPendingRow) {
        // NOTE: won't work for multiple pending rows not in a row
        if (hasPendingRow() && nonPendingRow >= pendingRow) {
            return nonPendingRow - 1;
        }

        return nonPendingRow;
	}

    public void insert(int index, MovieClipFrameElement newElement) {
        // TODO: make a command and add it to global UndoRedoManager
        this.frameElements.add(index, newElement);
        this.fireTableRowsInserted(index, index);

        this.updateFrameElements();
    }

    public void delete(int firstRow, int rowCount) {
        // TODO: make a command and add it to global UndoRedoManager
        this.frameElements.subList(firstRow, firstRow + rowCount).clear();
        this.fireTableRowsDeleted(firstRow, firstRow + rowCount);

        this.updateFrameElements();
    }

    private void updateFrameElements() {
        // Note: there is no need in notifying renderable MovieClip object as it always tries to get current frame elements from next frame.
        //  Actually, it is needed to update it if it is movie clip with single frame.
        this.frame.setElements(this.frameElements);

        this.forceFrameUpdate.run();
    }

    private void validateChildIndex(Integer newChildIndex, int row) {
        if (newChildIndex == null) {
            throw new IllegalArgumentException("Child index cannot be null");
        }

        if (newChildIndex < 0 || newChildIndex >= childCountGetter.get()) {
            throw new IndexOutOfBoundsException("Child index is out of bounds");
        }

        int indexOfFrameWithChildIndex = indexOfFrameWithChildIndex(newChildIndex);
        if (indexOfFrameWithChildIndex != -1 && indexOfFrameWithChildIndex != row) {
            throw new IllegalArgumentException("Child index must be unique within a frame");
        }
    }

    private int indexOfFrameWithChildIndex(int childIndex) {
        for (int i = 0; i < this.frameElements.size(); i++) {
            MovieClipFrameElement frameElement = this.frameElements.get(i);
            if (frameElement.childIndex() == childIndex) {
                return i;
            }
        }

        return -1;
    }
}
