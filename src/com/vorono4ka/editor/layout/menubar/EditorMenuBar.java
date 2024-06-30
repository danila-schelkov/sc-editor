package com.vorono4ka.editor.layout.menubar;

import com.vorono4ka.editor.layout.windows.EditorWindow;
import com.vorono4ka.editor.layout.menubar.menus.*;

import javax.swing.*;

public class EditorMenuBar extends JMenuBar {
    private final FileMenu fileMenu;
    private final EditMenu editMenu;
    private final ViewMenu viewMenu;
    private final OptionsMenu optionsMenu;
    private final HelpMenu helpMenu;

    public EditorMenuBar(EditorWindow window) {
        JFrame frame = window.getFrame();

        this.fileMenu = new FileMenu(frame);
        this.editMenu = new EditMenu();
        this.viewMenu = new ViewMenu();
        this.optionsMenu = new OptionsMenu();
        this.helpMenu = new HelpMenu(frame);

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
