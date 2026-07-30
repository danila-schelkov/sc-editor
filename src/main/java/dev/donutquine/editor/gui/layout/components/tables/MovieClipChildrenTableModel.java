package dev.donutquine.editor.gui.layout.components.tables;

import java.util.function.Function;
import javax.swing.table.AbstractTableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dev.donutquine.editor.renderer.BlendMode;
import dev.donutquine.renderer.impl.swf.objects.DisplayObject;
import dev.donutquine.renderer.impl.swf.objects.MovieClip;

public class MovieClipChildrenTableModel extends AbstractTableModel {
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieClipFramesTableModel.class);

    private static final String[] COLUMN_NAMES = {"#", "Id", "Type", "Name", "Blend Mode", "Visible"};
    private static final Class<?>[] COLUMN_CLASSES = {Integer.class, Integer.class, String.class, String.class, BlendMode.class, Boolean.class};

    public static final int COLUMN_INDEX = 0;
    public static final int COLUMN_ID_INDEX = 1;
    public static final int COLUMN_TYPE_INDEX = 2;
    public static final int COLUMN_NAME_INDEX = 3;
    public static final int COLUMN_BLEND_MODE_INDEX = 4;
    public static final int COLUMN_VISIBILITY_INDEX = 5;

	private final DisplayObject[] timelineChildren;
	private final String[] timelineChildrenNames;

    public MovieClipChildrenTableModel(MovieClip movieClip) {
        super();

        DisplayObject[] timelineChildren = movieClip.getTimelineChildren();
        String[] timelineChildrenNames = movieClip.getTimelineChildrenNames();
        assert timelineChildrenNames == null || timelineChildrenNames.length == 0 || timelineChildren.length == timelineChildrenNames.length;

        this.timelineChildren = timelineChildren;
        this.timelineChildrenNames = timelineChildrenNames;
	}

    @Override
    public int getRowCount() {
        return this.timelineChildren.length;
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
            case COLUMN_ID_INDEX -> false;
            case COLUMN_TYPE_INDEX -> false;
            case COLUMN_NAME_INDEX -> false;
            case COLUMN_BLEND_MODE_INDEX -> true;
            case COLUMN_VISIBILITY_INDEX -> true;
            default -> throw new IllegalArgumentException("Unknown column: " + column);
        };
    }

    @Override
    public Object getValueAt(int row, int column) {
        DisplayObject timelineChild = this.timelineChildren[row];
        String childName = this.timelineChildrenNames != null && this.timelineChildrenNames.length > 0 ? this.timelineChildrenNames[row] : null;

        return switch (column) {
            case COLUMN_INDEX -> row;
            case COLUMN_ID_INDEX -> timelineChild.getId();
            case COLUMN_TYPE_INDEX -> timelineChild.getClass().getSimpleName();
            case COLUMN_NAME_INDEX -> childName;
            case COLUMN_BLEND_MODE_INDEX -> timelineChild.getBlendMode();
            case COLUMN_VISIBILITY_INDEX -> timelineChild.isVisible();
            default -> throw new IllegalArgumentException("Unknown column: " + column);
        };
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
        DisplayObject timelineChild = this.timelineChildren[row];

        try {
            switch (column) {
                // NOTE: is not used because no enum cell editor is present
                case COLUMN_BLEND_MODE_INDEX -> {
                    BlendMode blendMode = (BlendMode) value;
                    timelineChild.setBlendMode(blendMode);
                }
                case COLUMN_VISIBILITY_INDEX -> {
                    boolean isVisible = (boolean) value;
                    timelineChild.setVisibleRecursive(isVisible);
                }
                default -> throw new IllegalArgumentException("Unknown column: " + column);
            }

            this.updateChildren();

            fireTableCellUpdated(row, column);
        } catch (Exception e) {
            // TODO: highlight cell with red border
            LOGGER.warn("New value rejected: {}", e.getLocalizedMessage());
        }
    }

    public void changeVisibility(int childIndex, Function<DisplayObject, Boolean> visibilityFunction) {
        DisplayObject displayObject = this.timelineChildren[childIndex];
        displayObject.setVisibleRecursive(visibilityFunction.apply(displayObject));
        this.fireTableCellUpdated(childIndex, COLUMN_VISIBILITY_INDEX);
    }

    public void setBlendMode(int childIndex, BlendMode blendMode) {
        DisplayObject displayObject = this.timelineChildren[childIndex];
        displayObject.setBlendMode(blendMode);
        this.fireTableCellUpdated(childIndex, COLUMN_BLEND_MODE_INDEX);
    }

    private void updateChildren() {
        // TODO: sync modification with MovieClip original
    }
}
