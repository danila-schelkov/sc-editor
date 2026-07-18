package dev.donutquine.editor.layout.components.tables;

import java.util.List;
import java.util.function.IntConsumer;
import javax.swing.table.AbstractTableModel;
import dev.donutquine.swf.movieclips.MovieClipFrame;

public class MovieClipFramesTableModel extends AbstractTableModel {
    // private static final Logger LOGGER = LoggerFactory.getLogger(MovieClipFramesTableModel.class);

    private static final String[] COLUMN_NAMES = {"#", "Name"};
    private static final Class<?>[] COLUMN_CLASSES = {Integer.class, String.class};

    private static final int COLUMN_INDEX = 0;
    private static final int COLUMN_NAME_INDEX = 1;

    private final List<MovieClipFrame> frames;
    private final IntConsumer currentFrameSetter;

    public MovieClipFramesTableModel(List<MovieClipFrame> frames, IntConsumer currentFrameSetter) {
        super();

        this.frames = frames;
        this.currentFrameSetter = currentFrameSetter;
	}

    @Override
    public int getRowCount() {
        return this.frames.size();
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
            case COLUMN_NAME_INDEX -> false;
            default -> throw new IllegalArgumentException("Unknown column: " + column);
        };
    }

    @Override
    public Object getValueAt(int row, int column) {
        MovieClipFrame frame = this.frames.get(row);

        return switch (column) {
            case COLUMN_INDEX -> row;
            case COLUMN_NAME_INDEX -> frame.getLabel();
            default -> throw new IllegalArgumentException("Unknown column: " + column);
        };
    }

    public void delete(int firstRow, int rowCount) {
        if (rowCount == this.frames.size()) {
            throw new IllegalArgumentException("At least one frame must remain");
        }

        // TODO: make a command and add it to global UndoRedoManager
        this.frames.subList(firstRow, firstRow + rowCount).clear();
        this.fireTableRowsDeleted(firstRow, firstRow + rowCount);

        this.updateFrames();
    }

    private void updateFrames() {
        // TODO: decide what frame should become next after changing frame count. Before the selection of after?
        this.currentFrameSetter.accept(0);
    }
}
