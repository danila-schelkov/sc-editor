package dev.donutquine.editor.gui.layout.panels;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import dev.donutquine.editor.gui.layout.SupercellSWFLayoutController;
import dev.donutquine.editor.gui.layout.components.listeners.DisplayObjectListMouseListener;
import dev.donutquine.editor.gui.layout.components.tables.DisplayObjectsTableModel;
import dev.donutquine.editor.gui.layout.contextmenus.DisplayObjectContextMenu;
import dev.donutquine.swf.DisplayObjectOriginal;
import dev.donutquine.swf.SupercellSWF;
import dev.donutquine.swf.exceptions.UnableToFindObjectException;
import dev.donutquine.swf.movieclips.MovieClipChild;
import dev.donutquine.swf.movieclips.MovieClipFrame;
import dev.donutquine.swf.movieclips.MovieClipOriginal;

public class DisplayObjectListPanel extends FilterableTablePanel<DisplayObjectsTableModel> {
    private static final String OPEN_SELECTED_ROW = "openSelectedRow";

    private final SupercellSWFLayoutController controller;

    public DisplayObjectListPanel(SupercellSWFLayoutController controller, List<? extends DisplayObjectOriginal> objects) {
        super(new DisplayObjectsTableModel(objects));

        this.controller = controller;

        this.table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        this.table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), OPEN_SELECTED_ROW);

        this.table.getActionMap().put(OPEN_SELECTED_ROW, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                handleRowSelected();
            }
        });

        new DisplayObjectContextMenu(this.table, controller);
        this.table.addMouseListener(new DisplayObjectListMouseListener(this::handleRowSelected));

        this.sorter.setSortKeys(List.of(
            new RowSorter.SortKey(DisplayObjectsTableModel.COLUMN_NAME_INDEX, SortOrder.DESCENDING)
        ));

        this.initializeFilters(controller.assetFile.asset);
    }

    protected void initializeFilters(SupercellSWF swf) {
        this.filters.put("id", (filterValue) -> {
            if (!filterValue.isEmpty()) {
                int id;
                try {
                    id = Integer.parseUnsignedInt(filterValue);
                } catch (NumberFormatException e) {
                    // TODO: log
                    id = -1;
                }

                final int finalId = id;

                return new RowFilter<DisplayObjectsTableModel, Integer>() {
                    @Override
                    public boolean include(RowFilter.Entry<? extends DisplayObjectsTableModel, ? extends Integer> entry) {
                        return (int) entry.getValue(DisplayObjectsTableModel.COLUMN_ID_INDEX) == finalId;
                    }
                };
            }

            return null;
        });

        this.filters.put("id~", (filterValue) -> {
            if (!filterValue.isEmpty()) {
                try {
                    Integer.parseUnsignedInt(filterValue);
                } catch (NumberFormatException e) {
                    // TODO: log
                }

                return new RowFilter<DisplayObjectsTableModel, Integer>() {
                    @Override
                    public boolean include(RowFilter.Entry<? extends DisplayObjectsTableModel, ? extends Integer> entry) {
                        return String.valueOf(entry.getValue(DisplayObjectsTableModel.COLUMN_ID_INDEX)).contains(filterValue);
                    }
                };
            }

            return null;
        });

        this.filters.put("exportName", (filterValue) -> {
            return new RowFilter<DisplayObjectsTableModel, Integer>() {
                @Override
                public boolean include(RowFilter.Entry<? extends DisplayObjectsTableModel, ? extends Integer> entry) {
                    String exportName = (String) entry.getValue(DisplayObjectsTableModel.COLUMN_NAME_INDEX);
                    return filterValue.equals(exportName);
                }
            };
        });

        this.filters.put("exportName~", (filterValue) -> {
            return new RowFilter<DisplayObjectsTableModel, Integer>() {
                @Override
                public boolean include(RowFilter.Entry<? extends DisplayObjectsTableModel, ? extends Integer> entry) {
                    String exportName = (String) entry.getValue(DisplayObjectsTableModel.COLUMN_NAME_INDEX);
                    return exportName != null && exportName.contains(filterValue);
                }
            };
        });

        // TODO: add childCount and frameCount filters
        this.filters.put("type", (filterValue) -> {
            // MovieClip, Shape, TextField — are the only supported types here
            return new RowFilter<DisplayObjectsTableModel, Integer>() {
                @Override
                public boolean include(RowFilter.Entry<? extends DisplayObjectsTableModel, ? extends Integer> entry) {
                    String type = (String) entry.getValue(DisplayObjectsTableModel.COLUMN_TYPE_INDEX);
                    return type.equals(filterValue);
                }
            };
        });

        this.filters.put("childId", (filterValue) -> {
            if (!filterValue.isEmpty()) {
                int childId;
                try {
                    childId = Integer.parseUnsignedInt(filterValue);
                } catch (NumberFormatException e) {
                    // TODO: log
                    childId = -1;
                }

                final int finalChildId = childId;

                return new MovieClipChildRowFilter(swf) {
                    @Override
                    protected boolean include(MovieClipChild child) {
                        return child.id() == finalChildId;
                    }
                };
            }

            return null;
        });

        this.filters.put("childId~", (filterValue) -> {
            if (!filterValue.isEmpty()) {
                try {
                    Integer.parseUnsignedInt(filterValue);
                } catch (NumberFormatException e) {
                    // TODO: log
                }

                return new MovieClipChildRowFilter(swf) {
                    @Override
                    protected boolean include(MovieClipChild child) {
                        return String.valueOf(child.id()).contains(filterValue);
                    }
                };
            }

            return null;
        });

        this.filters.put("childName", (childName) -> {
            return new MovieClipChildRowFilter(swf) {
                @Override
                protected boolean include(MovieClipChild child) {
                    return childName.equals(child.name());
                }
            };
        });

        this.filters.put("childName~", (childName) -> {
            return new MovieClipChildRowFilter(swf) {
                @Override
                protected boolean include(MovieClipChild child) {
                    return child.name() != null && child.name().contains(childName);
                }
            };
        });

        this.filters.put("frameLabel", (frameLabel) -> {
            return new MovieClipFrameRowFilter(swf) {
                @Override
                protected boolean include(MovieClipFrame frame) {
                    return frameLabel.equals(frame.getLabel());
                }
            };
        });

        this.filters.put("frameLabel~", (frameLabel) -> {
            return new MovieClipFrameRowFilter(swf) {
                @Override
                protected boolean include(MovieClipFrame frame) {
                    return frame.getLabel() != null && frame.getLabel().contains(frameLabel);
                }
            };
        });
    }

    public void selectObjectById(int id) {
        int row = this.table.indexOf(id, DisplayObjectsTableModel.COLUMN_ID_INDEX);
        if (row == -1) {
            this.resetFilter();

            row = this.table.indexOf(id, DisplayObjectsTableModel.COLUMN_ID_INDEX);
        }

        this.table.select(row);
    }

    private void handleRowSelected() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }

        int id = (int) table.getValueAt(selectedRow, DisplayObjectsTableModel.COLUMN_ID_INDEX);
        String name = (String) table.getValueAt(selectedRow, DisplayObjectsTableModel.COLUMN_NAME_INDEX);

        this.controller.selectObject(id, name);
    }

    // ANY
    private static abstract class MovieClipFrameRowFilter extends MovieClipRowFilter {
        public MovieClipFrameRowFilter(SupercellSWF swf) {
            super(swf);
        }

        @Override
        protected boolean include(MovieClipOriginal movieClipOriginal) {
            for (MovieClipFrame frame : movieClipOriginal.getFrames()) {
                if (include(frame)) {
                    return true;
                }
            }

            return false;
        }

        protected abstract boolean include(MovieClipFrame frame);
    }

    // ANY
    private static abstract class MovieClipChildRowFilter extends MovieClipRowFilter {
        public MovieClipChildRowFilter(SupercellSWF swf) {
            super(swf);
        }

        @Override
        protected boolean include(MovieClipOriginal movieClipOriginal) {
            for (MovieClipChild child : movieClipOriginal.getChildren()) {
                if (include(child)) {
                    return true;
                }
            }

            return false;
        }

        protected abstract boolean include(MovieClipChild child);
    }

    private static abstract class MovieClipRowFilter extends DisplayObjectRowFilter {
        public MovieClipRowFilter(SupercellSWF swf) {
            super(swf);
        }

        @Override
        protected boolean include(DisplayObjectOriginal original) {
            if (original instanceof MovieClipOriginal movieClipOriginal) {
                return include(movieClipOriginal);
            }

            return false;
        }

        protected abstract boolean include(MovieClipOriginal movieClipOriginal);
    }

    private static abstract class DisplayObjectRowFilter extends RowFilter<DisplayObjectsTableModel, Integer> {
        private final SupercellSWF swf;

        public DisplayObjectRowFilter(SupercellSWF swf) {
            this.swf = swf;
        }

        @Override
        public boolean include(Entry<? extends DisplayObjectsTableModel, ? extends Integer> entry) {
            assert entry.getValueCount() == DisplayObjectsTableModel.COLUMN_COUNT;

            int id = (int) entry.getValue(DisplayObjectsTableModel.COLUMN_ID_INDEX);

            DisplayObjectOriginal original;

            try {
                original = swf.getOriginalDisplayObject(id, null);
            } catch (UnableToFindObjectException e) {
                // NOTE: must not happen as we know the object exists
                throw new RuntimeException(e);
            }

            return include(original);
        }

        protected abstract boolean include(DisplayObjectOriginal original);
    }
}
