package dev.donutquine.editor.gui.layout.menubar.menus;

import java.awt.event.KeyEvent;
import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;
import dev.donutquine.editor.gui.layout.windows.EditorWindow;
import dev.donutquine.editor.gui.settings.EditorPreferences;
import dev.donutquine.editor.gui.theme.ThemeManager;
import dev.donutquine.editor.gui.theme.ThemeMode;

public class AppearanceMenu extends JMenu {
    public AppearanceMenu(EditorWindow window) {
        super("Appearance");

        this.setMnemonic(KeyEvent.VK_A);

        ButtonGroup themeGroup = new ButtonGroup();

        EditorPreferences preferences = window.getEditor().getPreferences();
        ThemeMode currentTheme = preferences.getTheme();

        for (ThemeMode themeMode : ThemeMode.values()) {
            String themeModeName = themeMode.name();

            JRadioButtonMenuItem themeMenuItem = new JRadioButtonMenuItem(capitalize(themeModeName), currentTheme == themeMode);
            themeMenuItem.addActionListener((e) -> {
                preferences.setTheme(themeMode);
                // TODO: System theme
                ThemeManager.setThemeAnimated(themeMode == ThemeMode.DARK);
            });

            themeGroup.add(themeMenuItem);
            this.add(themeMenuItem);
        }
    }

    private static String capitalize(String string) {
        return string.substring(0, 1) + string.substring(1).toLowerCase();
    }
}
