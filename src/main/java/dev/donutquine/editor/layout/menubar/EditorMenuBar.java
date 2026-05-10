package dev.donutquine.editor.layout.menubar;

import javax.swing.JMenuBar;
import dev.donutquine.editor.layout.menubar.menus.EditMenu;
import dev.donutquine.editor.layout.menubar.menus.FileMenu;
import dev.donutquine.editor.layout.menubar.menus.HelpMenu;
import dev.donutquine.editor.layout.menubar.menus.OptionsMenu;
import dev.donutquine.editor.layout.menubar.menus.ViewMenu;
import dev.donutquine.editor.layout.windows.EditorWindow;

public class EditorMenuBar extends JMenuBar {
    private final FileMenu fileMenu;
    private final EditMenu editMenu;
    private final ViewMenu viewMenu;
    private final OptionsMenu optionsMenu;
    private final HelpMenu helpMenu;

    public EditorMenuBar(EditorWindow window) {
        this.fileMenu = new FileMenu(window);
        this.editMenu = new EditMenu(window);
        this.viewMenu = new ViewMenu(window);
        this.optionsMenu = new OptionsMenu(window);
        this.helpMenu = new HelpMenu(window.getFrame());

        this.add(this.fileMenu);
        this.add(this.editMenu);
        this.add(this.viewMenu);
        this.add(this.optionsMenu);
        this.add(this.helpMenu);
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

    @Override
    public HelpMenu getHelpMenu() {
        return helpMenu;
    }
}
