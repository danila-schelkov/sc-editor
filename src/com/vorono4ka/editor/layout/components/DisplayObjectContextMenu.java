package com.vorono4ka.editor.layout.components;

import com.vorono4ka.editor.Main;
import com.vorono4ka.streams.ByteStream;
import com.vorono4ka.swf.SupercellSWF;
import com.vorono4ka.swf.exceptions.UnableToFindObjectException;
import com.vorono4ka.swf.originalObjects.DisplayObjectOriginal;
import com.vorono4ka.utilities.ByteArrayFlavor;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class DisplayObjectContextMenu extends ContextMenu {
    public static final Clipboard SYSTEM_CLIPBOARD = Toolkit.getDefaultToolkit().getSystemClipboard();
    private final Table table;

    public DisplayObjectContextMenu(Table table) {
        super(table, null);

        this.table = table;

        JMenuItem copyExportNameButton = this.add("Copy Export Name", KeyEvent.VK_E);
        copyExportNameButton.addActionListener(this::copyExportName);

        JMenu copyAsMenu = this.addMenu("Copy as...", KeyEvent.VK_A);

        JMenuItem copyAsBytesButton = this.add(copyAsMenu, "Copy as bytes", KeyEvent.VK_B);
        copyAsBytesButton.addActionListener(this::copyAsBytes);

        this.addSeparator();

        JMenuItem findUsagesButton = this.add("Find Usages", KeyEvent.VK_U);
        findUsagesButton.addActionListener(this::findUsages);

        this.addSeparator();

        this.add("Properties");

        this.popupMenu.addPopupMenuListener(new TablePopupMenuListener(this.popupMenu, table));
    }

    private void findUsages(ActionEvent event) {
        int selectedRow = this.table.getSelectedRow();
        if (selectedRow == -1) return;

        int displayObjectId = getDisplayObjectId(selectedRow);
        String displayObjectName = getDisplayObjectName(selectedRow);

        Main.editor.findUsages(displayObjectId, displayObjectName);
    }

    private void copyExportName(ActionEvent event) {
        int selectedRow = this.table.getSelectedRow();
        if (selectedRow == -1) return;

        String displayObjectName = getDisplayObjectName(selectedRow);

        SYSTEM_CLIPBOARD.setContents(
            new StringSelection(displayObjectName),
            null
        );
    }

    private void copyAsBytes(ActionEvent event) {
        int selectedRow = this.table.getSelectedRow();
        if (selectedRow == -1) return;

        SupercellSWF swf = Main.editor.getSwf();
        if (swf == null) return;

        int displayObjectId = getDisplayObjectId(selectedRow);
        try {
            DisplayObjectOriginal originalDisplayObject = swf.getOriginalDisplayObject(displayObjectId, null);
            ByteStream stream = new ByteStream();
            originalDisplayObject.save(stream);

            SYSTEM_CLIPBOARD.setContents(
                new ByteArrayFlavor(stream.getData()),
                null
            );
        } catch (UnableToFindObjectException e) {
            throw new RuntimeException(e);
            // TODO: add error window
        }
    }

    private String getDisplayObjectName(int selectedRow) {
        return (String) this.table.getValueAt(selectedRow, 1);
    }

    private int getDisplayObjectId(int selectedRow) {
        return (int) this.table.getValueAt(selectedRow, 0);
    }
}
