package dev.donutquine.editor.gui.layout.components.tables;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import dev.donutquine.swf.DisplayObjectOriginal;
import dev.donutquine.swf.movieclips.MovieClipOriginal;

public class DisplayObjectsTableModel extends AbstractTableModel {
    private static final String[] COLUMN_NAMES = {"Id", "Name", "Type"};
    private static final Class<?>[] COLUMN_CLASSES = {Integer.class, String.class, String.class};

    public static final int COLUMN_ID_INDEX = 0;
    public static final int COLUMN_NAME_INDEX = 1;
    public static final int COLUMN_TYPE_INDEX = 2;
    public static final int COLUMN_COUNT = 3;

	private final List<? extends DisplayObjectOriginal> objects;

    public DisplayObjectsTableModel(List<? extends DisplayObjectOriginal> objects) {
        super();

        this.objects = objects;
	}

    @Override
    public int getRowCount() {
        return this.objects.size();
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
            case COLUMN_ID_INDEX -> false;
            case COLUMN_NAME_INDEX -> false;
            case COLUMN_TYPE_INDEX -> false;
            default -> throw new IllegalArgumentException("Unknown column: " + column);
        };
    }

    @Override
    public Object getValueAt(int row, int column) {
        DisplayObjectOriginal object = this.objects.get(row);

        return switch (column) {
            case COLUMN_ID_INDEX -> object.getId();
            case COLUMN_NAME_INDEX -> object instanceof MovieClipOriginal movieClipOriginal ? movieClipOriginal.getExportName() : null;
            case COLUMN_TYPE_INDEX -> object.getClass().getSimpleName().replaceAll("Original$", "");
            default -> throw new IllegalArgumentException("Unknown column: " + column);
        };
    }
}
