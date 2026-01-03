package dev.donutquine.editor.layout.menubar;

import dev.donutquine.editor.Editor;
import dev.donutquine.editor.layout.menubar.menus.*;

import javax.swing.*;

public class EditorMenuBar extends JMenuBar {
    private final FileMenu fileMenu;
    private final EditMenu editMenu;
    private final ViewMenu viewMenu;
    private final OptionsMenu optionsMenu;
    private final HelpMenu helpMenu;
    private final ModMenu modMenu;

    public EditorMenuBar(Editor editor) {
        JFrame frame = editor.getWindow().getFrame();

        this.fileMenu = new FileMenu(frame, editor);
        this.editMenu = new EditMenu(editor);
        this.viewMenu = new ViewMenu(editor.getWindow());
        this.optionsMenu = new OptionsMenu(editor);
        this.helpMenu = new HelpMenu(frame);
        this.modMenu = new ModMenu(editor);

        this.add(this.fileMenu);
        this.add(this.editMenu);
        this.add(this.viewMenu);
        this.add(this.optionsMenu);
        this.add(this.helpMenu);
        this.add(this.modMenu);
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
