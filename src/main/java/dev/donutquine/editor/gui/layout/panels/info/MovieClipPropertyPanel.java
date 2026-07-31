package dev.donutquine.editor.gui.layout.panels.info;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.function.IntConsumer;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DropMode;
import javax.swing.InputMap;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dev.donutquine.editor.gui.layout.SupercellSWFLayoutController;
import dev.donutquine.editor.gui.layout.components.listeners.ChildrenListMouseListener;
import dev.donutquine.editor.gui.layout.components.listeners.FrameSelectionListener;
import dev.donutquine.editor.gui.layout.components.tables.MovieClipChildrenTableModel;
import dev.donutquine.editor.gui.layout.components.tables.MovieClipFrameElementsTableModel;
import dev.donutquine.editor.gui.layout.components.tables.MovieClipFramesTableModel;
import dev.donutquine.editor.gui.layout.components.tables.RowReorderTransferHandler;
import dev.donutquine.editor.gui.layout.components.tables.Table;
import dev.donutquine.editor.gui.layout.contextmenus.ChildrenTableContextMenu;
import dev.donutquine.editor.gui.layout.contextmenus.FrameElementTableContextMenu;
import dev.donutquine.editor.gui.layout.contextmenus.FrameTableContextMenu;
import dev.donutquine.editor.gui.layout.shortcut.KeyboardUtils;
import dev.donutquine.renderer.impl.swf.objects.MovieClip;
import dev.donutquine.renderer.impl.swf.objects.TextField;
import dev.donutquine.swf.ScMatrixBank;
import dev.donutquine.swf.movieclips.MovieClipFrame;

