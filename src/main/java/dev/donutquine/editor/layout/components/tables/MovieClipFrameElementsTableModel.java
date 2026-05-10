package dev.donutquine.editor.layout.components.tables;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import dev.donutquine.swf.movieclips.MovieClipFrame;
import dev.donutquine.swf.movieclips.MovieClipFrameElement;

public class MovieClipFrameElementsTableModel extends AbstractTableModel implements RowReorderableTableModel {
    private static final String[] COLUMN_NAMES = {"#", "Child #", "Matrix", "Color Transform"};
    private static final Class<?>[] COLUMN_CLASSES = {Integer.class, Integer.class, Integer.class, Integer.class};

    private static final int COLUMN_INDEX = 0;
    private static final int COLUMN_CHILD_INDEX = 1;
    private static final int COLUMN_MATRIX_INDEX = 2;
    private static final int COLUMN_COLOR_TRANSFORM_INDEX = 3;

    // Note: was final before, but I prefer modifying existing object rather than allocating a new one every sneeze
    private MovieClipFrame frame;
    private List<MovieClipFrameElement> frameElements;

    public MovieClipFrameElementsTableModel(MovieClipFrame frame) {
        super();

        this.setFrame(frame);
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
