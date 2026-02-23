package dev.donutquine.editor.layout.menubar;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

import dev.donutquine.editor.Editor;
import dev.donutquine.editor.layout.menubar.menus.DarkModeButton;
import dev.donutquine.editor.layout.menubar.menus.EditMenu;
import dev.donutquine.editor.layout.menubar.menus.FileMenu;
import dev.donutquine.editor.layout.menubar.menus.HelpMenu;
import dev.donutquine.editor.layout.menubar.menus.OptionsMenu;
import dev.donutquine.editor.layout.menubar.menus.ViewMenu;

public class EditorMenuBar extends JMenuBar {
    private final FileMenu fileMenu;
    private final EditMenu editMenu;
    private final ViewMenu viewMenu;
    private final OptionsMenu optionsMenu;
    private final HelpMenu helpMenu;
    private final DarkModeButton darkMode;

    public EditorMenuBar(Editor editor) {
        JFrame frame = editor.getWindow().getFrame();

        this.fileMenu = new FileMenu(frame, editor);
        this.editMenu = new EditMenu(editor);
        this.viewMenu = new ViewMenu(editor.getWindow());
        this.optionsMenu = new OptionsMenu(editor);
        this.helpMenu = new HelpMenu(frame);
        this.darkMode = new DarkModeButton(editor);

        this.add(this.fileMenu);
        this.add(this.editMenu);
        this.add(this.viewMenu);
        this.add(this.optionsMenu);
        this.add(this.helpMenu);

        this.add(Box.createHorizontalGlue());
        this.add(this.darkMode);
    }

    public FileMenu getFileMenu() {
        return fileMenu;
    }

    public EditMenu getEditMenu() {
        return editMenu;
    }

    public ViewMenu getViewMenu() {
        return viewMenu;
    }

    public OptionsMenu getOptionsMenu() {
        return optionsMenu;
    }

    public DarkModeButton getDarkModeMenu() {
        return darkMode;
    }

    @Override
    public HelpMenu getHelpMenu() {
        return helpMenu;
    }
}
