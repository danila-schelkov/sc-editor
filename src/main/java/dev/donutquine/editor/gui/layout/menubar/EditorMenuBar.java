package dev.donutquine.editor.gui.layout.menubar;

import javax.swing.JMenuBar;
import dev.donutquine.editor.SystemInfo;
import dev.donutquine.editor.gui.layout.menubar.menus.EditMenu;
import dev.donutquine.editor.gui.layout.menubar.menus.FileMenu;
import dev.donutquine.editor.gui.layout.menubar.menus.HelpMenu;
import dev.donutquine.editor.gui.layout.menubar.menus.OptionsMenu;
import dev.donutquine.editor.gui.layout.menubar.menus.ViewMenu;
import dev.donutquine.editor.gui.layout.menubar.menus.AppearanceMenu;
import dev.donutquine.editor.gui.layout.windows.EditorWindow;

public class EditorMenuBar extends JMenuBar {
    public final FileMenu fileMenu;
    public final EditMenu editMenu;
    public final ViewMenu viewMenu;
    public final OptionsMenu optionsMenu;
    public final HelpMenu helpMenu;
	public final AppearanceMenu appearanceMenu;

    public EditorMenuBar(EditorWindow window) {
        this.fileMenu = new FileMenu(window);
        this.editMenu = new EditMenu(window);
        this.viewMenu = new ViewMenu(window);
        this.optionsMenu = new OptionsMenu(window);
        this.helpMenu = new HelpMenu(window.getFrame());
        this.appearanceMenu = new AppearanceMenu(window);

        this.add(this.fileMenu);
        this.add(this.editMenu);
        this.add(this.viewMenu);
        this.add(this.optionsMenu);
        this.add(this.helpMenu);
        this.add(this.appearanceMenu);

        if (SystemInfo.IS_MAC) {
            // TODO: hide future preferences open dialog button and replace it with
            // if( desktop.isSupported( Desktop.Action.APP_PREFERENCES ) ) {
            //     desktop.setPreferencesHandler( e -> {
            //         // show preferences dialog
            //     } );
            // }
            //
        }
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
