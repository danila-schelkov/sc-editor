package dev.donutquine.editor.gui.layout.panels;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import dev.donutquine.editor.gui.layout.components.tables.Table;

public class FilterableTablePanel<T extends TableModel> extends JPanel {
    protected final T tableModel;
    protected final TableRowSorter<T> sorter;
    protected final Table table;

    protected final JTextField filterField;

    protected final Map<String, Function<String, RowFilter<T, Integer>>> filters = new HashMap<>();

    public FilterableTablePanel(T tableModel) {
        this.tableModel = tableModel;
        this.sorter = new TableRowSorter<>(tableModel);

        this.table = new Table(tableModel);
        this.table.setRowSorter(this.sorter);

        this.filterField = new JTextField();

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        panel.add(this.filterField);

        this.filterField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filter(filterField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filter(filterField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {}
        });

        setLayout(new BorderLayout());
        this.add(panel, BorderLayout.SOUTH);
        this.add(new JScrollPane(this.table), BorderLayout.CENTER);
    }

    public void resetFilter() {
        this.sorter.setRowFilter(null);
        this.filterField.setText(null);
    }

    public void setFocusOnTextField() {
        this.filterField.requestFocus();
    }

    // TODO: make ui for filters
    protected void filter(String text) {
        // TODO: grouping and priority for & (and) and | (or) filters
        List<RowFilter<T, Integer>> orFilters = new ArrayList<>();
        for (String orFilter : text.split("\\|")) { 
            List<RowFilter<T, Integer>> andFilters = new ArrayList<>();
            for (String filter : orFilter.split("&")) { 
                andFilters.add(createFilterFromString(filter.trim()));
            }
            orFilters.add(RowFilter.andFilter(andFilters));
        }

        if (orFilters.isEmpty()) {
            this.resetFilter();
            return;
        }

        this.sorter.setRowFilter(RowFilter.orFilter(orFilters));
    }

    protected RowFilter<T, Integer> createFilterFromString(String filter) {
        if (filter.startsWith("!")) {
            return RowFilter.notFilter(createFilterFromString(filter.substring(1).trim()));
        }

        String[] substring = filter.split("=");
        if (substring.length == 2) {
            String filterName = substring[0].trim();
            String filterValue = substring[1].trim();

            Function<String, RowFilter<T, Integer>> createFilter = this.filters.get(filterName);
            if (createFilter != null) {
                return createFilter.apply(filterValue);
            }
        }

        // Sets case-insensitive filter
        return RowFilter.regexFilter("(?i)" + Pattern.quote(filter));
    }
}
