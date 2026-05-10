package dev.donutquine.editor.layout.menubar.menus;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import dev.donutquine.editor.assets.AssetFile;
import dev.donutquine.editor.gizmos.Gizmos;
import dev.donutquine.editor.layout.SearchableLayoutController;
import dev.donutquine.editor.layout.shortcut.KeyboardUtils;
import dev.donutquine.editor.layout.windows.EditorWindow;
import dev.donutquine.editor.navigation.NavigableAsset;
import dev.donutquine.editor.navigation.NavigationHistory;
import dev.donutquine.editor.renderer.impl.EditorStage;

public class EditMenu extends JMenu {
    private final EditorWindow window;

    private final JMenuItem previous;
    private final JMenuItem next;

    public EditMenu(EditorWindow window) {
        super("Edit");

        this.window = window;

        setMnemonic(KeyEvent.VK_E);

        JMenuItem find = new JMenuItem("Find", KeyEvent.VK_F);
        find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyboardUtils.ctrlButton()));

        this.previous = new JMenuItem("Previous", KeyEvent.VK_P);
        this.previous.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, KeyboardUtils.ctrlButton() | InputEvent.ALT_DOWN_MASK));
        this.next = new JMenuItem("Next", KeyEvent.VK_N);
        this.next.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, KeyboardUtils.ctrlButton() | InputEvent.ALT_DOWN_MASK));

        find.addActionListener(this::focusFind);
        this.previous.addActionListener(this::previous);
        this.next.addActionListener(this::next);

        this.previous.setEnabled(false);
        this.next.setEnabled(false);

        JMenuItem undo = new JMenuItem("Undo", KeyEvent.VK_U);
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyboardUtils.ctrlButton()));
        JMenuItem redo = new JMenuItem("Redo", KeyEvent.VK_R);
        redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyboardUtils.ctrlButton() | InputEvent.SHIFT_DOWN_MASK));

        undo.addActionListener(this::undo);
        redo.addActionListener(this::redo);

        undo.setEnabled(false);
        redo.setEnabled(false);

        Gizmos gizmos = EditorStage.getInstance().getGizmos();
        gizmos.addUndoableListener(undo::setEnabled);
        gizmos.addRedoableListener(redo::setEnabled);

        this.add(find);
        this.addSeparator();
        this.add(undo);
        this.add(redo);
        this.addSeparator();
        this.add(this.previous);
        this.add(this.next);
    }

    public void updateNavigationButtons(NavigationHistory<?> navigationHistory) {
        this.previous.setEnabled(navigationHistory != null && navigationHistory.hasPrevious());
        this.next.setEnabled(navigationHistory != null && navigationHistory.hasNext());
    }

    private void focusFind(ActionEvent actionEvent) {
        if (this.window.getLayoutController() instanceof SearchableLayoutController searchableLayoutController) {
            searchableLayoutController.focusOnSearchField();
        }
    }

    private void previous(ActionEvent e) {
        AssetFile<?> activeFile = window.getEditor().getAssetFileManager().getActiveFile();
        if (activeFile instanceof NavigableAsset<?, ?> navigableAsset) {
            navigableAsset.getNavigationHistory().navigateToPrevious();
        } else {
            assert false : "The previous button must be disabled if navigation to previous is not available";
        }
    }

    private void next(ActionEvent e) {
        AssetFile<?> activeFile = window.getEditor().getAssetFileManager().getActiveFile();
        if (activeFile instanceof NavigableAsset<?, ?> navigableAsset) {
            navigableAsset.getNavigationHistory().navigateToNext();
        } else {
            assert false : "The next button must be disabled if navigation to next is not available";
        }
    }

    private void undo(ActionEvent e) {
        Gizmos gizmos = EditorStage.getInstance().getGizmos();
        gizmos.undo();
    }

    private void redo(ActionEvent e) {
        Gizmos gizmos = EditorStage.getInstance().getGizmos();
        gizmos.redo();
    }
}
