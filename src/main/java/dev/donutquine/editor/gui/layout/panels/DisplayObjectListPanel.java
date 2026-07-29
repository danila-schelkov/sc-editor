package dev.donutquine.editor.gui.layout.panels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import dev.donutquine.editor.gui.layout.SupercellSWFLayoutController;
import dev.donutquine.editor.gui.layout.components.tables.Table;
import dev.donutquine.editor.gui.layout.components.listeners.DisplayObjectListMouseListener;
import dev.donutquine.editor.gui.layout.contextmenus.DisplayObjectContextMenu;
import dev.donutquine.swf.DisplayObjectOriginal;
import dev.donutquine.swf.SupercellSWF;
import dev.donutquine.swf.exceptions.UnableToFindObjectException;
import dev.donutquine.swf.movieclips.MovieClipChild;
import dev.donutquine.swf.movieclips.MovieClipFrame;
import dev.donutquine.swf.movieclips.MovieClipOriginal;

public class DisplayObjectListPanel extends JPanel {
    private static final Object[] COLUMN_NAMES = {"Id", "Name", "Type"};
    private static final Class<?>[] COLUMN_CLASSES = {Integer.class, String.class, String.class};
    private static final String OPEN_SELECTED_ROW = "openSelectedRow";

    private final SupercellSWFLayoutController controller;

    private final TableRowSorter<TableModel> sorter;
    private final Table table;

    private final JTextField searchField;

