package dev.donutquine.editor.layout.contextmenus;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.util.function.Function;

import javax.swing.JMenuItem;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import dev.donutquine.editor.Editor;
import dev.donutquine.editor.MessageDialogs;
import dev.donutquine.editor.layout.components.Table;
import dev.donutquine.editor.layout.components.TablePopupMenuListener;
import dev.donutquine.renderer.impl.swf.objects.DisplayObject;
import dev.donutquine.renderer.impl.swf.objects.MovieClip;
import dev.donutquine.renderer.impl.swf.objects.TextField;

public class ChildrenTableContextMenu extends ContextMenu {
    private final Table table;
    private final Editor editor;
    private final JMenuItem textFieldDetailsItem;

    public ChildrenTableContextMenu(Table table, Editor editor) {
        super(table, null);

        this.table = table;
        this.editor = editor;

        this.add("Toggle visibility", event -> this.changeVisibility(child -> !child.isVisible()));
        this.add("Enable", event -> this.changeVisibility(child -> true));
        this.add("Disable", event -> this.changeVisibility(child -> false));
        this.add("Copy child name", event -> this.copyChildName());
        this.textFieldDetailsItem = this.add("Get TextField details", event -> this.getTextFieldDetails());
        this.textFieldDetailsItem.setVisible(false);

        this.popupMenu.addPopupMenuListener(new TablePopupMenuListener(this.popupMenu, table, rowIndex -> setMainComponentsEnabled(rowIndex != -1)));
        this.popupMenu.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                int row = table.getSelectedRow();

                boolean enabled =
                        row != -1 &&
                        getMovieClip()
                            .getTimelineChildren()[row]
                            .isTextField();

                textFieldDetailsItem.setVisible(enabled);
            }

            @Override public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {}
            @Override public void popupMenuCanceled(PopupMenuEvent e) {}
        });
    }

    private void getTextFieldDetails() {
        int selectedRow = this.table.getSelectedRow();
        DisplayObject selectedObject = this.getMovieClip().getTimelineChildren()[selectedRow];
        int type = -1; // -1: Unknown, 0: MovieClip, 1: TextField
        TextField textField = null;
        MovieClip movieClip = null;
        if (selectedObject.isTextField()) {
            textField = (TextField) selectedObject;
            type = 1;
        } else if (selectedObject.isMovieClip()) {
            movieClip = (MovieClip) selectedObject;
            type = 0;
        }
        switch (type) {
            case 0 -> {
                // handle MovieClip case
            }
            case 1 -> {
                MessageDialogs.showTextFieldDetailsPopup(this.editor, textField);
            }

        }
    }

    private void copyChildName() {
        int selectedRow = this.table.getSelectedRow();

        Object childName = this.table.getValueAt(selectedRow, this.table.getColumnIndexByName("Name"));
        if (childName != null) {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                new StringSelection(childName.toString()),
                null
            );
        }
    }

    public void changeVisibility(Function<DisplayObject, Boolean> visibilityFunction) {
        MovieClip movieClip = this.getMovieClip();
        if (movieClip == null) return;

        int[] selectedRows = this.table.getSelectedRows();
        for (int childIndex : selectedRows) {
            DisplayObject child = movieClip.getTimelineChildren()[childIndex];

            child.setVisibleRecursive(visibilityFunction.apply(child));

            this.table.setValueAt(child.isVisible(), childIndex, 4);
        }
    }

    private MovieClip getMovieClip() {
        DisplayObject selectedObject = editor.getSelectedObject();
        if (selectedObject.isMovieClip()) {
            return (MovieClip) selectedObject;
        }

        return null;
    }

    private TextField getTextField() {
        DisplayObject selectedObject = editor.getSelectedObject();
        if (selectedObject.isTextField()) {
            return (TextField) selectedObject;
        }

        return null;
    }
}
