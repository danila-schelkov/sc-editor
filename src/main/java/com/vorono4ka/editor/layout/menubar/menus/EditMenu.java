package com.vorono4ka.editor.layout.menubar.menus;

import com.vorono4ka.editor.Editor;
import com.vorono4ka.editor.layout.components.Table;
import com.vorono4ka.editor.layout.shortcut.KeyboardUtils;
import com.vorono4ka.editor.layout.windows.EditorWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class EditMenu extends JMenu {
    private final Editor editor;

    private final JMenuItem previous;
    private final JMenuItem next;

    public EditMenu(Editor editor) {
        super("Edit");

        this.editor = editor;

        setMnemonic(KeyEvent.VK_E);

        JMenuItem find = new JMenuItem("Find", KeyEvent.VK_F);
        find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyboardUtils.ctrlButton()));

        JMenuItem findUsages = new JMenuItem("Find Usages");
        findUsages.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyboardUtils.ctrlButton()));

        this.previous = new JMenuItem("Previous", KeyEvent.VK_P);
        this.previous.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, KeyboardUtils.ctrlButton() | InputEvent.ALT_DOWN_MASK));
        this.next = new JMenuItem("Next", KeyEvent.VK_N);
        this.next.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, KeyboardUtils.ctrlButton() | InputEvent.ALT_DOWN_MASK));

        find.addActionListener(this::find);
        findUsages.addActionListener(this::findUsages);
        this.previous.addActionListener(this::previous);
        this.next.addActionListener(this::next);

        this.previous.setEnabled(false);
        this.next.setEnabled(false);

        this.add(find);
        this.add(findUsages);
        this.addSeparator();
        this.add(this.previous);
        this.add(this.next);
    }

    private void find(ActionEvent actionEvent) {
        EditorWindow window = editor.getWindow();
        window.getTabbedPane().setSelectedIndex(0);
        window.getDisplayObjectPanel().setFocusOnTextField();
    }

    private void findUsages(ActionEvent event) {
        Table objectsTable = editor.getWindow().getObjectsTable();
        int selectedRow = objectsTable.getSelectedRow();
        if (selectedRow == -1) return;

        int displayObjectId = (int) objectsTable.getValueAt(selectedRow, 0);
        String displayObjectName = (String) objectsTable.getValueAt(selectedRow, 1);

        editor.findUsages(displayObjectId, displayObjectName);
    }

    private void previous(ActionEvent e) {
        editor.selectPrevious();
    }

    private void next(ActionEvent e) {
        editor.selectNext();
    }

    public void checkPreviousAvailable() {
        this.previous.setEnabled(editor.getSelectedIndex() > 0);
    }

    public void checkNextAvailable() {
        this.next.setEnabled(editor.getSelectedIndex() + 1 < editor.getClonedObjectCount());
    }
}