    public DisplayObjectListPanel(SupercellSWFLayoutController controller, Object[][] data) {
        this.controller = controller;

        this.table = new Table(data, COLUMN_NAMES, COLUMN_CLASSES);
        this.table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        this.sorter = new TableRowSorter<>(this.table.getModel());
        table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), OPEN_SELECTED_ROW);

        table.getActionMap().put(OPEN_SELECTED_ROW, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                handleRowSelected();
            }
        });

        new DisplayObjectContextMenu(this.table, controller);
        this.table.addMouseListener(new DisplayObjectListMouseListener(this::handleRowSelected));
        this.table.setRowSorter(this.sorter);

        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(1, SortOrder.DESCENDING));
        this.sorter.setSortKeys(sortKeys);

        this.searchField = new JTextField();

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        panel.add(this.searchField);

        this.searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                find(searchField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                find(searchField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

        setLayout(new BorderLayout());
        this.add(panel, BorderLayout.SOUTH);
        this.add(new JScrollPane(this.table), BorderLayout.CENTER);
    }

    public void resetFilter() {
        this.sorter.setRowFilter(null);
        this.searchField.setText(null);
    }

    public void selectObjectById(int id) {
        int row = this.table.indexOf(id, 0);
        if (row == -1) {
            this.resetFilter();

            row = this.table.indexOf(id, 0);
        }

        this.table.select(row);
    }

    public void setFocusOnTextField() {
        this.searchField.requestFocus();
    }

    // TODO: make ui for filters
    private void find(String text) {
        // TODO: grouping and priority for & (and) and | (or) filters
        List<RowFilter<TableModel, Integer>> orFilters = new ArrayList<>();
        for (String orFilter : text.split("\\|")) { 
            List<RowFilter<TableModel, Integer>> andFilters = new ArrayList<>();
            for (String filter : orFilter.split("&")) { 
                andFilters.add(createFilterFromString(controller.assetFile.asset, filter.trim()));
            }
            orFilters.add(RowFilter.andFilter(andFilters));
        }

        if (orFilters.isEmpty()) {
            this.resetFilter();
            return;
        }

        this.sorter.setRowFilter(RowFilter.orFilter(orFilters));
    }

    private static RowFilter<TableModel, Integer> createFilterFromString(SupercellSWF swf, String filter) {
        if (filter.startsWith("!")) {
            return RowFilter.notFilter(createFilterFromString(swf, filter.substring(1).trim()));
        }

        String[] substring = filter.split("=");
        if (substring.length == 2) {
            String filterName = substring[0].trim();
            String filterValue = substring[1].trim();

            // TODO: add childCount and frameCount filters
            if (filterName.equals("id") && !filterValue.isEmpty()) {
                int id;
                try {
                    id = Integer.parseUnsignedInt(filterValue);
                } catch (NumberFormatException e) {
                    // TODO: log
                    id = -1;
                }

                final int finalId = id;

                return new RowFilter<TableModel, Integer>() {
                    @Override
                    public boolean include(RowFilter.Entry<? extends TableModel, ? extends Integer> entry) {
                        return (int) entry.getValue(0) == finalId;
                    }
                };
            } else if (filterName.equals("id~") && !filterValue.isEmpty()) {
                try {
                    Integer.parseUnsignedInt(filterValue);
                } catch (NumberFormatException e) {
                    // TODO: log
                }

                return new RowFilter<TableModel, Integer>() {
                    @Override
                    public boolean include(RowFilter.Entry<? extends TableModel, ? extends Integer> entry) {
                        return String.valueOf(entry.getValue(0)).contains(filterValue);
                    }
                };
            } else if (filterName.equals("exportName")) {
                return new RowFilter<TableModel, Integer>() {
                    @Override
                    public boolean include(RowFilter.Entry<? extends TableModel, ? extends Integer> entry) {
                        String exportName = (String) entry.getValue(1);
                        return filterValue.equals(exportName);
                    }
                };
            } else if (filterName.equals("exportName~")) {
                return new RowFilter<TableModel, Integer>() {
                    @Override
                    public boolean include(RowFilter.Entry<? extends TableModel, ? extends Integer> entry) {
                        String exportName = (String) entry.getValue(1);
                        return exportName != null && exportName.contains(filterValue);
                    }
                };
            } else if (filterName.equals("type")) {
                // MovieClip, Shape, TextField — are the only supported types here
                return new RowFilter<TableModel, Integer>() {
                    @Override
                    public boolean include(RowFilter.Entry<? extends TableModel, ? extends Integer> entry) {
                        String type = (String) entry.getValue(2);
                        return type.equals(filterValue);
                    }
                };
            } else if (filterName.equals("childId") && !filterValue.isEmpty()) {
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
            } else if (filterName.equals("childId~") && !filterValue.isEmpty()) {
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
            } else if (filterName.equals("childName")) {
                String childName = filterValue;

                return new MovieClipChildRowFilter(swf) {
                    @Override
                    protected boolean include(MovieClipChild child) {
                        return childName.equals(child.name());
                    }
                };
            } else if (filterName.equals("childName~")) {
                String childName = filterValue;

                return new MovieClipChildRowFilter(swf) {
                    @Override
                    protected boolean include(MovieClipChild child) {
                        return child.name() != null && child.name().contains(childName);
                    }
                };
            } else if (filterName.equals("frameLabel")) {
                String frameLabel = filterValue;

                return new MovieClipFrameRowFilter(swf) {
                    @Override
                    protected boolean include(MovieClipFrame frame) {
                        return frameLabel.equals(frame.getLabel());
                    }
                };
            } else if (filterName.equals("frameLabel~")) {
                String frameLabel = filterValue;

                return new MovieClipFrameRowFilter(swf) {
                    @Override
                    protected boolean include(MovieClipFrame frame) {
                        return frame.getLabel() != null && frame.getLabel().contains(frameLabel);
                    }
                };
            } else {
                // TODO: log unknown filter
            }
        }

        // Sets case-insensitive filter
        return RowFilter.regexFilter("(?i)" + Pattern.quote(filter));
    }

    private void handleRowSelected() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }

        int id = (int) table.getValueAt(selectedRow, 0);
        String name = (String) table.getValueAt(selectedRow, 1);

        controller.selectObject(id, name);
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

    private static abstract class DisplayObjectRowFilter extends RowFilter<TableModel, Integer> {
        private final SupercellSWF swf;

		public DisplayObjectRowFilter(SupercellSWF swf) {
            this.swf = swf;
        }

        @Override
        public boolean include(Entry<? extends TableModel, ? extends Integer> entry) {
            assert entry.getValueCount() == COLUMN_NAMES.length;
            int value = (int) entry.getValue(0);

            DisplayObjectOriginal original;

            try {
                original = swf.getOriginalDisplayObject(value, null);
            } catch (UnableToFindObjectException e) {
                // NOTE: must not happen as we know the object exists
                throw new RuntimeException(e);
            }

            return include(original);
        }

        protected abstract boolean include(DisplayObjectOriginal original);
    }
}