public class MovieClipPropertyPanel extends JPanel {
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieClipPropertyPanel.class);

    private static final String DUPLICATE_ACTION_KEY = "duplicate";
    private static final String DELETE_ACTION_KEY = "delete";

    private final JTable timelineChildrenTable;
    private final JTable framesTable;
    private final JTable frameElementsTable;
    private final JPanel textInfoPanel;

    public MovieClipPropertyPanel(SupercellSWFLayoutController swfLayoutController, MovieClip movieClip) {
        this.setLayout(new GridLayout(0, 1));

        MovieClipChildrenTableModel childrenTableModel = new MovieClipChildrenTableModel(movieClip);
        this.timelineChildrenTable = createTimelineChildrenTable(childrenTableModel, swfLayoutController);

        List<MovieClipFrame> frames = movieClip.getFrames();
        // TODO: handle empty movie clips properly (is it even a valid state?)
        assert !frames.isEmpty();

        ScMatrixBank matrixBank = movieClip.getMatrixBank();
        MovieClipFrameElementsTableModel frameElementsTableModel = new MovieClipFrameElementsTableModel(
            frames.get(0), 
            () -> {
                movieClip.forceSetFrame(movieClip.getCurrentFrame());
            }, 
            movieClip::getTimelineChildCount, 
            matrixBank::getMatrixCount, 
            matrixBank::getColorTransformCount,
            (row) -> {
                // NOTE: cannot inline to use field as its not initialized at the variable capture moment, so using getter
                JTable frameElementsTable = this.getFrameElementsTable();
                frameElementsTable.editCellAt(row, MovieClipFrameElementsTableModel.COLUMN_CHILD_INDEX);
            }
        );

        this.frameElementsTable = createFrameElementsTable(frameElementsTableModel);

        IntConsumer elementsCurrentFrameSetter = (index) -> {
            frameElementsTableModel.setFrame(frames.get(index));
        };
        MovieClipFramesTableModel framesTableModel = new MovieClipFramesTableModel(frames, (index) -> {
            elementsCurrentFrameSetter.accept(index);
            movieClip.forceSetFrame(index);
        });

        this.framesTable = createFramesTable(framesTableModel, swfLayoutController, elementsCurrentFrameSetter);

        this.textInfoPanel = new JPanel();

        this.setTextInfo(
            "Export name: " + movieClip.getExportName(),
            "FPS: " + movieClip.getFps(),
            String.format("Duration: %.2fs", movieClip.getDuration())
        );

        this.textInfoPanel.setLayout(new BoxLayout(this.textInfoPanel, BoxLayout.Y_AXIS));

        JCheckBox showTextFieldsCheckbox = new JCheckBox("Show TextField placeholders", TextField.showPlaceholders);
        showTextFieldsCheckbox.addActionListener(e -> TextField.showPlaceholders = showTextFieldsCheckbox.isSelected());
        this.textInfoPanel.add(showTextFieldsCheckbox);

        this.add(new JScrollPane(this.timelineChildrenTable), "Children");
        this.add(new JScrollPane(this.framesTable), "Frames");
        this.add(new JScrollPane(this.frameElementsTable), "Frame elements");
        this.add(this.textInfoPanel, "Info");
    }

    private static Table createFrameElementsTable(MovieClipFrameElementsTableModel tableModel) {
        Table table = new Table(tableModel);
        table.setDragEnabled(true);
        table.setDropMode(DropMode.INSERT_ROWS);
        table.setTransferHandler(new RowReorderTransferHandler(table, tableModel));
        table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        AbstractAction deleteAction = new AbstractAction("Delete") {
            @Override
            public void actionPerformed(ActionEvent event) {
                int firstIndex = table.getSelectedRow();
                int elementCount = table.getSelectedRowCount();
                if (elementCount < 1) return;

                try {
                    tableModel.delete(firstIndex, elementCount);
                } catch (IllegalArgumentException e) {
                    LOGGER.warn(e.getLocalizedMessage());
                }

                // NOTE: Should we actually reset selection?
                table.clearSelection();
            }
        };

        AbstractAction insertBeforeAction = new AbstractAction("Insert new element before") {
            @Override
            public void actionPerformed(ActionEvent event) {
                int elementCount = table.getSelectedRowCount();
                if (elementCount < 1) return;

                int firstIndex = table.getSelectedRows()[0];

                // NOTE: Maybe we should ask a label for frame? I guess no, it's easier to add label later.
                try {
                    tableModel.insertPendingRow(firstIndex);
                } catch (IllegalArgumentException e) {
                    LOGGER.warn(e.getLocalizedMessage());
                }

                // NOTE: Should we actually reset selection?
                table.clearSelection();
            }
        };

        AbstractAction insertAfterAction = new AbstractAction("Insert new element after") {
            @Override
            public void actionPerformed(ActionEvent event) {
                int elementCount = table.getSelectedRowCount();
                if (elementCount < 1) return;

                int lastIndex = table.getSelectedRows()[elementCount - 1];

                // NOTE: Maybe we should ask a label for frame? I guess no, it's easier to add label later.
                try {
                    tableModel.insertPendingRow(lastIndex + 1);
                } catch (IllegalArgumentException e) {
                    LOGGER.warn(e.getLocalizedMessage());
                }

                // NOTE: Should we actually reset selection?
                table.clearSelection();
            }
        };

        InputMap inputMap = table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        ActionMap actionMap = table.getActionMap();

        // NOTE: is it okay to use different keystrokes on different systems? I guess so.
        KeyStroke keyStroke = KeyboardUtils.delete();
        deleteAction.putValue(Action.ACCELERATOR_KEY, keyStroke);
        actionMap.put(DELETE_ACTION_KEY, deleteAction);
        inputMap.put(keyStroke, DELETE_ACTION_KEY);

        new FrameElementTableContextMenu(table, deleteAction, insertBeforeAction, insertAfterAction);

        return table;
    }

    private static Table createFramesTable(MovieClipFramesTableModel tableModel, SupercellSWFLayoutController swfLayoutController, IntConsumer elementsCurrentFrameSetter) {
        Table table = new Table(tableModel);
        table.setDragEnabled(true);
        table.setDropMode(DropMode.INSERT_ROWS);
        table.setTransferHandler(new RowReorderTransferHandler(table, tableModel));
        table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        AbstractAction duplicateAction = new AbstractAction("Duplicate") {
            @Override
            public void actionPerformed(ActionEvent event) {
                int firstIndex = table.getSelectedRow();
                int elementCount = table.getSelectedRowCount();
                if (elementCount < 1) return;

                try {
                    tableModel.duplicate(firstIndex, elementCount);
                } catch (IllegalArgumentException e) {
                    LOGGER.warn(e.getLocalizedMessage());
                }

                // NOTE: Should we actually reset selection?
                table.clearSelection();
            }
        };

        AbstractAction deleteAction = new AbstractAction("Delete") {
            @Override
            public void actionPerformed(ActionEvent event) {
                int firstIndex = table.getSelectedRow();
                int elementCount = table.getSelectedRowCount();
                if (elementCount < 1) return;

                try {
                    tableModel.delete(firstIndex, elementCount);
                } catch (IllegalArgumentException e) {
                    LOGGER.warn(e.getLocalizedMessage());
                }

                // NOTE: Should we actually reset selection?
                table.clearSelection();
            }
        };

        AbstractAction insertBeforeAction = new AbstractAction("Insert new frame before") {
            @Override
            public void actionPerformed(ActionEvent event) {
                int elementCount = table.getSelectedRowCount();
                if (elementCount < 1) return;

                int firstIndex = table.getSelectedRows()[0];

                // NOTE: Maybe we should ask a label for frame? I guess no, it's easier to add label later.
                try {
                    tableModel.insert(firstIndex, MovieClipFrame.builder().build());
                } catch (IllegalArgumentException e) {
                    LOGGER.warn(e.getLocalizedMessage());
                }

                // NOTE: Should we actually reset selection?
                table.clearSelection();
            }
        };

        AbstractAction insertAfterAction = new AbstractAction("Insert new frame after") {
            @Override
            public void actionPerformed(ActionEvent event) {
                int elementCount = table.getSelectedRowCount();
                if (elementCount < 1) return;

                int lastIndex = table.getSelectedRows()[elementCount - 1];

                // NOTE: Maybe we should ask a label for frame? I guess no, it's easier to add label later.
                try {
                    tableModel.insert(lastIndex + 1, MovieClipFrame.builder().build());
                } catch (IllegalArgumentException e) {
                    LOGGER.warn(e.getLocalizedMessage());
                }

                // NOTE: Should we actually reset selection?
                table.clearSelection();
            }
        };

        InputMap inputMap = table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        ActionMap actionMap = table.getActionMap();

        KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyboardUtils.ctrlButton());
        duplicateAction.putValue(Action.ACCELERATOR_KEY, keyStroke);
        actionMap.put(DUPLICATE_ACTION_KEY, duplicateAction);
        inputMap.put(keyStroke, DUPLICATE_ACTION_KEY);

        // NOTE: is it okay to use different keystrokes on different systems? I guess so.
        keyStroke = KeyboardUtils.delete();
        deleteAction.putValue(Action.ACCELERATOR_KEY, keyStroke);
        actionMap.put(DELETE_ACTION_KEY, deleteAction);
        inputMap.put(keyStroke, DELETE_ACTION_KEY);

        table.addSelectionListener(new FrameSelectionListener(table, elementsCurrentFrameSetter));

        new FrameTableContextMenu(table, swfLayoutController, duplicateAction, insertBeforeAction, insertAfterAction, deleteAction);

        return table;
    }

    private static Table createTimelineChildrenTable(MovieClipChildrenTableModel tableModel, SupercellSWFLayoutController swfLayoutController) {
        Table table = new Table(tableModel);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.addMouseListener(new ChildrenListMouseListener(table, swfLayoutController));

        AbstractAction duplicateAction = new AbstractAction("Duplicate") {
            @Override
            public void actionPerformed(ActionEvent event) {
                int[] selectedRows = table.getSelectedRows();
                if (selectedRows.length < 1) return;

                try {
                    tableModel.duplicate(selectedRows);
                } catch (IllegalArgumentException e) {
                    LOGGER.warn(e.getLocalizedMessage());
                }

                // NOTE: Should we actually reset selection?
                table.clearSelection();
            }
        };

        InputMap inputMap = table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        ActionMap actionMap = table.getActionMap();

        KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyboardUtils.ctrlButton());
        duplicateAction.putValue(Action.ACCELERATOR_KEY, keyStroke);
        actionMap.put(DUPLICATE_ACTION_KEY, duplicateAction);
        inputMap.put(keyStroke, DUPLICATE_ACTION_KEY);

        new ChildrenTableContextMenu(table, duplicateAction);

        return table;
    }

    public void setTextInfo(String... lines) {
        this.textInfoPanel.removeAll();
        for (String line : lines) {
            this.addTextLine(line);
        }
    }

    public void addTextLine(String line) {
        this.textInfoPanel.add(new JLabel(line));
    }

    public Component add(JComponent comp, String title) {
        comp.setBorder(BorderFactory.createTitledBorder(title));
        return super.add(comp);
    }

    private JTable getFrameElementsTable() {
        return this.frameElementsTable;
    }
}
