package dev.donutquine.editor.gui.layout.components.tables;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.IntConsumer;
import javax.swing.table.AbstractTableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dev.donutquine.swf.Tag;
import dev.donutquine.swf.movieclips.MovieClipFrame;
import dev.donutquine.swf.movieclips.MovieClipFrameElement;
import dev.donutquine.swf.movieclips.MovieClipFrame.Builder;

public class MovieClipFramesTableModel extends AbstractTableModel implements RowReorderableTableModel, RowAppendableTableModel {
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieClipFramesTableModel.class);

    private static final String[] COLUMN_NAMES = {"#", "Name"};
    private static final Class<?>[] COLUMN_CLASSES = {Integer.class, String.class};

    public static final int COLUMN_INDEX = 0;
    public static final int COLUMN_NAME_INDEX = 1;

    private final List<MovieClipFrame> frames;
    private final IntConsumer currentFrameSetter;

    public MovieClipFramesTableModel(List<MovieClipFrame> frames, IntConsumer currentFrameSetter) {
        super();

        this.frames = frames;
        this.currentFrameSetter = currentFrameSetter;
	}

    @Override
    public int getRowCount() {
        // NOTE: empty "append" row
        return this.frames.size() + 1;
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
            case COLUMN_NAME_INDEX -> true;
            default -> throw new IllegalArgumentException("Unknown column: " + column);
        };
    }

    @Override
    public Object getValueAt(int row, int column) {
        if (isAppendRow(row)) {
            return null;
        }

        MovieClipFrame frame = this.frames.get(row);

        return switch (column) {
            case COLUMN_INDEX -> row;
            case COLUMN_NAME_INDEX -> frame.getLabel();
            default -> throw new IllegalArgumentException("Unknown column: " + column);
        };
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
        try {
            if (isAppendRow(row)) {
                assert column == COLUMN_NAME_INDEX;

                String label = ((String) value).trim();
                if (label.isEmpty()) {
                    label = null;
                }

                int indexOfFrameWithSameLabel = indexOfFrameWithLabel(label);
                if (label != null && indexOfFrameWithSameLabel != -1) {
                    throw new IllegalArgumentException("A movie clip frame label must be unique");
                }

                MovieClipFrame newFrame = MovieClipFrame.builder().withLabel(label).build();
                this.insert(row, newFrame);
                return;
            }

            MovieClipFrame frame = this.frames.get(row);
            switch (column) {
                case COLUMN_NAME_INDEX -> {
                    String label = ((String) value).trim();
                    if (label.isEmpty()) {
                        label = null;
                    }

                    int indexOfFrameWithSameLabel = indexOfFrameWithLabel(label);
                    if (label != null && indexOfFrameWithSameLabel != -1) {
                        // NOTE: do not update cell as the label is already the same value
                        if (indexOfFrameWithSameLabel == row) {
                            return;
                        }

                        throw new IllegalArgumentException("A movie clip frame label must be unique");
                    }

                    // TODO: make a command and add it to global UndoRedoManager
                    Builder builder = MovieClipFrame.builder();
                    // TODO: optimize by adding MovieClipFrame.setLabel
                    builder.withLabel(label);
                    builder.setIncludeElements(frame.getTag() == Tag.MOVIE_CLIP_FRAME);

                    // TODO: optimize by adding setElements or/and addAllElements methods to builder. 
                    //  Frame elements are immutable so may be copied as references.
                    for (MovieClipFrameElement frameElement : frame.getElements()) {
                        builder.addElement(frameElement);
                    }

                    this.frames.set(row, builder.build());
                }
                default -> throw new IllegalArgumentException("Unknown column: " + column);
            }


            this.updateFrames();

            fireTableCellUpdated(row, column);
        } catch (Exception e) {
            // TODO: highlight cell with red border
            LOGGER.warn("New value rejected: {}", e.getLocalizedMessage());
        }
    }

    @Override
    public void reorderRows(int firstRow, int rowCount, int targetRow) {
        List<MovieClipFrame> rowRange = this.frames.subList(firstRow, firstRow + rowCount);
        List<MovieClipFrame> movedFrames = new ArrayList<>(rowRange);

        // TODO: make a command and add it to global UndoRedoManager
        rowRange.clear();
        if (targetRow > firstRow) {
            targetRow -= rowCount;
        }

        this.fireTableRowsDeleted(firstRow, firstRow + rowCount);
        this.frames.addAll(targetRow, movedFrames);
        this.fireTableRowsInserted(targetRow, targetRow + rowCount);

        this.updateFrames();
    }

    public void insert(int index, MovieClipFrame newFrame) {
        // TODO: make a command and add it to global UndoRedoManager
        this.frames.add(index, newFrame);
        this.fireTableRowsInserted(index, index);

        this.updateFrames();
    }

    public void delete(int firstRow, int rowCount) {
        int lastIndex = firstRow + rowCount;

        if (firstRow == 0 && lastIndex == this.frames.size()) {
            throw new IllegalArgumentException("At least one frame must remain");
        }

        // TODO: make a command and add it to global UndoRedoManager
        this.frames.subList(firstRow, lastIndex).clear();
        this.fireTableRowsDeleted(firstRow, lastIndex);

        this.updateFrames();
    }

    public void duplicate(int firstRow, int rowCount) {
        int lastIndex = firstRow + rowCount;

        // NOTE: current strategy is to put all duplicated frames after selection (e.g. `|1 2 3| 4` -> `1 2 3 (1 2 3) 4`), 
        //  but there is also an idea of putting duplicate frame after original frame (e.g. `|1 2 3| 4` -> `1 (1) 2 (2) 3 (3) 4`).
        //
        //  Also, we may allow multiple selections, then we can put all duplicates
        //  - at the end                    (e.g. `|1 2 3| 4 5 |6 7| 8` -> `1 2 3 4 5 6 7 8 (1 2 3 6 7)`)
        //  - after last selection          (e.g. `|1 2 3| 4 5 |6 7| 8` -> `1 2 3 4 5 6 7 (1 2 3 6 7) 8`) (bad),
        //  - after theirs selection range  (e.g. `|1 2 3| 4 5 |6 7| 8` -> `1 2 3 (1 2 3) 4 5 6 7 (6 7) 8`),
        //  - after their original frames   (e.g. `|1 2 3| 4 5 |6 7| 8` -> `1 (1) 2 (2) 3 (3) 4 5 6 (6) 7 (7) 8`)
        List<MovieClipFrame> rangeToDuplicate = this.frames.subList(firstRow, lastIndex);

        List<MovieClipFrame> duplicates = new ArrayList<>(rowCount);
        for (MovieClipFrame frameToDuplicate : rangeToDuplicate) {
            Builder builder = MovieClipFrame.builder();
            // NOTE: label must be unique, so we set duplicate label to null. 
            //  Or maybe we should add some index to the original child name and set it?
            // builder.withLabel(null);  // Does nothing as null as already a default value in builder
            builder.setIncludeElements(frameToDuplicate.getTag() == Tag.MOVIE_CLIP_FRAME);

            // TODO: optimize by adding setElements or/and addAllElements methods to builder. 
            //  Frame elements are immutable so may be copied as references.
            for (MovieClipFrameElement frameElement : frameToDuplicate.getElements()) {
                builder.addElement(frameElement);
            }

            duplicates.add(builder.build());
        }

        // TODO: make a command and add it to global UndoRedoManager
        this.frames.addAll(lastIndex, duplicates);
        this.fireTableRowsInserted(lastIndex, lastIndex + rowCount);

        this.updateFrames();
    }

    private void updateFrames() {
        // TODO: decide what frame should become next after changing frame count. Before the selection of after?
        this.currentFrameSetter.accept(0);
        // TODO: sync modification with MovieClip original
    }

    @Override
    public boolean isAppendRow(int row) {
        return row == this.frames.size();
    }

    private int indexOfFrameWithLabel(String label) {
        for (int i = 0; i < this.frames.size(); i++) {
            MovieClipFrame frame = this.frames.get(i);
            if (Objects.equals(frame.getLabel(), label)) {
                return i;
            }
        }

        return -1;
    }
}
