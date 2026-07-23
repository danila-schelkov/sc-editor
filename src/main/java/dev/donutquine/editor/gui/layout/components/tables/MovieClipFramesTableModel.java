package dev.donutquine.editor.gui.layout.components.tables;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntConsumer;
import javax.swing.table.AbstractTableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dev.donutquine.swf.Tag;
import dev.donutquine.swf.movieclips.MovieClipFrame;
import dev.donutquine.swf.movieclips.MovieClipFrameElement;
import dev.donutquine.swf.movieclips.MovieClipFrame.Builder;

public class MovieClipFramesTableModel extends AbstractTableModel {
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieClipFramesTableModel.class);

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
            case COLUMN_NAME_INDEX -> true;
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

    @Override
    public void setValueAt(Object value, int row, int column) {
        MovieClipFrame frame = this.frames.get(row);

        try {
            switch (column) {
                case COLUMN_NAME_INDEX -> {
                    String newLabel = (String) value;

                    // TODO: make a command and add it to global UndoRedoManager
                    Builder builder = MovieClipFrame.builder();
                    // TODO: optmize by adding MovieClipFrame.setLabel
                    builder.withLabel(newLabel);
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

    public void delete(int firstRow, int rowCount) {
        if (rowCount == this.frames.size()) {
            throw new IllegalArgumentException("At least one frame must remain");
        }

        int lastIndex = firstRow + rowCount;
        // TODO: make a command and add it to global UndoRedoManager
        this.frames.subList(firstRow, lastIndex).clear();
        this.fireTableRowsDeleted(firstRow, lastIndex);

        this.updateFrames();
    }

    public void duplicate(int firstRow, int rowCount) {
        // TODO: make a command and add it to global UndoRedoManager
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
            builder.withLabel(frameToDuplicate.getLabel());
            builder.setIncludeElements(frameToDuplicate.getTag() == Tag.MOVIE_CLIP_FRAME);

            // TODO: optimize by adding setElements or/and addAllElements methods to builder. 
            //  Frame elements are immutable so may be copied as references.
            for (MovieClipFrameElement frameElement : frameToDuplicate.getElements()) {
                builder.addElement(frameElement);
            }

            duplicates.add(builder.build());
        }

        this.frames.addAll(lastIndex, duplicates);

        this.fireTableRowsInserted(lastIndex, lastIndex + rowCount);

        this.updateFrames();
    }

    private void updateFrames() {
        // TODO: decide what frame should become next after changing frame count. Before the selection of after?
        this.currentFrameSetter.accept(0);
        // TODO: sync modification with MovieClip original
    }
}
